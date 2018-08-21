package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import yu.e3mall.common.utils.E3Result;
import yu.e3mall.search.service.SearchItemService;

/**
 * 导入商品到索引库
 * @author yws
 *
 */
@Controller
public class SearchItemController {
	@Autowired
	private SearchItemService searchItemService;
	@RequestMapping("/index/item/import")
	@ResponseBody
	public E3Result importItemList() {
		return searchItemService.importAllItems();
	}
}
