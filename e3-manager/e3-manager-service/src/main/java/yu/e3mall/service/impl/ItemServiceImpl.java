package yu.e3mall.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import yu.e3mall.common.jedis.JedisClient;
import yu.e3mall.common.pojo.EasyUIDataGridResult;
import yu.e3mall.common.utils.E3Result;
import yu.e3mall.common.utils.IDUtils;
import yu.e3mall.common.utils.JsonUtils;
import yu.e3mall.mapper.TbItemDescMapper;
import yu.e3mall.mapper.TbItemMapper;
import yu.e3mall.pojo.TbItem;
import yu.e3mall.pojo.TbItemDesc;
import yu.e3mall.pojo.TbItemExample;
import yu.e3mall.service.ItemService;

/**
 * 商品管理service
 * 
 * @author yws
 *
 */
@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	@Autowired
	private JmsTemplate jmsTemplate;
	@Resource
	private Destination topicDestination;
	@Autowired
	private JedisClient jedisClient;
	@Value("${REDIS_ITEM_PRE}")
	private String REDIS_ITEM_PRE;
	@Value("${ITEM_CACHE_EXPIRE}")
	private int ITEM_CACHE_EXPIRE;
	

	@Override
	public TbItem getItemById(Long itemId) {
		String itemKey = REDIS_ITEM_PRE + ":" + itemId + ":BASE";
		try {
			String json = jedisClient.get(itemKey);
			if (StringUtils.isNotBlank(json)) {
				TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
				return tbItem;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
		if (tbItem != null) {
			try {
				jedisClient.set(itemKey, JsonUtils.objectToJson(tbItem));
				jedisClient.expire(itemKey, ITEM_CACHE_EXPIRE);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return tbItem;

	}

	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {
		PageHelper.startPage(page, rows);
		TbItemExample example = new TbItemExample();
		List<TbItem> list = tbItemMapper.selectByExample(example);
		EasyUIDataGridResult easyUIDataGridResult = new EasyUIDataGridResult();
		easyUIDataGridResult.setRows(list);
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		easyUIDataGridResult.setTotal(pageInfo.getTotal());
		return easyUIDataGridResult;
	}

	@Override
	public E3Result addItem(TbItem tbItem, String desc) {
		final long id = IDUtils.genItemId();
		tbItem.setId(id);
		// 1-正常，2-下架，3-删除
		tbItem.setStatus((byte) 1);
		Date date = new Date();
		tbItem.setCreated(date);
		tbItem.setUpdated(date);
		tbItemMapper.insert(tbItem);
		TbItemDesc tbItemDesc = new TbItemDesc();
		tbItemDesc.setItemId(id);
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setCreated(date);
		tbItemDesc.setUpdated(date);
		tbItemDescMapper.insert(tbItemDesc);
		jmsTemplate.send(topicDestination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage(id + "");
				return textMessage;
			}
		});
		return E3Result.ok();
	}

	@Override
	public TbItemDesc getItemDescById(Long itemId) {
		String itemKey = REDIS_ITEM_PRE + ":" + itemId + ":DESC";
		try {
			String json = jedisClient.get(itemKey);
			if (StringUtils.isNotBlank(json)) {
				TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				return tbItemDesc;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);
		if (tbItemDesc != null) {
			try {
				jedisClient.set(itemKey, JsonUtils.objectToJson(tbItemDesc));
				jedisClient.expire(itemKey, ITEM_CACHE_EXPIRE);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return tbItemDesc;
	}

}
