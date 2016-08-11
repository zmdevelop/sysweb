package cn.com.taiji.spiderconf.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.taiji.spiderconf.dto.SpiderChannelDto;
import cn.com.taiji.spiderconf.dto.SpiderSiteDto;
import cn.com.taiji.spiderconf.service.SpiderChannelService;
import cn.com.taiji.spiderconf.service.SpiderSiteService;
import cn.com.taiji.sys.util.Pagination;

@Controller
@RequestMapping("/channel")
public class SpiderChannelController {
	@Autowired
	private SpiderChannelService channelService;
	@Autowired
	private SpiderSiteService siteService;

	@RequestMapping("/list")
	public String page(Model model) {
		List<SpiderSiteDto> list = this.siteService.findAll();
		model.addAttribute("siteDtos", list);
		return "spider/channel/list";
	}

	@RequestMapping("/getOne")
	public String getOneConfig(String channelId, Model model) {
		SpiderChannelDto dto = channelService.findOne(channelId);
		model.addAttribute("result", dto);
		return "spider/channel/grid";
	}

	@RequestMapping("/grid")
	public String list(Model model, Integer pageSize, Integer pageNum,
			SpiderChannelDto searchDto) {
		pageSize = pageSize == null ? 5 : pageSize;
		pageNum = pageNum == null ? 1 : pageNum;
		Pagination<SpiderChannelDto> page = channelService.findByPage(pageSize,
				pageNum, searchDto);
		model.addAttribute("page", page);
		return "spider/channel/grid";
	}

	@RequestMapping("/saveOrUpdate")
	public String saveOrUpdate(SpiderChannelDto channelDto, Model model) {
		if (channelDto.getId() != null && !channelDto.getId().equals("")) {
			this.channelService.update(channelDto);
		} else {
			this.channelService.save(channelDto);
		}
		model.addAttribute("msg", "操作成功!");
		return "spider/channel/list";
	}

	@RequestMapping("/delete")
	public String delete(String channelId, Model model) {
		SpiderChannelDto channel = this.channelService.delete(channelId);
		model.addAttribute("msg", "操作成功!");
		model.addAttribute("spideChannel", channel);
		return "spider/channel/list";
	}
}
