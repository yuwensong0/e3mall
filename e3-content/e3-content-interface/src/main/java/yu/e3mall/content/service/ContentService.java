package yu.e3mall.content.service;

import java.util.List;

import yu.e3mall.common.utils.E3Result;
import yu.e3mall.pojo.TbContent;

public interface ContentService {
	E3Result addContent(TbContent tbContent);
	List<TbContent> getContentByCid(Long cid);
}
