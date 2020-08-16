package com.person.web.admin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.person.framework.mybatis.Criteria;
import com.person.web.admin.model.SchoolScore;
import com.person.web.admin.model.SchoolStudent;
import com.person.web.admin.service.ISchoolScoreService;
import com.person.web.admin.service.ISchoolStudentService;
import com.person.web.admin.repository.ISchoolScoreRepository;
import com.person.web.admin.service.ISysDictService;
import com.person.framework.service.IMessageService;
import com.person.framework.utils.DateUtil;
import com.person.framework.utils.StringUtil;
import com.person.framework.constant.MessageConstant;
import com.person.framework.constant.DefaultConstant;
import com.person.framework.exception.BizException;
import com.person.web.blog.service.ISequenceService;

public class SchoolScoreServiceImpl implements ISchoolScoreService {

	public List<Map<String, Object>> queryPageList(Criteria<SchoolScore> param) {
		List<SchoolScore> list = schoolScoreRepository.queryPage(param);
		List<Map<String, Object>> ret = sysDictService.translateToMapList(list);
		return ret;
	}

	public List<SchoolScore> queryPage(Criteria<SchoolScore> param) {
		List<SchoolScore> list = schoolScoreRepository.queryPage(param);
		return list;
	}

	public Integer queryPageCount(Criteria<SchoolScore> param) {
		return (Integer) schoolScoreRepository.queryPageCount(param);
	}
	/**
	 * 查询是否存在记录
	 * @param projectId
	 * @param studentId
	 * @return
	 */
	@Override
	public SchoolScore queryByStu(String projectId,String studentId) {
		if(StringUtil.isNullOrBlank(studentId) || StringUtil.isNullOrBlank(projectId)) 
			return null;
		Criteria<SchoolScore> param = new Criteria<SchoolScore>();
		param.addParam("projectId", projectId);
		param.addParam("studentId", studentId);
		return schoolScoreRepository.queryByStu(param);
	}
	
	public SchoolScore queryEntityById(String id) {
		return schoolScoreRepository.queryEntityById(id);
	}

	public String insert(SchoolScore record) {
		int result = schoolScoreRepository.insert(record);
		if (result != 1) {
			return messageService.message(MessageConstant.C00010);
		}
		return null;
	}

	public String insertOrUpdate(SchoolScore record) {
		String msg = null;
		try {
			if (record.getId() == null || record.getId().length() == 0) {
				record.setId(sequenceService
						.getUpdateQuerySeq(DefaultConstant.SEQ_COMMON));
				if (schoolScoreRepository.insert(record) == 0) {
					msg = messageService.message(MessageConstant.C00010);
				}
			} else {
				Criteria<SchoolScore> param = new Criteria<SchoolScore>();
				param.addParam("id", record.getId()).setRecord(record);
				if (schoolScoreRepository.updateByCriteria(param) == 0) {
					msg = messageService.message(MessageConstant.C00011);
				}
			}
		} catch (Exception e) {
			msg = messageService.message(MessageConstant.C00000);
		}
		return msg;
	}

	public String updateByCriteria(Criteria<SchoolScore> param) {
		int result = schoolScoreRepository.updateByCriteria(param);
		if (result != 1) {
			return messageService.message(MessageConstant.C00011);
		}
		return null;
	}
	@Override
	public String insertList(List<SchoolScore> list) {
		int result = schoolScoreRepository.insertList(list);
		if (result == 0) {
			return messageService.message(MessageConstant.C00010);
		}
		return null;
	}
	@Override
	public String updateList(List<SchoolScore> list) {
		int result = schoolScoreRepository.updateList(list);
		if (result == 0) {
			return messageService.message(MessageConstant.C00011);
		}
		return null;
	}
	
	/**
	 * 评分数据导入使用
	 * 
	 * @return
	 */
	@Override
	public void insertScoreData(Map<String,SchoolScore> map,String projectId,String userId){
		if(StringUtil.isNullOrBlank(projectId)) throw new BizException();
		SchoolScore queryScore = null;
		SchoolScore score = null;
		SchoolStudent student = null;
		List<SchoolStudent> studentList = new ArrayList<SchoolStudent>();
		List<SchoolScore> insertList = new ArrayList<SchoolScore>();
		List<SchoolScore> updateList = new ArrayList<SchoolScore>();
		for (String key : map.keySet()) {
			score = map.get(key);
			
			queryScore = queryByStu(projectId, key);
			if(queryScore == null) {
				score.setProjectId(projectId);
				score.setId(sequenceService.getUpdateQuerySeq(DefaultConstant.SEQ_COMMON));
				score.setCreateTime(DateUtil.getCurrentDate());
				insertList.add(score);
			}else {
				score.setId(queryScore.getId());
				updateList.add(score);
			}
			// 新学生
			if (schoolStudentService.queryEntityById(key) == null) {
				student = new SchoolStudent();
				student.setStudentId(score.getStudentId());
				student.setStudentClass(score.getStudentClass());
				student.setStudentName(score.getStudentName());
				student.setCreateTime(DateUtil.getCurrentDate());
				student.setCreateBy(userId);
				studentList.add(student);
			}
		}
		
		String ret = null;
		if(studentList.size() > 0) ret = schoolStudentService.inserList(studentList);
		if (!StringUtil.isNullOrBlank(ret)) {
			throw new BizException();
		}
		
		if(insertList.size() > 0) ret = insertList(insertList);
		if (!StringUtil.isNullOrBlank(ret)) {
			throw new BizException();
		}
		
		if(updateList.size() > 0) ret = updateList(updateList);
		if (!StringUtil.isNullOrBlank(ret)) {
			throw new BizException();
		}
	}

	public String deleteById(String id) {
		int result = schoolScoreRepository.deleteById(id);
		if (result != 1) {
			return messageService.message(MessageConstant.C00012);
		}
		return null;
	}

	public String deleteByCriteria(Criteria<SchoolScore> param) {
		schoolScoreRepository.deleteByCriteria(param);
		return null;
	}

	public void setSchoolScoreRepository(ISchoolScoreRepository repository) {
		this.schoolScoreRepository = repository;
	}

	public void setSysDictService(ISysDictService sysDictService) {
		this.sysDictService = sysDictService;
	}

	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	public void setSequenceService(ISequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

	public void setSchoolStudentService(ISchoolStudentService schoolStudentService) {
		this.schoolStudentService = schoolStudentService;
	}

	private ISchoolScoreRepository schoolScoreRepository;

	private ISysDictService sysDictService;

	private IMessageService messageService;

	private ISequenceService sequenceService;
	
	private ISchoolStudentService schoolStudentService;

}
