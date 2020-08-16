package com.person.web.admin.repository.impl;


import java.util.List;
import org.mybatis.spring.SqlSessionTemplate;
import org.apache.ibatis.session.RowBounds;
import com.person.framework.mybatis.Criteria;
import com.person.web.admin.model.SysGroup;
import com.person.web.admin.repository.ISysGroupRepository;


public class SysGroupRepositoryImpl implements ISysGroupRepository{

	public List<SysGroup> queryPage(Criteria<SysGroup> param){
		RowBounds r = null;
		if (param != null) r = new RowBounds(param.getStartIndex(), param.getPageSize());
		return sqlSessionTemplate.selectList(MAPPER_NAMESPACE + ".queryPage", param, r);
	}

	public Integer queryPageCount(Criteria<SysGroup> param){
		return (Integer) sqlSessionTemplate.selectOne(MAPPER_NAMESPACE+ ".queryPageCount", param);
	}

	public SysGroup queryEntityById(String id){
		return sqlSessionTemplate.selectOne(MAPPER_NAMESPACE + ".queryEntity",id);
	}

	public Integer insert(SysGroup record){
		return sqlSessionTemplate.insert(MAPPER_NAMESPACE + ".insert", record);
	}

	public Integer updateByCriteria(Criteria<SysGroup> param){
		return sqlSessionTemplate.update(MAPPER_NAMESPACE + ".updateByCriteria", param);
	}

	public Integer deleteById(String id){
		return sqlSessionTemplate.delete(MAPPER_NAMESPACE + ".deleteById",id);
	}

	public Integer deleteByCriteria(Criteria<SysGroup> param){
		return sqlSessionTemplate.delete(MAPPER_NAMESPACE + ".deleteByCriteria", param);
	}

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {this.sqlSessionTemplate = sqlSessionTemplate;}
	private SqlSessionTemplate sqlSessionTemplate; 

}
