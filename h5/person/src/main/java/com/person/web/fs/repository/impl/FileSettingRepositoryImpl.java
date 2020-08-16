package com.person.web.fs.repository.impl;


import org.mybatis.spring.SqlSessionTemplate;

import com.person.web.fs.model.FileSetting;
import com.person.web.fs.repository.IFileSettingRepository;


public class FileSettingRepositoryImpl implements IFileSettingRepository{

	public FileSetting queryEntity(String fileName){
		return sqlSessionTemplate.selectOne(MAPPER_NAMESPACE + ".queryEntity",fileName);
	}

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {this.sqlSessionTemplate = sqlSessionTemplate;}
	private SqlSessionTemplate sqlSessionTemplate; 
}
