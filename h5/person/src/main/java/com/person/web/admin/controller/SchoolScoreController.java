package com.person.web.admin.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.person.framework.constant.MessageConstant;
import com.person.framework.exception.BizException;
import com.person.framework.mybatis.Criteria;
import com.person.framework.utils.DateUtil;
import com.person.framework.utils.ExcelUtil;
import com.person.framework.utils.RandUtil;
import com.person.framework.utils.StringUtil;
import com.person.framework.web.AdminAppController;
import com.person.framework.web.JsonResult;
import com.person.framework.web.PageResult;
import com.person.web.admin.model.SchoolScore;
import com.person.web.admin.service.ISchoolScoreService;

@Controller
@RequestMapping(value = "admin/schoolScore")
public class SchoolScoreController extends AdminAppController<SchoolScore> {

	@Autowired
	private ISchoolScoreService service;

	@RequestMapping(value = "/showScore", method = { RequestMethod.GET })
	public String showScore() {
		return "web/admin/school/showScore";
	}
	
	@RequestMapping(value = "/listPage", method = { RequestMethod.GET })
	public String showList() {
		return "web/admin/school/scoreList";
	}
	
	@ResponseBody
	@RequestMapping(value = "/modify", method = { RequestMethod.POST })
	public JsonResult modify(
			@RequestParam(required = false) String guidanceParam,
			@RequestParam(required = false) String teacherParam) {
		JsonResult jr = JsonResult.newInstance();
		String ret = null;
		if(StringUtil.isNullOrBlank(teacherParam) 
				&&StringUtil.isNullOrBlank(guidanceParam))
		{
			return jr.declareFailure(messageService.message("保存失败"));
		}
		
		String[] list = null;
		String[] value = null;
		SchoolScore score = null;
		List<SchoolScore> updateList = new ArrayList<SchoolScore>();
		try {
			if (!StringUtil.isNullOrBlank(guidanceParam)) {
				list = guidanceParam.split(",");
				for (String string : list) {
					score = new SchoolScore();
					value = string.split("\\|");
					score.setGuidanceValue(Double.valueOf(value[0]));
					score.setId(value[1]);
					updateList.add(score);
				}
			}
			
			if (!StringUtil.isNullOrBlank(teacherParam)) {
				list = teacherParam.split(",");
				for (String string : list) {
					score = new SchoolScore();
					value = string.split("\\|");
					score.setTeacherValue(Double.valueOf(value[0]));
					score.setId(value[1]);
					updateList.add(score);
				}
			}
			
			ret = service.updateList(updateList);
			
		} catch (BizException e) {
			ret = e.getMessage();
		} catch (Exception e) {
			ret = messageService.message(MessageConstant.C00000);
		}
		return ret == null ? jr.declareSuccess() : jr.declareFailure(ret);
	}

	@ResponseBody
	@RequestMapping(value = "/data", method = { RequestMethod.POST })
	public PageResult queryPage(
			@RequestParam(required = false, defaultValue = "1", value = "page") int page,
			@RequestParam(required = false, defaultValue = "10", value = "rp") int pageSize,
			@RequestParam(required = false, value = "sEcho") String sEcho,
			@RequestParam(required = false) String studentId,
			@RequestParam(required = false) String studentName,
			@RequestParam(required = true,defaultValue = "1") String studentClass,
			@RequestParam(required = true,defaultValue = "1") String projectId
			) {
		Criteria<SchoolScore> param = new Criteria<SchoolScore>();
		param.setPageSize(pageSize).setStartIndex(page);
		if(!StringUtil.isNullOrBlank(studentId)) param.addParam("studentIdLike", studentId);
		if(!StringUtil.isNullOrBlank(studentName)) param.addParam("studentNameLike", studentName);
		param.addParam("studentClass", studentClass);
		param.addParam("projectId", projectId);
		param.setOrderBy(" sc.create_time desc ");
		
		PageResult pageResult = new PageResult();
		pageResult.setsEcho(sEcho);
		pageResult.setAaData(service.queryPageList(param));
		pageResult.setiTotalDisplayRecords(service.queryPageCount(param));
		pageResult
				.setiTotalRecords(service.queryPageCount(param.clearParams()));
		return pageResult;
	}

	@RequestMapping(value = "/addPage")
	public String detail(@RequestParam(required = false) String id, ModelMap map) {
		if (!StringUtil.isNullOrBlank(id)) {
			SchoolScore schoolScore = service.queryEntityById(id);
			if (null != schoolScore) {
				map.put("schoolScore", schoolScore);
			}
		}
		return "web/admin/school/scoreDetail";
	}

	@RequestMapping(value = "/importPage")
	public String importPage() {
		return "web/admin/school/importScore";
	}
	
	@ResponseBody
	@RequestMapping(value="dataimport",method = {RequestMethod.POST}, produces = { "text/plain;charset=utf-8" })
	public String batchimport(HttpServletRequest request,
			HttpServletResponse response,@RequestParam(required = true) String projectId) throws Exception{
		JsonResult jr = JsonResult.newInstance();
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);// 检查输入请求是否为multipart表单数据。
		if (isMultipart != true) {
			return JSONObject.toJSONString(jr.declareFailure(messageService
					.message(MessageConstant.C00000)));
		}
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest
				.getFile("file");
		try {
			//保存至本地
			FileItem fileitem = null;
			if (file != null) {
				fileitem = file.getFileItem();
			}
			if (fileitem == null
					|| (fileitem.getName().toLowerCase().lastIndexOf(".xls") < 0 && fileitem
							.getName().toLowerCase().lastIndexOf(".xlsx") < 0)) {
				return JSONObject.toJSONString(jr.declareFailure(messageService
						.message(MessageConstant.C00000)));
			}
			String fileName = fileitem.getName();
			if (fileName.indexOf("\\") > 0 || fileName.indexOf("/") > 0) {
				fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
			}
			String filename = DateUtil.formatDate(new Date(),
					DateUtil.FORMAT_DATETIME_YYYYMMDDHHMMSS)
					+ "_"
					+ RandUtil.randMath(5) + "_" + fileName;
			log.info("filename as to :"+filename);
			String os = System.getProperty("os.name");
			if (os == null || "".equals(os)) {
				return JSONObject.toJSONString(jr.declareFailure(messageService
						.message(MessageConstant.C00000)));
			}
			os = os.toLowerCase();
			String storePath = null;
			if (os.startsWith("Windows".toLowerCase())) {
				storePath = "D:/file/tmp/";
			} else {
				storePath = "/home/tmp/";
			}
			File dirname = new File(storePath);
			if (!dirname.isDirectory()) { // 目录不存在
				dirname.mkdirs(); // 创建目录
			}
	
			String filePath = storePath + filename;
			log.info("filePath as to :"+filePath);
			File storedFile = new File(filePath);
			fileitem.write(storedFile);
			
			//读取
			String[] fileds = new String[] { "SignValue","VideoValue","NvideoValue","ClassValue","VoteValue","BrainValue","DiscussValue",
					"TestValue","TaskValue"};
//			Map<String, SchoolScore> map = ExcelUtil.readSchoolExcel1(filePath, fileds);
			Map<String, SchoolScore> map = ExcelUtil.readSchoolExcel(filePath, fileds);
			log.info(map.toString());
			log.info(JSONObject.toJSONString(map));
			//操作
			service.insertScoreData(map, projectId, getApplicationUserId());
		} catch (Exception e) {
			return JSONObject.toJSONString(jr.declareFailure(messageService
					.message(MessageConstant.C00027)));
		}
		return JSONObject.toJSONString(jr.declareSuccess());
	}
}