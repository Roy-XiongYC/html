package com.person.web.blog.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.person.framework.mybatis.IModel;

public class BlogUser implements IModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 鑾峰彇 名称
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 璁剧疆 名称
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 鑾峰彇 用户表示
	 */
	public String getUserSign() {
		return userSign;
	}

	/**
	 * 璁剧疆 用户表示
	 */
	public void setUserSign(String userSign) {
		this.userSign = userSign;
	}

	/**
	 * 鑾峰彇 职业 多个使用、号隔开
	 */
	public String getOccupation() {
		return occupation;
	}

	/**
	 * 璁剧疆 职业 多个使用、号隔开
	 */
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	/**
	 * 鑾峰彇 简介
	 */
	public String getBrief() {
		return brief;
	}

	/**
	 * 璁剧疆 简介
	 */
	public void setBrief(String brief) {
		this.brief = brief;
	}

	/**
	 * 鑾峰彇 签名
	 */
	public String getSignature() {
		return signature;
	}

	/**
	 * 璁剧疆 签名
	 */
	public void setSignature(String signature) {
		this.signature = signature;
	}

	/**
	 * 鑾峰彇 主题
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 璁剧疆 主题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 鑾峰彇 头像
	 */
	public String getImage() {
		return image;
	}

	/**
	 * 璁剧疆 头像
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * 鑾峰彇 qq
	 */
	public String getQq() {
		return qq;
	}

	/**
	 * 璁剧疆 qq
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	private String userId;

	/**
	 * 名称
	 */
	private String userName;

	/**
	 * 用户表示
	 */
	private String userSign;

	/**
	 * 职业 多个使用、号隔开
	 */
	private String occupation;

	/**
	 * 简介
	 */
	private String brief;

	/**
	 * 签名
	 */
	private String signature;

	/**
	 * 主题
	 */
	private String title;

	/**
	 * 头像
	 */
	private String image;

	/**
	 * qq
	 */
	private String qq;

	private Date createTime;
	/**
	 * 关于我的内容
	 */
	private String aboutContent;
	public String getAboutContent() {
		return aboutContent;
	}

	public void setAboutContent(String aboutContent) {
		this.aboutContent = aboutContent;
	}
}