package yu.e3mall.content.service.imp;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import yu.e3mall.common.jedis.JedisClient;
import yu.e3mall.common.utils.E3Result;
import yu.e3mall.common.utils.JsonUtils;
import yu.e3mall.content.service.ContentService;
import yu.e3mall.mapper.TbContentMapper;
import yu.e3mall.pojo.TbContent;
import yu.e3mall.pojo.TbContentExample;
import yu.e3mall.pojo.TbContentExample.Criteria;

@Service
public class ContentServiceImpl implements ContentService {
	@Autowired
	private TbContentMapper tbContentMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${CONTENT_LIST}")
	private String CONTENT_LIST;

	@Override
	public E3Result addContent(TbContent tbContent) {
		Date date = new Date();
		tbContent.setCreated(date);
		tbContent.setUpdated(date);
		tbContentMapper.insert(tbContent);
		jedisClient.hdel(CONTENT_LIST, tbContent.getCategoryId().toString());
		return E3Result.ok();
	}

	/**
	 * 根据分类列表查询列表
	 */
	@Override
	public List<TbContent> getContentByCid(Long cid) {
		try {
			String json = jedisClient.hget(CONTENT_LIST, cid + "");
			if (StringUtils.isNotBlank(json)) {
				List<TbContent> tbContentList = JsonUtils.jsonToList(json, TbContent.class);
				return tbContentList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		List<TbContent> tbContentList = tbContentMapper.selectByExampleWithBLOBs(example);
		try {
			jedisClient.hset(CONTENT_LIST, cid + "", JsonUtils.objectToJson(tbContentList));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tbContentList;
	}

}
