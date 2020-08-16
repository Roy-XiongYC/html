package com.person.web.blog.service;

import java.util.List;

import com.person.framework.mybatis.Criteria;
import com.person.web.blog.model.BlogLearn;

public interface IBlogLearnService {

	/**
	 * 鏌ヨ闆嗗悎
	 * 
	 * @param param
	 * @return
	 */
	List<BlogLearn> queryPage(Criteria<BlogLearn> param);

	/**
	 * 鏌ヨ闆嗗悎鎬昏褰曟暟
	 * 
	 * @param param
	 * @return
	 */
	Integer queryPageCount(Criteria<BlogLearn> param);

	/**
	 * 鏌ヨ瀹炰綋
	 * 
	 * @param id
	 * @return
	 */
	BlogLearn queryEntityById(String id);

	/**
	 * 鏂板瀹炰綋
	 * 
	 * @param record
	 * @return
	 */
	String insert(BlogLearn record);

	String insertOrUpdate(BlogLearn record);

	/**
	 * 鏇存柊瀹炰綋
	 * 
	 * @param param
	 * @return
	 */
	String updateByCriteria(Criteria<BlogLearn> param);

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
	String deleteByCriteria(Criteria<BlogLearn> param);

	/**
	 * 读取随机五条数据 10000条数据以下使用方法
	 * 
	 * @return
	 */
	List<BlogLearn> queryPageByRan(String userSign);

	/**
	 * 读取最新五条数据
	 * 
	 * @return
	 */
	List<BlogLearn> queryPageByNew(String userSign);

	/**
	 * 读取推荐文章 五条数据
	 * 
	 * @return
	 */
	List<BlogLearn> queryPageByRomm(String userSign);

}
