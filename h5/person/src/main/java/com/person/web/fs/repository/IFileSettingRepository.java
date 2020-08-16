package com.person.web.fs.repository;


import com.person.web.fs.model.FileSetting;


public interface IFileSettingRepository{


	/**
	* 查询实体 
	* @param fileType
	* @return
	*/
	FileSetting queryEntity(String fileType);



	static final String MAPPER_NAMESPACE = "com.person.fileService.repository.FileSettingMapper";


}
