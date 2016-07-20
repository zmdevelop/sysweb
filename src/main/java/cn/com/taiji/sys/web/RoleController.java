package cn.com.taiji.sys.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.com.taiji.sys.dto.RoleDto;
import cn.com.taiji.sys.dto.TreeDto;
import cn.com.taiji.sys.service.RoleService;
import cn.com.taiji.sys.util.Pagination;

/**
 * 描述:角色控制层
 * 作者:zhao
 * 日期:2016年7月7日下午4:10:58
 * 版本:
 */
@Controller
@RequestMapping(value = "/sys/role")
public class RoleController {

	private static Logger log = LoggerFactory.getLogger(RoleController.class);
	
	@Inject
	RoleService roleService;
	
	@RequestMapping("/list")
	public String findAll(Model model)
	{
		return "sys/role-list";
	}
	
	@RequestMapping("/save")
	public String save(Model model,RoleDto roleDto)
	{
		if(roleDto.getRoleId()!=null && !roleDto.getRoleId().equals(""))
		{
			roleService.editOne(roleDto);
		}
		else
		{
		roleService.save(roleDto);
		}
		model.addAttribute("msg","保存成功！");
		return "sys/role-list";
	}
	
	@RequestMapping("/getOne")
	public String getOne(Model model,String roleId)
	{
		RoleDto dto = roleService.findById(roleId);
		model.addAttribute("result",dto);
		return "sys/role-list";
	}
	
	@RequestMapping("/grid")
	public String save(Model model , @RequestParam("pageNum") Integer pageNum,@RequestParam("pageSize") Integer pageSize,RoleDto roleDto)
	{
		Map searchParameters = new HashMap();
		searchParameters.put("pageNum", pageNum);
		searchParameters.put("pageSize", pageSize);
		Pagination page =  roleService.getPage(searchParameters);
		model.addAttribute("page",page);
		return "sys/role-grid";
	}
	
	@RequestMapping("/roleMenus")
	public String roleMenus(Model model , String roleId)
	{
		List<TreeDto> treeDtos =  roleService.getMenusByRole(roleId);
		model.addAttribute("treeDtos",treeDtos);
		return "sys/role-grid";
	}
	
	@RequestMapping("/setMenus")
	public String setMenus(Model model , String menuIds,String roleId)
	{
		roleService.setMenus(roleId, menuIds);
		model.addAttribute("msg","设置成功！");
		return "sys/role-grid";
	}
	
	@RequestMapping("/delete")
	public String delete(Model model,String roleId)
	{
		roleService.delete(roleId);
		model.addAttribute("msg","删除成功！");
		return "sys/role-list";
	}
}
