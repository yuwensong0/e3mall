package cn.e3mall.order.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import yu.e3mall.cart.service.CartService;
import yu.e3mall.common.utils.CookieUtils;
import yu.e3mall.common.utils.E3Result;
import yu.e3mall.common.utils.JsonUtils;
import yu.e3mall.pojo.TbItem;
import yu.e3mall.pojo.TbUser;
import yu.e3mall.sso.service.TokenService;

public class LoginInterceptor implements HandlerInterceptor {
	@Autowired
	private TokenService tokenService;
	@Autowired
	private CartService cartService;
	@Value("${SSO_URL}")
	private String SSO_URL;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = CookieUtils.getCookieValue(request, "token");
		if (StringUtils.isBlank(token)) {
			response.sendRedirect(SSO_URL + "/page/login?redirect=" + request.getRequestURL());
			return false;
		}
		E3Result e3Result = tokenService.getUserByToken(token);
		if (e3Result.getStatus() != 200) {
			response.sendRedirect(SSO_URL + "/page/login?redirect=" + request.getRequestURL());
			return false;
		}
		TbUser user = (TbUser) e3Result.getData();
		request.setAttribute("user", user);
		String cartJson = CookieUtils.getCookieValue(request, "cart", true);
		if (StringUtils.isNotBlank(cartJson)) {
			cartService.mergeCart(user.getId(), JsonUtils.jsonToList(cartJson, TbItem.class));
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
