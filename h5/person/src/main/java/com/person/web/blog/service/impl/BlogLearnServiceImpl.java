package com.person.web.blog.service.impl;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.person.framework.constant.DefaultConstant;
import com.person.framework.constant.MessageConstant;
import com.person.framework.constant.RedisConstant;
import com.person.framework.mybatis.Criteria;
import com.person.framework.redis.RedisServiceUtil;
import com.person.framework.service.IMessageService;
import com.person.framework.utils.DateUtil;
import com.person.framework.utils.StringUtil;
import com.person.web.blog.model.BlogLearn;
import com.person.web.blog.model.BlogUser;
import com.person.web.blog.repository.IBlogLearnRepository;
import com.person.web.blog.service.IBlogLearnService;
import com.person.web.blog.service.IBlogUserService;
import com.person.web.blog.service.ISequenceService;
import com.person.web.fs.service.FileService;

public class BlogLearnServiceImpl implements IBlogLearnService {

	public List<BlogLearn> queryPage(Criteria<BlogLearn> param) {
		return blogLearnRepository.queryPage(param);
	}

	public Integer queryPageCount(Criteria<BlogLearn> param) {
		return (Integer) blogLearnRepository.queryPageCount(param);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BlogLearn> queryPageByRomm(String userSign) {
		String str = RedisServiceUtil.getJedis(
				RedisServiceUtil.SERVICE_REDIS_COMMON,
				String.format(RedisConstant.redis_blog_learn_romm, userSign));
		if (!StringUtil.isNullOrBlank(str)) {
			return JSONObject.toJavaObject(JSONObject.parseObject(str),List.class);
		}
		Criteria<BlogLearn> param = new Criteria<BlogLearn>();
		param.addParam("learnIs", DefaultConstant.COMMON_YES);
		param.addParam("userSign", userSign);
		param.setOrderBy(" create_time desc ");
		param.setPageSize(5);
		return blogLearnRepository.queryPage(param);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BlogLearn> queryPageByNew(String userSign) {
		String str = RedisServiceUtil.getJedis(
				RedisServiceUtil.SERVICE_REDIS_COMMON,
				String.format(RedisConstant.redis_blog_learn_new, userSign));
		if (!StringUtil.isNullOrBlank(str)) {
			return JSONObject.toJavaObject(JSONObject.parseObject(str),List.class);
		}
		Criteria<BlogLearn> param = new Criteria<BlogLearn>();
		param.setOrderBy(" create_time desc ");
		param.addParam("userSign", userSign);
		param.setPageSize(5);
		return blogLearnRepository.queryPage(param);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BlogLearn> queryPageByRan(String userSign) {
		String str = RedisServiceUtil.getJedis(
				RedisServiceUtil.SERVICE_REDIS_COMMON,
				String.format(RedisConstant.redis_blog_learn_ran, userSign));
		if (!StringUtil.isNullOrBlank(str)) {
			return JSONObject.toJavaObject(JSONObject.parseObject(str),List.class);
		}
		Criteria<BlogLearn> param = new Criteria<BlogLearn>();
		param.setOrderBy(" rand() ");
		param.addParam("userSign", userSign);
		param.setPageSize(5);
		return blogLearnRepository.queryPage(param);
	}

	public BlogLearn queryEntityById(String id) {
		return blogLearnRepository.queryEntityById(id);
	}

	public String insert(BlogLearn record) {
		int result = blogLearnRepository.insert(record);
		if (result != 1) {
			return messageService.message(MessageConstant.C00010);
		}
		return null;
	}

	@Override
	public String insertOrUpdate(BlogLearn record) {
		String ret = null;
		BlogLearn learn = null;
		if (StringUtil.isNullOrBlank(record.getId())) {
			record.setId(sequenceService
					.getUpdateQuerySeq(DefaultConstant.SEQ_COMMON));
			record.setCreateTime(DateUtil.getCurrentDate());
			ret = insert(record);
		} else {
			learn = queryEntityById(record.getId());
			Criteria<BlogLearn> param = new Criteria<BlogLearn>();
			param.addParam("id", record.getId());
			param.setRecord(record);
			ret = updateByCriteria(param);
		}

		if (StringUtil.isNullOrBlank(ret)) {
			if (learn != null && !StringUtil.isNullOrBlank(learn.getLearnImg()) 
					&& !learn.getLearnImg().equals(record.getLearnImg())) {
				fileBill.deleteFile(learn.getLearnImg());
			}
			BlogUser user = blogUserService.queryEntityById(record.getUserId());
			RedisServiceUtil.delJedis(RedisServiceUtil.SERVICE_REDIS_COMMON, String
					.format(RedisConstant.redis_blog_user_sign,user.getUserSign()));
			RedisServiceUtil.delJedis(RedisServiceUtil.SERVICE_REDIS_COMMON, String
							.format(RedisConstant.redis_blog_learn,user.getUserSign()));
			RedisServiceUtil.delJedis(RedisServiceUtil.SERVICE_REDIS_COMMON, String
					.format(RedisConstant.redis_blog_learn_ran,user.getUserSign()));
			RedisServiceUtil.delJedis(RedisServiceUtil.SERVICE_REDIS_COMMON, String
					.format(RedisConstant.redis_blog_learn_romm,user.getUserSign()));
			RedisServiceUtil.delJedis(RedisServiceUtil.SERVICE_REDIS_COMMON, String
					.format(RedisConstant.redis_blog_learn_new,user.getUserSign()));
		}
		return ret;
	}
	
	public String updateByCriteria(Criteria<BlogLearn> param) {
		int result = blogLearnRepository.updateByCriteria(param);
		if (result != 1) {
			return messageService.message(MessageConstant.C00011);
		}
		return null;
	}

	public String deleteById(String id) {
		int result = blogLearnRepository.deleteById(id);
		if (result != 1) {
			return messageService.message(MessageConstant.C00012);
		}
		return null;
	}

	public String deleteByCriteria(Criteria<BlogLearn> param) {
		blogLearnRepository.deleteByCriteria(param);
		return null;
	}

	public void setBlogLearnRepository(IBlogLearnRepository repository) {
		this.blogLearnRepository = repository;
	}

	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	public void setBlogUserService(IBlogUserService blogUserService) {
		this.blogUserService = blogUserService;
	}

	public void setFileBill(FileService fileBill) {
		this.fileBill = fileBill;
	}

	public void setSequenceService(ISequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

	private IBlogLearnRepository blogLearnRepository;

	private IMessageService messageService;
	
	private IBlogUserService blogUserService;
	
	private FileService fileBill;
	
	private ISequenceService sequenceService;

}
