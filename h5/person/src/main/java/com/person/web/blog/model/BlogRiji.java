package com.person.web.blog.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.person.framework.mybatis.IModel;

public class BlogRiji implements IModel {
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
	public String getRijiContent() {
		return rijiContent;
	}

	/**
	 * 璁剧疆 内容
	 */
	public void setRijiContent(String rijiContent) {
		this.rijiContent = rijiContent;
	}

	/**
	 * 鑾峰彇 图片
	 */
	public String getImage() {
		return image;
	}

	/**
	 * 璁剧疆 图片
	 */
	public void setImage(String image) {
		this.image = image;
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
	 * 内容
	 */
	private String rijiContent;

	/**
	 * 图片
	 */
	private String image;

	private Date createTime;

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

}