package cn.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import yu.e3mall.common.pojo.EasyUITreeNode;
import yu.e3mall.common.utils.E3Result;
import yu.e3mall.content.service.ContentCategoryService;

/**
 * 内容分类Controller
 * @author yws
 *
 */
@Controller
public class ContentCatController {
	@Autowired
	private ContentCategoryService contentCategoryService;
	
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCatList(@RequestParam(value="id", defaultValue="0")Long parentId) {
		return contentCategoryService.getContentCatList(parentId);
	}
	
	@RequestMapping(value="/content/category/create", method=RequestMethod.POST)
	@ResponseBody
	public E3Result createContentCategory(Long parentId, String name) {
		return contentCategoryService.addContentCategory(parentId, name);
	}
}
