package com.person.framework.web;

import com.person.framework.mybatis.IModel;

public class AppAdminUserInfo implements IModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AppAdminUserInfo(String userId,String userName,String groupName,String ipAddress) {
		super();
		this.userId=userId;
		this.userName=userName;
		this.groupName=groupName;
		this.ipAddress=ipAddress;
	}
	
	private String userId;
	
	private String userName;
	/**
	 * 组名
	 */
	private String groupName;
	
	private String ipAddress;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	
}
