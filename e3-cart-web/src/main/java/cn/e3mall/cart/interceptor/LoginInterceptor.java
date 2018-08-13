package cn.e3mall.cart.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import yu.e3mall.common.utils.CookieUtils;
import yu.e3mall.common.utils.E3Result;
import yu.e3mall.pojo.TbUser;
import yu.e3mall.sso.service.TokenService;

public class LoginInterceptor implements HandlerInterceptor {
	@Autowired
	private TokenService tokenService;
	
	@Value("${TOKEN_KEY}")
	private String TOKEN_KEY;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = CookieUtils.getCookieValue(request, TOKEN_KEY);
		if (StringUtils.isBlank(token)) {
			return true;
		}
		E3Result e3Result = tokenService.getUserByToken(token);
		if (e3Result.getStatus() != 200) {
			return true;
		}
		TbUser tbUser = (TbUser) e3Result.getData();
		request.setAttribute("user", tbUser);
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
