package com.person.web.fs.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.person.framework.mybatis.IModel;

public class Files implements IModel {

	private static final long serialVersionUID = -6695393338459594746L;

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	/**
	 * 返回 原文件名
	 */
	public String getOriginalFileName() {
		return originalFileName;
	}

	/**
	 * 设置 原文件名
	 */
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	/**
	 * 返回 新文件名
	 */
	public String getNewFileName() {
		return newFileName;
	}

	/**
	 * 设置 新文件名
	 */
	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}

	/**
	 * 返回 扩展名
	 */
	public String getExtension() {
		return extension;
	}

	/**
	 * 设置 扩展名
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}

	/**
	 * 返回 文件路径
	 */
	public String getWebPath() {
		return webPath;
	}

	/**
	 * 设置 文件路径
	 */
	public void setWebPath(String webPath) {
		this.webPath = webPath;
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

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	/**
	 * 返回 0表示初始，1表示成功
	 */
	public Integer getState() {
		return state;
	}

	/**
	 * 设置 0表示初始，1表示成功
	 */
	public void setState(Integer state) {
		this.state = state;
	}

	/**
	 * 原文件名
	 */
	private String originalFileName;

	/**
	 * 新文件名
	 */
	private String newFileName;

	/**
	 * 扩展名
	 */
	private String extension;

	/**
	 * 文件WEB访问路径
	 */
	private String webPath;

	/**
	 * 文件存储目录
	 */
	private String folder;
	
	/**
	 * 文件类型
	 */
	private String fileType;

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	private Date uploadDate;

	/**
	 * 0表示初始，1表示成功
	 */
	private Integer state;

}