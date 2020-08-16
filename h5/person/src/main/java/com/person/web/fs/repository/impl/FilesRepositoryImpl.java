package com.person.web.fs.repository.impl;


import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;

import com.person.framework.mybatis.Criteria;
import com.person.web.fs.model.Files;
import com.person.web.fs.repository.IFilesRepository;


public class FilesRepositoryImpl implements IFilesRepository{

	public List<Files> queryPage(Criteria<Files> param){
		RowBounds r = null;
		if (param != null) r = new RowBounds(param.getStartIndex(), param.getPageSize());
		return sqlSessionTemplate.selectList(MAPPER_NAMESPACE + ".queryPage", param, r);
	}

	public Integer queryPageCount(Criteria<Files> param){
		return (Integer) sqlSessionTemplate.selectOne(MAPPER_NAMESPACE+ ".queryPageCount", param);
	}

	public Files queryEntity(String fileName){
		return sqlSessionTemplate.selectOne(MAPPER_NAMESPACE + ".queryEntity",fileName);
	}

	public Integer insert(Files record){
		return sqlSessionTemplate.insert(MAPPER_NAMESPACE + ".insert", record);
	}

	public Integer update(String fileName){
		return sqlSessionTemplate.update(MAPPER_NAMESPACE + ".update", fileName);
	}

	public Integer delete(String fileName){
		return sqlSessionTemplate.delete(MAPPER_NAMESPACE + ".deleteById",fileName);
	}

	public Integer deleteByCriteria(Criteria<Files> param){
		return sqlSessionTemplate.delete(MAPPER_NAMESPACE + ".deleteByCriteria", param);
	}

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {this.sqlSessionTemplate = sqlSessionTemplate;}
	private SqlSessionTemplate sqlSessionTemplate; 

}
