package com.person.web.blog.service.impl;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.person.framework.constant.DefaultConstant;
import com.person.framework.constant.MessageConstant;
import com.person.framework.constant.RedisConstant;
import com.person.framework.constant.UrlConstant;
import com.person.framework.mybatis.Criteria;
import com.person.framework.redis.RedisServiceUtil;
import com.person.framework.service.IMessageService;
import com.person.framework.utils.DateUtil;
import com.person.framework.utils.StringUtil;
import com.person.framework.web.AppBlogUserInfo;
import com.person.web.blog.model.BlogAbout;
import com.person.web.blog.model.BlogUser;
import com.person.web.blog.repository.IBlogUserRepository;
import com.person.web.blog.service.IBlogAboutService;
import com.person.web.blog.service.IBlogLearnService;
import com.person.web.blog.service.IBlogLinkService;
import com.person.web.blog.service.IBlogUserService;
import com.person.web.blog.service.ISequenceService;
import com.person.web.fs.service.FileService;

public class BlogUserServiceImpl implements IBlogUserService {

	public List<BlogUser> queryPage(Criteria<BlogUser> param) {
		return blogUserRepository.queryPage(param);
	}
	public Integer queryPageCount(Criteria<BlogUser> param) {
		return (Integer) blogUserRepository.queryPageCount(param);
	}

	public BlogUser queryEntityById(String id) {
		return blogUserRepository.queryEntityById(id);
	}

	public BlogUser queryEntityBySign(String userSign) {
		return blogUserRepository.queryEntityBySign(userSign);
	}

	/**
	 * 从redis中获取数据，qq与image 均已加工完成
	 */
	@Override
	public AppBlogUserInfo queryUserInfoBySign(String userSign) {
		AppBlogUserInfo user = null;
		String userStr = RedisServiceUtil.getJedis(
				RedisServiceUtil.SERVICE_REDIS_COMMON,
				String.format(RedisConstant.redis_blog_user_sign, userSign));
		if (!StringUtil.isNullOrBlank(userStr)) {
			user = JSONObject.toJavaObject(JSONObject.parseObject(userStr),
					AppBlogUserInfo.class);
		} else {
			user = new AppBlogUserInfo();
			// 个人信息
			BlogUser blogUser = blogUserRepository.queryEntityBySign(userSign);
			blogUser.setQq(String.format(UrlConstant.QQ_URL, blogUser.getQq()));
			blogUser.setImage(String.format(UrlConstant.RESOURCE_URL,
					blogUser.getImage()));

			// 文章
			user.setBlogUser(blogUser);
			user.setLinkList(blogLinkService.queryPageLimitFive(blogUser.getUserId()));
			user.setRanLearn(blogLearnService.queryPageByRan(userSign));
			user.setNewLearn(blogLearnService.queryPageByNew(userSign));
			user.setRecomLearn(blogLearnService.queryPageByRomm(userSign));
			RedisServiceUtil
					.setJedisObject(RedisServiceUtil.SERVICE_REDIS_COMMON,
							String.format(RedisConstant.redis_blog_user_sign,
									userSign), user);
		}
		return user;
	}

	public String insert(BlogUser record) {
		int result = blogUserRepository.insert(record);
		if (result != 1) {
			return messageService.message(MessageConstant.C00010);
		}
		return null;
	}
	
	@Override
	public String insertOrUpdate(BlogUser record) {
		String ret = null;
		BlogUser user = queryEntityBySign(record.getUserSign());
		if (user == null) {
			record.setCreateTime(DateUtil.getCurrentDate());
			ret = insert(record);
		}else {
			if (!user.getUserId().equals(record.getUserId())) {
				return messageService.message(MessageConstant.C00001,"用户标识");
			}
			Criteria<BlogUser> param = new Criteria<BlogUser>();
			param.addParam("userId", record.getUserId());
			param.setRecord(user);
			ret = updateByCriteria(param);
		}
		//关于我
		if (StringUtil.isNullOrBlank(ret)) {
			//服务器图片删除
			if (!StringUtil.isNullOrBlank(user.getImage())
					&& record.getImage().indexOf(user.getImage()) < 0) {
				fileBill.deleteFile(user.getImage());
			}
			
			RedisServiceUtil.delJedis(RedisServiceUtil.SERVICE_REDIS_COMMON, String
					.format(RedisConstant.redis_blog_user_sign, queryEntityById(record.getUserId()).getUserSign()));
			
			BlogAbout about = blogAboutService.queryEntityByUser(record.getUserSign());
			if (about == null) {
				about = new BlogAbout();
				about.setId(sequenceService.getUpdateQuerySeq(DefaultConstant.SEQ_COMMON));
				about.setUserId(record.getUserId());
				about.setCreateTime(DateUtil.getCurrentDate());
				about.setOpeateTime(DateUtil.getCurrentDate());
				about.setAboutContent(record.getAboutContent());
				ret = blogAboutService.insert(about);
			}else {
				Criteria<BlogAbout> param = new Criteria<BlogAbout>();
				about.setOpeateTime(DateUtil.getCurrentDate());
				about.setAboutContent(record.getAboutContent());
				param.setRecord(about);
				param.addParam("id", about.getId());
				ret = blogAboutService.updateByCriteria(param);
				
				if (StringUtil.isNullOrBlank(ret)) {
					RedisServiceUtil.delJedis(RedisServiceUtil.SERVICE_REDIS_COMMON, String
							.format(RedisConstant.redis_blog_about, queryEntityById(record.getUserId()).getUserSign()));
				}
			}
		}
		return ret;
	}

	public String updateByCriteria(Criteria<BlogUser> param) {
		int result = blogUserRepository.updateByCriteria(param);
		if (result != 1) {
			return messageService.message(MessageConstant.C00011);
		}
		return null;
	}

	public String deleteById(String id) {
		int result = blogUserRepository.deleteById(id);
		if (result != 1) {
			return messageService.message(MessageConstant.C00012);
		}
		return null;
	}

	public String deleteByCriteria(Criteria<BlogUser> param) {
		blogUserRepository.deleteByCriteria(param);
		return null;
	}

	public void setBlogUserRepository(IBlogUserRepository repository) {
		this.blogUserRepository = repository;
	}

	private IBlogUserRepository blogUserRepository;

	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	public void setBlogLearnService(IBlogLearnService blogLearnService) {
		this.blogLearnService = blogLearnService;
	}

	public void setBlogLinkService(IBlogLinkService blogLinkService) {
		this.blogLinkService = blogLinkService;
	}

	public void setBlogAboutService(IBlogAboutService blogAboutService) {
		this.blogAboutService = blogAboutService;
	}
	public void setSequenceService(ISequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

	public void setFileBill(FileService fileBill) {
		this.fileBill = fileBill;
	}

	private IMessageService messageService;

	private IBlogLearnService blogLearnService;

	private IBlogLinkService blogLinkService;
	
	private IBlogAboutService blogAboutService;
	
	private ISequenceService sequenceService;
	
	private FileService fileBill;
}
