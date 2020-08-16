package com.person.web.admin.repository.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.apache.ibatis.session.RowBounds;

import com.person.framework.mybatis.Criteria;
import com.person.web.admin.model.SysUser;
import com.person.web.admin.repository.ISysUserRepository;


public class SysUserRepositoryImpl implements ISysUserRepository{

	public List<SysUser> queryPage(Criteria<SysUser> param){
		RowBounds r = null;
		if (param != null) r = new RowBounds(param.getStartIndex(), param.getPageSize());
		return sqlSessionTemplate.selectList(MAPPER_NAMESPACE + ".queryPage", param, r);
	}

	public Integer queryPageCount(Criteria<SysUser> param){
		return (Integer) sqlSessionTemplate.selectOne(MAPPER_NAMESPACE+ ".queryPageCount", param);
	}

	public SysUser queryEntityById(String id){
		return sqlSessionTemplate.selectOne(MAPPER_NAMESPACE + ".queryEntity",id);
	}

	public Integer insert(SysUser record){
		return sqlSessionTemplate.insert(MAPPER_NAMESPACE + ".insert", record);
	}

	public Integer updateByCriteria(Criteria<SysUser> param){
		return sqlSessionTemplate.update(MAPPER_NAMESPACE + ".updateByCriteria", param);
	}
	@Override
	public int changePassword(String userId, String newPassword, Date passwordExpDate)
	{

		Map<String, Object> param = new HashMap<String, Object>();

		param.put("userId", userId);
		param.put("password", newPassword);
		param.put("passwordExpDate", passwordExpDate);

		return sqlSessionTemplate.update(MAPPER_NAMESPACE + ".changePassword", param);
	}

	public Integer deleteById(String id){
		return sqlSessionTemplate.delete(MAPPER_NAMESPACE + ".deleteById",id);
	}

	public Integer deleteByCriteria(Criteria<SysUser> param){
		return sqlSessionTemplate.delete(MAPPER_NAMESPACE + ".deleteByCriteria", param);
	}

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {this.sqlSessionTemplate = sqlSessionTemplate;}
	private SqlSessionTemplate sqlSessionTemplate; 

}
