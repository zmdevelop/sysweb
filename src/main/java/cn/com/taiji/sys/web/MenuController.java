package cn.com.taiji.sys.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hazelcast.util.StringUtil;

import cn.com.taiji.security.SecurityUser;
import cn.com.taiji.sys.domain.Menu;
import cn.com.taiji.sys.domain.User;
import cn.com.taiji.sys.dto.MenuDto;
import cn.com.taiji.sys.dto.TreeDto;
import cn.com.taiji.sys.service.MenuService;

/**
 * 描述:菜单controller
 * 作者:zhao
 * 日期:2016年7月6日下午2:36:54
 * 版本:1.0
 */
@Controller
@RequestMapping("/sys/menu")
public class MenuController {

	private Logger log = LoggerFactory.getLogger(MenuController.class);
	
	@Inject
	MenuService menuService;
	
	@RequestMapping("/save")
	public String  save(MenuDto menuDto,Model model)
	{
		if(StringUtils.isEmpty(menuDto.getMenuId()))
		{
			menuService.save(menuDto);
			model.addAttribute("msg", "保存成功！");
		}
		else
		{
			menuService.editOne(menuDto);
			model.addAttribute("msg", "更新成功！");
		}
		return "sys/menu-list";
	}
	
	@RequestMapping("/edit")
	public String  edit(MenuDto menuDto,Model model)
	{
		return "sys/menu-list";
	}
	
	@RequestMapping("/delete")
	public String  delete(String menuId,Model model)
	{
		menuService.delete(menuId);
		Map result = new HashMap();
		result.put("msg","删除成功！");
		model.addAttribute("result", result);
		return "sys/menu-list";
	}
	
	@RequestMapping("/getOneMenu")
	public String getOneMenu(Model model,@RequestParam("menuId") String menuId)
	{
		log.debug(menuId);
		MenuDto menuDto = menuService.findById(menuId);
		model.addAttribute("result", menuDto);
		return "sys/menu-list";
	}
	
	@RequestMapping("/list")
	public String list(Model model)
	{
		List<MenuDto> menuDtos =  menuService.findMenusByTreeOrder();
		model.addAttribute("menuDtos", menuDtos);
		log.debug("----"+menuDtos.size()+"-----");
		return "sys/menu-list";
	}
	
	@RequestMapping("/treeMenu")
	public String treeMenu(Model model)
	{
		List<TreeDto> TreeDtos =  menuService.findMenusByTreeDto();
		model.addAttribute("TreeDtos", TreeDtos);
		log.debug("----"+TreeDtos.size()+"-----");
		return "sys/menu-list";
	}
	
	@RequestMapping("/getMenusByUser")
	public String getMenusByRole(Model model)
	{
		SecurityUser user = getCurrentUserAccount();
		Set<MenuDto> menuDtos = menuService.findByUser(user.getUserId());
		log.debug(menuDtos.size()+"");
		model.addAttribute("menuDtos",menuDtos);
		return "sys/menu-list";
	}
	
	private SecurityUser getCurrentUserAccount() {
        if (SecurityContextHolder.getContext().getAuthentication() == null)
            return null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof SecurityUser) {
            return (SecurityUser) principal;
        }
        return null;
    }

	
}
