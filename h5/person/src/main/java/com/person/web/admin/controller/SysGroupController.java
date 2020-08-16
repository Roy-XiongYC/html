package com.person.web.admin.controller;

import java.util.ArrayList;
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
import com.person.framework.mybatis.Criteria;
import com.person.framework.utils.DateUtil;
import com.person.framework.utils.StringUtil;
import com.person.framework.web.AdminAppController;
import com.person.framework.web.JsonResult;
import com.person.framework.web.PageResult;
import com.person.web.admin.model.SysGroup;
import com.person.web.admin.model.SysGroupUser;
import com.person.web.admin.service.ISysGroupService;
import com.person.web.admin.service.ISysGroupUserService;

@Controller
@RequestMapping(value = "admin/sysGroup")
public class SysGroupController extends AdminAppController<SysGroup> {

	@Autowired
	private ISysGroupService service;
	
	@Autowired
	private ISysGroupUserService sysGroupUserService;

	@RequestMapping(value = "/listPage", method = { RequestMethod.GET })
	public String showList() {
		return "web/admin/system/sysGroupList";
	}

	@ResponseBody
	@RequestMapping(value = "/modify", method = { RequestMethod.POST })
	public JsonResult modify(@RequestParam(required = false) String groupId,
			@RequestParam(required = false) String groupName) {
		SysGroup sysGroup = new SysGroup();
		if (StringUtil.isNullOrBlank(groupId)) {
			sysGroup.setCreateBy(getApplicationUserId());
			sysGroup.setCreateTime(DateUtil.getCurrentDate());
		}
		sysGroup.setGroupId(groupId);
		sysGroup.setGroupName(groupName);
		String ret = service.insertOrUpdate(sysGroup);
		JsonResult jr = JsonResult.newInstance();
		return ret == null ? jr.declareSuccess() : jr
				.declareFailure(messageService.message(ret));
	}

	@ResponseBody
	@RequestMapping(value = "/data", method = { RequestMethod.POST })
	public PageResult queryPage(
			@RequestParam(required = false, value = "groupName") String groupName,
			@RequestParam(required = false, defaultValue = "1", value = "page") int page,
			@RequestParam(required = false, defaultValue = "10", value = "rp") int pageSize,
			@RequestParam(required = false, value = "sEcho") String sEcho) {
		Criteria<SysGroup> param = new Criteria<SysGroup>();
		if (!StringUtil.isNullOrBlank(groupName)) {
			param.addParam("groupName_like", groupName);
		}
		List<String> status = new ArrayList<String>();
		status.add(String.valueOf(DefaultConstant.USER_ACTIVE));
		status.add(String.valueOf(DefaultConstant.USER_STOP));
		param.addParam("groupStatusIn", status);
		param.setPageSize(pageSize).setStartIndex(page);
		param.setOrderBy(" create_time desc");
		PageResult pageResult = new PageResult();
		pageResult.setsEcho(sEcho);
		pageResult.setAaData(service.queryPageList(param));
		pageResult.setiTotalDisplayRecords(service.queryPageCount(param));
		pageResult
				.setiTotalRecords(service.queryPageCount(param.clearParams()));
		return pageResult;
	}

	@RequestMapping(value = "/addPage")
	public String detail(@RequestParam(required = false) String groupId, ModelMap map) {
		SysGroup sysGroup = service.queryEntityById(groupId);
		if (null != sysGroup) {
			map.put("sysGroup", sysGroup);
		}
		return "web/admin/system/sysGroupDetail";
	}
	
	@ResponseBody
	@RequestMapping(value = "/del", method = { RequestMethod.POST })
	public JsonResult DeleteEntity(@RequestParam(required = true) String groupId) {
		JsonResult jr = JsonResult.newInstance();
		Criteria<SysGroupUser> uparam = new Criteria<SysGroupUser>();
		uparam.addParam("groupId", groupId);
		List<SysGroupUser> list = sysGroupUserService.queryPage(uparam);
		if (list != null && list.size()>0) {
			return jr.declareFailure(messageService.message(MessageConstant.C00025));
		}
		Criteria<SysGroup> param = new Criteria<SysGroup>();
		param.addParam("groupId", groupId);
		SysGroup record = service.queryEntityById(groupId);
		record.setGroupStatus(DefaultConstant.USER_DELETE);
		param.setRecord(record);
		String ret = service.updateByCriteria(param);
		return ret == null ? jr.declareSuccess() : jr.declareFailure(ret);
	}
	
	
	/**
	 * 启用
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/start", method = { RequestMethod.POST }) 
	public JsonResult StartEntity(
			@RequestParam(required = true, value = "groupId") String groupId
			) {
		Criteria<SysGroup> param = new Criteria<SysGroup>();
		SysGroup sysGroup = new SysGroup();
		sysGroup.setGroupStatus(DefaultConstant.USER_ACTIVE);
		param.addParam("groupId", groupId);
		param.setRecord(sysGroup);
		String ret = null;
		if (!StringUtil.isNullOrBlank(service.updateByCriteria(param))) {
			ret = MessageConstant.C00011;
		}

		JsonResult jr = JsonResult.newInstance();
		return ret == null ? jr.declareSuccess() : jr.declareFailure(messageService
				.message(ret));
	}
	
	/**
	 * 停用
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/stop", method = { RequestMethod.POST }) 
	public JsonResult StopEntity(
			@RequestParam(required = true, value = "groupId") String groupId
			) {
		Criteria<SysGroup> param = new Criteria<SysGroup>();
		SysGroup sysGroup = new SysGroup();
		sysGroup.setGroupStatus(DefaultConstant.USER_STOP);
		param.addParam("groupId", groupId);
		param.setRecord(sysGroup);
		String ret = null;
		if (!StringUtil.isNullOrBlank(service.updateByCriteria(param))) {
			ret = MessageConstant.C00011;
		}

		JsonResult jr = JsonResult.newInstance();
		return ret == null ? jr.declareSuccess() : jr.declareFailure(messageService
				.message(ret));
	}
	
	/**
	 * 配置权限页面
	 * @param groupId
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/resource")
	public String resource(@RequestParam(required = false) String groupId, ModelMap map) {
		SysGroup sysGroup = service.queryEntityById(groupId);
		if (null != sysGroup) {
			map.put("sysGroup", sysGroup);
		}
		return "web/admin/system/groupResource";
	}

}