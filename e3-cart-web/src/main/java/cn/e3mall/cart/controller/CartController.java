package cn.e3mall.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import yu.e3mall.cart.service.CartService;
import yu.e3mall.common.utils.CookieUtils;
import yu.e3mall.common.utils.E3Result;
import yu.e3mall.common.utils.JsonUtils;
import yu.e3mall.pojo.TbItem;
import yu.e3mall.pojo.TbUser;
import yu.e3mall.service.ItemService;

/**
 * 购物车Controller
 * 
 * @author yws
 *
 */
@Controller
public class CartController {
	@Autowired
	private CartService cartService;
	@Autowired
	private ItemService itemService;
	@Value("${CART_COOKIE_EXPIRE}")
	private Integer CART_COOKIE_EXPIRE;

	@RequestMapping("/cart/add/{itemId}")
	public String addCart(@PathVariable Long itemId, @RequestParam(defaultValue = "1") Integer num,
			HttpServletRequest request, HttpServletResponse response) {
		TbUser user = (TbUser) request.getAttribute("user");
		if (user != null) {
			cartService.addCart(user.getId(), itemId, num);
			return "cartSuccess";
		}
		List<TbItem> itemList = getItemListFromCookie(request);
		boolean flag = false;
		for (TbItem tbItem : itemList) {
			if (tbItem.getId().longValue() == itemId.longValue()) {
				flag = true;
				tbItem.setNum(tbItem.getNum() + num);
				break;
			}
		}
		if (!flag) {
			TbItem tbItem = itemService.getItemById(itemId);
			tbItem.setNum(num);
			String image = tbItem.getImage();
			if (StringUtils.isNoneBlank(image)) {
				tbItem.setImage(image.split(",")[0]);
			}
			itemList.add(tbItem);
		}
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(itemList), CART_COOKIE_EXPIRE, true);
		return "cartSuccess";
	}

	@RequestMapping("/cart/cart")
	public String showCartList(HttpServletRequest request, HttpServletResponse response) {
		List<TbItem> itemList = getItemListFromCookie(request);
		TbUser user = (TbUser) request.getAttribute("user");
		if (user != null) {
			cartService.mergeCart(user.getId(), itemList);
			CookieUtils.deleteCookie(request, response, "cart");
			itemList = cartService.getCartList(user.getId());
		}
		request.setAttribute("cartList", itemList);
		return "cart";
	}

	@RequestMapping("/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public E3Result updateCartNumber(@PathVariable Long itemId, @PathVariable Integer num, HttpServletRequest request,
			HttpServletResponse response) {
		TbUser user = (TbUser) request.getAttribute("user");
		if (user != null) {
			return cartService.updateCartNum(user.getId(), itemId, num);
		}
		List<TbItem> itemList = getItemListFromCookie(request);
		for (TbItem tbItem : itemList) {
			if (tbItem.getId().longValue() == itemId.longValue()) {
				tbItem.setNum(num);
				break;
			}

		}
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(itemList), CART_COOKIE_EXPIRE, true);
		return E3Result.ok();
	}

	@RequestMapping("/cart/delete/{itemId}")
	public String deleteCartItem(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response) {
		TbUser user = (TbUser) request.getAttribute("user");
		if (user != null) {
			cartService.deleteCartItem(user.getId(), itemId);
			return "redirect:/cart/cart.html";
		}
		List<TbItem> itemList = getItemListFromCookie(request);
		for (TbItem tbItem : itemList) {
			if (tbItem.getId().longValue() == itemId.longValue()) {
				itemList.remove(tbItem);
				break;
			}

		}
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(itemList), CART_COOKIE_EXPIRE, true);
		return "redirect:/cart/cart.html";
	}

	private List<TbItem> getItemListFromCookie(HttpServletRequest request) {
		String json = CookieUtils.getCookieValue(request, "cart", true);
		if (StringUtils.isBlank(json)) {
			return new ArrayList<>();
		}
		return JsonUtils.jsonToList(json, TbItem.class);
	}
}
