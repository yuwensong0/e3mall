package yu.e3mall.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yu.e3mall.common.pojo.SearchResult;
import yu.e3mall.search.dao.SearchDao;
import yu.e3mall.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {
	@Autowired
	private SearchDao searchDao;

	@Override
	public SearchResult search(String keyword, Integer page, Integer rows) throws Exception {
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery(keyword);
		if (page == null || page <= 0) {
			page = 1;
		}
		solrQuery.setStart((page - 1) * rows);
		solrQuery.setRows(rows);
		solrQuery.set("df", "item_title");
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("item_title");
		solrQuery.setHighlightSimplePre("<em sytle=\"color:red\">");
		solrQuery.setHighlightSimplePost("</em>");
		SearchResult searchResult = searchDao.search(solrQuery);
		Long recordCount = searchResult.getRecordCount();
		int totalPages = (int) (recordCount / rows);
		if (recordCount % rows > 0) {
			totalPages++;
		}
		searchResult.setTotalPages(totalPages);
		return searchResult;
	}

}
