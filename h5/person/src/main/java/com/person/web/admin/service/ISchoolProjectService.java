package com.person.web.admin.service;


import java.util.List;
import java.util.Map;
import com.person.framework.mybatis.Criteria;
import com.person.web.admin.model.SchoolProject;


public interface ISchoolProjectService{

/**
* 鏌ヨ闆嗗悎 
* @param param
* @return
*/
List<Map<String,Object>> queryPageList(Criteria<SchoolProject> param);

/**
* 鏌ヨ闆嗗悎 
* @param param
* @return
*/
List<SchoolProject> queryPage(Criteria<SchoolProject> param);

/**
* 鏌ヨ闆嗗悎鎬昏褰曟暟 
* @param param
* @return
*/
Integer queryPageCount(Criteria<SchoolProject> param);

/**
* 鏌ヨ瀹炰綋 
* @param id
* @return
*/
SchoolProject queryEntityById(String projectId);

/**
* 鏂板瀹炰綋 
* @param record
* @return
*/
String insert(SchoolProject record);

/**
* 鏂板瀹炰綋 
* @param record
* @return
*/
String insertOrUpdate(SchoolProject record);

/**
* 鏇存柊瀹炰綋 
* @param param
* @return
*/
String updateByCriteria(Criteria<SchoolProject> param);

/**
* 鍒犻櫎瀹炰綋 
* @param id
* @return
*/
String deleteById(String projectId);

/**
* 鍒犻櫎瀹炰綋 
* @param id
* @return
*/
String deleteByCriteria(Criteria<SchoolProject> param);



}
