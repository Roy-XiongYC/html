package com.person.web.blog.repository;


import java.util.List;

import com.person.framework.mybatis.Criteria;
import com.person.web.blog.model.BlogAbout;


public interface IBlogAboutRepository{

	/**
	* 查询所有
	* @param param
	* @return
	*/
	List<BlogAbout> queryPage(Criteria<BlogAbout> param);

	/**
	* 查询总数
	* @param param
	* @return
	*/
	Integer queryPageCount(Criteria<BlogAbout> param);

	/**
	* id查询
	* @param id
	* @return
	*/
	BlogAbout queryEntityById(String id);

	/**
	* 新增
	* @param record
	* @return
	*/
	Integer insert(BlogAbout record);

	/**
	* 修改
	* @param param
	* @return
	*/
	Integer updateByCriteria(Criteria<BlogAbout> param);

	/**
	* id删除
	* @param id
	* @return
	*/
	Integer deleteById(String id);

	/**
	* 条件删除
	* @param id
	* @return
	*/
	Integer deleteByCriteria(Criteria<BlogAbout> param);

	static final String MAPPER_NAMESPACE = "com.person.web.repository.BlogAboutMapper";

	BlogAbout queryEntityByUser(String userSign);


}
