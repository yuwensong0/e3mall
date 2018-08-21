package yu.e3mall.fast;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import yu.e3mall.common.utils.FastDFSClient;

public class FastDFSTest {
/*	@Test
	public void testUpload() throws Exception {
		ClientGlobal.init("D:/workspace/e3-manager-web/src/main/resources/conf/client.conf");
		TrackerClient trackerClient = new TrackerClient();
		TrackerServer trackerServer = trackerClient.getConnection();
		StorageServer storageServer = null;
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		String[] messages = storageClient.upload_file("C:/Users/yws/Desktop/中期/background.gif", "jpg", null);
		for (String string : messages) {
			System.out.println(string);
		}		
	}
	
	@Test
	public void testFastDFSClient() throws Exception {
		FastDFSClient fastDFSClient = new FastDFSClient("D:/workspace/e3-manager-web/src/main/resources/conf/client.conf");
		String messages = fastDFSClient.uploadFile("C:/Users/yws/Desktop/中期/FeSeC.gif");
		System.out.println(messages);
	}*/
}
