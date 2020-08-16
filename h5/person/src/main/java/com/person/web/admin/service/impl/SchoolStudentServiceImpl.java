package com.person.web.admin.service.impl;

import java.util.List;
import java.util.Map;
import com.person.framework.mybatis.Criteria;
import com.person.web.admin.model.SchoolStudent;
import com.person.web.admin.service.ISchoolStudentService;

import com.person.web.admin.repository.ISchoolStudentRepository;

import com.person.web.admin.service.ISysDictService;
import com.person.framework.service.IMessageService;
import com.person.framework.constant.MessageConstant;
import com.person.framework.constant.DefaultConstant;
import com.person.web.blog.service.ISequenceService;

public class SchoolStudentServiceImpl implements ISchoolStudentService {

	public List<Map<String, Object>> queryPageList(Criteria<SchoolStudent> param) {
		List<SchoolStudent> list = schoolStudentRepository.queryPage(param);
		List<Map<String, Object>> ret = sysDictService.translateToMapList(list);
		return ret;
	}

	public List<SchoolStudent> queryPage(Criteria<SchoolStudent> param) {
		List<SchoolStudent> list = schoolStudentRepository.queryPage(param);
		return list;
	}

	public Integer queryPageCount(Criteria<SchoolStudent> param) {
		return (Integer) schoolStudentRepository.queryPageCount(param);
	}

	public SchoolStudent queryEntityById(String id) {
		return schoolStudentRepository.queryEntityById(id);
	}

	public String insert(SchoolStudent record) {
		int result = schoolStudentRepository.insert(record);
		if (result != 1) {
			return messageService.message(MessageConstant.C00010);
		}
		return null;
	}
	@Override
	public String inserList(List<SchoolStudent> list) {
		int result = schoolStudentRepository.insertList(list);
		if (result == 0) {
			return messageService.message(MessageConstant.C00010);
		}
		return null;
	}

	public String insertOrUpdate(SchoolStudent record) {
		String msg = null;
		try {
			if (record.getStudentId() == null
					|| record.getStudentId().length() == 0) {
				record.setStudentId(sequenceService
						.getUpdateQuerySeq(DefaultConstant.SEQ_COMMON));
				if (schoolStudentRepository.insert(record) == 0) {
					msg = messageService.message(MessageConstant.C00010);
				}
			} else {
				Criteria<SchoolStudent> param = new Criteria<SchoolStudent>();
				param.addParam("studentId", record.getStudentId()).setRecord(
						record);
				if (schoolStudentRepository.updateByCriteria(param) == 0) {
					msg = messageService.message(MessageConstant.C00011);
				}
			}
		} catch (Exception e) {
			msg = messageService.message(MessageConstant.C00000);
		}
		return msg;
	}

	public String updateByCriteria(Criteria<SchoolStudent> param) {
		int result = schoolStudentRepository.updateByCriteria(param);
		if (result != 1) {
			return messageService.message(MessageConstant.C00011);
		}
		return null;
	}

	public String deleteById(String id) {
		int result = schoolStudentRepository.deleteById(id);
		if (result != 1) {
			return messageService.message(MessageConstant.C00012);
		}
		return null;
	}

	public String deleteByCriteria(Criteria<SchoolStudent> param) {
		schoolStudentRepository.deleteByCriteria(param);
		return null;
	}

	public void setSchoolStudentRepository(ISchoolStudentRepository repository) {
		this.schoolStudentRepository = repository;
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

	private ISchoolStudentRepository schoolStudentRepository;

	private ISysDictService sysDictService;

	private IMessageService messageService;

	private ISequenceService sequenceService;

}
