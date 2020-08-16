package com.person.web.blog.service.impl;

import java.util.List;

import com.person.framework.constant.DefaultConstant;
import com.person.framework.constant.MessageConstant;
import com.person.framework.constant.RedisConstant;
import com.person.framework.mybatis.Criteria;
import com.person.framework.redis.RedisServiceUtil;
import com.person.framework.service.IMessageService;
import com.person.framework.utils.DateUtil;
import com.person.framework.utils.StringUtil;
import com.person.web.blog.model.BlogUser;
import com.person.web.blog.model.BlogXc;
import com.person.web.blog.repository.IBlogXcRepository;
import com.person.web.blog.service.IBlogUserService;
import com.person.web.blog.service.IBlogXcService;
import com.person.web.blog.service.ISequenceService;
import com.person.web.fs.service.FileService;

public class BlogXcServiceImpl implements IBlogXcService {

	public List<BlogXc> queryPage(Criteria<BlogXc> param) {
		return blogXcRepository.queryPage(param);
	}

	public Integer queryPageCount(Criteria<BlogXc> param) {
		return (Integer) blogXcRepository.queryPageCount(param);
	}

	public BlogXc queryEntityById(String id) {
		return blogXcRepository.queryEntityById(id);
	}

	public String insert(BlogXc record) {
		int result = blogXcRepository.insert(record);
		if (result != 1) {
			return messageService.message(MessageConstant.C00010);
		}
		return null;
	}

	@Override
	public String insertOrUpdate(BlogXc record) {
		String ret = null;
		BlogXc sc = null;
		if (StringUtil.isNullOrBlank(record.getId())) {
			record.setId(sequenceService
					.getUpdateQuerySeq(DefaultConstant.SEQ_COMMON));
			record.setCreateTime(DateUtil.getCurrentDate());
			ret = insert(record);
		} else {
			sc = queryEntityById(record.getId());
			Criteria<BlogXc> param = new Criteria<BlogXc>();
			param.addParam("id", record.getId());
			param.setRecord(record);
			ret = updateByCriteria(param);
		}

		if (StringUtil.isNullOrBlank(ret)) {
			if (sc != null && !StringUtil.isNullOrBlank(sc.getImg()) 
					&& !sc.getImg().equals(record.getImg())) {
				fileBill.deleteFile(sc.getImg());
			}
			BlogUser user = blogUserService.queryEntityById(record.getUserId());
			RedisServiceUtil.delJedis(RedisServiceUtil.SERVICE_REDIS_COMMON, String
					.format(RedisConstant.redis_blog_user_sign,user.getUserSign()));
			RedisServiceUtil
					.delJedis(RedisServiceUtil.SERVICE_REDIS_COMMON, String
							.format(RedisConstant.redis_blog_xc,user.getUserSign()));
		}
		return ret;
	}

	public String updateByCriteria(Criteria<BlogXc> param) {
		int result = blogXcRepository.updateByCriteria(param);
		if (result != 1) {
			return messageService.message(MessageConstant.C00011);
		}
		return null;
	}

	public String deleteById(String id) {
		int result = blogXcRepository.deleteById(id);
		if (result != 1) {
			return messageService.message(MessageConstant.C00012);
		}
		return null;
	}

	public String deleteByCriteria(Criteria<BlogXc> param) {
		blogXcRepository.deleteByCriteria(param);
		return null;
	}

	public void setBlogXcRepository(IBlogXcRepository repository) {
		this.blogXcRepository = repository;
	}

	private IBlogXcRepository blogXcRepository;

	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	public void setBlogUserService(IBlogUserService blogUserService) {
		this.blogUserService = blogUserService;
	}

	public void setSequenceService(ISequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

	public void setFileBill(FileService fileBill) {
		this.fileBill = fileBill;
	}

	private IMessageService messageService;
	private IBlogUserService blogUserService;

	private ISequenceService sequenceService;
	
	private FileService fileBill;
}
