package com.person.web.blog.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.person.framework.mybatis.IModel;

public class BlogXc implements IModel {
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
	 * 鑾峰彇 相册图片地址
	 */
	public String getImg() {
		return img;
	}

	/**
	 * 璁剧疆 相册图片地址
	 */
	public void setImg(String img) {
		this.img = img;
	}

	/**
	 * 鑾峰彇 相册图片宽度
	 */
	public Integer getImgWidth() {
		return imgWidth;
	}

	/**
	 * 璁剧疆 相册图片宽度
	 */
	public void setImgWidth(Integer imgWidth) {
		this.imgWidth = imgWidth;
	}

	/**
	 * 鑾峰彇 相册图片高度
	 */
	public Integer getImgHeight() {
		return imgHeight;
	}

	/**
	 * 璁剧疆 相册图片高度
	 */
	public void setImgHeight(Integer imgHeight) {
		this.imgHeight = imgHeight;
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
	 * 相册图片地址
	 */
	private String img;

	/**
	 * 相册图片宽度
	 */
	private Integer imgWidth;

	/**
	 * 相册图片高度
	 */
	private Integer imgHeight;

	private Date createTime;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}