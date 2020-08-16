package com.person.web.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.person.framework.constant.DefaultConstant;
import com.person.framework.mybatis.Criteria;
import com.person.framework.utils.DateUtil;
import com.person.framework.utils.StringUtil;
import com.person.framework.web.AdminAppController;
import com.person.framework.web.JsonResult;
import com.person.framework.web.PageResult;
import com.person.web.blog.model.BlogShuo;
import com.person.web.blog.service.IBlogShuoService;
import com.person.web.blog.service.ISequenceService;

@Controller
@RequestMapping(value = "admin/blogShuo")
public class BlogShuoController extends AdminAppController<BlogShuo> {

	@Autowired
	private IBlogShuoService service;
	@Autowired
	private ISequenceService sequenceService;
	
	@RequestMapping(value = "/listPage", method = { RequestMethod.GET })
	public String showList() {
		return "/web/admin/blog/blogShuoList";
	}

	@ResponseBody
	@RequestMapping(value = "/modify", method = { RequestMethod.POST })
	public JsonResult modify(
			@RequestParam(required = false) String userId,
			@RequestParam(required = true) String shuoContent) {
		BlogShuo blogShuo = new BlogShuo();
		blogShuo.setId(sequenceService.getUpdateQuerySeq(DefaultConstant.SEQ_COMMON));
		blogShuo.setUserId(getDefaultUserId(userId));
		blogShuo.setShuoContent(shuoContent);
		blogShuo.setCreateTime(DateUtil.getCurrentDate());
		String ret = service.insert(blogShuo);
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
		Criteria<BlogShuo> param = new Criteria<BlogShuo>();
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
	public String detail(ModelMap map) {
		map.put("userId", getApplicationUserId());
		return "web/admin/blog/blogShuoDetail";
	}

	@ResponseBody
	@RequestMapping(value = "/del", method = { RequestMethod.POST })
	public JsonResult DeleteEntity(@RequestParam(required = true) String id) {
		String ret = service.deleteById(id);
		JsonResult jr = JsonResult.newInstance();
		return ret == null ? jr.declareSuccess() : jr.declareFailure(ret);
	}

}