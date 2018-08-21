package yu.e3mall.sso.service;

import yu.e3mall.common.utils.E3Result;

public interface TokenService {
	E3Result getUserByToken(String token);
}
