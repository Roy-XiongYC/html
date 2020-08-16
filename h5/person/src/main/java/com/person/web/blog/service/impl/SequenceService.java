package com.person.web.blog.service.impl;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.person.framework.utils.DateUtil;
import com.person.web.blog.repository.ISequenceRepository;
import com.person.web.blog.service.ISequenceService;

public class SequenceService implements ISequenceService {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public static final String SEQ_HEAD = "SEQ_HEAD";

	public String getUpdateQuerySeq(String prefix) {
		logger.info("seq is start :" + prefix);
		Integer seq = sequenceRepository.updateQuerySeq(prefix);
		String dateStr = DateUtil.formatDate(Calendar.getInstance().getTime(),
				DateUtil.FORMAT_DATE_YYYYMMDD);
		logger.info("seq is end :" + prefix);
		return dateStr + String.format("%011d", seq);
	}

	public void setSequenceRepository(ISequenceRepository sequenceRepository) {
		this.sequenceRepository = sequenceRepository;
	}

	private ISequenceRepository sequenceRepository;

}
