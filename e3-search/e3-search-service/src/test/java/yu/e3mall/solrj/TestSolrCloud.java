package yu.e3mall.solrj;

import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestSolrCloud {
	/*@Test
	public void testAddDocument() throws Exception {
		CloudSolrServer solrServer = new CloudSolrServer("192.168.25.129:2182,192.168.25.129:2183,192.168.25.129:2184");
		solrServer.setDefaultCollection("collection2");
		SolrInputDocument solrInputDocument = new SolrInputDocument();
		solrInputDocument.setField("id", "solrCloud01");
		solrInputDocument.setField("item_title", "test goods");
		solrInputDocument.setField("item_price", 1002);
		solrServer.add(solrInputDocument);
		solrServer.commit();
	}*/
}
