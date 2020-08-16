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
import com.person.web.admin.model.SchoolStudent;
import com.person.web.admin.service.ISchoolStudentService;

@Controller
@RequestMapping(value = "admin/schoolStudent")
public class SchoolStudentController extends AdminAppController<SchoolStudent> {

	@Autowired
	private ISchoolStudentService service;

	@RequestMapping(value = "/listPage", method = { RequestMethod.GET })
	public String showList() {
		return "web/admin/school/studentList";
	}

	@ResponseBody
	@RequestMapping(value = "/modify", method = { RequestMethod.POST })
	public JsonResult modify(@RequestParam(required = false) String studentId,
			@RequestParam(required = false) String studentName,
			@RequestParam(required = false) String studentClass) {
		SchoolStudent schoolStudent = new SchoolStudent();

		schoolStudent.setStudentId(studentId);
		schoolStudent.setStudentName(studentName);
		schoolStudent.setStudentClass(studentClass);
		schoolStudent.setCreateBy(getApplicationUserId());
		schoolStudent.setCreateTime(DateUtil.getCurrentDate());
		String ret = service.insertOrUpdate(schoolStudent);
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
			@RequestParam(required = false) String studentId,
			@RequestParam(required = false) String studentName,
			@RequestParam(required = false) String studentClass) {
		Criteria<SchoolStudent> param = new Criteria<SchoolStudent>();
		param.setPageSize(pageSize).setStartIndex(page);
		param.addParam("studentIdLike", studentId);
		param.addParam("studentNameLike", studentName);
		param.addParam("studentClassLike", studentClass);
		param.setOrderBy(" create_time desc ");
		
		PageResult pageResult = new PageResult();
		pageResult.setsEcho(sEcho);
		pageResult.setAaData(service.queryPageList(param));
		pageResult.setiTotalDisplayRecords(service.queryPageCount(param));
		pageResult
				.setiTotalRecords(service.queryPageCount(param.clearParams()));
		return pageResult;
	}

	@RequestMapping(value = "/addPage")
	public String detail(@RequestParam(required = false) String id, ModelMap map) {
		if(!StringUtil.isNullOrBlank(id)){
			SchoolStudent schoolStudent = service.queryEntityById(id);
			if (null != schoolStudent) {
				map.put("schoolStudent", schoolStudent);
			}
		}
		return "web/admin/school/studentDetail";
	}

	@ResponseBody
	@RequestMapping(value = "/del", method = { RequestMethod.POST })
	public JsonResult DeleteEntity(@RequestParam(required = true) String id) {
		String ret = service.deleteById(id);
		JsonResult jr = JsonResult.newInstance();
		return ret == null ? jr.declareSuccess() : jr.declareFailure(ret);
	}

}