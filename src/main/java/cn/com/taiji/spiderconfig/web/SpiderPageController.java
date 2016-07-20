package cn.com.taiji.spiderconfig.web;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.taiji.spiderconfig.dto.SpiderPageDto;
import cn.com.taiji.spiderconfig.service.SpiderConfigService;
import cn.com.taiji.spiderconfig.service.SpiderPageService;
import cn.com.taiji.sys.util.Pagination;

@Controller
@RequestMapping("/spiderPage")
public class SpiderPageController {
	
	@Inject
	SpiderPageService spiderPageService;
	
	@Inject
	SpiderConfigService spiderConfigService;

	@RequestMapping("/list")
	public String list(Model model)
	{
		model.addAttribute("configDtos", spiderConfigService.findAll());
		return "spider/spider-page-list";
	}
	@RequestMapping("/grid")
	public String grids(Model model,Integer pageNum,Integer pageSize,SpiderPageDto SearchDto)
	{
		pageNum = pageNum==null?1:pageNum;
		pageSize = pageSize==null?5:pageSize;
		Pagination<SpiderPageDto> page =  spiderPageService.findByPage(pageNum,pageSize,SearchDto);
		model.addAttribute("page",page);
		return "spider/spider-page-grid";
	}
	
	@RequestMapping("/saveOrUpdate")
	public String saveOrUpdate(Model model,SpiderPageDto dto)
	{
		if(StringUtils.isEmpty(dto.getId()))
		{
			spiderPageService.save(dto);
		}
		else
		{
			spiderPageService.update(dto);
		}
		model.addAttribute("msg","操作成功！");
		return "spider/spider-page-list";
	}
	
	@RequestMapping("/findOne")
	public String findOne(Model model,String pageId)
	{
		model.addAttribute("result",spiderPageService.findOne(pageId));
		return "spider/spider-page-list";
	}
	
	@RequestMapping("/delete")
	public String saveOrUpdate(Model model,String pageId)
	{
		spiderPageService.deleteById(pageId);
		model.addAttribute("msg","操作成功！");
		return "spider/spider-page-list";
	}
}
