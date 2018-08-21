package yu.e3mall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yu.e3mall.common.pojo.EasyUITreeNode;
import yu.e3mall.mapper.TbItemCatMapper;
import yu.e3mall.pojo.TbItemCat;
import yu.e3mall.pojo.TbItemCatExample;
import yu.e3mall.pojo.TbItemCatExample.Criteria;
import yu.e3mall.service.ItemCatService;

/**
 * 查询商品分裂列表服务
 * @author yws
 *
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {
	@Autowired
	private TbItemCatMapper tbItemCatMapper;

	@Override
	public List<EasyUITreeNode> getItemCatList(Long parentId) {
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> list = tbItemCatMapper.selectByExample(example);
		List<EasyUITreeNode> resultList = new ArrayList<>();
		for (TbItemCat tbItemCat : list) {
			EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
			easyUITreeNode.setId(tbItemCat.getId());
			easyUITreeNode.setText(tbItemCat.getName());
			easyUITreeNode.setState(tbItemCat.getIsParent() ? "closed" : "open");
			resultList.add(easyUITreeNode);
		}
		return resultList;
	}

}
