package com.person.web.blog.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.person.framework.mybatis.IModel;

public class BlogLink implements IModel {
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
	 * 鑾峰彇 连接名称
	 */
	public String getLinkName() {
		return linkName;
	}

	/**
	 * 璁剧疆 连接名称
	 */
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	/**
	 * 鑾峰彇 连接地址
	 */
	public String getLinkUrl() {
		return linkUrl;
	}

	/**
	 * 璁剧疆 连接地址
	 */
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	private String id;

	private String userId;
	private String userName;

	/**
	 * 连接名称
	 */
	private String linkName;

	/**
	 * 连接地址
	 */
	private String linkUrl;

	private Date createTime;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}