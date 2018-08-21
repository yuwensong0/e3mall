package yu.e3mall.content.service;
import java.util.List;

import yu.e3mall.common.pojo.EasyUITreeNode;
import yu.e3mall.common.utils.E3Result;

public interface ContentCategoryService {
	List<EasyUITreeNode> getContentCatList(Long parentId);
	E3Result addContentCategory(Long parentId, String name);
}
