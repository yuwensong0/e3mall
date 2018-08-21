package yu.e3mall.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import yu.e3mall.common.pojo.SearchItem;
import yu.e3mall.common.pojo.SearchResult;

/**
 * 
 * @author yws
 *
 */
@Repository
public class SearchDao {
	@Autowired
	private SolrServer solrServer;
	
	public SearchResult search(SolrQuery solrQuery) throws Exception {
		QueryResponse queryResponse = solrServer.query(solrQuery);
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		SearchResult searchResult = new SearchResult();
		searchResult.setRecordCount(solrDocumentList.getNumFound());
		List<SearchItem> searchItemList = new ArrayList<>();
		//for highlighting
		Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
		//for goods field search
		for (SolrDocument solrDocument : solrDocumentList) {
			SearchItem searchItem = new SearchItem();
			searchItem.setId((String) solrDocument.get("id"));
			searchItem.setCategory_name((String) solrDocument.get("item_category_name"));
			searchItem.setImage((String) solrDocument.get("item_image"));
			searchItem.setPrice((Long) solrDocument.get("item_price"));
			searchItem.setSell_point((String) solrDocument.get("item_sell_point"));
			List<String> highlightingList = highlighting.get(solrDocument.get("id")).get("item_title");
			String title = "";
			if (highlightingList != null && highlightingList.size() > 0) {
				title = highlightingList.get(0);
			} else {
				title = (String) solrDocument.get("item_title");
			}
			searchItem.setTitle(title);
			searchItemList.add(searchItem);
		}
		//add field list
		searchResult.setItemList(searchItemList);
		return searchResult; 
	}
}
