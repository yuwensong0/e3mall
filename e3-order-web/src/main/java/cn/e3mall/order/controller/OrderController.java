package cn.e3mall.order.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import yu.e3mall.cart.service.CartService;
import yu.e3mall.common.utils.E3Result;
import yu.e3mall.order.pojo.OrderInfo;
import yu.e3mall.order.service.OrderService;
import yu.e3mall.pojo.TbItem;
import yu.e3mall.pojo.TbUser;

/**
 * 订单管理Controller
 * 
 * @author yws
 *
 */
@Controller
public class OrderController {
	@Autowired
	private CartService cartService;
	@Autowired
	private OrderService orderService;

	@RequestMapping("/order/order-cart")
	public String showOrderCart(HttpServletRequest request) {
		TbUser user = (TbUser) request.getAttribute("user");
		List<TbItem> cartList = cartService.getCartList(user.getId());
		request.setAttribute("cartList", cartList);
		return "order-cart";
	}
	
	@RequestMapping(value="/order/create", method=RequestMethod.POST)
	public String createOrder(OrderInfo orderInfo, HttpServletRequest request) {
		TbUser user = (TbUser) request.getAttribute("user");
		orderInfo.setUserId(user.getId());
		orderInfo.setBuyerNick(user.getUsername());
		E3Result e3Result = orderService.createOrder(orderInfo);
		if (e3Result.getStatus() == 200) {
			cartService.clearCartItem(user.getId());
		}
		request.setAttribute("orderId", e3Result.getData());
		request.setAttribute("payment", orderInfo.getPayment());
		return "success";
	}
}
