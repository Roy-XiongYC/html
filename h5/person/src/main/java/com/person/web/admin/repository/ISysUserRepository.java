package com.person.web.admin.repository;


import java.util.Date;
import java.util.List;

import com.person.framework.mybatis.Criteria;
import com.person.web.admin.model.SysUser;


public interface ISysUserRepository{

	/**
	* 鏌ヨ闆嗗悎 
	* @param param
	* @return
	*/
	List<SysUser> queryPage(Criteria<SysUser> param);

	/**
	* 鏌ヨ闆嗗悎鎬昏褰曟暟 
	* @param param
	* @return
	*/
	Integer queryPageCount(Criteria<SysUser> param);

	/**
	* 鏌ヨ瀹炰綋 
	* @param id
	* @return
	*/
	SysUser queryEntityById(String userId);

	/**
	* 鏂板瀹炰綋 
	* @param record
	* @return
	*/
	Integer insert(SysUser record);

	/**
	* 鏇存柊瀹炰綋 
	* @param param
	* @return
	*/
	Integer updateByCriteria(Criteria<SysUser> param);

	/**
	* 鍒犻櫎瀹炰綋 
	* @param id
	* @return
	*/
	Integer deleteById(String userId);

	/**
	* 鍒犻櫎瀹炰綋 
	* @param id
	* @return
	*/
	Integer deleteByCriteria(Criteria<SysUser> param);

	static final String MAPPER_NAMESPACE = "com.person.web.admin.repository.SysUserMapper";

	int changePassword(String userId, String newPassword, Date passwordExpDate);


}
