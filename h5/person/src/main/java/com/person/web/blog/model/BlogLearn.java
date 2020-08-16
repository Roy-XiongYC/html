package com.person.web.blog.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.person.framework.annotation.Dict;
import com.person.framework.mybatis.IModel;

public class BlogLearn implements IModel {
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
	 * 鑾峰彇 标题
	 */
	public String getLearnTitle() {
		return learnTitle;
	}

	/**
	 * 璁剧疆 标题
	 */
	public void setLearnTitle(String learnTitle) {
		this.learnTitle = learnTitle;
	}

	/**
	 * 鑾峰彇 发布时间
	 */
	public Date getLearnTime() {
		return learnTime;
	}

	/**
	 * 璁剧疆 发布时间
	 */
	public void setLearnTime(Date learnTime) {
		this.learnTime = learnTime;
	}

	/**
	 * 鑾峰彇 作者
	 */
	public String getLearnAuth() {
		return learnAuth;
	}

	/**
	 * 璁剧疆 作者
	 */
	public void setLearnAuth(String learnAuth) {
		this.learnAuth = learnAuth;
	}

	/**
	 * 鑾峰彇 文章转载地
	 */
	public String getLearnAdd() {
		return learnAdd;
	}

	/**
	 * 璁剧疆 文章转载地
	 */
	public void setLearnAdd(String learnAdd) {
		this.learnAdd = learnAdd;
	}

	/**
	 * 鑾峰彇 是否推荐 0-否 1-是
	 */
	public Integer getLearnIs() {
		return learnIs;
	}

	/**
	 * 璁剧疆 是否推荐 0-否 1-是
	 */
	public void setLearnIs(Integer learnIs) {
		this.learnIs = learnIs;
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

	public String getLearnImg() {
		return learnImg;
	}

	public void setLearnImg(String learnImg) {
		this.learnImg = learnImg;
	}

	private String id;

	private String userId;
	private String userName;

	/**
	 * 标题
	 */
	private String learnTitle;
	/**
	 * 图片
	 */
	private String learnImg;

	/**
	 * 发布时间
	 */
	private Date learnTime;

	/**
	 * 作者
	 */
	private String learnAuth;

	/**
	 * 文章转载地
	 */
	private String learnAdd;

	/**
	 * 是否推荐 0-否 1-是
	 */
	@Dict(dictCode="whetherIs")
	private Integer learnIs;

	/**
	 * 内容
	 */
	private String aboutContent;

	private Date createTime;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}