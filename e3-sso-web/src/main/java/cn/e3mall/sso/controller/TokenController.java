package cn.e3mall.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import yu.e3mall.common.utils.E3Result;
import yu.e3mall.sso.service.TokenService;

@Controller
public class TokenController {
	@Autowired
	private TokenService tokenService;

	@RequestMapping("/user/token/{token}")
	@ResponseBody
	public Object getUserByToken(@PathVariable String token, String callback) {
		E3Result e3Result = tokenService.getUserByToken(token);
		if (StringUtils.isNoneBlank(callback)) {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(e3Result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}
		return e3Result;
	}
}
