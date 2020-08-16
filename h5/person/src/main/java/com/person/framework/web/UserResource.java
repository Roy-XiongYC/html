package com.person.framework.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.person.framework.utils.StringUtil;
import com.person.web.admin.model.SysResource;
import com.person.web.admin.model.SysUser;
import com.person.web.admin.service.ISysResourceService;
import com.person.web.admin.service.ISysUserService;
/**
 * 获取用户菜单信息
 * @author liulq
 *
 */
public class UserResource {
	
	private static ISysUserService service;
	
	private static ISysResourceService sourceService;

	@Autowired
	public void setService(ISysUserService service) {
		UserResource.service = service;
	}
	@Autowired
	public void setSourceService(ISysResourceService sourceService) {
		UserResource.sourceService = sourceService;
	}

	private UserResource(){}
	
	public static ApplicationResourceInfo getUserResource(SysUser user,String path){
		//获取用户菜单权限列表
		List<SourceTree> lst = SourceList(user);
		if(lst != null && lst.size() > 0){
			
			ApplicationResourceInfo ari = new ApplicationResourceInfo(user.getUserId(),null,null,lst);
			//设置面包屑功能
			if(StringUtils.isNotBlank(path))
				ari.setBreadcrumb(getBreadcrumb(lst, path));
				
			return ari;
		}else{
			return null;
		}
	}
	
	public static SysUser getUserById(String userId){
		SysUser user = service.queryEntityById(userId);
		return user;
	}
	
	private static List<SourceTree> SourceList(SysUser user){
		List<SourceTree> rootList = null;
		if(user != null&&!StringUtil.isNullOrBlank(user.getGroupName())){
			//设置全局用户信息  传统全局用户信息
			AppAdminUserInfo applicationUserInfo = new AppAdminUserInfo(user.getUserId(),user.getUserName(),user.getGroupName(), user.getIp());
			AppAdminContext.setUserInfo(applicationUserInfo); 
			List<SysResource> list = sourceService.queryAllByUser(user);
			rootList = new ArrayList<SourceTree>();
			if (list == null || list.size() == 0) {
				return rootList;
			}
			// 去除按钮
			for (int i = 0;i<list.size();i++) {
				if ("2".equals(list.get(i).getResourceType())) {
					list.remove(i);
					i--;
				}
			}

			for (SysResource sysResource : list) {
				if (StringUtil.isNullOrBlank(sysResource.getParentResourceId())) {
					SourceTree note = new SourceTree();
					note.setId(sysResource.getResourceId());
					note.setText(sysResource.getResourceName());
					note.setUrl(sysResource.getUrl()==null?"":sysResource.getUrl());
					note.setIconClass(sysResource.getIconClass());
					rootList.add(note);
				}
			}
			for (int i = 0; i < rootList.size(); i++) {
				SourceTree note = rootList.get(i);
				service.initTree(list, note);
			}
		}
		return rootList;
	}
	
	@SuppressWarnings("unused")
	private static String getUrls(List<SourceTree> list,StringBuilder sb){
		for(SourceTree st : list){
			if(StringUtils.isNotBlank(st.getUrl())){
				sb.append(st.getUrl()).append(",");
			}
			if(st.getChildren() != null){
				getUrls(st.getChildren(),sb);
			}
		}
		return sb.toString();
	}
	
	
	/**
	 * 获取面包屑
	 * @return
	 */
	public static Map<String, Object> getBreadcrumb(List<SourceTree> list,String requestURI){
		if(list == null){
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<SourceTree> lst = new ArrayList<SourceTree>();
		SourceTree sTree = null;
		boolean result = false; //是否找到匹配菜单
		//寻找当前路径
		for (SourceTree st : list) {
			if(result) break;
			List<SourceTree> children1 = st.getChildren();
			sTree = new SourceTree();
			sTree.setId(st.getId());
			sTree.setText(st.getText());
			sTree.setUrl(st.getUrl());
			if(children1 != null && children1.size() > 0){
				for(SourceTree st1 : children1){
					SourceTree sTree1 = new SourceTree();
					List<SourceTree> sList1 = new ArrayList<SourceTree>();
					sTree1.setId(st1.getId());
					sTree1.setText(st1.getText());
					sTree1.setUrl(st1.getUrl());
					sList1.add(sTree1);
					sTree.setChildren(sList1);
					
					if(StringUtils.isNotBlank(st1.getUrl()) && st1.getUrl().contains(requestURI)){
						result = true;
						break;
					}
				}
			}
			
		}
		
		if(result){
			lst.add(sTree);
			map.put("breadcrumb", lst);
		}
		return map;
		
	}
}
