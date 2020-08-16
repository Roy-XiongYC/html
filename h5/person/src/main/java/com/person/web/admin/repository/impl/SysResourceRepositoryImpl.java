package com.person.web.admin.repository.impl;


import java.util.List;
import org.mybatis.spring.SqlSessionTemplate;
import org.apache.ibatis.session.RowBounds;
import com.person.framework.mybatis.Criteria;
import com.person.web.admin.model.SysResource;
import com.person.web.admin.repository.ISysResourceRepository;


public class SysResourceRepositoryImpl implements ISysResourceRepository{

	public List<SysResource> queryPage(Criteria<SysResource> param){
		RowBounds r = null;
		if (param != null) r = new RowBounds(param.getStartIndex(), param.getPageSize());
		return sqlSessionTemplate.selectList(MAPPER_NAMESPACE + ".queryPage", param, r);
	}
	@Override
	public List<SysResource> queryPageNoLimit(Criteria<SysResource> param){
		return sqlSessionTemplate.selectList(MAPPER_NAMESPACE + ".queryPageNoLimit", param);
	}
	
	@Override
	public List<SysResource> queryAllPage(Criteria<SysResource> param){
		return sqlSessionTemplate.selectList(MAPPER_NAMESPACE + ".queryAllPage", param);
	}
	
	public Integer queryPageCount(Criteria<SysResource> param){
		return (Integer) sqlSessionTemplate.selectOne(MAPPER_NAMESPACE+ ".queryPageCount", param);
	}
	/**
	 * 查询所有菜单并对传入用户组验证是否有权限
	 * @param groupId
	 * @return
	 */
	@Override
	public List<SysResource> queryByGroup(Criteria<SysResource> param){
		return sqlSessionTemplate.selectList(MAPPER_NAMESPACE + ".queryByGroup", param);
	}
	public SysResource queryEntityById(String id){
		return sqlSessionTemplate.selectOne(MAPPER_NAMESPACE + ".queryEntity",id);
	}

	public Integer insert(SysResource record){
		return sqlSessionTemplate.insert(MAPPER_NAMESPACE + ".insert", record);
	}

	public Integer updateByCriteria(Criteria<SysResource> param){
		return sqlSessionTemplate.update(MAPPER_NAMESPACE + ".updateByCriteria", param);
	}

	public Integer deleteById(String id){
		return sqlSessionTemplate.delete(MAPPER_NAMESPACE + ".deleteById",id);
	}

	public Integer deleteByCriteria(Criteria<SysResource> param){
		return sqlSessionTemplate.delete(MAPPER_NAMESPACE + ".deleteByCriteria", param);
	}

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {this.sqlSessionTemplate = sqlSessionTemplate;}
	private SqlSessionTemplate sqlSessionTemplate; 

}
