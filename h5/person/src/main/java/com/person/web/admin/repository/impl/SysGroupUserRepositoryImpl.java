package com.person.web.admin.repository.impl;


import java.util.List;
import org.mybatis.spring.SqlSessionTemplate;
import org.apache.ibatis.session.RowBounds;
import com.person.framework.mybatis.Criteria;
import com.person.web.admin.model.SysGroupUser;
import com.person.web.admin.repository.ISysGroupUserRepository;


public class SysGroupUserRepositoryImpl implements ISysGroupUserRepository{

	public List<SysGroupUser> queryPage(Criteria<SysGroupUser> param){
		RowBounds r = null;
		if (param != null) r = new RowBounds(param.getStartIndex(), param.getPageSize());
		return sqlSessionTemplate.selectList(MAPPER_NAMESPACE + ".queryPage", param, r);
	}

	public Integer queryPageCount(Criteria<SysGroupUser> param){
		return (Integer) sqlSessionTemplate.selectOne(MAPPER_NAMESPACE+ ".queryPageCount", param);
	}

	public SysGroupUser queryEntityById(String id){
		return sqlSessionTemplate.selectOne(MAPPER_NAMESPACE + ".queryEntity",id);
	}
	@Override
	public SysGroupUser queryByUserId(String userId){
		return sqlSessionTemplate.selectOne(MAPPER_NAMESPACE + ".queryByUserId",userId);
	}

	public Integer insert(SysGroupUser record){
		return sqlSessionTemplate.insert(MAPPER_NAMESPACE + ".insert", record);
	}

	public Integer updateByCriteria(Criteria<SysGroupUser> param){
		return sqlSessionTemplate.update(MAPPER_NAMESPACE + ".updateByCriteria", param);
	}

	public Integer deleteById(String id){
		return sqlSessionTemplate.delete(MAPPER_NAMESPACE + ".deleteById",id);
	}

	public Integer deleteByCriteria(Criteria<SysGroupUser> param){
		return sqlSessionTemplate.delete(MAPPER_NAMESPACE + ".deleteByCriteria", param);
	}

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {this.sqlSessionTemplate = sqlSessionTemplate;}
	private SqlSessionTemplate sqlSessionTemplate; 

}
