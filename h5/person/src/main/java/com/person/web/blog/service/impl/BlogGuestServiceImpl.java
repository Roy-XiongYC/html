package com.person.web.blog.service.impl;

import java.util.List;

import com.person.framework.constant.MessageConstant;
import com.person.framework.mybatis.Criteria;
import com.person.framework.service.IMessageService;
import com.person.web.blog.model.BlogGuest;
import com.person.web.blog.repository.IBlogGuestRepository;
import com.person.web.blog.service.IBlogGuestService;

public class BlogGuestServiceImpl implements IBlogGuestService {

	public List<BlogGuest> queryPage(Criteria<BlogGuest> param) {
		return blogGuestRepository.queryPage(param);
	}

	public Integer queryPageCount(Criteria<BlogGuest> param) {
		return (Integer) blogGuestRepository.queryPageCount(param);
	}

	public BlogGuest queryEntityById(String id) {
		return blogGuestRepository.queryEntityById(id);
	}

	public String insert(BlogGuest record) {
		int result = blogGuestRepository.insert(record);
		if (result != 1) {
			return messageService.message(MessageConstant.C00010);
		}
		return null;
	}

	public String updateByCriteria(Criteria<BlogGuest> param) {
		int result = blogGuestRepository.updateByCriteria(param);
		if (result != 1) {
			return messageService.message(MessageConstant.C00011);
		}
		return null;
	}

	public String deleteById(String id) {
		int result = blogGuestRepository.deleteById(id);
		if (result != 1) {
			return messageService.message(MessageConstant.C00012);
		}
		return null;
	}

	public String deleteByCriteria(Criteria<BlogGuest> param) {
		blogGuestRepository.deleteByCriteria(param);
		return null;
	}

	public void setBlogGuestRepository(IBlogGuestRepository repository) {
		this.blogGuestRepository = repository;
	}

	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	private IBlogGuestRepository blogGuestRepository;

	private IMessageService messageService;

}
