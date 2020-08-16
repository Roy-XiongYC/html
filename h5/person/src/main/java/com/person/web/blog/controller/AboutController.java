package com.person.web.blog.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.person.framework.constant.MessageConstant;
import com.person.framework.constant.RedisConstant;
import com.person.framework.redis.RedisServiceUtil;
import com.person.framework.utils.StringUtil;
import com.person.framework.web.BlogController;
import com.person.framework.web.JsonResult;
import com.person.web.blog.model.BlogAbout;
import com.person.web.blog.service.IBlogAboutService;

@Controller
@RequestMapping("blog")
public class AboutController  extends BlogController{

	@RequestMapping("about.html")
	public String showPage(){
		return "web/blog/about";
	}
	
	@ResponseBody
	@RequestMapping(value = "aboutData" , method = {RequestMethod.POST})
	public JsonResult getData(HttpServletRequest request,HttpServletResponse response){
		JsonResult jr = new JsonResult();
		String userSign = getBlogSign(request);
		String str = RedisServiceUtil.getJedis(RedisServiceUtil.SERVICE_REDIS_COMMON, String.format(RedisConstant.redis_blog_about,userSign));
		if (!StringUtil.isNullOrBlank(str)) {
			jr.success(str);
			return jr.declareSuccess();
		}
		
		BlogAbout about = blogAboutService.queryEntityByUser(userSign);
		if (about != null) {
			jr.success(JSONObject.toJSONString(about));
			RedisServiceUtil.setJedisObject(RedisServiceUtil.SERVICE_REDIS_COMMON, String.format(RedisConstant.redis_blog_about,userSign), about);
			return jr.declareSuccess();
		}
		return jr.declareFailure(messageService.message(MessageConstant.C00000));
	}
	
	@Autowired
	private IBlogAboutService blogAboutService;
	
	
}
