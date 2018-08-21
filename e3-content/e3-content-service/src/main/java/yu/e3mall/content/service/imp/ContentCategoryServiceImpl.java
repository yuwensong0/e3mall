package yu.e3mall.content.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yu.e3mall.common.pojo.EasyUITreeNode;
import yu.e3mall.common.utils.E3Result;
import yu.e3mall.content.service.ContentCategoryService;
import yu.e3mall.mapper.TbContentCategoryMapper;
import yu.e3mall.pojo.TbContentCategory;
import yu.e3mall.pojo.TbContentCategoryExample;
import yu.e3mall.pojo.TbContentCategoryExample.Criteria;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;
	@Override
	public List<EasyUITreeNode> getContentCatList(Long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> catList = tbContentCategoryMapper.selectByExample(example);
		List<EasyUITreeNode> easyUITreeNodeList = new ArrayList<>();
		for (TbContentCategory tbContentCategory : catList) {
			EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
			easyUITreeNode.setId(tbContentCategory.getId());
			easyUITreeNode.setText(tbContentCategory.getName());
			easyUITreeNode.setState(tbContentCategory.getIsParent() ? "closed" : "open");
			easyUITreeNodeList.add(easyUITreeNode);
		}
		return easyUITreeNodeList;
	}
	
	@Override
	public E3Result addContentCategory(Long parentId, String name) {
		TbContentCategory tbContentCategory = new TbContentCategory();
		tbContentCategory.setParentId(parentId);
		tbContentCategory.setName(name);
		//(正常),2(删除)
		tbContentCategory.setStatus(1);
		//默认为1
		tbContentCategory.setSortOrder(1);
		tbContentCategory.setIsParent(false);
		Date date = new Date();
		
		tbContentCategory.setCreated(date);
		tbContentCategory.setUpdated(date);
		tbContentCategoryMapper.insert(tbContentCategory);
		TbContentCategory parentCat = tbContentCategoryMapper.selectByPrimaryKey(parentId);
		if (!parentCat.getIsParent()) {
			parentCat.setIsParent(true);
			tbContentCategoryMapper.updateByPrimaryKey(parentCat);
		}
		return E3Result.ok(tbContentCategory);
	}

}
