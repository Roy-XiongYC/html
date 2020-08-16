package com.person.web.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.person.framework.mybatis.Criteria;
import com.person.framework.utils.StringUtil;
import com.person.framework.web.AdminAppController;
import com.person.framework.web.JsonResult;
import com.person.framework.web.PageResult;
import com.person.web.blog.model.BlogLink;
import com.person.web.blog.service.IBlogLinkService;

@Controller
@RequestMapping(value = "admin/blogLink")
public class BlogLinkController extends AdminAppController<BlogLink> {

	@Autowired
	private IBlogLinkService service;

	@RequestMapping(value = "/listPage", method = { RequestMethod.GET })
	public String showList() {
		return "web/admin/blog/blogLinkList";
	}

	@ResponseBody
	@RequestMapping(value = "/modify", method = { RequestMethod.POST })
	public JsonResult modify(@RequestParam(required = false) String id,
			@RequestParam(required = false) String userId,
			@RequestParam(required = true) String linkName,
			@RequestParam(required = true) String linkUrl) {
		BlogLink blogLink = new BlogLink();

		blogLink.setId(id);
		blogLink.setUserId(getDefaultUserId(userId));
		blogLink.setLinkName(linkName);
		blogLink.setLinkUrl(linkUrl);
		String ret = service.insertOrUpdate(blogLink);
		JsonResult jr = JsonResult.newInstance();
		return ret == null ? jr.declareSuccess() : jr
				.declareFailure(messageService.message(ret));
	}

	@ResponseBody
	@RequestMapping(value = "/data", method = { RequestMethod.POST })
	public PageResult queryPage(
			@RequestParam(required = false, value = "userName") String userName,
			@RequestParam(required = false, defaultValue = "1", value = "page") int page,
			@RequestParam(required = false, defaultValue = "10", value = "rp") int pageSize,
			@RequestParam(required = false, value = "sEcho") String sEcho) {
		Criteria<BlogLink> param = new Criteria<BlogLink>();
		param = validGroup(param);
		if (!StringUtil.isNullOrBlank(userName)) {
			param.addParam("userName_like", userName);
		}
		param.setPageSize(pageSize).setStartIndex(page);
		param.setOrderBy(" create_time desc");
		PageResult pageResult = new PageResult();
		pageResult.setsEcho(sEcho);
		pageResult.setAaData(service.queryPage(param));
		pageResult.setiTotalDisplayRecords(service.queryPageCount(param));
		pageResult
				.setiTotalRecords(service.queryPageCount(param.clearParams()));
		return pageResult;
	}

	@RequestMapping(value = "/addPage")
	public String detail(@RequestParam(required = false) String id, ModelMap map) {
		BlogLink blogLink = service.queryEntityById(id);
		if (null != blogLink) {
			map.put("blogLink", blogLink);
		}
		return "web/admin/blog/blogLinkDetail";
	}

	@ResponseBody
	@RequestMapping(value = "/del", method = { RequestMethod.POST })
	public JsonResult DeleteEntity(@RequestParam(required = true) String id) {
		String ret = service.deleteById(id);
		JsonResult jr = JsonResult.newInstance();
		return ret == null ? jr.declareSuccess() : jr.declareFailure(ret);
	}

}