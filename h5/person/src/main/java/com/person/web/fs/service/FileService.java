package com.person.web.fs.service;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.person.framework.constant.FileMessageConstant;
import com.person.framework.service.IMessageService;
import com.person.framework.web.JsonResult;
import com.person.web.fs.model.FileSetting;
import com.person.web.fs.model.Files;
import com.person.web.fs.repository.IFileSettingRepository;
import com.person.web.fs.repository.IFilesRepository;

public class FileService {
	/**
	 * 根据文件类型获取文件夹
	 * 
	 * @param fileType
	 * @return
	 */
	public FileSetting getFileFolder(String fileType) {
		return fileSettingRepositoryImpl.queryEntity(fileType);
	}

	/**
	 * 根据文件获取文件信息
	 * 
	 * @param fileName
	 * @return
	 */
	public Files getFile(String fileName) {
		return filesRepositoryImpl.queryEntity(fileName);
	}

	/**
	 * 插入新文件
	 * 
	 * @param model
	 * @return
	 */
	public boolean insertFileInfo(Files model) {
		return filesRepositoryImpl.insert(model) > 0;
	}

	/**
	 * 删除文件
	 * 
	 * @param fileName
	 * @return
	 */
	public boolean deleteFileInfo(String fileName) {
		return filesRepositoryImpl.delete(fileName) > 0;
	}

	public JsonResult deleteFile(String fileName) {
		JsonResult jsonResult = new JsonResult();
		try {
			// 获取文件数据
			Files model = getFile(fileName);
			if (model == null) {
				return jsonResult.declareFailure(messageService
						.message(FileMessageConstant.M011));
			}

//			File storedFile = new File(model.getFolder()+ model.getNewFileName());
			File storedFile = new File(model.getNewFileName());
			if (storedFile.exists()) // 检查文件是否存在
			{
				boolean result = storedFile.delete(); // 删除文件
				if (!result) {
					log.error(messageService.message(FileMessageConstant.M012,
							fileName));
					return jsonResult.declareFailure(messageService.message(
							FileMessageConstant.M012, fileName));
				}
			} else {
				log.error(messageService.message(FileMessageConstant.M014,
						fileName));
			}

			// 删除文件数据
			if (deleteFileInfo(fileName)) {
				jsonResult.declareSuccess();
			} else {
				log.error(messageService.message(FileMessageConstant.M013,
						fileName));
				return jsonResult.declareFailure(messageService.message(
						FileMessageConstant.M013, fileName));
			}

		} catch (Exception e) {
			log.error(
					messageService.message(FileMessageConstant.M010,
							e.getMessage()), e);
			jsonResult.declareFailure(messageService.message(
					FileMessageConstant.M010, e.getMessage()));
		}

		return jsonResult;
	}
	
	public boolean updateFileInfo(String fileName) {
		Integer result = filesRepositoryImpl.update(fileName);
		return result > 0;
	}

	public void setFilesRepositoryImpl(IFilesRepository filesRepositoryImpl) {
		this.filesRepositoryImpl = filesRepositoryImpl;
	}

	public void setFileSettingRepositoryImpl(
			IFileSettingRepository fileSettingRepositoryImpl) {
		this.fileSettingRepositoryImpl = fileSettingRepositoryImpl;
	}

	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	private IFilesRepository filesRepositoryImpl;
	private IFileSettingRepository fileSettingRepositoryImpl;
	
	private IMessageService messageService;
	/** log instance. */
	protected final Logger log = LoggerFactory.getLogger(getClass());
}
