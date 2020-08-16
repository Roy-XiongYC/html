package com.person.framework.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * admin应用Context,包含当前用户相关的信息. 该信息是作为可完全信任的用户信息存在
 * 
 */
public final class AppAdminContext {

	/** log instance. */
	protected final static Logger log = LoggerFactory
			.getLogger(AppAdminContext.class);

	/**
	 * 当前线程中的用户信息
	 */
	private final static ThreadLocal<AppAdminUserInfo> currentUser = new ThreadLocal<AppAdminUserInfo>();

	/**
	 * 设置用户信息
	 * 
	 * @param userInfo
	 *            用户信息
	 */
	public static void setUserInfo(AppAdminUserInfo userInfo) {
		currentUser.set(userInfo);
	}

	/**
	 * 返回当前用户信息
	 * 
	 * @return
	 */
	public static AppAdminUserInfo getUserInfo() {
		if (currentUser.get() == null) {
			log.warn("No context user found, should be noticed");
			return null;
		}

		return currentUser.get();
	}

	public static void clean() {
		currentUser.remove();

	}

}
