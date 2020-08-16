package com.person.web.blog.repository;

public interface ISequenceRepository {
	
	public Integer updateQuerySeq(String sequenceName);
	
	static final String MAPPER_NAMESPACE = "com.person.system.repository.SequenceMapper";
}
