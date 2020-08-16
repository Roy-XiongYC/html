package com.person.web.blog.repository.impl;


import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;

import com.person.framework.mybatis.Criteria;
import com.person.web.blog.model.BlogGuest;
import com.person.web.blog.repository.IBlogGuestRepository;


public class BlogGuestRepositoryImpl implements IBlogGuestRepository{

	public List<BlogGuest> queryPage(Criteria<BlogGuest> param){
		RowBounds r = null;
		if (param != null) r = new RowBounds(param.getStartIndex(), param.getPageSize());
		return sqlSessionTemplate.selectList(MAPPER_NAMESPACE + ".queryPage", param, r);
	}

	public Integer queryPageCount(Criteria<BlogGuest> param){
		return (Integer) sqlSessionTemplate.selectOne(MAPPER_NAMESPACE+ ".queryPageCount", param);
	}

	public BlogGuest queryEntityById(String id){
		return sqlSessionTemplate.selectOne(MAPPER_NAMESPACE + ".queryEntity",id);
	}

	public Integer insert(BlogGuest record){
		return sqlSessionTemplate.insert(MAPPER_NAMESPACE + ".insert", record);
	}

	public Integer updateByCriteria(Criteria<BlogGuest> param){
		return sqlSessionTemplate.update(MAPPER_NAMESPACE + ".updateByCriteria", param);
	}

	public Integer deleteById(String id){
		return sqlSessionTemplate.delete(MAPPER_NAMESPACE + ".deleteById",id);
	}

	public Integer deleteByCriteria(Criteria<BlogGuest> param){
		return sqlSessionTemplate.delete(MAPPER_NAMESPACE + ".deleteByCriteria", param);
	}

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {this.sqlSessionTemplate = sqlSessionTemplate;}
	private SqlSessionTemplate sqlSessionTemplate; 

}
