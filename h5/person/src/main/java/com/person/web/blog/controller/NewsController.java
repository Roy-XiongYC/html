package com.person.web.blog.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.person.framework.constant.SessionConstant;
import com.person.web.blog.model.BlogLearn;
import com.person.web.blog.service.IBlogLearnService;

@Controller
@RequestMapping("blog")
public class NewsController {

	@RequestMapping("new/{id}.html")
	public String showPage(
			HttpServletRequest request,
			@PathVariable String id){
		BlogLearn learn = blogLearnService.queryEntityById(id);
		request.getSession(true).setAttribute(SessionConstant.BLOG_NEWS, learn);
		return "web/blog/news";
	}
	
	@Autowired
	private IBlogLearnService blogLearnService;
}
