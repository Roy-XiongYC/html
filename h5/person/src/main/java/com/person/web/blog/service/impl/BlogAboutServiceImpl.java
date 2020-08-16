package com.person.web.blog.service.impl;

import java.util.List;

import com.person.framework.constant.MessageConstant;
import com.person.framework.mybatis.Criteria;
import com.person.framework.service.IMessageService;
import com.person.web.blog.model.BlogAbout;
import com.person.web.blog.repository.IBlogAboutRepository;
import com.person.web.blog.service.IBlogAboutService;

public class BlogAboutServiceImpl implements IBlogAboutService {

	public List<BlogAbout> queryPage(Criteria<BlogAbout> param) {
		return blogAboutRepository.queryPage(param);
	}

	public Integer queryPageCount(Criteria<BlogAbout> param) {
		return (Integer) blogAboutRepository.queryPageCount(param);
	}

	public BlogAbout queryEntityById(String id) {
		return blogAboutRepository.queryEntityById(id);
	}
	@Override
	public BlogAbout queryEntityByUser(String userSign) {
		return blogAboutRepository.queryEntityByUser(userSign);
	}

	public String insert(BlogAbout record) {
		int result = blogAboutRepository.insert(record);
		if (result != 1) {
			return messageService.message(MessageConstant.C00010);
		}
		return null;
	}

	public String updateByCriteria(Criteria<BlogAbout> param) {
		int result = blogAboutRepository.updateByCriteria(param);
		if (result != 1) {
			return messageService.message(MessageConstant.C00011);
		}
		return null;
	}

	public String deleteById(String id) {
		int result = blogAboutRepository.deleteById(id);
		if (result != 1) {
			return messageService.message(MessageConstant.C00012);
		}
		return null;
	}

	public String deleteByCriteria(Criteria<BlogAbout> param) {
		blogAboutRepository.deleteByCriteria(param);
		return null;
	}

	public void setBlogAboutRepository(IBlogAboutRepository repository) {
		this.blogAboutRepository = repository;
	}

	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	private IBlogAboutRepository blogAboutRepository;

	private IMessageService messageService;

}
