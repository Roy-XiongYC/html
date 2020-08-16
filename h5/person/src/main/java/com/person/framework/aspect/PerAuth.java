package com.person.framework.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限注解类日志
 * @author 
 *
 */
@Target({ElementType.TYPE,ElementType. METHOD})
@Retention(RetentionPolicy.RUNTIME )
public @interface PerAuth {
	
	public abstract String resourceId();

}
