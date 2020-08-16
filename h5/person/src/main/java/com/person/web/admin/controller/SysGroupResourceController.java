package com.person.web.admin.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.person.framework.mybatis.Criteria;
import com.person.framework.web.AdminAppController;
import com.person.framework.web.JsonResult;
import com.person.framework.web.ZTreeResult;
import com.person.web.admin.model.SysGroupResource;
import com.person.web.admin.model.SysResource;
import com.person.web.admin.service.ISysGroupResourceService;
import com.person.web.admin.service.ISysResourceService;

@Controller
@RequestMapping("admin/groupResource")
public class SysGroupResourceController extends AdminAppController<SysGroupResource>{

	/**
	 * 权限页面数据
	 * @param groupId
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/resourceData" , method = {RequestMethod.POST})
	public List<ZTreeResult> resourceData(
			@RequestParam(required = true) String groupId,
			@RequestParam(required = true) String accessAuth
			) {
		List<ZTreeResult> treeList = new ArrayList<ZTreeResult>();
		ZTreeResult tree = null;
		Criteria<SysResource> param = new Criteria<SysResource>();
		param.addParam("groupId", groupId);
		param.addParam("accessAuth", accessAuth);
		List<SysResource> list = resourceService.queryByGroup(param);
		for (SysResource sysResource : list) {
			tree = new ZTreeResult();
			tree.setName(sysResource.getResourceId()+"-"+sysResource.getResourceName());
			tree.setId(sysResource.getResourceId());
			tree.setpId(sysResource.getParentResourceId());
			treeList.add(tree);
		}
		return treeList;
	}
	
	@ResponseBody
	@RequestMapping(value = "saveResource" , method = {RequestMethod.POST})
	public JsonResult saveResource(
			@RequestParam(required = true) String resource,
			@RequestParam(required = true) String groupId){
		JsonResult jr = JsonResult.newInstance();
		try {
			groupResourceService.insertResource(groupId, resource, getApplicationUserId());
		} catch (Exception e) {
			jr.declareFailure(null);
		}
		return jr.declareSuccess();
	}
	
	@Autowired
	private ISysResourceService resourceService;
	@Autowired
	private ISysGroupResourceService groupResourceService;
}
