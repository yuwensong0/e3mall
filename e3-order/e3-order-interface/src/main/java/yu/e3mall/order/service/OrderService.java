package yu.e3mall.order.service;

import yu.e3mall.common.utils.E3Result;
import yu.e3mall.order.pojo.OrderInfo;

public interface OrderService {
	E3Result createOrder(OrderInfo orderInfo);
}
