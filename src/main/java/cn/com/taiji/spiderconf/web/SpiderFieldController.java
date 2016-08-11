package cn.com.taiji.spiderconf.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.taiji.spiderconf.dto.SpiderFieldDto;
import cn.com.taiji.spiderconf.service.SpiderFieldService;
import cn.com.taiji.sys.util.Pagination;

@Controller
@RequestMapping("/field")
public class SpiderFieldController {
	@Autowired
	private SpiderFieldService fieldService;

	@RequestMapping("/getOne")
	public String getOneConfig(String fieldId, Model model) {
		SpiderFieldDto dto = fieldService.findOne(fieldId);
		model.addAttribute("result", dto);
		return "spider/field/grid";
	}

	@RequestMapping("/grid")
	public String list(Model model, Integer pageSize, Integer pageNum,
			SpiderFieldDto searchDto) {
		pageSize = pageSize == null ? 5 : pageSize;
		pageNum = pageNum == null ? 1 : pageNum;
		Pagination<SpiderFieldDto> page = fieldService.findByPage(pageSize,
				pageNum, searchDto);
		model.addAttribute("page", page);
		String parentId = searchDto.getSpiderChannelId()==null?searchDto.getSpiderContentId():searchDto.getSpiderChannelId();
		model.addAttribute("parentId", parentId);
		return "spider/field/grid";
	}

	@RequestMapping("/saveOrUpdate")
	public String saveOrUpdate(SpiderFieldDto fieldDto, Model model) {
		if (fieldDto.getId() != null && !fieldDto.getId().equals("")) {
			this.fieldService.update(fieldDto);
		} else {
			this.fieldService.save(fieldDto);
		}
		model.addAttribute("msg", "操作成功!");
		return "spider/field/list";
	}

	@RequestMapping("/delete")
	public String delete(String fieldId, Model model) {
		SpiderFieldDto field = this.fieldService.delete(fieldId);
		model.addAttribute("msg", "操作成功!");
		model.addAttribute("spideField", field);
		return "spider/field/list";
	}
}
