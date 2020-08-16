package com.person.web.blog.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.person.framework.mybatis.IModel;

public class BlogAbout implements IModel {
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
	public String getAboutContent() {
		return aboutContent;
	}

	/**
	 * 璁剧疆 内容
	 */
	public void setAboutContent(String aboutContent) {
		this.aboutContent = aboutContent;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getOpeateTime() {
		return opeateTime;
	}

	public void setOpeateTime(Date opeateTime) {
		this.opeateTime = opeateTime;
	}

	private String id;

	private String userId;

	/**
	 * 内容
	 */
	private String aboutContent;

	private Date createTime;

	private Date opeateTime;

}