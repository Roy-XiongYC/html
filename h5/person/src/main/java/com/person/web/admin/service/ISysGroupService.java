package com.person.web.admin.service;

import java.util.List;
import java.util.Map;

import com.person.framework.mybatis.Criteria;
import com.person.web.admin.model.SysGroup;

public interface ISysGroupService {

	/**
	 * 鏌ヨ闆嗗悎
	 * 
	 * @param param
	 * @return
	 */
	List<SysGroup> queryPage(Criteria<SysGroup> param);

	/**
	 * 鏌ヨ闆嗗悎鎬昏褰曟暟
	 * 
	 * @param param
	 * @return
	 */
	Integer queryPageCount(Criteria<SysGroup> param);

	/**
	 * 鏌ヨ瀹炰綋
	 * 
	 * @param id
	 * @return
	 */
	SysGroup queryEntityById(String groupId);

	/**
	 * 鏂板瀹炰綋
	 * 
	 * @param record
	 * @return
	 */
	String insert(SysGroup record);

	/**
	 * 鏇存柊瀹炰綋
	 * 
	 * @param param
	 * @return
	 */
	String updateByCriteria(Criteria<SysGroup> param);

	/**
	 * 鍒犻櫎瀹炰綋
	 * 
	 * @param id
	 * @return
	 */
	String deleteById(String groupId);

	/**
	 * 鍒犻櫎瀹炰綋
	 * 
	 * @param id
	 * @return
	 */
	String deleteByCriteria(Criteria<SysGroup> param);

	List<Map<String, Object>> queryPageList(Criteria<SysGroup> param);

	String insertOrUpdate(SysGroup record);

}
