package com.person.web.admin.repository;


import java.util.List;
import com.person.framework.mybatis.Criteria;
import com.person.web.admin.model.SchoolProject;


public interface ISchoolProjectRepository{

	/**
	* 查询集合 
	* @param param
	* @return
	*/
	List<SchoolProject> queryPage(Criteria<SchoolProject> param);

	/**
	* 查询集合总记录数 
	* @param param
	* @return
	*/
	Integer queryPageCount(Criteria<SchoolProject> param);

	/**
	* 查询实体 
	* @param id
	* @return
	*/
	SchoolProject queryEntityById(String projectId);

	/**
	* 新增实体 
	* @param record
	* @return
	*/
	Integer insert(SchoolProject record);

	/**
	* 更新实体 
	* @param param
	* @return
	*/
	Integer updateByCriteria(Criteria<SchoolProject> param);

	/**
	* 删除实体 
	* @param id
	* @return
	*/
	Integer deleteById(String projectId);

	/**
	* 删除实体 
	* @param id
	* @return
	*/
	Integer deleteByCriteria(Criteria<SchoolProject> param);

	static final String MAPPER_NAMESPACE = "com.person.web.admin.repository.SchoolProjectMapper";


}
