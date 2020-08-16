package com.person.web.admin.service.impl;

import java.util.List;
import java.util.Map;
import com.person.framework.mybatis.Criteria;
import com.person.web.admin.model.SchoolProject;
import com.person.web.admin.service.ISchoolProjectService;

import com.person.web.admin.repository.ISchoolProjectRepository;

import com.person.web.admin.service.ISysDictService;
import com.person.framework.service.IMessageService;
import com.person.framework.constant.MessageConstant;
import com.person.framework.constant.DefaultConstant;
import com.person.web.blog.service.ISequenceService;

public class SchoolProjectServiceImpl implements ISchoolProjectService {

	public List<Map<String, Object>> queryPageList(Criteria<SchoolProject> param) {
		List<SchoolProject> list = schoolProjectRepository.queryPage(param);
		List<Map<String, Object>> ret = sysDictService.translateToMapList(list);
		return ret;
	}

	public List<SchoolProject> queryPage(Criteria<SchoolProject> param) {
		List<SchoolProject> list = schoolProjectRepository.queryPage(param);
		return list;
	}

	public Integer queryPageCount(Criteria<SchoolProject> param) {
		return (Integer) schoolProjectRepository.queryPageCount(param);
	}

	public SchoolProject queryEntityById(String id) {
		return schoolProjectRepository.queryEntityById(id);
	}

	public String insert(SchoolProject record) {
		int result = schoolProjectRepository.insert(record);
		if (result != 1) {
			return messageService.message(MessageConstant.C00010);
		}
		return null;
	}

	public String insertOrUpdate(SchoolProject record) {
		String msg = null;
		try {
			if (record.getProjectId() == null
					|| record.getProjectId().length() == 0) {
				record.setProjectId(sequenceService
						.getUpdateQuerySeq(DefaultConstant.SEQ_COMMON));
				if (schoolProjectRepository.insert(record) == 0) {
					msg = messageService.message(MessageConstant.C00010);
				}
			} else {
				Criteria<SchoolProject> param = new Criteria<SchoolProject>();
				param.addParam("projectId", record.getProjectId()).setRecord(
						record);
				if (schoolProjectRepository.updateByCriteria(param) == 0) {
					msg = messageService.message(MessageConstant.C00011);
				}
			}
		} catch (Exception e) {
			msg = messageService.message(MessageConstant.C00000);
		}
		return msg;
	}

	public String updateByCriteria(Criteria<SchoolProject> param) {
		int result = schoolProjectRepository.updateByCriteria(param);
		if (result != 1) {
			return messageService.message(MessageConstant.C00011);
		}
		return null;
	}

	public String deleteById(String id) {
		int result = schoolProjectRepository.deleteById(id);
		if (result != 1) {
			return messageService.message(MessageConstant.C00012);
		}
		return null;
	}

	public String deleteByCriteria(Criteria<SchoolProject> param) {
		schoolProjectRepository.deleteByCriteria(param);
		return null;
	}

	public void setSchoolProjectRepository(ISchoolProjectRepository repository) {
		this.schoolProjectRepository = repository;
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

	private ISchoolProjectRepository schoolProjectRepository;

	private ISysDictService sysDictService;

	private IMessageService messageService;

	private ISequenceService sequenceService;

}
