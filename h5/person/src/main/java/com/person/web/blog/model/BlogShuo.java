package com.person.web.blog.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.person.framework.mybatis.IModel;

public class BlogShuo implements IModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 鑾峰彇 内容
	 */
	public String getShuoContent() {
		return shuoContent;
	}

	/**
	 * 璁剧疆 内容
	 */
	public void setShuoContent(String shuoContent) {
		this.shuoContent = shuoContent;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	private String id;

	private String userId;

	/**
	 * 内容
	 */
	private String shuoContent;
	private String userName;
	private String userSign;

	private Date createTime;

	public String getUserSign() {
		return userSign;
	}

	public void setUserSign(String userSign) {
		this.userSign = userSign;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}