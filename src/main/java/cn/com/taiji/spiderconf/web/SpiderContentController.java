package cn.com.taiji.spiderconf.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.taiji.spiderconf.dto.SpiderContentDto;
import cn.com.taiji.spiderconf.dto.SpiderSiteDto;
import cn.com.taiji.spiderconf.service.SpiderContentService;
import cn.com.taiji.spiderconf.service.SpiderSiteService;
import cn.com.taiji.sys.util.Pagination;

@Controller
@RequestMapping("/content")
public class SpiderContentController {
	@Autowired
	private SpiderContentService contentService;
	@Autowired
	private SpiderSiteService siteService;

	@RequestMapping("/list")
	public String page(Model model) {
		List<SpiderSiteDto> list = this.siteService.findAll();
		model.addAttribute("siteDtos", list);
		return "spider/content/list";
	}

	@RequestMapping("/getOne")
	public String getOneConfig(String contentId, Model model) {
		SpiderContentDto dto = contentService.findOne(contentId);
		model.addAttribute("result", dto);
		return "spider/content/grid";
	}

	@RequestMapping("/grid")
	public String list(Model model, Integer pageSize, Integer pageNum,
			SpiderContentDto searchDto) {
		pageSize = pageSize == null ? 5 : pageSize;
		pageNum = pageNum == null ? 1 : pageNum;
		Pagination<SpiderContentDto> page = contentService.findByPage(pageSize,
				pageNum, searchDto);
		model.addAttribute("page", page);
		return "spider/content/grid";
	}

	@RequestMapping("/saveOrUpdate")
	public String saveOrUpdate(SpiderContentDto contentDto, Model model) {
		if (contentDto.getId() != null && !contentDto.getId().equals("")) {
			this.contentService.update(contentDto);
		} else {
			this.contentService.save(contentDto);
		}
		model.addAttribute("msg", "操作成功!");
		return "spider/content/list";
	}

	@RequestMapping("/delete")
	public String delete(String contentId, Model model) {
		SpiderContentDto content = this.contentService.delete(contentId);
		model.addAttribute("msg", "操作成功!");
		model.addAttribute("spideContent", content);
		return "spider/content/list";
	}
}
