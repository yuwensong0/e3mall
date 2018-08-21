package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import yu.e3mall.common.utils.E3Result;
import yu.e3mall.content.service.ContentService;
import yu.e3mall.pojo.TbContent;

/**
 * 内容管理Controller
 * @author yws
 *
 */
@Controller
public class ContentController {
	@Autowired
	private ContentService contentService;
	
	@RequestMapping(value="/content/save", method=RequestMethod.POST)
	@ResponseBody
	public E3Result addContent(TbContent tbContent) {
		return contentService.addContent(tbContent);
	}
}
