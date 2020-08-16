package com.person.web.blog.repository;


import java.util.List;

import com.person.framework.mybatis.Criteria;
import com.person.web.blog.model.BlogLink;


public interface IBlogLinkRepository{

	/**
	* 鏌ヨ闆嗗悎 
	* @param param
	* @return
	*/
	List<BlogLink> queryPage(Criteria<BlogLink> param);

	/**
	* 鏌ヨ闆嗗悎鎬昏褰曟暟 
	* @param param
	* @return
	*/
	Integer queryPageCount(Criteria<BlogLink> param);

	/**
	* 鏌ヨ瀹炰綋 
	* @param id
	* @return
	*/
	BlogLink queryEntityById(String id);

	/**
	* 鏂板瀹炰綋 
	* @param record
	* @return
	*/
	Integer insert(BlogLink record);

	/**
	* 鏇存柊瀹炰綋 
	* @param param
	* @return
	*/
	Integer updateByCriteria(Criteria<BlogLink> param);

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
	Integer deleteByCriteria(Criteria<BlogLink> param);

	static final String MAPPER_NAMESPACE = "com.person.web.repository.BlogLinkMapper";
	/**
	 * 查询五条
	 * @return
	 */
	List<BlogLink> queryPageLimitFive(String userId);


}
