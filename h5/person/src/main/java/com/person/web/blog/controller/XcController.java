package com.person.web.blog.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.person.framework.constant.RedisConstant;
import com.person.framework.constant.SessionConstant;
import com.person.framework.constant.UrlConstant;
import com.person.framework.mybatis.Criteria;
import com.person.framework.redis.RedisServiceUtil;
import com.person.framework.utils.StringUtil;
import com.person.framework.web.BlogController;
import com.person.web.blog.model.BlogXc;
import com.person.web.blog.service.IBlogXcService;

@Controller
@RequestMapping("blog")
public class XcController extends BlogController{

	@RequestMapping("xc.html")
	public String showPage(HttpServletRequest request,HttpServletResponse response){
		String userSign = getBlogSign(request);
		String str = RedisServiceUtil.getJedis(RedisServiceUtil.SERVICE_REDIS_COMMON, String.format(RedisConstant.redis_blog_xc,userSign));
		if (!StringUtil.isNullOrBlank(str)) {
			request.getSession(true).setAttribute(SessionConstant.BLOG_XC, JSONObject.toJavaObject(JSONObject.parseArray(str), List.class));
		}
		
		Criteria<BlogXc> param = new Criteria<BlogXc>();
		param.addParam("userSign", userSign);
		param.setOrderBy(" create_time desc ");
		param.setPageSize(50);
		List<BlogXc> list = blogXcService.queryPage(param);
		if (list != null && list.size()>0) {
			for (BlogXc model : list) {
				model.setImg(String.format(UrlConstant.RESOURCE_URL, model.getImg()));
			}
			request.getSession(true).setAttribute(SessionConstant.BLOG_XC, list);
			RedisServiceUtil.setJedisObject(RedisServiceUtil.SERVICE_REDIS_COMMON, String.format(RedisConstant.redis_blog_xc,userSign), list);
		}
		return "web/blog/xc";
	}
	@Autowired
	private IBlogXcService blogXcService;
	
}
