package yu.e3mall.search.mapper;

import java.util.List;

import yu.e3mall.common.pojo.SearchItem;

public interface ItemMapper {
	List<SearchItem> getItemList();
	SearchItem searchItemById(Long itemId);
}
