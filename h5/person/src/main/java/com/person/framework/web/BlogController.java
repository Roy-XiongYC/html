package com.person.framework.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.person.framework.service.IMessageService;

/**
 * blog 通用控制器
 * Blog class for controller, though sub controller should define {@link
 * @Controller} itself.
 * 
 */
public abstract class BlogController {

	/** log instance. */
	protected final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	protected IMessageService messageService;
	
	protected String getBlogSign(HttpServletRequest request){
		String url = request.getServerName();
		log.info("service name is : " + url);
		return url.indexOf(".")>0 ? url.substring(0,url.indexOf(".")) : "blog";
	}
	
}
