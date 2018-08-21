package yu.e3mall.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import yu.e3mall.common.utils.E3Result;
import yu.e3mall.mapper.TbUserMapper;
import yu.e3mall.pojo.TbUser;
import yu.e3mall.pojo.TbUserExample;
import yu.e3mall.pojo.TbUserExample.Criteria;
import yu.e3mall.sso.service.RegisterService;

@Service
public class RegisterServiceImpl implements RegisterService {
	@Autowired
	private TbUserMapper tbUserMapper;

	@Override
	public E3Result checkData(String param, int type) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		// 1:用户名。2 手机号 3 邮箱检验
		if (type == 1) {
			criteria.andUsernameEqualTo(param);
		} else if (type == 2) {
			criteria.andPhoneEqualTo(param);
		} else if (type == 3) {
			criteria.andEmailEqualTo(param);
		} else {
			return E3Result.build(400, "数据类型错误");
		}
		List<TbUser> tbUserList = tbUserMapper.selectByExample(example);
		if (tbUserList != null && tbUserList.size() > 0) {
			return E3Result.ok(false);
		}
		return E3Result.ok(true);
	}

	@Override
	public E3Result register(TbUser tbUser) {
		if (StringUtils.isBlank(tbUser.getUsername()) || StringUtils.isBlank(tbUser.getPhone())
				|| StringUtils.isBlank(tbUser.getPassword())) {
			return E3Result.build(400, "用戶信息不全，註冊失敗");
		}
		if (!(boolean) checkData(tbUser.getUsername(), 1).getData()) {
			return E3Result.build(400, "用户名已被注册");
		}
		if (!(boolean) checkData(tbUser.getPhone(), 2).getData()) {
			return E3Result.build(400, "手机号已被注册");
		}

		String passwordAsHex = DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes());
		tbUser.setPassword(passwordAsHex);
		Date date = new Date();
		tbUser.setCreated(date);
		tbUser.setUpdated(date);
		tbUserMapper.insert(tbUser);
		return E3Result.ok();
	}

}
