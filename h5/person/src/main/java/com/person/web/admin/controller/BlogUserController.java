package com.person.web.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.person.framework.constant.DefaultConstant;
import com.person.framework.constant.MessageConstant;
import com.person.framework.constant.UrlConstant;
import com.person.framework.mybatis.Criteria;
import com.person.framework.utils.StringUtil;
import com.person.framework.web.AdminAppController;
import com.person.framework.web.JsonResult;
import com.person.framework.web.PageResult;
import com.person.web.blog.model.BlogAbout;
import com.person.web.blog.model.BlogUser;
import com.person.web.blog.service.IBlogAboutService;
import com.person.web.blog.service.IBlogUserService;
import com.person.web.fs.service.FileService;

@Controller
@RequestMapping(value = "admin/blogUser")
public class BlogUserController extends AdminAppController<BlogUser> {

	@Autowired
	private IBlogUserService service;
	@Autowired
	private IBlogAboutService aboutService;
	@Autowired
	private FileService fileBll;

	@RequestMapping(value = "/listPage", method = { RequestMethod.GET })
	public String showList() {
		return "web/admin/blog/blogUserList";
	}

	@ResponseBody
	@RequestMapping(value = "/modify", method = { RequestMethod.POST })
	public JsonResult modify(@RequestParam(required = false) String userId,
			@RequestParam(required = true) String userName,
			@RequestParam(required = true) String userSign,
			@RequestParam(required = true) String occupation,
			@RequestParam(required = false) String brief,
			@RequestParam(required = false) String signature,
			@RequestParam(required = false) String title,
			@RequestParam(required = false) String image,
			@RequestParam(required = false) String qq,
			@RequestParam(required = false) String aboutContent) {
		BlogUser blogUser = new BlogUser();
		blogUser.setUserId(getDefaultUserId(userId));
		blogUser.setUserName(userName);
		blogUser.setUserSign(userSign);
		blogUser.setOccupation(occupation);
		blogUser.setBrief(brief);
		blogUser.setSignature(signature);
		blogUser.setTitle(title);
		blogUser.setImage(image);
		blogUser.setQq(qq);
		blogUser.setAboutContent(aboutContent);
		String ret = service.insertOrUpdate(blogUser);
		JsonResult jr = JsonResult.newInstance();
		return ret == null ? jr.declareSuccess() : jr
				.declareFailure(messageService.message(ret));
	}

	@ResponseBody
	@RequestMapping(value = "/data", method = { RequestMethod.POST })
	public PageResult queryPage(
			@RequestParam(required = false, value = "userName") String userName,
			@RequestParam(required = false, value = "userSign") String userSign,
			@RequestParam(required = false, defaultValue = "1", value = "page") int page,
			@RequestParam(required = false, defaultValue = "10", value = "rp") int pageSize,
			@RequestParam(required = false, value = "sEcho") String sEcho) {
		Criteria<BlogUser> param = new Criteria<BlogUser>();
		param = validGroup(param);
		if (!StringUtil.isNullOrBlank(userName)) {
			param.addParam("userName_like", userName);
		}
		if (!StringUtil.isNullOrBlank(userSign)) {
			param.addParam("userSign_like", userSign);
		}
		param.setPageSize(pageSize).setStartIndex(page);
		param.setOrderBy(" create_time desc");
		List<BlogUser> list = service.queryPage(param);
		if (list!=null && list.size()>0) {
			for (BlogUser blogUser : list) {
				if (!StringUtil.isNullOrBlank(blogUser.getImage())) {
					blogUser.setImage(String.format(UrlConstant.RESOURCE_URL,blogUser.getImage()));
				}
			}
		}
		PageResult pageResult = new PageResult();
		pageResult.setsEcho(sEcho);
		pageResult.setAaData(list);
		pageResult.setiTotalDisplayRecords(service.queryPageCount(param));
		pageResult
				.setiTotalRecords(service.queryPageCount(param.clearParams()));
		return pageResult;
	}

	@RequestMapping(value = "/detailPage")
	public String detail(@RequestParam(required = false) String userId, ModelMap map) {
		BlogUser blogUser = service.queryEntityById(getDefaultUserId(userId));
		if (null != blogUser) {
			if (!StringUtil.isNullOrBlank(blogUser.getImage())) {
				blogUser.setImage(String.format(UrlConstant.RESOURCE_URL, blogUser.getImage()));
			}
			map.put("blogUser", blogUser);
			BlogAbout about = aboutService.queryEntityByUser(blogUser.getUserSign());
			if (about != null) {
				map.put("aboutContent", about.getAboutContent());
			}
		}
		return "web/admin/blog/blogUserDetail";
	}

	@ResponseBody
	@RequestMapping(value = "/del", method = { RequestMethod.POST })
	public JsonResult DeleteEntity(@RequestParam(required = true) String userId) {
		JsonResult jr = JsonResult.newInstance();
		if (userId.equals(DefaultConstant.DEFAULT_ADMIN_USER)) {
			return jr.declareFailure(messageService
					.message(MessageConstant.C00000));
		}
		String ret = service.deleteById(userId);
		if (StringUtil.isNullOrBlank(ret)) {
			try {
				BlogUser user = service.queryEntityById(userId);
				if (!StringUtil.isNullOrBlank(user.getImage())) {
					fileBll.deleteFile(user.getImage());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ret == null ? jr.declareSuccess() : jr.declareFailure(ret);
	}

}