package cn.e3mall.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import yu.e3mall.content.service.ContentService;
import yu.e3mall.pojo.TbContent;

/**
 * 首页Controller
 * @author yws
 *
 */
@Controller
public class IndexController {
	@Autowired
	private ContentService contentService;
	@Value("${CONTENT_LUNBO_ID}")
	private Long CONTENT_LUNBO_ID;
	@RequestMapping("/index")
	public String shoeIndex(Model model) {
		List<TbContent> ad1ContentList = contentService.getContentByCid(CONTENT_LUNBO_ID);
		model.addAttribute("ad1List", ad1ContentList);
		return "index";
	}
}
