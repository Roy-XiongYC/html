package com.person.web.fs.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.person.framework.constant.FileMessageConstant;
import com.person.framework.constant.UrlConstant;
import com.person.framework.service.IMessageService;
import com.person.framework.utils.DateUtil;
import com.person.framework.utils.RandUtil;
import com.person.framework.utils.StringUtil;
import com.person.framework.web.JsonResult;
import com.person.web.fs.model.FileSetting;
import com.person.web.fs.model.Files;
import com.person.web.fs.service.FileService;

@Controller
@RequestMapping("admin/file")
public class UploadFileController {

	@Autowired
	private FileService fileBll;

	@Autowired
	private IMessageService messageService;

	protected final Logger log = LoggerFactory.getLogger(getClass());

	@ResponseBody
	@RequestMapping(value = "uploadFile", method = { RequestMethod.POST })
	public JsonResult uploadFile(HttpServletRequest req) throws IOException {
		JsonResult jsonResult = new JsonResult();

		try {
			boolean isMultipart = ServletFileUpload.isMultipartContent(req);//
			// 检查输入请求是否为multipart表单数据。
			if (isMultipart != true) {
				return jsonResult.declareFailure(messageService
						.message(FileMessageConstant.M002));
			}

			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
			CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest
					.getFile("file");// 获取前台的图片信息

			if (file == null) {
				return jsonResult.declareFailure(messageService
						.message(FileMessageConstant.M001));
			}
			
			// 文件WEB访问路径
			String webPath = "";
			// 文件保存的目录
			String folder = "";
			// 支持的护展名
			String Extensions = "";
			// 默认获取文件上传路径
			FileSetting pathModel = fileBll.getFileFolder("ab");
			if (pathModel == null) {
				return jsonResult.declareFailure(messageService
						.message(FileMessageConstant.M004));
			} else if (StringUtil.isNullOrBlank(pathModel.getFolder())
					|| StringUtil.isNullOrBlank(pathModel.getWebPath())) {
				return jsonResult.declareFailure(messageService
						.message(FileMessageConstant.M005));
			} else {
				folder = pathModel.getFolder();
				webPath = pathModel.getWebPath();
				Extensions = pathModel.getExtensions();
			}

			Map<String, Object> filePathList = new HashMap<String, Object>();

			int count = 0;
			FileItem item = (FileItem) file.getFileItem();
			// 检查当前项目是普通表单项目还是上传文件。
			if (!item.isFormField()
					&& !StringUtil.isNullOrBlank(item.getName())) { // 如果是普通表单项目，显示表单内容。
				String fileName = item.getName();// 获得文件名
				String extention = ""; // 扩展名
				String FieldName = item.getFieldName();

				int i = fileName.lastIndexOf(".");
				if (i > -1 && i < fileName.length()) {
					extention = fileName.substring(i + 1).toLowerCase(); // --扩展名
				}

				// 根据扩展名判断是文件类型是不是所支持的类型
				if (Extensions.indexOf(extention) < 0) {
					log.error(messageService.message(FileMessageConstant.M006));
					return jsonResult.declareFailure(messageService
							.message(FileMessageConstant.M006));
				}

				String newFileName = DateUtil.formatDate(new Date(),
						DateUtil.FORMAT_DATETIME_YYYYMMDDHHMMSS)
						+ RandUtil.randMath(6) + "." + extention;// 生成在服务器保存为新的文件名

				if (folder.lastIndexOf("/") != folder.length() - 1) {
					folder = folder + "/";
				}
				if (webPath.lastIndexOf("/") != webPath.length() - 1) {
					webPath = webPath + "/";
				}
				// 操作系统
				String osName = System.getProperty("os.name");
				if (!osName.toLowerCase().startsWith("windows")) {
					if (folder.indexOf("/") == 0) {
						folder = "/" + folder;
					}
				}

				// 写入数据库
				Files fileModel = new Files();
				fileModel.setFileType("ab");
				fileModel.setNewFileName(webPath+newFileName);
				fileModel.setOriginalFileName(fileName);
				fileModel.setFolder(folder);
				fileModel.setWebPath(webPath);
				fileModel.setExtension(extention);
				fileModel.setUploadDate(new Date());
				if (!fileBll.insertFileInfo(fileModel)) {
					log.error(messageService.message(FileMessageConstant.M007));
					return jsonResult.declareFailure(messageService
							.message(FileMessageConstant.M007));
				}

				File dirname = new File(folder);
				if (!dirname.exists()) { // 目录不存在
					boolean result = dirname.mkdirs(); // 创建目录
					log.info(messageService.message(FileMessageConstant.M008,
							result, dirname.getAbsolutePath()));
				}

				// -------------
				File storedFile = new File(folder + newFileName);
				item.write(storedFile);

				if (filePathList.containsKey(FieldName)) {
					filePathList.put(FieldName + count, webPath + newFileName);
				} else {
					filePathList.put(FieldName, webPath + newFileName);
					filePathList.put("show", String.format(UrlConstant.RESOURCE_URL , webPath + newFileName));
				}

				count++; // 计数器，用来区别存在重复表单NAME时
				fileBll.updateFileInfo(newFileName); // 更改文件状态
				jsonResult.declareSuccess();
				jsonResult.addAll(filePathList);
			}
		} catch (FileUploadException e) {
			log.error(
					messageService.message(FileMessageConstant.M009,
							e.getMessage()), e);
			jsonResult.declareFailure(messageService.message(
					FileMessageConstant.M009, e.getMessage()));
		} catch (Exception e) {
			log.error(
					messageService.message(FileMessageConstant.M010,
							e.getMessage()), e);
			jsonResult.declareFailure(messageService.message(
					FileMessageConstant.M010, e.getMessage()));
		}

		return jsonResult;
	}

	/**
	 * 删除单个文件
	 * 
	 * @param fileName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteFile", method = { RequestMethod.POST,RequestMethod.GET })
	public JsonResult deleteFile(@RequestParam(required = true) String fileName) {
		return fileBll.deleteFile(fileName);
	}

	/**
	 * 删除多个文件，以逗号分隔。
	 * 
	 * @param fileNames
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteFiles", method = { RequestMethod.POST })
	public JsonResult deleteFiles(@RequestParam(required = true) String fileName) {
		JsonResult jsonResult = new JsonResult();
		String[] filename = fileName.split(",");
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < filename.length; i++) {
			try {
				jsonResult = fileBll.deleteFile(filename[i]);
				if (!jsonResult.getSuccess()) {
					log.info("delete file error : " + filename[i]);
					continue;
				}
			} catch (Exception e) {
				map.put(filename[i], false);
				log.error(
						messageService.message(FileMessageConstant.M010,
								e.getMessage()), e);
			}
		}
		jsonResult.declareSuccess();
		jsonResult.addAll(map);
		return jsonResult;
	}
}
