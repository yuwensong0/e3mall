package yu.e3mall.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yu.e3mall.common.pojo.SearchItem;
import yu.e3mall.common.utils.E3Result;
import yu.e3mall.search.mapper.ItemMapper;
import yu.e3mall.search.service.SearchItemService;

/**
 * 索引库维护服务
 * @author yws
 *
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private SolrServer solrServer;
	@Override
	public E3Result importAllItems() {
		try {
			List<SearchItem> itemList = itemMapper.getItemList();
			for (SearchItem searchItem : itemList) {
				SolrInputDocument solrInputDocument = new SolrInputDocument();
				solrInputDocument.addField("item_title", searchItem.getTitle());
				solrInputDocument.addField("item_sell_point", searchItem.getSell_point());
				solrInputDocument.addField("item_price", searchItem.getPrice());
				solrInputDocument.addField("item_image", searchItem.getImage());
				solrInputDocument.addField("item_category_name", searchItem.getCategory_name());
				solrInputDocument.addField("id", searchItem.getId());
				solrServer.add(solrInputDocument);
			}
			solrServer.commit();
			return E3Result.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return E3Result.build(500, "数据导入失败");
		}
	}

}
