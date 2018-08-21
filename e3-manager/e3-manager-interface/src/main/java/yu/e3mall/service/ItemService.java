package yu.e3mall.service;

import yu.e3mall.common.pojo.EasyUIDataGridResult;
import yu.e3mall.common.utils.E3Result;
import yu.e3mall.pojo.TbItem;
import yu.e3mall.pojo.TbItemDesc;

public interface ItemService {
	TbItem getItemById(Long itemId);
	EasyUIDataGridResult getItemList(int page, int rows);
	E3Result addItem(TbItem tbItem, String desc);
	TbItemDesc getItemDescById(Long itemId);
}
