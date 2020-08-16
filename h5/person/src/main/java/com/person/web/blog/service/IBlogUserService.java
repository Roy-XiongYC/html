package com.person.web.blog.service;

import java.util.List;

import com.person.framework.mybatis.Criteria;
import com.person.framework.web.AppBlogUserInfo;
import com.person.web.blog.model.BlogUser;

public interface IBlogUserService {

	/**
	 * 鏌ヨ闆嗗悎
	 * 
	 * @param param
	 * @return
	 */
	List<BlogUser> queryPage(Criteria<BlogUser> param);

	/**
	 * 鏌ヨ闆嗗悎鎬昏褰曟暟
	 * 
	 * @param param
	 * @return
	 */
	Integer queryPageCount(Criteria<BlogUser> param);

	/**
	 * 鏌ヨ瀹炰綋
	 * 
	 * @param id
	 * @return
	 */
	BlogUser queryEntityById(String userId);
	BlogUser queryEntityBySign(String userSign);
	/**
	 * 鏂板瀹炰綋
	 * 
	 * @param record
	 * @return
	 */
	String insert(BlogUser record);
	String insertOrUpdate(BlogUser record);
	/**
	 * 鏇存柊瀹炰綋
	 * 
	 * @param param
	 * @return
	 */
	String updateByCriteria(Criteria<BlogUser> param);

	/**
	 * 鍒犻櫎瀹炰綋
	 * 
	 * @param id
	 * @return
	 */
	String deleteById(String userId);

	/**
	 * 鍒犻櫎瀹炰綋
	 * 
	 * @param id
	 * @return
	 */
	String deleteByCriteria(Criteria<BlogUser> param);
	/**
	 * 从redis中获取数据
	 * @param userSign
	 * @return
	 */
	AppBlogUserInfo queryUserInfoBySign(String userSign);

	

}
