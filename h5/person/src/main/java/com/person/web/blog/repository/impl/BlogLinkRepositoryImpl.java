package com.person.web.blog.repository.impl;


import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;

import com.person.framework.mybatis.Criteria;
import com.person.web.blog.model.BlogLink;
import com.person.web.blog.repository.IBlogLinkRepository;


public class BlogLinkRepositoryImpl implements IBlogLinkRepository{

	public List<BlogLink> queryPage(Criteria<BlogLink> param){
		RowBounds r = null;
		if (param != null) r = new RowBounds(param.getStartIndex(), param.getPageSize());
		return sqlSessionTemplate.selectList(MAPPER_NAMESPACE + ".queryPage", param, r);
	}
	@Override
	public List<BlogLink> queryPageLimitFive(String userId){
		return sqlSessionTemplate.selectList(MAPPER_NAMESPACE + ".queryPageLimitFive", userId);
	}

	public Integer queryPageCount(Criteria<BlogLink> param){
		return (Integer) sqlSessionTemplate.selectOne(MAPPER_NAMESPACE+ ".queryPageCount", param);
	}

	public BlogLink queryEntityById(String id){
		return sqlSessionTemplate.selectOne(MAPPER_NAMESPACE + ".queryEntity",id);
	}

	public Integer insert(BlogLink record){
		return sqlSessionTemplate.insert(MAPPER_NAMESPACE + ".insert", record);
	}

	public Integer updateByCriteria(Criteria<BlogLink> param){
		return sqlSessionTemplate.update(MAPPER_NAMESPACE + ".updateByCriteria", param);
	}

	public Integer deleteById(String id){
		return sqlSessionTemplate.delete(MAPPER_NAMESPACE + ".deleteById",id);
	}

	public Integer deleteByCriteria(Criteria<BlogLink> param){
		return sqlSessionTemplate.delete(MAPPER_NAMESPACE + ".deleteByCriteria", param);
	}

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {this.sqlSessionTemplate = sqlSessionTemplate;}
	private SqlSessionTemplate sqlSessionTemplate; 

}
