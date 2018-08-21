package yu.e3mall.order.pojo;

import java.io.Serializable;
import java.util.List;

import yu.e3mall.pojo.TbItem;
import yu.e3mall.pojo.TbOrder;
import yu.e3mall.pojo.TbOrderItem;
import yu.e3mall.pojo.TbOrderShipping;

public class OrderInfo extends TbOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<TbOrderItem> orderItems;
	private TbOrderShipping orderShipping;

	public List<TbOrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<TbOrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public TbOrderShipping getOrderShipping() {
		return orderShipping;
	}

	public void setOrderShipping(TbOrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}
}
