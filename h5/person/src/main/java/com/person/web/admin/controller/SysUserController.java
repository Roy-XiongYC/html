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
import com.person.framework.utils.EncryptUtil;
import com.person.framework.utils.StringUtil;
import com.person.framework.web.AdminAppController;
import com.person.framework.web.JsonResult;
import com.person.framework.web.PageResult;
import com.person.web.admin.model.SysGroupUser;
import com.person.web.admin.model.SysUser;
import com.person.web.admin.service.ISysGroupUserService;
import com.person.web.admin.service.ISysUserService;
import com.person.web.blog.service.ISequenceService;

@Controller
@RequestMapping("admin")
public class SysUserController extends AdminAppController<SysUser>{

	@RequestMapping("userPage")
	public String showPage(){
		return "web/admin/system/sysUserList";
	}
	
	@ResponseBody
	@RequestMapping(value = "userData",method = {RequestMethod.POST})
	public PageResult userData(
			@RequestParam(required = false, value = "sEcho") String sEcho,
			@RequestParam(required = false, defaultValue = "1", value = "page") int page,
			@RequestParam(required = false, defaultValue = "10", value = "rp") int pageSize,
			@RequestParam String userName){
		Criteria<SysUser> param = new Criteria<SysUser>();
		if(!StringUtil.isNullOrBlank(userName)){
			param.addParam("userNameLike", userName);
		}
		List<String> status = new ArrayList<String>();
		status.add(String.valueOf(DefaultConstant.USER_ACTIVE));
		status.add(String.valueOf(DefaultConstant.USER_STOP));
		param.addParam("userStatusIn", status);
		param.setStartIndex(page).setPageSize(pageSize);
		param.setOrderBy(" create_time desc ");
		PageResult pageResult = new PageResult();
		pageResult.setsEcho(sEcho);
		pageResult.setAaData(sysUserService.queryPageList(param));
		pageResult.setiTotalDisplayRecords(sysUserService.queryPageCount(param));
		pageResult.setiTotalRecords(sysUserService.queryPageCount(param.clearParams()));
		return pageResult;
	}
	
	/**
	 * 启用
	 * @param userIdList
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/user/start", method = { RequestMethod.POST }) 
	public JsonResult StartEntity(
			@RequestParam(required = true, value = "userId") String userId
			) {
		Criteria<SysUser> param = new Criteria<SysUser>();
		SysUser sysUser = new SysUser();
		sysUser.setUserStatus(DefaultConstant.USER_ACTIVE);
		param.addParam("userId", userId);
		param.setRecord(sysUser);
		String ret = null;
		if (!StringUtil.isNullOrBlank(sysUserService.updateByCriteria(param))) {
			ret = MessageConstant.C00011;
		}

		JsonResult jr = JsonResult.newInstance();
		return ret == null ? jr.declareSuccess() : jr.declareFailure(messageService
				.message(ret));
	}
	
	/**
	 * 停用
	 * @param userIdList
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/user/stop", method = { RequestMethod.POST }) 
	public JsonResult StopEntity(
			@RequestParam(required = true, value = "userId") String userId
			) {
		Criteria<SysUser> param = new Criteria<SysUser>();
		SysUser sysUser = new SysUser();
		sysUser.setUserStatus(DefaultConstant.USER_STOP);
		param.addParam("userId", userId);
		param.setRecord(sysUser);
		String ret = null;
		if (!StringUtil.isNullOrBlank(sysUserService.updateByCriteria(param))) {
			ret = MessageConstant.C00011;
		}

		JsonResult jr = JsonResult.newInstance();
		return ret == null ? jr.declareSuccess() : jr.declareFailure(messageService
				.message(ret));
	}
	
	
	/**
	 * 删除
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/user/del", method = { RequestMethod.POST }) 
	public JsonResult DelEntity(
			@RequestParam(required = true, value = "userId") String userId
			) {
		JsonResult jr = JsonResult.newInstance();
		if (userId.equals(DefaultConstant.DEFAULT_ADMIN_USER)) {
			return jr.declareFailure(messageService
					.message(MessageConstant.C00000));
		
		}
		String ret = sysUserService.deleteById(userId);
		return ret == null ? jr.declareSuccess() : jr.declareFailure(messageService
				.message(ret));
	}
	
	/**
	 * 新增页面
	 * @return
	 */
	@RequestMapping(value = "/user/addPage", method = { RequestMethod.GET }) 
	public String addPage(ModelMap map,
			@RequestParam(required = false, value = "userId") String userId
			) {
		if (!StringUtil.isNullOrBlank(userId)) {
			SysUser user = sysUserService.queryEntityById(userId);
			map.put("user", user);
		}
		return "web/admin/system/addUser";
		
	}
	
