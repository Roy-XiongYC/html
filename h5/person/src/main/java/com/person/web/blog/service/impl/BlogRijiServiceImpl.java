package com.person.web.blog.service.impl;

import java.util.List;

import com.person.framework.constant.MessageConstant;
import com.person.framework.constant.RedisConstant;
import com.person.framework.mybatis.Criteria;
import com.person.framework.redis.RedisServiceUtil;
import com.person.framework.service.IMessageService;
import com.person.web.blog.model.BlogRiji;
import com.person.web.blog.model.BlogUser;
import com.person.web.blog.repository.IBlogRijiRepository;
import com.person.web.blog.service.IBlogRijiService;
import com.person.web.blog.service.IBlogUserService;

public class BlogRijiServiceImpl implements IBlogRijiService {

	public List<BlogRiji> queryPage(Criteria<BlogRiji> param) {
		return blogRijiRepository.queryPage(param);
	}

	public Integer queryPageCount(Criteria<BlogRiji> param) {
		return (Integer) blogRijiRepository.queryPageCount(param);
	}

	public BlogRiji queryEntityById(String id) {
		return blogRijiRepository.queryEntityById(id);
	}

	public String insert(BlogRiji record) {
		int result = blogRijiRepository.insert(record);
		if (result != 1) {
			return messageService.message(MessageConstant.C00010);
		}
		BlogUser user = blogUserService.queryEntityById(record.getUserId());
		RedisServiceUtil.delJedis(RedisServiceUtil.SERVICE_REDIS_COMMON, String
				.format(RedisConstant.redis_blog_user_sign,user.getUserSign()));
		RedisServiceUtil.delJedis(RedisServiceUtil.SERVICE_REDIS_COMMON, String
				.format(RedisConstant.redis_blog_riji, user.getUserSign()));
		return null;
	}

	public String updateByCriteria(Criteria<BlogRiji> param) {
		int result = blogRijiRepository.updateByCriteria(param);
		if (result != 1) {
			return messageService.message(MessageConstant.C00011);
		}
		return null;
	}

	public String deleteById(String id) {
		int result = blogRijiRepository.deleteById(id);
		if (result != 1) {
			return messageService.message(MessageConstant.C00012);
		}
		return null;
	}

	public String deleteByCriteria(Criteria<BlogRiji> param) {
		blogRijiRepository.deleteByCriteria(param);
		return null;
	}

	public void setBlogRijiRepository(IBlogRijiRepository repository) {
		this.blogRijiRepository = repository;
	}


	private IBlogRijiRepository blogRijiRepository;

	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	public void setBlogUserService(IBlogUserService blogUserService) {
		this.blogUserService = blogUserService;
	}

	private IMessageService messageService;

	private IBlogUserService blogUserService;
}
