package com.person.web.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.person.framework.mybatis.Criteria;
import com.person.framework.utils.StringUtil;
import com.person.framework.web.AdminAppController;
import com.person.framework.web.PageResult;
import com.person.web.admin.model.SysResource;
import com.person.web.admin.service.ISysResourceService;

@Controller
@RequestMapping(value = "admin/sysResource")
public class SysResourceController extends AdminAppController<SysResource> {

	@Autowired
	private ISysResourceService service;

	@RequestMapping(value = "/listPage", method = { RequestMethod.GET })
	public String showList() {
		return "web/admin/system/sysResourceList";
	}

	@ResponseBody
	@RequestMapping(value = "/data", method = { RequestMethod.POST })
	public PageResult queryPage(
			@RequestParam(required = false, value = "resourceName") String resourceName,
			@RequestParam(required = false, defaultValue = "1", value = "page") int page,
			@RequestParam(required = false, defaultValue = "10", value = "rp") int pageSize,
			@RequestParam(required = false, value = "sEcho") String sEcho) {
		Criteria<SysResource> param = new Criteria<SysResource>();
		if(!StringUtil.isNullOrBlank(resourceName)){
			param.addParam("resourceName_like", resourceName);
		}
		param.setPageSize(pageSize).setStartIndex(page);
		param.setOrderBy(" resource_id,parent_resource_id,order_number");
		PageResult pageResult = new PageResult();
		pageResult.setsEcho(sEcho);
		pageResult.setAaData(service.queryPageList(param));
		pageResult.setiTotalDisplayRecords(service.queryPageCount(param));
		pageResult
				.setiTotalRecords(service.queryPageCount(param.clearParams()));
		return pageResult;
	}
}