package com.person.web.blog.repository;


import java.util.List;

import com.person.framework.mybatis.Criteria;
import com.person.web.blog.model.BlogGuest;


public interface IBlogGuestRepository{

	/**
	* 查询所有 
	* @param param
	* @return
	*/
	List<BlogGuest> queryPage(Criteria<BlogGuest> param);

	/**
	* 查询总数
	* @param param
	* @return
	*/
	Integer queryPageCount(Criteria<BlogGuest> param);

	/**
	* id查询
	* @param id
	* @return
	*/
	BlogGuest queryEntityById(String id);

	/**
	* 新增
	* @param record
	* @return
	*/
	Integer insert(BlogGuest record);

	/**
	* 修改
	* @param param
	* @return
	*/
	Integer updateByCriteria(Criteria<BlogGuest> param);

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
	Integer deleteByCriteria(Criteria<BlogGuest> param);

	static final String MAPPER_NAMESPACE = "com.person.web.repository.BlogGuestMapper";


}
