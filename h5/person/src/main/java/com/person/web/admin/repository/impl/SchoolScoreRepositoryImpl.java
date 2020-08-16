package com.person.web.admin.repository.impl;


import java.util.List;
import org.mybatis.spring.SqlSessionTemplate;
import org.apache.ibatis.session.RowBounds;
import com.person.framework.mybatis.Criteria;
import com.person.web.admin.model.SchoolScore;
import com.person.web.admin.repository.ISchoolScoreRepository;


public class SchoolScoreRepositoryImpl implements ISchoolScoreRepository{

	public List<SchoolScore> queryPage(Criteria<SchoolScore> param){
		RowBounds r = null;
		if (param != null) r = new RowBounds(param.getStartIndex(), param.getPageSize());
		return sqlSessionTemplate.selectList(MAPPER_NAMESPACE + ".queryPage", param, r);
	}

	public Integer queryPageCount(Criteria<SchoolScore> param){
		return (Integer) sqlSessionTemplate.selectOne(MAPPER_NAMESPACE+ ".queryPageCount", param);
	}
	
	@Override
	public SchoolScore queryByStu(Criteria<SchoolScore> param){
		return sqlSessionTemplate.selectOne(MAPPER_NAMESPACE+ ".queryByStu", param);
	}
	
	public SchoolScore queryEntityById(String id){
		return sqlSessionTemplate.selectOne(MAPPER_NAMESPACE + ".queryEntity",id);
	}

	public Integer insert(SchoolScore record){
		return sqlSessionTemplate.insert(MAPPER_NAMESPACE + ".insert", record);
	}

	public Integer updateByCriteria(Criteria<SchoolScore> param){
		return sqlSessionTemplate.update(MAPPER_NAMESPACE + ".updateByCriteria", param);
	}
	@Override
	public Integer insertList(List<SchoolScore> list){
		return sqlSessionTemplate.insert(MAPPER_NAMESPACE + ".insertList", list);
	}
	@Override
	public Integer updateList(List<SchoolScore> list){
		return sqlSessionTemplate.update(MAPPER_NAMESPACE + ".updateList", list);
	}
	
	public Integer deleteById(String id){
		return sqlSessionTemplate.delete(MAPPER_NAMESPACE + ".deleteById",id);
	}

	public Integer deleteByCriteria(Criteria<SchoolScore> param){
		return sqlSessionTemplate.delete(MAPPER_NAMESPACE + ".deleteByCriteria", param);
	}

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {this.sqlSessionTemplate = sqlSessionTemplate;}
	private SqlSessionTemplate sqlSessionTemplate; 

}
