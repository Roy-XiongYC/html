package com.person.framework.web;

import java.util.ArrayList;
import java.util.List;

import com.person.framework.mybatis.IModel;
import com.person.web.blog.model.BlogLearn;
import com.person.web.blog.model.BlogLink;
import com.person.web.blog.model.BlogUser;

/**
 * blog 用户信息存放model
 * @author wangy
 *
 */
public class AppBlogUserInfo implements IModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户基本信息
	 */
	private BlogUser blogUser = new BlogUser();
	/**
	 * 推荐文章
	 */
	private List<BlogLearn> recomLearn = new ArrayList<BlogLearn>();
	/**
	 * 最新文章
	 */
	private List<BlogLearn> newLearn = new ArrayList<BlogLearn>();
	/**
	 * 随机文章
	 */
	private List<BlogLearn> ranLearn = new ArrayList<BlogLearn>();
	/**
	 * 友情链接
	 */
	private List<BlogLink> linkList = new ArrayList<BlogLink>();
	public List<BlogLearn> getRecomLearn() {
		return recomLearn;
	}
	public void setRecomLearn(List<BlogLearn> recomLearn) {
		this.recomLearn = recomLearn;
	}
	public List<BlogLearn> getNewLearn() {
		return newLearn;
	}
	public void setNewLearn(List<BlogLearn> newLearn) {
		this.newLearn = newLearn;
	}
	public List<BlogLearn> getRanLearn() {
		return ranLearn;
	}
	public void setRanLearn(List<BlogLearn> ranLearn) {
		this.ranLearn = ranLearn;
	}
	public List<BlogLink> getLinkList() {
		return linkList;
	}
	public void setLinkList(List<BlogLink> linkList) {
		this.linkList = linkList;
	}
	public BlogUser getBlogUser() {
		return blogUser;
	}
	public void setBlogUser(BlogUser blogUser) {
		this.blogUser = blogUser;
	}
	
	
}
