package com.person.framework.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.person.framework.constant.RedisConstant;
import com.person.framework.constant.SessionConstant;
import com.person.framework.redis.RedisServiceUtil;
import com.person.web.admin.model.SysUser;

/**
 * Servlet Filter implementation class AdminUserFilter
 */
public class AdminUserFilter implements Filter {

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest httpRequest = (HttpServletRequest) request;
//		HttpServletResponse httpResponse = (HttpServletResponse) model;

		Cookie[] cookies = httpRequest.getCookies();

		String path = httpRequest.getRequestURI();
//		System.out.println("path: ---> " + path);

		if(path.indexOf("/vailYzm") > -1 ||path.indexOf("/code.do") > -1 ||path.indexOf("/login.html") > -1 ||path.indexOf("/login.jsp") > -1 || path.indexOf("/logout") > -1 || path.indexOf("/login") > -1 || path.indexOf("/static") > -1){
			chain.doFilter(request, response);
			return ;
		}
		
		// 菜单信息
		ApplicationResourceInfo ari = (ApplicationResourceInfo) httpRequest
				.getSession(true).getAttribute(SessionConstant.USER_RESOURCE);
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				if (RedisConstant.COOKIE_NAME.equals(cookie.getName())) {
					String cookieVal = cookie.getValue();
					// System.out.println(RedisUtil.getStr(cookieVal));
					request.setAttribute("key_id_name", RedisServiceUtil
							.getJedis(RedisServiceUtil.SERVICE_REDIS_COMMON,
									cookieVal));
					// 判断是否是否用
					String userId = cookieVal.split("_")[0];
					SysUser user = UserResource.getUserById(userId);
					// 判断菜单是否失效
					if (ari == null) {
						// 根据redis从数据库取用户信息

						ApplicationResourceInfo resourceInfo = UserResource
								.getUserResource(user, path);

						httpRequest.getSession(true).setAttribute(
								SessionConstant.USER_RESOURCE, resourceInfo);
						storeUserIntoSession(httpRequest, user);

					} else {
						AppAdminUserInfo appUserInfo = (AppAdminUserInfo) httpRequest
								.getSession(true).getAttribute(
										SessionConstant.USER_INFO);
						// 清除菜单
						httpRequest.getSession(true).removeAttribute(
								SessionConstant.USER_RESOURCE);
						
						// 如果cookie名与session登录名不一致则重新设置session,一致则重新设置breadcrumb
						if (!appUserInfo.getUserId().equals(userId)) {
							// 重新设置菜单树
							ApplicationResourceInfo resourceInfo = UserResource
									.getUserResource(user, path);	
							httpRequest.getSession()
									.setAttribute(SessionConstant.USER_RESOURCE,resourceInfo);
						}else {
							ari.setBreadcrumb(UserResource.getBreadcrumb(ari.getSourceTree(), path));
							httpRequest.getSession().setAttribute(SessionConstant.USER_RESOURCE,ari);
						}
						storeUserIntoSession(httpRequest, user);
					}
					chain.doFilter(request, response);
					return;
				}
			}
		}

		RequestDispatcher rd=request.getRequestDispatcher("/admin/login.html");
        rd.forward(request, response);
		// pass the request along the filter chain
//		chain.doFilter(request, model);
	}
	
	
	public void storeUserIntoSession(HttpServletRequest request, SysUser userinfo) {
		 if(userinfo == null)
			 return;
		 
		 String ip  =  request.getHeader( " x-forwarded-for " );  
	       if (ip  ==   null   ||  ip.length()  ==   0   ||   " unknown " .equalsIgnoreCase(ip))  {  
	          ip  =  request.getHeader( " Proxy-Client-IP " );  
	      }   
	       if (ip  ==   null   ||  ip.length()  ==   0   ||   " unknown " .equalsIgnoreCase(ip))  {  
	          ip  =  request.getHeader( " WL-Proxy-Client-IP " );  
	      }   
	       if (ip  ==   null   ||  ip.length()  ==   0   ||   " unknown " .equalsIgnoreCase(ip))  {  
	         ip  =  request.getRemoteAddr();  
	     }   
	    //测试shiro暂时注释
	    AppAdminUserInfo appUserInfo = new AppAdminUserInfo(userinfo.getUserId(), userinfo.getUserName(),userinfo.getGroupName(), ip);
		request.getSession(true).setAttribute(SessionConstant.USER_INFO, appUserInfo);
		AppAdminContext.setUserInfo(appUserInfo);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
