package com.person.web.admin.service;

import java.util.List;
import java.util.Map;

import com.person.framework.mybatis.Criteria;
import com.person.framework.web.SourceTree;
import com.person.web.admin.model.SysResource;
import com.person.web.admin.model.SysUser;

public interface ISysUserService {

	/**
	 * 鏌ヨ闆嗗悎
	 * 
	 * @param param
	 * @return
	 */
	List<SysUser> queryPage(Criteria<SysUser> param);

	/**
	 * 鏌ヨ闆嗗悎鎬昏褰曟暟
	 * 
	 * @param param
	 * @return
	 */
	Integer queryPageCount(Criteria<SysUser> param);

	/**
	 * 鏌ヨ瀹炰綋
	 * 
	 * @param id
	 * @return
	 */
	SysUser queryEntityById(String userId);

	/**
	 * 鏂板瀹炰綋
	 * 
	 * @param record
	 * @return
	 */
	String insert(SysUser record);

	/**
	 * 鏇存柊瀹炰綋
	 * 
	 * @param param
	 * @return
	 */
	String updateByCriteria(Criteria<SysUser> param);

	/**
	 * 鍒犻櫎瀹炰綋
	 * 
	 * @param id
	 * @return
	 */
	String deleteById(String userId);

	/**
	 * 鍒犻櫎瀹炰綋
	 * 
	 * @param id
	 * @return
	 */
	String deleteByCriteria(Criteria<SysUser> param);
	
	SysUser loginSys(Criteria<SysUser> param);

	String insertOrUpdate(SysUser record);

	void initTree(List<SysResource> list, SourceTree currentNode);

	List<Map<String, Object>> queryPageList(Criteria<SysUser> param);

	String changePassword(String userId, String oldPassword,
			String newPassword, boolean isValid);

}
