package com.person.web.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.person.framework.web.BlogController;

@Controller
@RequestMapping("blog")
public class BlogIndexController extends BlogController{
	
	@RequestMapping("index.html")
	public String showPage(){
		return "web/blog/index";
	}
	
}
