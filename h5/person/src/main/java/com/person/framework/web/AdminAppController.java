package com.person.framework.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.person.framework.constant.DefaultConstant;
import com.person.framework.constant.RedisConstant;
import com.person.framework.constant.SessionConstant;
import com.person.framework.mybatis.Criteria;
import com.person.framework.mybatis.IModel;
import com.person.framework.redis.RedisServiceUtil;
import com.person.framework.service.IMessageService;
import com.person.framework.utils.StringUtil;
import com.person.web.admin.model.SysUser;

/**
 * admin 通用控制器
 * @author wangy
 *
 */
public abstract class AdminAppController<T extends IModel> {

	/** log instance. */
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected IMessageService messageService;
	
	public AppAdminUserInfo getApplicationUserInfo() {
		return AppAdminContext.getUserInfo();
	}
	
	/*
	 * public void setApplicationUserId(String userId){
	 * getApplicationUserInfo().setUserId(userId); }
	 * 
	 * public String getApplicationUserId(){ return
	 * getApplicationUserInfo().getUserId(); }
	 */

	public String getApplicationUserName() {
		if (getApplicationUserInfo() != null) {
			return getApplicationUserInfo().getUserName();
		} else {
			return null;
		}
	}

	public String getApplicationUserId() {
		if (getApplicationUserInfo() != null) {
			return getApplicationUserInfo().getUserId();
		} else {
			return null;
		}

	}

	
	public String getApplicationIpAddress() {
		if (getApplicationUserInfo() != null) {
			return getApplicationUserInfo().getIpAddress();
		} else {
			return null;
		}
	}

	public boolean isLogined(HttpServletRequest request,HttpServletResponse response) {
		
		if (getApplicationUserInfo() == null
				|| StringUtil.isNullOrBlank(getApplicationUserInfo()
						.getUserId())) {
			return false;
		}
		
		//验证是否成功登录的方法 
		Subject subject = SecurityUtils.getSubject();  
		if (!subject.isAuthenticated()) { 
			removeCookieAndRedis(getApplicationUserInfo(), response, request);
			// 清除菜单
			request.getSession(true).removeAttribute(
					SessionConstant.USER_RESOURCE);
			return false;
		}

		return true;
	}

	protected void removeCookieAndRedis(AppAdminUserInfo user,HttpServletResponse response,HttpServletRequest request){
		String redisKey = user.getUserId() + "_" + user.getUserName();
		//String[] cookies = getCookie(request);
		if(StringUtils.isNotBlank(redisKey)){
			Cookie cookie = new Cookie(RedisConstant.COOKIE_NAME, null);
			cookie.setPath("/");
			cookie.setMaxAge(0); 	//	设置cook过期时间为: 马上过期
			response.addCookie(cookie);
			RedisServiceUtil.delJedis(RedisServiceUtil.SERVICE_REDIS_COMMON,redisKey);
		}
	}
	/**
	 * 验证是否为管理员   不是则添加用户条件
	 * @param param
	 * @return
	 */
	protected Criteria<T> validGroup(Criteria<T> param){
		if (param == null) {
			param = new Criteria<T>();
		}
		SysUser user = UserResource.getUserById(getApplicationUserId());
		if (user!=null&&!StringUtil.isNullOrBlank(user.getGroupId())&&
				!user.getGroupId().equals(DefaultConstant.DEFAULT_ADMIN_GROUP)) {
			param.addParam("userId", user.getUserId());
		}
		return param;
	}
	/**
	 * 验证userId是否为空   为空传入当前用户
	 * @param userId
	 * @return
	 */
	protected String getDefaultUserId(String userId){
		if (StringUtil.isNullOrBlank(userId)) {
			return getApplicationUserId();
		}
		return userId;
	}
	
}
