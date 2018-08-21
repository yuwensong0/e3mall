package yu.e3mall.sso.service;

import yu.e3mall.common.utils.E3Result;
import yu.e3mall.pojo.TbUser;

public interface RegisterService {
	E3Result checkData(String param, int type);
	E3Result register(TbUser tbUser);
}
