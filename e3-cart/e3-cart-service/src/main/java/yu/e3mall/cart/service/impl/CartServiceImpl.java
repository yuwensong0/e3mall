package yu.e3mall.cart.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import yu.e3mall.cart.service.CartService;
import yu.e3mall.common.jedis.JedisClient;
import yu.e3mall.common.utils.E3Result;
import yu.e3mall.common.utils.JsonUtils;
import yu.e3mall.mapper.TbItemMapper;
import yu.e3mall.pojo.TbItem;

@Service
public class CartServiceImpl implements CartService {
	@Autowired
	private JedisClient jedisClient;
	@Value("${REDIS_CART_PRE}")
	private String REDIS_CART_PRE;
	@Autowired
	private TbItemMapper tbItemMapper;

	@Override
	public E3Result addCart(long userId, long itemId, int num) {
		String key = REDIS_CART_PRE + ":" + userId;
		String field = itemId + "";
		Boolean hexists = jedisClient.hexists(key, field);
		if (hexists) {
			String json = jedisClient.hget(key, field);
			TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
			tbItem.setNum(tbItem.getNum() + num);
			jedisClient.hset(key, field, JsonUtils.objectToJson(tbItem));
			return E3Result.ok();
		}
		TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
		tbItem.setNum(num);
		String image = tbItem.getImage();
		if (StringUtils.isNotBlank(image)) {
			tbItem.setImage(image.split(",")[0]);
		}
		jedisClient.hset(key, field, JsonUtils.objectToJson(tbItem));
		return E3Result.ok();
	}

	@Override
	public E3Result mergeCart(long userId, List<TbItem> itemList) {
		for (TbItem tbItem : itemList) {
			addCart(userId, tbItem.getId(), tbItem.getNum());
		}
		return E3Result.ok();
	}

	@Override
	public List<TbItem> getCartList(long userId) {
		List<String> jsonList = jedisClient.hvals(REDIS_CART_PRE + ":" + userId);
		List<TbItem> itemList = new ArrayList<>();
		for (String json : jsonList) {
			TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
			itemList.add(tbItem);
		}
		return itemList;
	}

	@Override
	public E3Result updateCartNum(long userId, long itemId, int num) {
		String key = REDIS_CART_PRE + ":" + userId;
		String field = itemId + "";
		String json = jedisClient.hget(key, field);
		TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
		tbItem.setNum(num);
		jedisClient.hset(key, field, JsonUtils.objectToJson(tbItem));
		return E3Result.ok();
	}

	@Override
	public E3Result deleteCartItem(long userId, long itemId) {
		String key = REDIS_CART_PRE + ":" + userId;
		String field = itemId + "";
		jedisClient.hdel(key, field);
		return E3Result.ok();
	}

	@Override
	public E3Result clearCartItem(long userId) {
		jedisClient.del(REDIS_CART_PRE + ":" + userId);
		return E3Result.ok();
	}

}
