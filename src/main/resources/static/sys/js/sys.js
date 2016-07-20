function initMenus()
{
  $.ajax({
	  url:"/sys/menu/getMenusByUser.json",
	  type:"post",
	  success:function(data)
	  {
		  var menuHtml = '';
		  var menus = data.menuDtos;
		  for(var i in menus )
			  {
			  if(!menus[i].parentId)
				  {
			  var icon = menus[i].iconPath?menus[i].iconPath:"fa fa-table fa-fw";
			  menuHtml= menuHtml +'<li id='+menus[i].menuId+'>'+
			  '<a href="'+ menus[i].menuUrl+'"><i class="'+icon+'"></i>'+menus[i].menuName+'<span class="fa arrow"></span></a>'+
			  '</li>';
				  }
			  }
		  $("#side-menu").html(menuHtml);
		  for(var j in menus )
		  {
		  if(menus[j].parentId)
			  {
			  if($("#"+menus[j].parentId).length>0)
				 {  console.log("0000000000");
				  var ul = $("#"+menus[j].parentId).find("ul");
				  if(ul.length>0)
				   {
					  ul.append('<li><a href = "'+menus[j].menuUrl+'">'+menus[j].menuName+'</a></li>');
				   }
				  else
					  {
					  $("#"+menus[j].parentId).append('<ul class="nav nav-second-level"><li><a href = "'+menus[j].menuUrl+'">'+menus[j].menuName+'</a></li></ul>');
					  }
				 }
				  else
				  {
					  var icon = menus[j].iconPath?menus[j].iconPath:"fa fa-table fa-fw";
					   var menuInnerHtml= menuHtml +'<li id='+menus[j].menuId+'>'+
					  '<a href="'+ menus[j].menuUrl+'"><i class="'+icon+'"></i>'+menus[j].menuName+'</a>'+
					  '</li>';
					  $("#side-menu").append(menuInnerHtml);
				  }
			  }
		   }
		  $('#side-menu').metisMenu();
		  bindLoadSize();
	  }
  })
}

function bindLoadSize()
{
	$(window).bind("load resize", function() {
        topOffset = 50;
        width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
        if (width < 768) {
            $('div.navbar-collapse').addClass('collapse');
            topOffset = 100; // 2-row-menu
        } else {
            $('div.navbar-collapse').removeClass('collapse');
        }

        height = ((this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height) - 1;
        height = height - topOffset;
        if (height < 1) height = 1;
        if (height > topOffset) {
            $("#page-wrapper").css("min-height", (height) + "px");
        }
    });

    var url = window.location;
    var element = $('ul.nav a').filter(function() {
        return this.href == url || url.href.indexOf(this.href) == 0;
    }).addClass('active').parent().parent().addClass('in').parent();
    if (element.is('li')) {
        element.addClass('active');
    }	
}