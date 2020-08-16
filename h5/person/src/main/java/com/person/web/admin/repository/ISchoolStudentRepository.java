package com.person.web.admin.repository;


import java.util.List;

import com.person.framework.mybatis.Criteria;
import com.person.web.admin.model.SchoolStudent;


public interface ISchoolStudentRepository{

	/**
	* 查询集合 
	* @param param
	* @return
	*/
	List<SchoolStudent> queryPage(Criteria<SchoolStudent> param);

	/**
	* 查询集合总记录数 
	* @param param
	* @return
	*/
	Integer queryPageCount(Criteria<SchoolStudent> param);

	/**
	* 查询实体 
	* @param id
	* @return
	*/
	SchoolStudent queryEntityById(String studentId);

	/**
	* 新增实体 
	* @param record
	* @return
	*/
	Integer insert(SchoolStudent record);

	/**
	* 更新实体 
	* @param param
	* @return
	*/
	Integer updateByCriteria(Criteria<SchoolStudent> param);

	/**
	* 删除实体 
	* @param id
	* @return
	*/
	Integer deleteById(String studentId);

	/**
	* 删除实体 
	* @param id
	* @return
	*/
	Integer deleteByCriteria(Criteria<SchoolStudent> param);

	static final String MAPPER_NAMESPACE = "com.person.web.admin.repository.SchoolStudentMapper";

	Integer insertList(List<SchoolStudent> list);


}
