package com.person.web.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.person.framework.constant.UrlConstant;
import com.person.framework.mybatis.Criteria;
import com.person.framework.utils.StringUtil;
import com.person.framework.web.AdminAppController;
import com.person.framework.web.JsonResult;
import com.person.framework.web.PageResult;
import com.person.web.blog.model.BlogXc;
import com.person.web.blog.service.IBlogXcService;
import com.person.web.fs.service.FileService;

@Controller
@RequestMapping(value = "admin/blogXc")
public class BlogXcController extends AdminAppController<BlogXc> {

	@Autowired
	private IBlogXcService service;
	@Autowired
	private FileService fileBll;
	
	@RequestMapping(value = "/listPage", method = { RequestMethod.GET })
	public String showList() {
		return "web/admin/blog/blogXcList";
	}

	@ResponseBody
	@RequestMapping(value = "/modify", method = { RequestMethod.POST })
	public JsonResult modify(@RequestParam(required = false) String id,
			@RequestParam(required = false) String userId,
			@RequestParam(required = true) String img,
			@RequestParam(required = true) Integer imgWidth,
			@RequestParam(required = true) Integer imgHeight) {
		BlogXc blogXc = new BlogXc();
		
		blogXc.setId(id);
		blogXc.setUserId(getDefaultUserId(userId));
		blogXc.setImg(img);
		blogXc.setImgWidth(imgWidth);
		blogXc.setImgHeight(imgHeight);
		String ret = service.insertOrUpdate(blogXc);
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
		Criteria<BlogXc> param = new Criteria<BlogXc>();
		param = validGroup(param);
		if (!StringUtil.isNullOrBlank(userName)) {
			param.addParam("userName_like", userName);
		}
		param.setPageSize(pageSize).setStartIndex(page);
		param.setOrderBy(" create_time desc");
		List<BlogXc> list = service.queryPage(param);
		if (list != null) {
			for (BlogXc blogXc : list) {
				blogXc.setImg(String.format(UrlConstant.RESOURCE_URL, blogXc.getImg()));
			}
		}
		PageResult pageResult = new PageResult();
		pageResult.setsEcho(sEcho);
		pageResult.setAaData(list);
		pageResult.setiTotalDisplayRecords(service.queryPageCount(param));
		pageResult.setiTotalRecords(service.queryPageCount(param.clearParams()));
		return pageResult;
	}

	@RequestMapping(value = "/addPage")
	public String detail(@RequestParam(required = false) String id, ModelMap map) {
		BlogXc blogXc = service.queryEntityById(id);
		if (null != blogXc) {
			blogXc.setImg(String.format(UrlConstant.RESOURCE_URL, blogXc.getImg()));
			map.put("blogXc", blogXc);
		}
		return "web/admin/blog/blogXcDetail";
	}

	@ResponseBody
	@RequestMapping(value = "/del", method = { RequestMethod.POST })
	public JsonResult DeleteEntity(@RequestParam(required = true) String id) {
		String ret = service.deleteById(id);
		if (StringUtil.isNullOrBlank(ret)) {
			fileBll.deleteFile(service.queryEntityById(id).getImg());
		}
		JsonResult jr = JsonResult.newInstance();
		return ret == null ? jr.declareSuccess() : jr.declareFailure(ret);
	}

}