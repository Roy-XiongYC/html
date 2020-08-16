package com.person.web.blog.service.impl;

import java.util.List;

import com.person.framework.constant.MessageConstant;
import com.person.framework.constant.RedisConstant;
import com.person.framework.mybatis.Criteria;
import com.person.framework.redis.RedisServiceUtil;
import com.person.framework.service.IMessageService;
import com.person.web.blog.model.BlogShuo;
import com.person.web.blog.model.BlogUser;
import com.person.web.blog.repository.IBlogShuoRepository;
import com.person.web.blog.service.IBlogShuoService;
import com.person.web.blog.service.IBlogUserService;

public class BlogShuoServiceImpl implements IBlogShuoService {

	public List<BlogShuo> queryPage(Criteria<BlogShuo> param) {
		return blogShuoRepository.queryPage(param);
	}

	public Integer queryPageCount(Criteria<BlogShuo> param) {
		return (Integer) blogShuoRepository.queryPageCount(param);
	}

	public BlogShuo queryEntityById(String id) {
		return blogShuoRepository.queryEntityById(id);
	}

	public String insert(BlogShuo record) {
		int result = blogShuoRepository.insert(record);
		if (result != 1) {
			return messageService.message(MessageConstant.C00010);
		}
		BlogUser user = blogUserService.queryEntityById(record.getUserId());
		RedisServiceUtil.delJedis(RedisServiceUtil.SERVICE_REDIS_COMMON, String
				.format(RedisConstant.redis_blog_user_sign,user.getUserSign()));
		RedisServiceUtil.delJedis(RedisServiceUtil.SERVICE_REDIS_COMMON, String
				.format(RedisConstant.redis_blog_shuo, user.getUserSign()));
		return null;
	}

	public String updateByCriteria(Criteria<BlogShuo> param) {
		int result = blogShuoRepository.updateByCriteria(param);
		if (result != 1) {
			return messageService.message(MessageConstant.C00011);
		}
		return null;
	}

	public String deleteById(String id) {
		int result = blogShuoRepository.deleteById(id);
		if (result != 1) {
			return messageService.message(MessageConstant.C00012);
		}
		return null;
	}

	public String deleteByCriteria(Criteria<BlogShuo> param) {
		blogShuoRepository.deleteByCriteria(param);
		return null;
	}

	public void setBlogShuoRepository(IBlogShuoRepository repository) {
		this.blogShuoRepository = repository;
	}

	private IBlogShuoRepository blogShuoRepository;

	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	public void setBlogUserService(IBlogUserService blogUserService) {
		this.blogUserService = blogUserService;
	}

	private IMessageService messageService;

	private IBlogUserService blogUserService;
}
