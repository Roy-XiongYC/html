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
import com.person.framework.utils.DateUtil;
import com.person.framework.utils.StringUtil;
import com.person.framework.web.AdminAppController;
import com.person.framework.web.JsonResult;
import com.person.framework.web.PageResult;
import com.person.web.admin.service.ISysDictService;
import com.person.web.blog.model.BlogLearn;
import com.person.web.blog.service.IBlogLearnService;
import com.person.web.fs.service.FileService;

@Controller
@RequestMapping(value = "admin/blogLearn")
public class BlogLearnController extends AdminAppController<BlogLearn> {

	@Autowired
	private IBlogLearnService service;
	@Autowired
	private FileService fileBll;
	@Autowired
	private ISysDictService sysDictService;	

	@RequestMapping(value = "/listPage", method = { RequestMethod.GET })
	public String showList() {
		return "web/admin/blog/blogLearnList";
	}

	@ResponseBody
	@RequestMapping(value = "/modify", method = { RequestMethod.POST })
	public JsonResult modify(@RequestParam(required = false) String id,
			@RequestParam(required = true) String learnTitle,
			@RequestParam(required = true) String learnImg,
			@RequestParam(required = true) String learnTime,
			@RequestParam(required = true) String learnAuth,
			@RequestParam(required = true) String learnAdd,
			@RequestParam(required = true) String learnIs,
			@RequestParam(required = true) String aboutContent) {
		BlogLearn blogLearn = new BlogLearn();

		blogLearn.setId(id);
		blogLearn.setUserId(getDefaultUserId(null));
		blogLearn.setLearnTitle(learnTitle);
		if (!StringUtil.isNullOrBlank(learnImg)) {
			blogLearn.setLearnImg(learnImg);
		}
		blogLearn.setLearnTime(DateUtil.parse(learnTime));
		blogLearn.setLearnAuth(learnAuth);
		blogLearn.setLearnAdd(learnAdd);
		blogLearn.setLearnIs(Integer.parseInt(learnIs));
		blogLearn.setAboutContent(aboutContent);
		String ret = service.insertOrUpdate(blogLearn);
		JsonResult jr = JsonResult.newInstance();
		return ret == null ? jr.declareSuccess() : jr
				.declareFailure(messageService.message(ret));
	}

	@ResponseBody
	@RequestMapping(value = "/data", method = { RequestMethod.POST })
	public PageResult queryPage(
			@RequestParam(required = false, value = "userName") String userName,
			@RequestParam(required = false, value = "learnTitle") String learnTitle,
			@RequestParam(required = false, defaultValue = "1", value = "page") int page,
			@RequestParam(required = false, defaultValue = "10", value = "rp") int pageSize,
			@RequestParam(required = false, value = "sEcho") String sEcho) {
		Criteria<BlogLearn> param = new Criteria<BlogLearn>();
		param = validGroup(param);
		if (!StringUtil.isNullOrBlank(userName)) {
			param.addParam("userName_like", userName);
		}
		if (!StringUtil.isNullOrBlank(learnTitle)) {
			param.addParam("learnTitle_like", learnTitle);
		}
		param.setPageSize(pageSize).setStartIndex(page);
		param.setOrderBy(" create_time desc");
		List<BlogLearn> list = service.queryPage(param);
		if (list != null) {
			for (BlogLearn blogLearn : list) {
				blogLearn.setLearnImg(String.format(UrlConstant.RESOURCE_URL, blogLearn.getLearnImg()));
			}
		}
		PageResult pageResult = new PageResult();
		pageResult.setsEcho(sEcho);
		pageResult.setAaData(sysDictService.translateToMapList(list));
		pageResult.setiTotalDisplayRecords(service.queryPageCount(param));
		pageResult
				.setiTotalRecords(service.queryPageCount(param.clearParams()));
		return pageResult;
	}

	@RequestMapping(value = "/addPage")
	public String detail(@RequestParam(required = false) String id, ModelMap map) {
		BlogLearn blogLearn = service.queryEntityById(id);
		if (null != blogLearn) {
			blogLearn.setLearnImg(String.format(UrlConstant.RESOURCE_URL, blogLearn.getLearnImg()));
			map.put("blogLearn", blogLearn);
		}
		return "web/admin/blog/blogLearnDetail";
	}

	@ResponseBody
	@RequestMapping(value = "/del", method = { RequestMethod.POST })
	public JsonResult DeleteEntity(@RequestParam String id) {
		String ret = service.deleteById(id);
		if (StringUtil.isNullOrBlank(ret)) {
			fileBll.deleteFile(service.queryEntityById(id).getLearnImg());
		}
		JsonResult jr = JsonResult.newInstance();
		return ret == null ? jr.declareSuccess() : jr.declareFailure(ret);
	}

}