package yu.e3mall.sso.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import yu.e3mall.common.jedis.JedisClient;
import yu.e3mall.common.utils.E3Result;
import yu.e3mall.common.utils.JsonUtils;
import yu.e3mall.pojo.TbUser;
import yu.e3mall.sso.service.TokenService;

@Service
public class TokenServiceImpl implements TokenService {
	@Autowired
	private JedisClient jedisClient;
	@Value("${TOKEN_EXPIRE_TIME}")
	private Integer TOKEN_EXPIRE_TIME;

	@Override
	public E3Result getUserByToken(String token) {
		String json = jedisClient.get("SESSION:" + token);
		if (StringUtils.isBlank(json)) {
			return E3Result.build(201, "登录已过期");
		}
		jedisClient.expire("SESSION:" + token, TOKEN_EXPIRE_TIME);
		TbUser tbUser = JsonUtils.jsonToPojo(json, TbUser.class);
		return E3Result.ok(tbUser);
	}

}
