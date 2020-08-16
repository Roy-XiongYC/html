package com.person.web.admin.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.person.framework.constant.DefaultConstant;
import com.person.framework.constant.MessageConstant;
import com.person.framework.constant.RedisConstant;
import com.person.framework.constant.SessionConstant;
import com.person.framework.mybatis.Criteria;
import com.person.framework.redis.RedisServiceUtil;
import com.person.framework.utils.EncryptUtil;
import com.person.framework.web.AdminAppController;
import com.person.framework.web.JsonResult;
import com.person.web.admin.model.SysUser;
import com.person.web.admin.service.ISysUserService;

@Controller
@RequestMapping("admin")
public class AdminIndexController extends AdminAppController<SysUser> {

	@RequestMapping("index.html")
	public String showPage(HttpServletRequest request,HttpServletResponse response) {
		if (!isLogined(request,response)) {
			return "redirect:/admin/login.html";
		}
		return "web/admin/index";
	}

	@RequestMapping("login.html")
	public String showLoginPage(HttpServletRequest request,
			HttpServletResponse response) {
		if(isLogined(request,response)){
			removeCookieAndRedis(getApplicationUserInfo(), response, request);
			// 清除菜单
			request.getSession(true).removeAttribute(
						SessionConstant.USER_RESOURCE);
			// shiro 登出
			Subject subject = SecurityUtils.getSubject();  
			subject.logout();  
		}
		return "web/admin/login";
	}
	
	@ResponseBody
	@RequestMapping(value = "login", method = { RequestMethod.POST })
	public JsonResult subLogin(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required = true) String username,
			@RequestParam(required = true) String password) {
		JsonResult jr = new JsonResult();
		// 编写登录
		Criteria<SysUser> param = new Criteria<SysUser>();
		param.addParam("userName", username);
		SysUser userinfo = sysUserService.loginSys(param);
		if (userinfo == null) {
			return jr.declareFailure(messageService
					.message(MessageConstant.C00020));
		}
		if (DefaultConstant.USER_ACTIVE != userinfo.getUserStatus()) {
			return jr.declareFailure(messageService
					.message(MessageConstant.C00023));
		}
		// 验证用户账号与密码是否对应
		String pwd = userinfo.getPassword();
		if (!EncryptUtil.validatePassword(password, pwd,
				userinfo.getUserId())) {
			int misTimes = 10;
			// 用户密码不匹配则修改错误密码次数
			userinfo.setPasswordRepTimes(userinfo.getPasswordRepTimes() + 1);
			int times = userinfo.getPasswordRepTimes();
			if (times >= misTimes) {
				// 如果超过尝试次数则锁定
				userinfo.setUserStatus(DefaultConstant.USER_STOP);
				times = misTimes;
			}
			sysUserService.insertOrUpdate(userinfo);
			return jr.declareFailure(messageService.message(MessageConstant.C00022,misTimes - times));
		}
		if (userinfo.getPasswordRepTimes() > 0) {
			userinfo.setPasswordRepTimes(0);
			sysUserService.insertOrUpdate(userinfo);
		}
		
		response = setCookieAndRedis(userinfo,response);
		
		
		UsernamePasswordToken token = new UsernamePasswordToken(username,
				EncryptUtil.encodePassword(password, userinfo.getUserId()));
		token.setRememberMe(true);
		System.out.println("为了验证登录用户而封装的token为"
				+ ReflectionToStringBuilder.toString(token,
						ToStringStyle.MULTI_LINE_STYLE));
		// 获取当前的Subject
		Subject currentUser = SecurityUtils.getSubject();
		try {
			// 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
			// 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
			// 所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
			System.out.println("对用户[" + username + "]进行登录验证..验证开始");
			currentUser.login(token);
			System.out.println("对用户[" + username + "]进行登录验证..验证通过");
		} catch (UnknownAccountException uae) {
			System.out.println("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
			return jr.declareFailure( "未知账户");
		} catch (IncorrectCredentialsException ice) {
			System.out.println("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
			return jr.declareFailure( "密码不正确");
		} catch (LockedAccountException lae) {
			System.out.println("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
			return jr.declareFailure( "账户已锁定");
		} catch (ExcessiveAttemptsException eae) {
			System.out.println("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
			return jr.declareFailure( "用户名或密码错误次数过多");
		} catch (AuthenticationException ae) {
			// 通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
			System.out.println("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
			ae.printStackTrace();
			return jr.declareFailure( "用户名或密码不正确");
		}
		// 验证是否登录成功
		if (currentUser.isAuthenticated()) {
			System.out.println("用户[" + username
					+ "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
		} else {
			token.clear();
		}
		
		return jr.declareSuccess();
	}

	
	private HttpServletResponse setCookieAndRedis(SysUser user,HttpServletResponse response){
		String redisKey = user.getUserId() + "_" + user.getUserName();
		//创建Cookie
		Cookie cookie = new Cookie(RedisConstant.COOKIE_NAME, redisKey);
		cookie.setPath("/");
		cookie.setMaxAge(-1); 	//	设置cook过期时间为: 关闭浏览器后过期
		response.addCookie(cookie);
		RedisServiceUtil.setJedis(RedisServiceUtil.SERVICE_REDIS_COMMON,redisKey, redisKey + "_" + user.getPassword());
		return response;
	}
	
	@Autowired
	private ISysUserService sysUserService;
}
