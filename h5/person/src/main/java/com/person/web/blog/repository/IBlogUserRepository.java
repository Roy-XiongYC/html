package com.person.web.blog.repository;


import java.util.List;

import com.person.framework.mybatis.Criteria;
import com.person.web.blog.model.BlogUser;


public interface IBlogUserRepository{

	/**
	* 鏌ヨ闆嗗悎 
	* @param param
	* @return
	*/
	List<BlogUser> queryPage(Criteria<BlogUser> param);

	/**
	* 鏌ヨ闆嗗悎鎬昏褰曟暟 
	* @param param
	* @return
	*/
	Integer queryPageCount(Criteria<BlogUser> param);

	/**
	* 鏌ヨ瀹炰綋 
	* @param id
	* @return
	*/
	BlogUser queryEntityById(String userId);
	BlogUser queryEntityBySign(String userSign);
	/**
	* 鏂板瀹炰綋 
	* @param record
	* @return
	*/
	Integer insert(BlogUser record);

	/**
	* 鏇存柊瀹炰綋 
	* @param param
	* @return
	*/
	Integer updateByCriteria(Criteria<BlogUser> param);

	/**
	* 鍒犻櫎瀹炰綋 
	* @param id
	* @return
	*/
	Integer deleteById(String userId);

	/**
	* 鍒犻櫎瀹炰綋 
	* @param id
	* @return
	*/
	Integer deleteByCriteria(Criteria<BlogUser> param);

	static final String MAPPER_NAMESPACE = "com.person.web.repository.BlogUserMapper";

	


}
