package com.person.web.blog.repository.impl;

import org.mybatis.spring.SqlSessionTemplate;

import com.person.web.blog.repository.ISequenceRepository;

public class SequenceRespositoryImpl implements ISequenceRepository {

	public Integer updateQuerySeq(String sequenceName) {
		return sqlSessionTemplate.selectOne(MAPPER_NAMESPACE + ".updateQuerySeq",
				sequenceName);
	}

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate)
	{
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	private SqlSessionTemplate sqlSessionTemplate;
}
