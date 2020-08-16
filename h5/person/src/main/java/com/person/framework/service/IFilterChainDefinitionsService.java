package com.person.framework.service;

import java.util.Map;
/**
 * shiro 自定义   过滤规则
 * @author wangy
 *
 */
public interface IFilterChainDefinitionsService {

	public static final String PREMISSION_STRING = "perms[%s]"; // 资源结构格式  
    public static final String ROLE_STRING = "roles[%s]"; // 角色结构格式  
  
    /** 初始化框架权限资源配置 */  
    public abstract void intiPermission();  
  
    /** 重新加载框架权限资源配置 (强制线程同步) */  
    public abstract void updatePermission();  
  
    /** 初始化第三方权限资源配置 */  
    public abstract Map<String, String> initOtherPermission();  
	
}
