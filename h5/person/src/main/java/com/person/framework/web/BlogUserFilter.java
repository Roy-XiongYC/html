package com.person.framework.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.person.framework.constant.DefaultConstant;
import com.person.framework.constant.SessionConstant;
import com.person.framework.exception.BizException;
import com.person.framework.utils.SpringContextUtil;
import com.person.web.blog.service.IBlogUserService;

/**
 * blog 用户信息filter
 * Servlet Filter implementation class BlogUserFilter
 */
public class BlogUserFilter implements Filter {
	private static Logger log = LoggerFactory.getLogger(BlogUserFilter.class);

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		log.debug(" BlogUserFilter destroy");
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		AppBlogUserInfo user = (AppBlogUserInfo) httpRequest.getSession(true).getAttribute(
				SessionConstant.BLOG_USER);
		String url = httpRequest.getServerName().indexOf(".") > 0 
				? httpRequest.getServerName().substring(0,httpRequest.getServerName().indexOf("."))
				: DefaultConstant.DEFAULT_BLOG_USER;
		
		if(user!=null && !user.getBlogUser().getUserSign().equals(url)){
			user = null;
		}
		
		if (user == null) {
			IBlogUserService blogUserService = (IBlogUserService)SpringContextUtil.getBean("blogUserService");
			try {
				user = blogUserService.queryUserInfoBySign(url);
				if(user == null) throw new BizException();
				httpRequest.getSession(true).setAttribute(SessionConstant.BLOG_USER, user);
			} catch (Exception e) {
//				错误页面 if(url.equals(DefaultConstant.DEFAULT_BLOG_USER)) 
				log.error("error e ：" + e.getLocalizedMessage());
				httpResponse.sendRedirect(DefaultConstant.DEFAULT_BLOG_USER_URL);
				return;
			}
		}

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		log.debug(" BlogUserFilter init");
	}

}
