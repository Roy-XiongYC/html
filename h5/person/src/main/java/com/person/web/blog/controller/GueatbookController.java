package com.person.web.blog.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.person.framework.constant.DefaultConstant;
import com.person.framework.constant.MessageConstant;
import com.person.framework.utils.DateUtil;
import com.person.framework.utils.StringUtil;
import com.person.framework.web.BlogController;
import com.person.framework.web.JsonResult;
import com.person.web.blog.model.BlogGuest;
import com.person.web.blog.service.IBlogGuestService;
import com.person.web.blog.service.IBlogUserService;
import com.person.web.blog.service.ISequenceService;

@Controller
@RequestMapping("blog")
public class GueatbookController  extends BlogController{

	@RequestMapping("guestbook.html")
	public String showPage(){
		return "web/blog/guestbook";
	}
	
	@ResponseBody
	@RequestMapping(value="subGuest" , method = {RequestMethod.POST})
	public JsonResult subGuest(
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(required=true) String subGuest){
		JsonResult jr = new JsonResult();
		String userSign = getBlogSign(request);
		BlogGuest guest = new BlogGuest();
		guest.setCreateTime(DateUtil.getCurrentDate());
		guest.setGuestContent(subGuest);
		guest.setUserId(blogUserService.queryEntityBySign(userSign).getUserId());
		guest.setId(sequenceService.getUpdateQuerySeq(DefaultConstant.SEQ_COMMON));
		if(!StringUtil.isNullOrBlank(blogGuestService.insert(guest))){
			return jr.declareFailure(messageService.message(MessageConstant.C00010));
		}
		return jr.declareSuccess();
	}
	
	@Autowired
	private IBlogGuestService blogGuestService;
	@Autowired
	private IBlogUserService blogUserService;
	@Autowired
	private ISequenceService sequenceService;
}
