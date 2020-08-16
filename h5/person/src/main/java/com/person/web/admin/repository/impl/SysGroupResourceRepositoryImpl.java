package com.person.web.admin.repository.impl;


import java.util.List;
import org.mybatis.spring.SqlSessionTemplate;
import org.apache.ibatis.session.RowBounds;
import com.person.framework.mybatis.Criteria;
import com.person.web.admin.model.SysGroupResource;
import com.person.web.admin.repository.ISysGroupResourceRepository;


public class SysGroupResourceRepositoryImpl implements ISysGroupResourceRepository{

	public List<SysGroupResource> queryPage(Criteria<SysGroupResource> param){
		RowBounds r = null;
		if (param != null) r = new RowBounds(param.getStartIndex(), param.getPageSize());
		return sqlSessionTemplate.selectList(MAPPER_NAMESPACE + ".queryPage", param, r);
	}

	public Integer queryPageCount(Criteria<SysGroupResource> param){
		return (Integer) sqlSessionTemplate.selectOne(MAPPER_NAMESPACE+ ".queryPageCount", param);
	}

	public SysGroupResource queryEntityById(String id){
		return sqlSessionTemplate.selectOne(MAPPER_NAMESPACE + ".queryEntity",id);
	}
	@Override
	public Integer insertList(List<SysGroupResource> list){
		return sqlSessionTemplate.insert(MAPPER_NAMESPACE + ".insertList", list);
	}
	public Integer insert(SysGroupResource record){
		return sqlSessionTemplate.insert(MAPPER_NAMESPACE + ".insert", record);
	}

	public Integer updateByCriteria(Criteria<SysGroupResource> param){
		return sqlSessionTemplate.update(MAPPER_NAMESPACE + ".updateByCriteria", param);
	}

	public Integer deleteById(String id){
		return sqlSessionTemplate.delete(MAPPER_NAMESPACE + ".deleteById",id);
	}

	public Integer deleteByCriteria(Criteria<SysGroupResource> param){
		return sqlSessionTemplate.delete(MAPPER_NAMESPACE + ".deleteByCriteria", param);
	}

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {this.sqlSessionTemplate = sqlSessionTemplate;}
	private SqlSessionTemplate sqlSessionTemplate; 

}
