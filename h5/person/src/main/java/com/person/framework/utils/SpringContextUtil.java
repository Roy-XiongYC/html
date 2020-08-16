package com.person.framework.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtil implements ApplicationContextAware
{

	public  void setApplicationContext(ApplicationContext arg0) throws BeansException
	{
		applicationContext = arg0;
	}
	
	public static boolean  isApplicationContext(){
		if(applicationContext==null){
			return false;
		}else{
			return true;
		}
	}

	/**
	 * 从当前IOC获取bean
	 * 
	 * @param <T>
	 * 
	 * @param id bean的id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanId)
	{
		Object object = null;
		object = applicationContext.getBean(beanId);
		return (T) object;
	}

	/**
	 * 从当前IOC获取bean
	 * 
	 * @param <T>
	 * 
	 * @param id bean的id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz)
	{
		Object object = null;
		object = applicationContext.getBean(clazz);
		return (T) object;
	}

	private static ApplicationContext applicationContext;
}
