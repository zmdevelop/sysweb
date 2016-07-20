package cn.com.taiji.spiderconfig.web;


import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.taiji.spiderconfig.domain.SpiderSeed;
import cn.com.taiji.spiderconfig.dto.SpiderConfigDto;
import cn.com.taiji.spiderconfig.dto.SpiderSeedDto;
import cn.com.taiji.spiderconfig.service.SpiderConfigService;
import cn.com.taiji.sys.util.Pagination;

@Controller
@RequestMapping("/spiderConfig")
public class SpiderConfigController {
	
	@Inject
	SpiderConfigService spiderConfigService;

	@RequestMapping("/list")
	public String list(Model model)
	{
		return "spider/spider-list";
	}
	
	@RequestMapping("/getOneConfig")
	public String getOneConfig(String configId,Model model)
	{
		SpiderConfigDto dto =  spiderConfigService.findOne(configId);
		model.addAttribute("result",dto);
		return "spider/spider-grid";
	}
	
	@RequestMapping("/grid")
	public String list(Model model,Integer pageSize,Integer pageNum,SpiderConfigDto searchDto)
	{
		pageSize = pageSize==null?5:pageSize;
		pageNum = pageNum==null?1:pageNum;
		Pagination<SpiderConfigDto> page = spiderConfigService.findByPage(pageSize, pageNum, searchDto);
		model.addAttribute("page",page);
		return "spider/spider-grid";
	}
	
	@RequestMapping("/getOne")
	public String list(Model model,String seedId)
	{
		SpiderSeedDto spiderSeedDto = spiderConfigService.findOneSeed(seedId);
		model.addAttribute("result",spiderSeedDto);
		return "spider/spider-grid";
	}
	
	@RequestMapping("/seedGrid")
	public String seedGrid(Model model,Integer pageSize,Integer pageNum,String configId)
	{
		pageSize = pageSize==null?5:pageSize;
		pageNum = pageNum==null?1:pageNum;
		Pagination<SpiderSeedDto> page = spiderConfigService.findSeedByPage(pageSize, pageNum, configId);
		model.addAttribute("page",page);
		model.addAttribute("configId",configId);
		return "spider/spider-seed-grid";
	}
	
	@RequestMapping("/saveOrUpdateSeed")
	public String addSeedGrid(Model model,SpiderSeedDto dto)
	{
		if(StringUtils.isEmpty(dto.getSeedId()))
		{
		spiderConfigService.saveSeed(dto);
		}
		else
		{
			spiderConfigService.updateSeed(dto);
		}
		model.addAttribute("msg","操作成功！");
		return "spider/spider-seed-grid";
	}
	
	@RequestMapping("/deleteSeed")
	public String deleteSeedGrid(Model model,String seedId)
	{
		spiderConfigService.deleteSeed(seedId);
		model.addAttribute("msg","操作成功");
		return "spider/spider-seed-grid";
	}
	
	@RequestMapping("/saveOrUpdate")
	public String save(SpiderConfigDto dto,Model model)
	{
		if(StringUtils.isEmpty(dto.getId()))
		{
			spiderConfigService.save(dto);
		}
		else
		{
			spiderConfigService.update(dto);
		}
		model.addAttribute("msg","操作成功！");
		return "spider/spider-list";
	}
	
	@RequestMapping("/delete")
	public String save(Model model,String spiderConfigId)
	{
		spiderConfigService.delete(spiderConfigId);
		return "spider/spider-list";
	}
	
	@RequestMapping("/startSpider")
	public String startSpider(Model model,String spiderConfigId,Boolean status)
	{
		spiderConfigService.startSpider(spiderConfigId,status);
		model.addAttribute("msg","操作成功！");
		return "spider/spider-list";
	}
}