	/**
	 * 新增修改
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/user/modify", method = { RequestMethod.POST }) 
	public JsonResult ModifyEntity(
			@RequestParam(required = false, value = "userId") String userId,
			@RequestParam(required = true, value = "userName") String userName,
			@RequestParam(required = true, value = "mpNo") String mpNo,
			@RequestParam(required = true, value = "emailAddr") String emailAddr
			) {
		SysUser sysUser = new SysUser();
		String ret = null;
		sysUser.setUserName(userName);
		sysUser.setEmailAddr(emailAddr);
		sysUser.setMpNo(mpNo);
		if (!StringUtil.isNullOrBlank(userId)) {
			sysUser.setUserId(userId);
		}else {
			sysUser.setCreateBy(getApplicationUserId());
		}
		ret = sysUserService.insertOrUpdate(sysUser);
		JsonResult jr = JsonResult.newInstance();
		return ret == null ? jr.declareSuccess() : jr.declareFailure(messageService
				.message(ret));
	}
	
	/**
	 * 重置密码
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/user/password", method = { RequestMethod.POST }) 
	public JsonResult PasswordEntity(
			@RequestParam(required = true, value = "userId") String userId
			) {
		JsonResult jr = JsonResult.newInstance();
		SysUser sysUser = null;
		String ret = null;
		if (StringUtil.isNullOrBlank(userId)) {
			return jr.declareFailure(messageService.message(MessageConstant.C00020));
		}
		sysUser = sysUserService.queryEntityById(userId);
		if (sysUser == null) {
			return jr.declareFailure(messageService.message(MessageConstant.C00020));
		}
		sysUser.setPassword(EncryptUtil.encodePassword("8888", userId));
		ret = sysUserService.insertOrUpdate(sysUser);
		
		return ret == null ? jr.declareSuccess() : jr.declareFailure(messageService
				.message(ret));
	}
	
	/**
	 * 个人信息
	 * @return
	 */
	@RequestMapping(value = "/user/userInfo", method = { RequestMethod.GET }) 
	public String userInfo(ModelMap map,
			@RequestParam(required = true, value = "userId") String userId
			) {
		map.put("user", sysUserService.queryEntityById(userId));
		return "web/admin/system/userInfo";
		
	}
	
	/**
	 * 修改密码页面
	 * @return
	 */
	@RequestMapping(value = "/user/passPage", method = { RequestMethod.GET }) 
	public String passPage(ModelMap map,
			@RequestParam(required = true, value = "userId") String userId
			) {
		map.put("userId", userId);
		return "web/admin/system/changePass";
		
	}
	
	/**
	 * 修改密码
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/user/changePass", method = { RequestMethod.POST }) 
	public JsonResult ChangePassEntity(
			@RequestParam(required = true, value = "userId") String userId,
			@RequestParam(required = true, value = "oldPass") String oldPass,
			@RequestParam(required = true, value = "newPass") String newPass
			) {
		JsonResult jr = JsonResult.newInstance();
		String ret = sysUserService.changePassword(super.getApplicationUserId(), oldPass, newPass, true);
		
		return ret == null ? jr.declareSuccess() : jr.declareFailure(messageService
				.message(ret));
	}
	
	/**
	 * 修改用户组页面
	 * @return
	 */
	@RequestMapping(value = "/user/groupPage", method = { RequestMethod.GET }) 
	public String groupPage(ModelMap map,
			@RequestParam(required = true, value = "userId") String userId
			) {
		if (!StringUtil.isNullOrBlank(userId)) {
			SysUser user = sysUserService.queryEntityById(userId);
			map.put("user", user);
		}
		return "web/admin/system/userGroup";
		
	}
	
	/**
	 * 修改用户组
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/user/modifyGroup", method = { RequestMethod.POST }) 
	public JsonResult ModifyGroup(
			@RequestParam(required = true, value = "userId") String userId,
			@RequestParam(required = true, value = "groupId") String groupId
			) {
		String ret = null;
		SysGroupUser record = new SysGroupUser();
		record.setGroupId(groupId);
		record.setUserId(userId);
		Criteria<SysGroupUser> param = new Criteria<SysGroupUser>();
		param.addParam("userId", userId);
		List<SysGroupUser> list = groupUserService.queryPage(param);
		if (list != null && list.size()>0) {
			record.setId(list.get(0).getId());
			param.setRecord(record);
			ret = groupUserService.updateByCriteria(param);
		}else {
			record.setId(sequenceService.getUpdateQuerySeq(DefaultConstant.SEQ_COMMON));
			ret = groupUserService.insert(record);
		}
		
		JsonResult jr = JsonResult.newInstance();
		return ret == null ? jr.declareSuccess() : jr.declareFailure(messageService
				.message(ret));
	}
	
	@Autowired
	private ISysUserService sysUserService;
	@Autowired
	private ISysGroupUserService groupUserService;
	@Autowired
	private ISequenceService sequenceService;
}
