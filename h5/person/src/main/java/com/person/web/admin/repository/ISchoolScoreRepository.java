package com.person.web.admin.repository;


import java.util.List;

import com.person.framework.mybatis.Criteria;
import com.person.web.admin.model.SchoolScore;


public interface ISchoolScoreRepository{

	/**
	* 查询集合 
	* @param param
	* @return
	*/
	List<SchoolScore> queryPage(Criteria<SchoolScore> param);

	/**
	* 查询集合总记录数 
	* @param param
	* @return
	*/
	Integer queryPageCount(Criteria<SchoolScore> param);

	/**
	* 查询实体 
	* @param id
	* @return
	*/
	SchoolScore queryEntityById(String id);

	/**
	* 新增实体 
	* @param record
	* @return
	*/
	Integer insert(SchoolScore record);

	/**
	* 更新实体 
	* @param param
	* @return
	*/
	Integer updateByCriteria(Criteria<SchoolScore> param);

	/**
	* 删除实体 
	* @param id
	* @return
	*/
	Integer deleteById(String id);

	/**
	* 删除实体 
	* @param id
	* @return
	*/
	Integer deleteByCriteria(Criteria<SchoolScore> param);

	static final String MAPPER_NAMESPACE = "com.person.web.admin.repository.SchoolScoreMapper";

	Integer insertList(List<SchoolScore> list);

	Integer updateList(List<SchoolScore> list);

	SchoolScore queryByStu(Criteria<SchoolScore> param);


}
