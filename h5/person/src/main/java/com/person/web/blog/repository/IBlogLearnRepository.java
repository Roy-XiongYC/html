package com.person.web.blog.repository;

import java.util.List;

import com.person.framework.mybatis.Criteria;
import com.person.web.blog.model.BlogLearn;

public interface IBlogLearnRepository {

	/**
	 * 查询
	 * 
	 * @param param
	 * @return
	 */
	List<BlogLearn> queryPage(Criteria<BlogLearn> param);

	/**
	 * 查询总数
	 * 
	 * @param param
	 * @return
	 */
	Integer queryPageCount(Criteria<BlogLearn> param);

	/**
	 * id查询
	 * 
	 * @param id
	 * @return
	 */
	BlogLearn queryEntityById(String id);

	/**
	 * 新增
	 * 
	 * @param record
	 * @return
	 */
	Integer insert(BlogLearn record);

	/**
	 * 修改
	 * 
	 * @param param
	 * @return
	 */
	Integer updateByCriteria(Criteria<BlogLearn> param);

	/**
	 * id删除
	 * 
	 * @param id
	 * @return
	 */
	Integer deleteById(String id);

	/**
	 * 条件删除
	 * 
	 * @param id
	 * @return
	 */
	Integer deleteByCriteria(Criteria<BlogLearn> param);

	static final String MAPPER_NAMESPACE = "com.person.web.repository.BlogLearnMapper";

}
