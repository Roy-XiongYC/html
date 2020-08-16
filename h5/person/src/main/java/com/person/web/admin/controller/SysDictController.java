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
import com.person.web.admin.model.SysDict;
import com.person.web.admin.service.ISysDictService;

@Controller
@RequestMapping(value = "admin/sysDict")
public class SysDictController extends AdminAppController<SysDict> {

	@Autowired
	private ISysDictService service;

	@RequestMapping(value = "/listPage", method = { RequestMethod.GET })
	public String showList() {
		return "web/admin/system/sysDictList";
	}

	@ResponseBody
	@RequestMapping(value = "/modify", method = { RequestMethod.POST })
	public JsonResult modify(@RequestParam(required = false) String id,
			@RequestParam(required = true) String dictCode,
			@RequestParam(required = true) String dictValue,
			@RequestParam(required = true) String dictRemark) {
		SysDict sysDict = new SysDict();
		sysDict.setId(id);
		sysDict.setDictCode(dictCode);
		sysDict.setDictValue(dictValue);
		sysDict.setDictRemark(dictRemark);
		String ret = service.insertOrUpdate(sysDict);
		JsonResult jr = JsonResult.newInstance();
		return ret == null ? jr.declareSuccess() : jr
				.declareFailure(messageService.message(ret));
	}

	@ResponseBody
	@RequestMapping(value = "/data", method = { RequestMethod.POST })
	public PageResult queryPage(
			@RequestParam(required = false, value = "dictCode") String dictCode,
			@RequestParam(required = false, defaultValue = "1", value = "page") int page,
			@RequestParam(required = false, defaultValue = "10", value = "rp") int pageSize,
			@RequestParam(required = false, value = "sEcho") String sEcho) {
		Criteria<SysDict> param = new Criteria<SysDict>();
		if (!StringUtil.isNullOrBlank(dictCode)) {
			param.addParam("dictCode", dictCode);
		}
		param.setPageSize(pageSize).setStartIndex(page);
		PageResult pageResult = new PageResult();
		pageResult.setsEcho(sEcho);
		pageResult.setAaData(service.queryPageList(param));
		pageResult.setiTotalDisplayRecords(service.queryPageCount(param));
		pageResult.setiTotalRecords(service.queryPageCount(param.clearParams()));
		return pageResult;
	}

	@RequestMapping(value = "/addPage")
	public String detail(@RequestParam(required = false) String id, ModelMap map) {
		SysDict sysDict = service.queryEntityById(id);
		if (null != sysDict) {
			map.put("sysDict", sysDict);
		}
		return "web/admin/system/sysDictDetail";
	}

	@ResponseBody
	@RequestMapping(value = "/del", method = { RequestMethod.POST })
	public JsonResult DeleteEntity(@RequestParam(required = true) String id) {
		String ret = service.deleteById(id);
		JsonResult jr = JsonResult.newInstance();
		return ret == null ? jr.declareSuccess() : jr.declareFailure(ret);
	}

}