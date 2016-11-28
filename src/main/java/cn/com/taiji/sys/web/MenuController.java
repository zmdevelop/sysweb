package cn.com.taiji.sys.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
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
import org.springframework.web.bind.annotation.ResponseBody;


import cn.com.taiji.security.SecurityUser;
import cn.com.taiji.sys.dto.MenuDto;
import cn.com.taiji.sys.dto.TreeDto;
import cn.com.taiji.sys.service.MenuService;
import cn.com.taiji.sys.util.UserUtil;

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
		SecurityUser user = UserUtil.getCurrentUserDto();
		Set<MenuDto> menuDtos = menuService.findByUser(user.getUserId());
		log.debug(menuDtos.size()+"");
		model.addAttribute("menuDtos",menuDtos);
		return "sys/menu-list";
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/loadMenus")
	public @ResponseBody
	Object loadMenus(Model model) {
		try {
			Comparator<MenuDto> c = new Comparator<MenuDto>() {
				public int compare(MenuDto o1, MenuDto o2) {
					return (int) ((o1.getMenuIndex() == null ? 0 : o1.getMenuIndex()) - (o2
							.getMenuIndex() == null ? 0 : o2.getMenuIndex()));
				}
			};
			SecurityUser user = UserUtil.getCurrentUserDto();
			Set<MenuDto> menuDtos = menuService.findByUser(user.getUserId());
			log.debug(menuDtos.size()+"");
			List list = new ArrayList();
			List<MenuDto> menuList = new ArrayList<MenuDto>();
			for (MenuDto userMenu : menuDtos) {
				menuList.add(userMenu);
			}
			Collections.sort(menuList, c);
			for (MenuDto m : menuList) {
				Map map = new HashMap();
				map.put("id", m.getMenuId());
				map.put("name", m.getMenuName());
				if (m.getParentId() != null) {
						map.put("pId", m.getParentId());
				} else {
					map.put("pId", 0);
				}
				map.put("icon", new String(m.getSmallIconPath()));
				map.put("url", m.getMenuUrl());
				if (m.getChildren().size() == 0) {
					map.put("isLeaf", true);
				} else {
					map.put("isLeaf", false);
				}
				list.add(map);
			}
			model.addAttribute(list);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(errorJson("异常"));
		}
		return "sys/menu-list";
	}
	public Map errorJson(final String msg) {
        Map map = new HashMap() {{
            put("status", 0);
            put("msg", msg);
        }};
        return map;
    }
}
