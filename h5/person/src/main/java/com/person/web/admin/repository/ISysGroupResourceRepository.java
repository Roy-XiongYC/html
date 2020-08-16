package com.person.web.admin.repository;


import java.util.List;

import com.person.framework.mybatis.Criteria;
import com.person.web.admin.model.SysGroupResource;


public interface ISysGroupResourceRepository{

	/**
	* 鏌ヨ闆嗗悎 
	* @param param
	* @return
	*/
	List<SysGroupResource> queryPage(Criteria<SysGroupResource> param);

	/**
	* 鏌ヨ闆嗗悎鎬昏褰曟暟 
	* @param param
	* @return
	*/
	Integer queryPageCount(Criteria<SysGroupResource> param);

	/**
	* 鏌ヨ瀹炰綋 
	* @param id
	* @return
	*/
	SysGroupResource queryEntityById(String id);

	/**
	* 鏂板瀹炰綋 
	* @param record
	* @return
	*/
	Integer insert(SysGroupResource record);

	/**
	* 鏇存柊瀹炰綋 
	* @param param
	* @return
	*/
	Integer updateByCriteria(Criteria<SysGroupResource> param);

	/**
	* 鍒犻櫎瀹炰綋 
	* @param id
	* @return
	*/
	Integer deleteById(String id);

	/**
	* 鍒犻櫎瀹炰綋 
	* @param id
	* @return
	*/
	Integer deleteByCriteria(Criteria<SysGroupResource> param);

	static final String MAPPER_NAMESPACE = "com.person.web.admin.repository.SysGroupResourceMapper";

	Integer insertList(List<SysGroupResource> list);


}
