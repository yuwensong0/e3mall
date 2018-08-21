package yu.e3mall.order.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import yu.e3mall.common.jedis.JedisClient;
import yu.e3mall.common.utils.E3Result;
import yu.e3mall.mapper.TbOrderItemMapper;
import yu.e3mall.mapper.TbOrderMapper;
import yu.e3mall.mapper.TbOrderShippingMapper;
import yu.e3mall.order.pojo.OrderInfo;
import yu.e3mall.order.service.OrderService;
import yu.e3mall.pojo.TbOrderItem;
import yu.e3mall.pojo.TbOrderShipping;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private TbOrderMapper tbOrderMapper;
	@Autowired
	private TbOrderItemMapper tbOrderItemMapper;
	@Autowired
	private TbOrderShippingMapper tbOrderShippingMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${ORDER_ID_GEN_KEY}")
	private String ORDER_ID_GEN_KEY;
	@Value("${ORDER_ID_START}")
	private String ORDER_ID_START;
	@Value("${ORDER_DETAIL_ID_GEN_KEY}")
	private String ORDER_DETAIL_ID_GEN_KEY;

	@Override
	public E3Result createOrder(OrderInfo orderInfo) {
		if (!jedisClient.exists(ORDER_ID_GEN_KEY)) {
			jedisClient.set(ORDER_ID_GEN_KEY, ORDER_ID_START);
		}
		System.out.println(ORDER_ID_START);
		String orderId = jedisClient.incr(ORDER_ID_GEN_KEY).toString();
		orderInfo.setOrderId(orderId);
		// 状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
		orderInfo.setStatus(1);
		Date date = new Date();
		orderInfo.setCreateTime(date);
		orderInfo.setUpdateTime(date);
		tbOrderMapper.insert(orderInfo);
		List<TbOrderItem> orderItems = orderInfo.getOrderItems();
		for (TbOrderItem tbOrderItem : orderItems) {
			String orderDetailId = jedisClient.incr(ORDER_DETAIL_ID_GEN_KEY).toString();
			tbOrderItem.setId(orderDetailId);
			tbOrderItem.setOrderId(orderId);
			tbOrderItemMapper.insert(tbOrderItem);
		}
		TbOrderShipping tbOrderShipping = orderInfo.getOrderShipping();
		tbOrderShipping.setOrderId(orderId);
		tbOrderShipping.setCreated(date);
		tbOrderShipping.setUpdated(date);
		tbOrderShippingMapper.insert(tbOrderShipping);
		return E3Result.ok(orderId);
	}

}
