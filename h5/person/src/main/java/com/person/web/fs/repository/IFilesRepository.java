package com.person.web.fs.repository;


import java.util.List;

import com.person.framework.mybatis.Criteria;
import com.person.web.fs.model.Files;


public interface IFilesRepository{

	/**
	* 查询集合 
	* @param param
	* @return
	*/
	List<Files> queryPage(Criteria<Files> param);

	/**
	* 查询集合总记录数 
	* @param param
	* @return
	*/
	Integer queryPageCount(Criteria<Files> param);

	/**
	* 查询实体 
	* @param fileName
	* @return
	*/
	Files queryEntity(String fileName);

	/**
	* 新增实体 
	* @param record
	* @return
	*/
	Integer insert(Files record);

	/**
	* 更新实体 
	* @param param
	* @return
	*/
	Integer update(String fileName);

	/**
	* 删除实体 
	* @param faileName
	* @return
	*/
	Integer delete(String fileName);

	/**
	* 删除实体 
	* @param id
	* @return
	*/
	Integer deleteByCriteria(Criteria<Files> param);

	static final String MAPPER_NAMESPACE = "com.person.fileService.repository.FilesMapper";


}
