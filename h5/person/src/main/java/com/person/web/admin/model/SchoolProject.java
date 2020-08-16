package com.person.web.admin.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import com.person.framework.mybatis.IModel;
import java.math.BigDecimal;
import java.util.Date;


public class SchoolProject implements IModel { 

	public String toString() {return ReflectionToStringBuilder.toString(this);}	private static final long serialVersionUID = 1L;

 	/** 
	* 获取  项目id
	*/ 
	public String getProjectId(){ return projectId ; } 
	/** 
	* 设置 项目id
	*/ 
	public void setProjectId(String projectId){ this.projectId=projectId ; } 
 	/** 
	* 获取  项目名
	*/ 
	public String getProjectName(){ return projectName ; } 
	/** 
	* 设置 项目名
	*/ 
	public void setProjectName(String projectName){ this.projectName=projectName ; } 
 	public String getCreateBy(){ return createBy ; } 
	public void setCreateBy(String createBy){ this.createBy=createBy ; } 
 	public Date getCreateTime(){ return createTime ; } 
	public void setCreateTime(Date createTime){ this.createTime=createTime ; } 

 	/** 
	* 项目id
	*/ 
	private String projectId; 

 	/** 
	* 项目名
	*/ 
	private String projectName; 

 	private String createBy; 

 	private Date createTime; 



} 