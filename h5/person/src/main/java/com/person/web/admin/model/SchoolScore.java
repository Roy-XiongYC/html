package com.person.web.admin.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import com.person.framework.mybatis.IModel;
import java.math.BigDecimal;
import java.util.Date;


public class SchoolScore implements IModel { 

	public String toString() {return ReflectionToStringBuilder.toString(this);}	private static final long serialVersionUID = 1L;

 	/** 
	* 获取  无意义主键
	*/ 
	public String getId(){ return id ; } 
	/** 
	* 设置 无意义主键
	*/ 
	public void setId(String id){ this.id=id ; } 
 	/** 
	* 获取  学号
	*/ 
	public String getStudentId(){ return studentId ; } 
	/** 
	* 设置 学号
	*/ 
	public void setStudentId(String studentId){ this.studentId=studentId ; } 
 	/** 
	* 获取  项目id
	*/ 
	public String getProjectId(){ return projectId ; } 
	/** 
	* 设置 项目id
	*/ 
	public void setProjectId(String projectId){ this.projectId=projectId ; } 
 	/** 
	* 获取  签到经验值
	*/ 
	public Double getSignValue(){ return signValue ; } 
	/** 
	* 设置 签到经验值
	*/ 
	public void setSignValue(Double signValue){ this.signValue=signValue ; } 
 	/** 
	* 获取  视频资源学习经验值
	*/ 
	public Double getVideoValue(){ return videoValue ; } 
	/** 
	* 设置 视频资源学习经验值
	*/ 
	public void setVideoValue(Double videoValue){ this.videoValue=videoValue ; } 
 	/** 
	* 获取  非视频资源学习经验值
	*/ 
	public Double getNvideoValue(){ return nvideoValue ; } 
	/** 
	* 设置 非视频资源学习经验值
	*/ 
	public void setNvideoValue(Double nvideoValue){ this.nvideoValue=nvideoValue ; } 
 	/** 
	* 获取  课堂表现经验值
	*/ 
	public Double getClassValue(){ return classValue ; } 
	/** 
	* 设置 课堂表现经验值
	*/ 
	public void setClassValue(Double classValue){ this.classValue=classValue ; } 
 	/** 
	* 获取  投票问卷经验值
	*/ 
	public Double getVoteValue(){ return voteValue ; } 
	/** 
	* 设置 投票问卷经验值
	*/ 
	public void setVoteValue(Double voteValue){ this.voteValue=voteValue ; } 
 	/** 
	* 获取  头脑风暴经验值
	*/ 
	public Double getBrainValue(){ return brainValue ; } 
	/** 
	* 设置 头脑风暴经验值
	*/ 
	public void setBrainValue(Double brainValue){ this.brainValue=brainValue ; } 
 	/** 
	* 获取  讨论答疑经验值
	*/ 
	public Double getDiscussValue(){ return discussValue ; } 
	/** 
	* 设置 讨论答疑经验值
	*/ 
	public void setDiscussValue(Double discussValue){ this.discussValue=discussValue ; } 
 	/** 
	* 获取  测试经验值
	*/ 
	public Double getTestValue(){ return testValue ; } 
	/** 
	* 设置 测试经验值
	*/ 
	public void setTestValue(Double testValue){ this.testValue=testValue ; } 
 	/** 
	* 获取  作业小组任务经验值
	*/ 
	public Double getTaskValue(){ return taskValue ; } 
	/** 
	* 设置 作业小组任务经验值
	*/ 
	public void setTaskValue(Double taskValue){ this.taskValue=taskValue ; } 
 	/** 
	* 获取  校外指导打分
	*/ 
	public Double getGuidanceValue(){ return guidanceValue ; } 
	/** 
	* 设置 校外指导打分
	*/ 
	public void setGuidanceValue(Double guidanceValue){ this.guidanceValue=guidanceValue ; } 
 	/** 
	* 获取  教师打分
	*/ 
	public Double getTeacherValue(){ return teacherValue ; } 
	/** 
	* 设置 教师打分
	*/ 
	public void setTeacherValue(Double teacherValue){ this.teacherValue=teacherValue ; } 
 	/** 
	* 获取  总评成绩
	*/ 
	public Double getTotalMark(){ return totalMark ; } 
	/** 
	* 设置 总评成绩
	*/ 
	public void setTotalMark(Double totalMark){ this.totalMark=totalMark ; } 
 	public Date getCreateTime(){ return createTime ; } 
	public void setCreateTime(Date createTime){ this.createTime=createTime ; } 

 	/** 
	* 无意义主键
	*/ 
	private String id; 

 	/** 
	* 学号
	*/ 
	private String studentId; 

 	/** 
	* 项目id
	*/ 
	private String projectId; 

 	/** 
	* 签到经验值
	*/ 
	private Double signValue; 

 	/** 
	* 视频资源学习经验值
	*/ 
	private Double videoValue; 

 	/** 
	* 非视频资源学习经验值
	*/ 
	private Double nvideoValue; 

 	/** 
	* 课堂表现经验值
	*/ 
	private Double classValue; 

 	/** 
	* 投票问卷经验值
	*/ 
	private Double voteValue; 

 	/** 
	* 头脑风暴经验值
	*/ 
	private Double brainValue; 

 	/** 
	* 讨论答疑经验值
	*/ 
	private Double discussValue; 

 	/** 
	* 测试经验值
	*/ 
	private Double testValue; 

 	/** 
	* 作业小组任务经验值
	*/ 
	private Double taskValue; 

 	/** 
	* 校外指导打分
	*/ 
	private Double guidanceValue; 

 	/** 
	* 教师打分
	*/ 
	private Double teacherValue; 

 	/** 
	* 总评成绩
	*/ 
	private Double totalMark; 

 	private Date createTime; 

 	
 	private String studentName;
 	private String studentClass;
 	private String projectName;

	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentClass() {
		return studentClass;
	}
	public void setStudentClass(String studentClass) {
		this.studentClass = studentClass;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

} 