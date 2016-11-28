package cn.com.taiji.spiderconf.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.taiji.spiderconf.dto.SpiderSiteDto;
import cn.com.taiji.spiderconf.service.SpiderSiteService;
import cn.com.taiji.sys.util.Pagination;

@Controller
@RequestMapping("/site")
public class SpiderSiteController {

	@Autowired
	private SpiderSiteService siteService;

	@RequestMapping("/list")
	public String list(Model model) {
		return "spider/site/list";
	}
	@RequestMapping("/page")
	public String page(Model model) {
		return "spider/site/page";
	}

	@RequestMapping("/getOne")
	public String getOneConfig(String siteId, Model model) {
		SpiderSiteDto dto = siteService.findOne(siteId);
		model.addAttribute("result", dto);
		return "spider/site/grid";
	}

	@RequestMapping("/grid")
	public String list(Model model, Integer pageSize, Integer pageNum,
			SpiderSiteDto searchDto) {
		pageSize = pageSize == null ? 5 : pageSize;
		pageNum = pageNum == null ? 1 : pageNum;
		Pagination<SpiderSiteDto> page = siteService.findByPage(pageSize,
				pageNum, searchDto);
		model.addAttribute("page", page);
		return "spider/site/grid";
	}

	@RequestMapping("/saveOrUpdate")
	public String saveOrUpdate(SpiderSiteDto siteDto, Model model) {
		if (siteDto.getId() != null && !siteDto.getId().equals("")) {
			this.siteService.update(siteDto);
		} else {
			this.siteService.save(siteDto);
		}
		model.addAttribute("msg", "操作成功!");
		return "spider/site/list";
	}

	@RequestMapping("/delete")
	public String delete(String siteId, Model model) {
		SpiderSiteDto site = this.siteService.delete(siteId);
		model.addAttribute("msg", "操作成功!");
		model.addAttribute("spideSite", site);
		return "spider/site/list";
	}

	@RequestMapping("/startSpider")
	public String startSpider(Model model, String siteId, Boolean status) {
		siteService.startSpider(siteId, status);
		model.addAttribute("msg", "操作成功！");
		return "spider/site/list";
	}
}
