package com.person.web.admin.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import com.person.framework.mybatis.IModel;
import java.math.BigDecimal;
import java.util.Date;


public class SchoolStudent implements IModel { 

	public String toString() {return ReflectionToStringBuilder.toString(this);}	private static final long serialVersionUID = 1L;

 	/** 
	* 获取  学号
	*/ 
	public String getStudentId(){ return studentId ; } 
	/** 
	* 设置 学号
	*/ 
	public void setStudentId(String studentId){ this.studentId=studentId ; } 
 	/** 
	* 获取  姓名
	*/ 
	public String getStudentName(){ return studentName ; } 
	/** 
	* 设置 姓名
	*/ 
	public void setStudentName(String studentName){ this.studentName=studentName ; } 
 	/** 
	* 获取  班级
	*/ 
	public String getStudentClass(){ return studentClass ; } 
	/** 
	* 设置 班级
	*/ 
	public void setStudentClass(String studentClass){ this.studentClass=studentClass ; } 
 	public String getCreateBy(){ return createBy ; } 
	public void setCreateBy(String createBy){ this.createBy=createBy ; } 
 	public Date getCreateTime(){ return createTime ; } 
	public void setCreateTime(Date createTime){ this.createTime=createTime ; } 

 	/** 
	* 学号
	*/ 
	private String studentId; 

 	/** 
	* 姓名
	*/ 
	private String studentName; 

 	/** 
	* 班级
	*/ 
	private String studentClass; 

 	private String createBy; 

 	private Date createTime; 



} 