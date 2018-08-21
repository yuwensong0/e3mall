package yu.e3mall.search.message;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import yu.e3mall.common.pojo.SearchItem;
import yu.e3mall.search.mapper.ItemMapper;

public class ItemAddMessageListener implements MessageListener {
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private SolrServer solrServer;

	@Override
	public void onMessage(Message message) {
		try {
			TextMessage textMessage = (TextMessage) message;
			Long itemId = Long.parseLong(textMessage.getText());
			Thread.sleep(1000);
			SearchItem searchItem = itemMapper.searchItemById(itemId);
			SolrInputDocument solrInputDocument = new SolrInputDocument();
			solrInputDocument.addField("item_title", searchItem.getTitle());
			solrInputDocument.addField("item_sell_point", searchItem.getSell_point());
			solrInputDocument.addField("item_price", searchItem.getPrice());
			solrInputDocument.addField("item_image", searchItem.getImage());
			solrInputDocument.addField("item_category_name", searchItem.getCategory_name());
			solrInputDocument.addField("id", searchItem.getId());
			solrServer.add(solrInputDocument);
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
