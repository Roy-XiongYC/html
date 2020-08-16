package com.person.web.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.person.framework.mybatis.Criteria;
import com.person.framework.utils.DateUtil;
import com.person.framework.utils.StringUtil;
import com.person.framework.web.AdminAppController;
import com.person.framework.web.JsonResult;
import com.person.framework.web.PageResult;
import com.person.web.admin.model.SchoolProject;
import com.person.web.admin.service.ISchoolProjectService;

@Controller
@RequestMapping(value = "admin/schoolProject")
public class SchoolProjectController extends AdminAppController<SchoolProject> {

	@Autowired
	private ISchoolProjectService service;

	@RequestMapping(value = "/listPage", method = { RequestMethod.GET })
	public String showList() {
		return "web/admin/school/projectList";
	}

	@ResponseBody
	@RequestMapping(value = "/modify", method = { RequestMethod.POST })
	public JsonResult modify(@RequestParam(required = false) String projectId,
			@RequestParam(required = true) String projectName) {
		SchoolProject schoolProject = new SchoolProject();

		schoolProject.setProjectId(projectId);
		schoolProject.setProjectName(projectName);
		schoolProject.setCreateBy(getApplicationUserId());
		schoolProject.setCreateTime(DateUtil.getCurrentDate());
		String ret = service.insertOrUpdate(schoolProject);
		JsonResult jr = JsonResult.newInstance();
		return ret == null ? jr.declareSuccess() : jr
				.declareFailure(messageService.message(ret));
	}

	@ResponseBody
	@RequestMapping(value = "/data", method = { RequestMethod.POST })
	public PageResult queryPage(
			@RequestParam(required = false, defaultValue = "1", value = "page") int page,
			@RequestParam(required = false, defaultValue = "10", value = "rp") int pageSize,
			@RequestParam(required = false, value = "sEcho") String sEcho,
			@RequestParam(required = false) String projectName) {
		Criteria<SchoolProject> param = new Criteria<SchoolProject>();
		param.setPageSize(pageSize).setStartIndex(page);
		param.addParam("projectNameLike", projectName);
		param.setOrderBy(" create_time desc ");
		
		PageResult pageResult = new PageResult();
		pageResult.setsEcho(sEcho);
		pageResult.setAaData(service.queryPageList(param));
		pageResult.setiTotalDisplayRecords(service.queryPageCount(param));
		pageResult.setiTotalRecords(service.queryPageCount(param.clearParams()));
		return pageResult;
	}

	@RequestMapping(value = "/addPage")
	public String detail(@RequestParam(required = false) String id, ModelMap map) {
		if(!StringUtil.isNullOrBlank(id)) {
			SchoolProject schoolProject = service.queryEntityById(id);
			if (null != schoolProject) {
				map.put("schoolProject", schoolProject);
			}
		}
		return "web/admin/school/projectDetail";
	}

	@ResponseBody
	@RequestMapping(value = "/del", method = { RequestMethod.POST })
	public JsonResult DeleteEntity(@RequestParam(required = true) String id) {
		String ret = service.deleteById(id);
		JsonResult jr = JsonResult.newInstance();
		return ret == null ? jr.declareSuccess() : jr.declareFailure(ret);
	}

}