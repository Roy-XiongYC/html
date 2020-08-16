package com.person.web.admin.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.person.framework.constant.DefaultConstant;
import com.person.framework.constant.MessageConstant;
import com.person.framework.exception.BizException;
import com.person.framework.mybatis.Criteria;
import com.person.framework.service.IMessageService;
import com.person.framework.utils.DateUtil;
import com.person.framework.utils.EncryptUtil;
import com.person.framework.utils.GenerateCode;
import com.person.framework.utils.StringUtil;
import com.person.framework.web.SourceTree;
import com.person.web.admin.model.SysResource;
import com.person.web.admin.model.SysUser;
import com.person.web.admin.repository.ISysUserRepository;
import com.person.web.admin.service.ISysDictService;
import com.person.web.admin.service.ISysUserService;

public class SysUserServiceImpl implements ISysUserService {

	@Override
	public SysUser loginSys(Criteria<SysUser> param)
	{
		List<SysUser> list = sysUserRepository.queryPage(param);
		if (list.size() == 1)
		{
			return list.get(0);
		}
		else
		{
			return null;
		}
	}
	/**
	 * 菜单树重置
	 * @param list
	 * @param currentNode
	 */
	@Override
	public void initTree(List<SysResource> list, SourceTree currentNode)
	{
		List<SourceTree> listchild = isChild(currentNode, list);
		if (listchild.size() != 0)
		{
			currentNode.setChildren(listchild);
			for (int i = 0; i < listchild.size(); i++)
			{
				initTree(list, listchild.get(i));
			}

		}
	}
	
	public List<SourceTree> isChild(SourceTree currentNode, List<SysResource> list)
	{
		List<SourceTree> childList = new ArrayList<SourceTree>();
		for (SysResource resource : list)
		{
			if (!StringUtil.isNullOrBlank(resource.getParentResourceId())&& resource.getParentResourceId().equals(currentNode.getId()))
			{
				SourceTree note = new SourceTree();
				note.setId(resource.getResourceId());
				note.setState("open");
				note.setText(resource.getResourceName());
				note.setUrl(resource.getUrl()==null?"":resource.getUrl());
				note.setIconClass(resource.getIconClass());
				childList.add(note);
			}
		}
		return childList;
	}
	
	public List<SysUser> queryPage(Criteria<SysUser> param) {
		List<SysUser> list = sysUserRepository.queryPage(param);
		return list;
	}
	@Override
	public List<Map<String, Object>> queryPageList(Criteria<SysUser> param) {
		List<SysUser> list = sysUserRepository.queryPage(param);
		return sysDictService.translateToMapList(list);
	}
	public Integer queryPageCount(Criteria<SysUser> param) {
		return (Integer) sysUserRepository.queryPageCount(param);
	}

	public SysUser queryEntityById(String id) {
		return sysUserRepository.queryEntityById(id);
	}

	public String insert(SysUser record) {
		int result = sysUserRepository.insert(record);
		if (result != 1) {
			return messageService.message(MessageConstant.C00010);
		}
		return null;
	}
	@Override
	public String insertOrUpdate(SysUser record)
	{
		Criteria<SysUser> param = new Criteria<SysUser>();

		param.addParam("userName", record.getUserName()).setRecord(record);
		List<SysUser> list = sysUserRepository.queryPage(param);

		String msg = null;
		if (record.getUserId() == null || record.getUserId().length() == 0)
		{
			if (list != null && !list.isEmpty() && !list.get(0).getUserId().equals

			(record.getUserId())) { return messageService.message(MessageConstant.C00013); }
			
			record.setUserId(GenerateCode.GenerateId());
			record.setPassword(EncryptUtil.encodePassword("8888", record.getUserId()));
			Calendar c = Calendar.getInstance();
			record.setCreateTime(c.getTime());
			c.add(Calendar.DATE, 365);
			record.setPasswordExpDate(c.getTime());
			record.setPasswordRepTimes(0);

			if (sysUserRepository.insert(record) == 0)
			{
				msg = messageService.message(MessageConstant.C00010);
			}
		}
		else
		{
			record.setPassword(null);
			param.clearParams().addParam("userId", record.getUserId());
			
			//清除密码错误次数
			if(list.get(0).getUserStatus() == DefaultConstant.USER_STOP && record.getUserStatus() == DefaultConstant.USER_ACTIVE) record.setPasswordRepTimes(0);

			if (sysUserRepository.updateByCriteria(param) == 0){
				msg = messageService.message(MessageConstant.C00011);
			}
		}
		return msg;
	}

	@Override
	public String changePassword(String userId, String oldPassword, String newPassword, boolean isValid)
	{

		SysUser user = queryEntityById(userId);

		if (user == null || StringUtil.isNullOrBlank(user.getUserId()))
		{
			// 找不到指定的用户
			return messageService.message(MessageConstant.C00020);
		}
		//是否验证用户密码
			boolean validate = EncryptUtil.validatePassword(oldPassword, user.getPassword(), user.getUserId());
			if(isValid){
	
			if (!validate)
			{ // 用户密码不匹配
				return messageService.message(MessageConstant.C00021);
			}
		}

		newPassword = EncryptUtil.encodePassword(newPassword, user.getUserId());

		Date passwordExpDate = DateUtil.append(new Date(), 365);

		int i = sysUserRepository.changePassword(userId, newPassword, passwordExpDate);

		if (i != 1) { throw new BizException(messageService.message(MessageConstant.C00020)); }

		return null;
	}
	
	public String updateByCriteria(Criteria<SysUser> param) {
		int result = sysUserRepository.updateByCriteria(param);
		if (result != 1) {
			return messageService.message(MessageConstant.C00011);
		}
		return null;
	}

	public String deleteById(String id) {
		int result = sysUserRepository.deleteById(id);
		if (result != 1) {
			return messageService.message(MessageConstant.C00012);
		}
		return null;
	}

	public String deleteByCriteria(Criteria<SysUser> param) {
		sysUserRepository.deleteByCriteria(param);
		return null;
	}

	public void setSysUserRepository(ISysUserRepository repository) {
		this.sysUserRepository = repository;
	}

	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	public void setSysDictService(ISysDictService sysDictService) {
		this.sysDictService = sysDictService;
	}

	private ISysUserRepository sysUserRepository;

	private IMessageService messageService;
	private ISysDictService sysDictService;
}
