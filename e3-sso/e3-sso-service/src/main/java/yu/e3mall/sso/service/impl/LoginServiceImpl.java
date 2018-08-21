package yu.e3mall.sso.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import yu.e3mall.common.jedis.JedisClient;
import yu.e3mall.common.utils.E3Result;
import yu.e3mall.common.utils.JsonUtils;
import yu.e3mall.mapper.TbUserMapper;
import yu.e3mall.pojo.TbUser;
import yu.e3mall.pojo.TbUserExample;
import yu.e3mall.pojo.TbUserExample.Criteria;
import yu.e3mall.sso.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {
	@Autowired
	private TbUserMapper tbUserMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${TOKEN_EXPIRE_TIME}")
	private Integer TOKEN_EXPIRE_TIME;

	@Override
	public E3Result userLogin(String username, String password) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> tbUserList = tbUserMapper.selectByExample(example);
		if (tbUserList == null || tbUserList.size() == 0) {
			return E3Result.build(400, "用户名或者密码错误");
		}
		TbUser tbUser = tbUserList.get(0);
		if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(tbUser.getPassword())) {
			return E3Result.build(400, "用户名或者密码错误");
		}
		tbUser.setPassword(null);
		String token = UUID.randomUUID().toString();
		String key = "SESSION:" + token;
		jedisClient.set(key, JsonUtils.objectToJson(tbUser));
		jedisClient.expire(key, TOKEN_EXPIRE_TIME);
		return E3Result.ok(token);
	}

}
