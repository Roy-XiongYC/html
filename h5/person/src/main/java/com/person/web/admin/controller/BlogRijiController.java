package com.person.web.admin.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.person.framework.constant.DefaultConstant;
import com.person.framework.constant.UrlConstant;
import com.person.framework.mybatis.Criteria;
import com.person.framework.utils.DateUtil;
import com.person.framework.utils.StringUtil;
import com.person.framework.web.AdminAppController;
import com.person.framework.web.JsonResult;
import com.person.framework.web.PageResult;
import com.person.web.blog.model.BlogRiji;
import com.person.web.blog.service.IBlogRijiService;
import com.person.web.blog.service.ISequenceService;
import com.person.web.fs.service.FileService;

@Controller
@RequestMapping(value = "admin/blogRiji")
public class BlogRijiController extends AdminAppController<BlogRiji> {

	@Autowired
	private IBlogRijiService service;
	@Autowired
	private ISequenceService sequenceService;
	@Autowired
	private FileService fileBll;

	@RequestMapping(value = "/listPage", method = { RequestMethod.GET })
	public String showList() {
		return "web/admin/blog/blogRijiList";
	}

	@ResponseBody
	@RequestMapping(value = "/modify", method = { RequestMethod.POST })
	public JsonResult modify(@RequestParam(required = false) String userId,
			@RequestParam(required = false) String rijiContent,
			@RequestParam(required = false) String image,
			@RequestParam(required = false) Date createTime) {
		BlogRiji blogRiji = new BlogRiji();

		blogRiji.setId(sequenceService
				.getUpdateQuerySeq(DefaultConstant.SEQ_COMMON));
		blogRiji.setUserId(getDefaultUserId(userId));
		blogRiji.setRijiContent(rijiContent);
		blogRiji.setImage(image);
		blogRiji.setCreateTime(DateUtil.getCurrentDate());
		String ret = service.insert(blogRiji);
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
		Criteria<BlogRiji> param = new Criteria<BlogRiji>();
		param = validGroup(param);
		if (!StringUtil.isNullOrBlank(userName)) {
			param.addParam("userName_like", userName);
		}
		param.setPageSize(pageSize).setStartIndex(page);
		param.setOrderBy(" create_time desc");
		List<BlogRiji> list = service.queryPage(param);
		if (list != null) {
			for (BlogRiji blogRiji : list) {
				if (!StringUtil.isNullOrBlank(blogRiji.getImage())) {
					blogRiji.setImage(String.format(UrlConstant.RESOURCE_URL, blogRiji.getImage()));
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

	@RequestMapping(value = "/addPage")
	public String detail(ModelMap map) {
		return "web/admin/blog/blogRijiDetail";
	}

	@ResponseBody
	@RequestMapping(value = "/del", method = { RequestMethod.POST })
	public JsonResult DeleteEntity(@RequestParam(required = true) String id) {
		String ret = service.deleteById(id);
		if (StringUtil.isNullOrBlank(ret)) {
			fileBll.deleteFile(service.queryEntityById(id).getImage());
		}
		JsonResult jr = JsonResult.newInstance();
		return ret == null ? jr.declareSuccess() : jr.declareFailure(ret);
	}

}