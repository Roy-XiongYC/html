package com.person.web.blog.controller;

import java.util.List;

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
import com.person.framework.mybatis.Criteria;
import com.person.framework.redis.RedisServiceUtil;
import com.person.framework.utils.StringUtil;
import com.person.framework.web.BlogController;
import com.person.framework.web.JsonResult;
import com.person.web.blog.model.BlogShuo;
import com.person.web.blog.service.IBlogShuoService;

@Controller
@RequestMapping("blog")
public class ShuoController  extends BlogController{

	@RequestMapping("shuo.html")
	public String showPage(){
		return "web/blog/shuo";
	}
	
	@ResponseBody
	@RequestMapping(value = "shuoData" , method = {RequestMethod.POST})
	public JsonResult getData(HttpServletRequest request,HttpServletResponse response){
		JsonResult jr = new JsonResult();
		String userSign = getBlogSign(request);
		String str = RedisServiceUtil.getJedis(RedisServiceUtil.SERVICE_REDIS_COMMON, String.format(RedisConstant.redis_blog_shuo,userSign));
		if (!StringUtil.isNullOrBlank(str)) {
			jr.success(str);
			return jr.declareSuccess();
		}
		
		Criteria<BlogShuo> param = new Criteria<BlogShuo>();
		param.addParam("userSign", userSign);
		param.setOrderBy(" create_time desc ");
		param.setPageSize(10);
		List<BlogShuo> list = blogShuoService.queryPage(param);
		if (list != null && list.size()>0) {
			jr.success(JSONObject.toJSONString(list));
			RedisServiceUtil.setJedisObject(RedisServiceUtil.SERVICE_REDIS_COMMON, String.format(RedisConstant.redis_blog_shuo,userSign), list);
			return jr.declareSuccess();
		}
		return jr.declareFailure(messageService.message(MessageConstant.C00000));
	}
	@Autowired
	private IBlogShuoService blogShuoService;
	
}
