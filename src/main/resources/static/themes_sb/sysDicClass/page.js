/*<![CDATA[*/ 
$(document).ready(function() {
	$("#listPage").load("./pageList?pageNum=1&pageSize=5&classCode=--");
});
//分页
function turnTo(flagPage){
		 var pagecurrentnum = $("#pagecurrentnum").val();
		 var pagecount = $("#pagecount").val();
		 var turnToPage = $("#page").val();
		 if(flagPage == "prev"){
			 if(pagecurrentnum == 1){
					alert("已经是第一页");
					return ;
			 }
			 pagecurrentnum = parseInt(pagecurrentnum) -1;
		 }else if(flagPage == "next"){
			 if(parseInt(pagecurrentnum) >= parseInt(pagecount)){
					alert("已经是最后一页");
					return ;
			 }
			 pagecurrentnum = parseInt(pagecurrentnum) + 1;
		 }else if(flagPage == "first"){
			 if(pagecurrentnum == 1){
					alert("已经是第一页");
					return ;
			 }
			 pagecurrentnum = 1;
		 }else if(flagPage == "last"){
			 if(parseInt(pagecurrentnum) >= parseInt(pagecount)){
					alert("已经是最后一页");
					return ;
			 }
			 pagecurrentnum = pagecount;
		 }else{
			 if(turnToPage == ""){
				alert("请输入要跳转页面的页数");
				$("#page").focus();
				return;
			 }
			 /*var arr1 = new RegExp("^[0-9]*$");
			 if(!arr1.exec(turnToPage)){
				 alert("输入内容非法，请输入正确数字");
				 $("#page").val("");
				 $("#page").focus();
				 return;
			 }*/
			 if(isNaN(turnToPage)){
				 alert("输入内容非法，请输入正确数字");
				 $("#page").val("");
				 $("#page").focus();
			 }
			 if(parseInt(turnToPage) > parseInt(pagecount) || 1 > parseInt(turnToPage)){
				 alert("输入页数超出范围，请出新输入");
				 $("#page").val("");
				 $("#page").focus();
				 return;
			 }
			 pagecurrentnum = turnToPage;
		 }
		 $("#pagecurrentnum").val(pagecurrentnum);
		 var para=$("#para").val()+"&pageNum="+pagecurrentnum+"&pageSize="+$("#pagesize").val();
		 ajaxList(para);
	}
//模糊、分页查询
function ajaxList(para){
	$.ajax({
        type: "post",
        url: "./pageList",
        data: para,
        dataType: "html",
        success: function (data) {
        	//location.reload(true);
        	$("#listPage").html(data);
        },
        error: function (data) {
            alert("异步提交表单错误.");
        }
    });
}
//大类、子类修改/保存
function baocun(){
	if(check()){
		var para=$("#para").val();
		alert("确定提交？");
		//验证通过
		$.ajax({
            type: "post",
            url: "./saveClass.json",
            data: $("#addsForm").serialize(),
            success: function (data) {
            	alert("保存成功");
            	$("#myModal").modal('hide');
            	//location.reload(true);
            	$("#listPage").load("./pageList?pageNum=1&pageSize=5&"+para);
            },
            error: function (data) {
                alert("异步提交表单错误.");
            }
        });
	}
}
//保存验证
function check(){
	if($("#dicCode1").val()==""){
		alert("字典代码不能为空");
		return false;
	}/*else if($("#dicCode1").length>10){
		alert("字典代码输入长度不能大于255个字符");
		return false;
	}*/
	if($("#dicName1").val()==""){
		alert("字典名称不能为空");
		return false;
	}
	if($("#dicValue1").val()==""){
		alert("字典值不能为空");
		return false;
	}
	return true;
}
//“删除”按扭
function deleteDic(ele){
	var dicId=$(ele).siblings("input").eq(0).val();
	var para=$("#para").val();
	$.ajax({
        type: "post",
        url: "./deleteDic.json",
        data: "dicId="+dicId,
        success: function (data) {
        	alert("删除成功");
        	//location.reload(true);
        	$("#listPage").load("./pageList?pageNum=1&pageSize=5&"+para);
        },
        error: function (data) {
            alert("异步提交表单错误.");
        }
    });
}
//“添加子类”按扭
function changeClassCode(ele){
	clearField();
	$("#classCode1").val($(ele).parents("tr").find("a").eq(0).text());
	$("#className1").val($(ele).parents("tr").find("td").eq(1).text());
}
//“字典代码”链接
function editOne(ele){
	clearField();
	$("#dicCode1").val($(ele).text());
	$("#dicName1").val($(ele).parents("tr").find("td").eq(1).text());
	$("#dicValue1").val($(ele).parents("tr").find("td").eq(2).text());
	$("#classCode1").val($(ele).parents("tr").find("td").eq(3).text());
	$("#className1").val($(ele).parents("tr").find("td").eq(4).text());
	$("#dicId1").val($(ele).parents("tr").find("input").eq(0).val());
	$("#dicMemo1").text($(ele).parents("tr").find("input").eq(1).val());
}
//“查询”按扭
function searchDic(){
	var para=$("#filter_form").serialize()+"&pageNum="+1+"&pageSize="+5;
	ajaxList(para);
}
//“查看子类”按扭
function displayChildDic(ele){
	var classCode=$(ele).parents("tr").find("a").eq(0).text();
	var para="classCode="+classCode+"&pageNum="+1+"&pageSize="+5;
	ajaxList(para);
}
//新增大类
function newClass(){
	clearField();
	$("#classCode1").val("--");
	$("#className1").val("--");
}
//清空弹窗字段值
function clearField(){
	$("#dicCode1").val("");
	$("#dicName1").val("");
	$("#dicValue1").val("");
	$("#classCode1").val("");
	$("#className1").val("");
	$("#dicId1").val("");
	$("#dicMemo1").text("");
}
//页面显示条数
function viewStyle(){
	var para=$("#para").val();
	$("#listPage").load("./pageList?pageNum=1&pageSize="+$("#pagesize").val()+"&"+para);
}
/*]]>*/
