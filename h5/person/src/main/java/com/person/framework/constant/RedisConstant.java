package com.person.framework.constant;

public class RedisConstant {

	/**
	 * admin ∨
	 */
	/**
	 * redis adminUserFilter
	 */
	public static final String COOKIE_NAME = "WYBEGINUSERLOGINFILTER";
	/**
	 * redis GROUP_SELECT_NAME 
	 */
	public static final String GROUP_SELECT_NAME = "GROUP_SELECT_NAME";
	
	
	
	/**
	 * blog   ∨
	 */
	
	/**
	 * redis 用户标示
	 */
	public static final String redis_blog_user_sign = "blog_sign:[%s]";
	
	/**
	 * redis 用户碎言碎语
	 */
	public static final String redis_blog_shuo = "blog_shuo:[%s]";
	/**
	 * redis 用户关于
	 */
	public static final String redis_blog_about = "blog_about:[%s]";
	/**
	 * redis 用户日记
	 */
	public static final String redis_blog_riji = "blog_riji:[%s]";
	/**
	 * redis 用户相册
	 */
	public static final String redis_blog_xc = "blog_xc:[%s]";
	/**
	 * redis 用户文章
	 */
	public static final String redis_blog_learn = "blog_learn:[%s]";
	
	/**
	 * redis 用户随机文章
	 */
	public static final String redis_blog_learn_ran = "blog_learn_ran:[%s]";
	/**
	 * redis 用户推荐文章
	 */
	public static final String redis_blog_learn_romm = "blog_learn_romm:[%s]";
	/**
	 * redis 用户最新文章
	 */
	public static final String redis_blog_learn_new = "blog_learn_new:[%s]";
}
