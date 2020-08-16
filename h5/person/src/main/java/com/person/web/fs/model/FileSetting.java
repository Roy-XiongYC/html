package com.person.web.fs.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.person.framework.mybatis.IModel;

public class FileSetting implements IModel {

	private static final long serialVersionUID = 8461993352685467988L;

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 返回 文件类型
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * 设置 文件类型
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * 返回 文件夹
	 */
	public String getFolder() {
		return folder;
	}

	/**
	 * 设置 文件夹
	 */
	public void setFolder(String folder) {
		this.folder = folder;
	}

	private Integer id;

	/**
	 * 文件类型
	 */
	private String fileType;
	
	/**
	 * 文件WEB访问路径
	 */
	private String webPath;
	
	/**
	 * 文件夹
	 */
	private String folder;

	/**
	 * 支持的扩展名
	 */
	private String extensions;
	
	public String getExtensions() {
		return extensions;
	}

	public void setExtensions(String extensions) {
		this.extensions = extensions;
	}

	public String getWebPath() {
		return webPath;
	}

	public void setWebPath(String webPath) {
		this.webPath = webPath;
	}

}