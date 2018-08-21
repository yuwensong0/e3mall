package cn.e3mall.item.listener;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.e3mall.item.pojo.Item;
import freemarker.template.Configuration;
import freemarker.template.Template;
import yu.e3mall.pojo.TbItem;
import yu.e3mall.pojo.TbItemDesc;
import yu.e3mall.service.ItemService;

public class HtmlGenListener implements MessageListener {
	@Autowired
	private ItemService itemService;
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Value("${HTML_GEN_PATH}")
	private String HTML_GEN_PATH;
	@Override
	public void onMessage(Message message) {
		try {
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			Long itemId = new Long(text);
			Thread.sleep(1000);
			TbItem tbItem = itemService.getItemById(itemId);
			Item item = new Item(tbItem);
			TbItemDesc itemDesc = itemService.getItemDescById(itemId);
			Map<String, Object> data = new HashMap<>();
			data.put("item", item);
			data.put("itemDesc", itemDesc);
			Configuration configuration = freeMarkerConfigurer.getConfiguration();
			Template template = configuration.getTemplate("item.ftl");
			Writer out = new FileWriter(HTML_GEN_PATH + itemId + ".html");
			template.process(data, out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
