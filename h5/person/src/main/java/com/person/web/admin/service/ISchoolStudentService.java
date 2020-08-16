package com.person.web.admin.service;


import java.util.List;
import java.util.Map;

import com.person.framework.mybatis.Criteria;
import com.person.web.admin.model.SchoolStudent;


public interface ISchoolStudentService{

/**
* 鏌ヨ闆嗗悎 
* @param param
* @return
*/
List<Map<String,Object>> queryPageList(Criteria<SchoolStudent> param);

/**
* 鏌ヨ闆嗗悎 
* @param param
* @return
*/
List<SchoolStudent> queryPage(Criteria<SchoolStudent> param);

/**
* 鏌ヨ闆嗗悎鎬昏褰曟暟 
* @param param
* @return
*/
Integer queryPageCount(Criteria<SchoolStudent> param);

/**
* 鏌ヨ瀹炰綋 
* @param id
* @return
*/
SchoolStudent queryEntityById(String studentId);

/**
* 鏂板瀹炰綋 
* @param record
* @return
*/
String insert(SchoolStudent record);

/**
* 鏂板瀹炰綋 
* @param record
* @return
*/
String insertOrUpdate(SchoolStudent record);

/**
* 鏇存柊瀹炰綋 
* @param param
* @return
*/
String updateByCriteria(Criteria<SchoolStudent> param);

/**
* 鍒犻櫎瀹炰綋 
* @param id
* @return
*/
String deleteById(String studentId);

/**
* 鍒犻櫎瀹炰綋 
* @param id
* @return
*/
String deleteByCriteria(Criteria<SchoolStudent> param);

String inserList(List<SchoolStudent> list);



}
