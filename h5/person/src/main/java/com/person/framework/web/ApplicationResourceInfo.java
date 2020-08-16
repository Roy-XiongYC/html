package com.person.framework.web;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
/**
 * 用户菜单权限&面包屑列表
 * @author 
 *
 */
public class ApplicationResourceInfo {
	public String toString() {return ReflectionToStringBuilder.toString(this);}
	public ApplicationResourceInfo(String userId,String urls, Map<String, Object> breadcrumb, List<SourceTree> sourceTree){
		super();
		this.userId = userId;
		this.urls = urls;
		this.breadcrumb = breadcrumb;
		this.sourceTree = sourceTree;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUrls() {
		return urls;
	}
	public void setUrls(String urls) {
		this.urls = urls;
	}
	public Map<String, Object> getBreadcrumb() {
		return breadcrumb;
	}
	public void setBreadcrumb(Map<String, Object> breadcrumb) {
		this.breadcrumb = breadcrumb;
	}
	public List<SourceTree> getSourceTree() {
		return sourceTree;
	}
	public void setSourceTree(List<SourceTree> sourceTree) {
		this.sourceTree = sourceTree;
	}
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 所有url地址，以,分隔
	 */
	private String urls;
	/**
	 * 面包屑功能
	 */
	private Map<String, Object> breadcrumb;
	/**
	 * 用户菜单权限列表
	 */
	private List<SourceTree> sourceTree;
}
