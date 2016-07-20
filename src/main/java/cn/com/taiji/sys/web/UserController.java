package cn.com.taiji.sys.web;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.taiji.sys.domain.UserRepository;
import cn.com.taiji.sys.dto.UserDto;
import cn.com.taiji.sys.service.UserService;
import cn.com.taiji.sys.util.Pagination;
import net.kernal.spiderman.Config;
import net.kernal.spiderman.Spiderman;
import net.kernal.spiderman.kit.XMLConfBuilder;
import net.sf.json.JSONObject;

/**
 * 用户Controller
 * @date 2016-06-06
 * @author zhangjb
 *
 */

@Controller
@RequestMapping(value = "/sysGover")
public class UserController {

	private static Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/user-list")
	public String userList(Model model,HttpServletRequest request,
		    @ModelAttribute("dto") UserDto pr,HttpServletResponse response){
		
		response.setHeader("X-Frame-Options", "SAMEORIGIN");
		String currentPageStr = request.getParameter("pagecurrentnum");
		String pagesize = request.getParameter("pagesize");
		Pagination<UserDto> page = newPagination(currentPageStr, pagesize);
		page = userService.findAllUser(pr, page);
		model.addAttribute("userInfo", page.getPageResult());
		model.addAttribute("pagevar", page);
		log.info("用户维护列表加载成功！");
		return "sys/user-list";
	}
	
	
	@RequestMapping(value = "/user-add")
	public String userSave(Model model, HttpServletRequest request,
			@ModelAttribute("dto")UserDto ud,HttpServletResponse response){
		try{
//			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			log.info(ud.getUserId()+"--------------");
			if(ud.getUserId()==null && (ud.getUserId()).equals("")){
				//1.对比登录名看是否添加用户
				userService.add(ud);
			}else{
				log.info(ud.getUserId() +"----"+ ud.getLoginName());
				userService.edit(ud);
			}
			//2.加载新的list
			model.addAttribute("userInfo", userService.among());
		}catch(Exception e){
			log.error("Exception{}:", e.getMessage());
			model.addAttribute("error", e.getMessage());
		}
			
		
		return "success";
	}
	
	@RequestMapping(value = "/user-edit")
	@ResponseBody
	public String userEdit(Model model,HttpServletRequest request){
		String id = request.getParameter("userId");
		UserDto userDto = userService.findUserDtoByUserId(id);
		JSONObject jsonStu = JSONObject.fromObject(userDto); 
		log.info(jsonStu.toString());
		
		return jsonStu.toString();
	}
	
	
    /**
     * 初始化Pagination对象
     * 
     * @param currentPageStr
     *            当前页页数
     * @return
     */
    private <T> Pagination<T> newPagination(String currentPageStr,String pageSizeStr) {
	Pagination<T> page = new Pagination<T>();
	int currentPage = page.getPageCurrentNum() != 0 ? page
		.getPageCurrentNum() : 1;
	int pageSize = page.getPageSize() != 0 ? page.getPageSize() : 10;
	if (currentPageStr != null && !"".equals(currentPageStr)) {
	    currentPage = Integer.parseInt(currentPageStr);
	}
	int pageStartNo = page.getPageStartNo() != 0 ? page.getPageStartNo()
		: 1;
	pageStartNo = (currentPage - 1) * pageSize;
	page.setPageStartNo(pageStartNo);
	if (pageSizeStr != null && !"".equals(pageSizeStr)) {
	    pageSize = Integer.parseInt(pageSizeStr);
	}
	page.setPageSize(pageSize);
	page.setPageCurrentNum(currentPage);
	return page;
    }
}
