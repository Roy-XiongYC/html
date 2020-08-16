package com.person.web.admin.service;


import java.util.List;
import java.util.Map;

import com.person.framework.mybatis.Criteria;
import com.person.web.admin.model.SchoolScore;


public interface ISchoolScoreService{

/**
* 鏌ヨ闆嗗悎 
* @param param
* @return
*/
List<Map<String,Object>> queryPageList(Criteria<SchoolScore> param);

/**
* 鏌ヨ闆嗗悎 
* @param param
* @return
*/
List<SchoolScore> queryPage(Criteria<SchoolScore> param);

/**
* 鏌ヨ闆嗗悎鎬昏褰曟暟 
* @param param
* @return
*/
Integer queryPageCount(Criteria<SchoolScore> param);

/**
* 鏌ヨ瀹炰綋 
* @param id
* @return
*/
SchoolScore queryEntityById(String id);

/**
* 鏂板瀹炰綋 
* @param record
* @return
*/
String insert(SchoolScore record);

/**
* 鏂板瀹炰綋 
* @param record
* @return
*/
String insertOrUpdate(SchoolScore record);

/**
* 鏇存柊瀹炰綋 
* @param param
* @return
*/
String updateByCriteria(Criteria<SchoolScore> param);

/**
* 鍒犻櫎瀹炰綋 
* @param id
* @return
*/
String deleteById(String id);

/**
* 鍒犻櫎瀹炰綋 
* @param id
* @return
*/
String deleteByCriteria(Criteria<SchoolScore> param);

String insertList(List<SchoolScore> list);

String updateList(List<SchoolScore> list);

/**
 * 导入数据
 * @param map
 * @param projectId
 * @param userId
 */
void insertScoreData(Map<String, SchoolScore> map, String projectId,
		String userId);

SchoolScore queryByStu(String projectId, String studentId);



}
