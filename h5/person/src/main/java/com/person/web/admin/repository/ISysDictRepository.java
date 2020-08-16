package com.person.web.admin.repository;


import java.util.List;
import com.person.framework.mybatis.Criteria;
import com.person.web.admin.model.SysDict;


public interface ISysDictRepository{

	/**
	* 鏌ヨ闆嗗悎 
	* @param param
	* @return
	*/
	List<SysDict> queryPage(Criteria<SysDict> param);

	/**
	* 鏌ヨ闆嗗悎鎬昏褰曟暟 
	* @param param
	* @return
	*/
	Integer queryPageCount(Criteria<SysDict> param);

	/**
	* 鏌ヨ瀹炰綋 
	* @param id
	* @return
	*/
	SysDict queryEntityById(String id);

	/**
	* 鏂板瀹炰綋 
	* @param record
	* @return
	*/
	Integer insert(SysDict record);

	/**
	* 鏇存柊瀹炰綋 
	* @param param
	* @return
	*/
	Integer updateByCriteria(Criteria<SysDict> param);

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
	Integer deleteByCriteria(Criteria<SysDict> param);

	static final String MAPPER_NAMESPACE = "com.person.web.admin.repository.SysDictMapper";


}
