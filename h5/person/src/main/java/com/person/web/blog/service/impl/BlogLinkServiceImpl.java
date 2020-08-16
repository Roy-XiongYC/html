package com.person.web.blog.service.impl;

import java.util.List;

import com.person.framework.constant.DefaultConstant;
import com.person.framework.constant.MessageConstant;
import com.person.framework.constant.RedisConstant;
import com.person.framework.mybatis.Criteria;
import com.person.framework.redis.RedisServiceUtil;
import com.person.framework.service.IMessageService;
import com.person.framework.utils.DateUtil;
import com.person.framework.utils.StringUtil;
import com.person.web.blog.model.BlogLink;
import com.person.web.blog.repository.IBlogLinkRepository;
import com.person.web.blog.service.IBlogLinkService;
import com.person.web.blog.service.IBlogUserService;
import com.person.web.blog.service.ISequenceService;

public class BlogLinkServiceImpl implements IBlogLinkService {

	public List<BlogLink> queryPage(Criteria<BlogLink> param) {
		return blogLinkRepository.queryPage(param);
	}

	public Integer queryPageCount(Criteria<BlogLink> param) {
		return (Integer) blogLinkRepository.queryPageCount(param);
	}
	@Override
	public List<BlogLink> queryPageLimitFive(String userId) {
		return blogLinkRepository.queryPageLimitFive(userId);
	}
	public BlogLink queryEntityById(String id) {
		return blogLinkRepository.queryEntityById(id);
	}

	public String insert(BlogLink record) {
		int result = blogLinkRepository.insert(record);
		if (result != 1) {
			return messageService.message(MessageConstant.C00010);
		}
		return null;
	}
	
	@Override
	public String insertOrUpdate(BlogLink record) {
		String ret = null;
		record.setCreateTime(DateUtil.getCurrentDate());
		if (StringUtil.isNullOrBlank(record.getId())) {
			record.setId(sequenceService.getUpdateQuerySeq(DefaultConstant.SEQ_COMMON));
			ret = insert(record);
		}else {
			Criteria<BlogLink> param = new Criteria<BlogLink>();
			param.addParam("id", record.getId());
			param.setRecord(record);
			ret = updateByCriteria(param);
		}
		
		if (StringUtil.isNullOrBlank(ret)) {
			RedisServiceUtil.delJedis(RedisServiceUtil.SERVICE_REDIS_COMMON, String
					.format(RedisConstant.redis_blog_user_sign, blogUserService
							.queryEntityById(record.getUserId()).getUserSign()));
		}
		return ret;
	}

	public String updateByCriteria(Criteria<BlogLink> param) {
		int result = blogLinkRepository.updateByCriteria(param);
		if (result != 1) {
			return messageService.message(MessageConstant.C00011);
		}
		return null;
	}

	public String deleteById(String id) {
		int result = blogLinkRepository.deleteById(id);
		if (result != 1) {
			return messageService.message(MessageConstant.C00012);
		}
		return null;
	}

	public String deleteByCriteria(Criteria<BlogLink> param) {
		blogLinkRepository.deleteByCriteria(param);
		return null;
	}

	public void setBlogLinkRepository(IBlogLinkRepository repository) {
		this.blogLinkRepository = repository;
	}

	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	public void setSequenceService(ISequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

	public void setBlogUserService(IBlogUserService blogUserService) {
		this.blogUserService = blogUserService;
	}

	private IBlogLinkRepository blogLinkRepository;

	private IMessageService messageService;
	
	private ISequenceService sequenceService;
	
	private IBlogUserService blogUserService;

}
