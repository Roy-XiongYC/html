package com.person.web.blog.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.person.framework.mybatis.IModel;

public class BlogGuest implements IModel {
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
	 * 鑾峰彇 留言内容
	 */
	public String getGuestContent() {
		return guestContent;
	}

	/**
	 * 璁剧疆 留言内容
	 */
	public void setGuestContent(String guestContent) {
		this.guestContent = guestContent;
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
	 * 留言内容
	 */
	private String guestContent;

	private Date createTime;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}