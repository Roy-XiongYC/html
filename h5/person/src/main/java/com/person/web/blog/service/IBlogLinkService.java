package com.person.web.blog.service;

import java.util.List;

import com.person.framework.mybatis.Criteria;
import com.person.web.blog.model.BlogLink;

public interface IBlogLinkService {

	/**
	 * 鏌ヨ闆嗗悎
	 * 
	 * @param param
	 * @return
	 */
	List<BlogLink> queryPage(Criteria<BlogLink> param);

	/**
	 * 鏌ヨ闆嗗悎鎬昏褰曟暟
	 * 
	 * @param param
	 * @return
	 */
	Integer queryPageCount(Criteria<BlogLink> param);

	/**
	 * 鏌ヨ瀹炰綋
	 * 
	 * @param id
	 * @return
	 */
	BlogLink queryEntityById(String id);

	/**
	 * 鏂板瀹炰綋
	 * 
	 * @param record
	 * @return
	 */
	String insert(BlogLink record);

	String insertOrUpdate(BlogLink record);

	/**
	 * 鏇存柊瀹炰綋
	 * 
	 * @param param
	 * @return
	 */
	String updateByCriteria(Criteria<BlogLink> param);

	/**
	 * 鍒犻櫎瀹炰綋
	 * 
	 * @param id
	 * @return
	 */
	String deleteById(String id);

	/**
	 * 鍒犻櫎瀹炰綋
	 * 
	 * @param id
	 * @return
	 */
	String deleteByCriteria(Criteria<BlogLink> param);

	/**
	 * 查询五条
	 * 
	 * @param param
	 * @return
	 */
	List<BlogLink> queryPageLimitFive(String userId);

}
