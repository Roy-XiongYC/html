package com.person.web.blog.repository;


import java.util.List;

import com.person.framework.mybatis.Criteria;
import com.person.web.blog.model.BlogShuo;


public interface IBlogShuoRepository{

	/**
	* 鏌ヨ闆嗗悎 
	* @param param
	* @return
	*/
	List<BlogShuo> queryPage(Criteria<BlogShuo> param);

	/**
	* 鏌ヨ闆嗗悎鎬昏褰曟暟 
	* @param param
	* @return
	*/
	Integer queryPageCount(Criteria<BlogShuo> param);

	/**
	* 鏌ヨ瀹炰綋 
	* @param id
	* @return
	*/
	BlogShuo queryEntityById(String id);

	/**
	* 鏂板瀹炰綋 
	* @param record
	* @return
	*/
	Integer insert(BlogShuo record);

	/**
	* 鏇存柊瀹炰綋 
	* @param param
	* @return
	*/
	Integer updateByCriteria(Criteria<BlogShuo> param);

	/**
	* 鍒犻櫎瀹炰綋 
	* @param id
	* @return
	*/
	Integer deleteById(String id);

	/**
	* 鍒犻櫎瀹炰綋 
	* @param id
	* @return
	*/
	Integer deleteByCriteria(Criteria<BlogShuo> param);

	static final String MAPPER_NAMESPACE = "com.person.web.repository.BlogShuoMapper";


}
