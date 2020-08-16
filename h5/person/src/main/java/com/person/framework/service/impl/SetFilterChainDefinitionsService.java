package com.person.framework.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.person.framework.mybatis.Criteria;
import com.person.framework.service.IFilterChainDefinitionsService;
import com.person.framework.utils.StringUtil;
import com.person.web.admin.model.SysGroupResource;
import com.person.web.admin.model.SysResource;
import com.person.web.admin.service.ISysGroupResourceService;
import com.person.web.admin.service.ISysResourceService;

/**
 * shiro filterchaindefinitions 动态设置
 * 
 * @author wangy
 *
 */
public class SetFilterChainDefinitionsService implements IFilterChainDefinitionsService {

	/**
	 * 初始化注解
	 */
    @PostConstruct  
    public void intiPermission() {  
        shiroFilterFactoryBean.setFilterChainDefinitionMap(obtainPermission());  
        log.debug("initialize shiro permission success...");  
    } 
	
    public void updatePermission() {  
    	  
        synchronized (shiroFilterFactoryBean) {  
  
            AbstractShiroFilter shiroFilter = null;  
  
            try {  
                shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();  
            } catch (Exception e) {  
                log.error(e.getMessage());  
            }  
  
            // 获取过滤管理器  
            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter  
                    .getFilterChainResolver();  
            DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();  
  
            // 清空初始权限配置  
            manager.getFilterChains().clear();  
            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();  
  
            // 重新构建生成  
            shiroFilterFactoryBean.setFilterChainDefinitions(definitions);  
            Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();  
  
            for (Map.Entry<String, String> entry : chains.entrySet()) {  
                String url = entry.getKey();  
                String chainDefinition = entry.getValue().trim().replace(" ", "");  
                manager.createChain(url, chainDefinition);  
            }  
  
            Map<String, String> permissionMap = initOtherPermission();  
            if (permissionMap != null && !permissionMap.isEmpty()) {  
            	for (Map.Entry<String, String> entry : permissionMap.entrySet()) {  
                    String url = entry.getKey();  
                    String chainDefinition = entry.getValue().trim().replace(" ", "");  
                    manager.createChain(url, chainDefinition);  
                } 
            }  
            
            log.debug("update shiro permission success...");  
        }  
    }  
	
	/** 读取配置资源 */  
    private Section obtainPermission() {  
        Ini ini = new Ini();  
        ini.load(definitions); // 加载资源文件节点串  
        Section section = ini.getSection("urls"); // 使用默认节点  
        if (CollectionUtils.isEmpty(section)) {  
            section = ini.getSection(Ini.DEFAULT_SECTION_NAME); // 如不存在默认节点切割,则使用空字符转换  
        }  
        Map<String, String> permissionMap = initOtherPermission();  
        if (permissionMap != null && !permissionMap.isEmpty()) {  
            section.putAll(permissionMap);  
        }  
        return section;  
    }  
  
    // 获取相应菜单权限
    public Map<String, String> initOtherPermission() {
    	List<SysResource> resourceList = sysResourceService.queryAllPage(null);
    	if (resourceList == null || resourceList.size() == 0) {
			return null;
		}
    	
    	Map<String, String> map = new HashMap<String, String>();
    	List<SysGroupResource> list;
    	Criteria<SysGroupResource> param = new Criteria<SysGroupResource>();
    	for (SysResource sysResource : resourceList) {
    		// url 使用roles 限制权限
    		// 当前系统设计的基础上 shiro无法控制到按钮权限  需另使用其他aop控制
    		if (!StringUtil.isNullOrBlank(sysResource.getUrl())) {
    			param.clearParams().addParam("resourceId", sysResource.getResourceId());
        		list = groupResourceService.queryPage(param);
        		if (list != null && !list.isEmpty()) {
        			String roles = "";
        			for (SysGroupResource sysGroupResource : list) {
        				roles += sysGroupResource.getGroupId()+",";
    				}
        			roles = roles.substring(0,roles.length() - 1);
        			map.put(sysResource.getUrl().substring(sysResource.getUrl().indexOf("/admin")), "authc,"+String.format(ROLE_STRING, roles));
    			}
			}
		}
    	return map;
	}
  
    public String getDefinitions() {  
        return definitions;  
    }  
  
    public void setDefinitions(String definitions) {  
        this.definitions = definitions;  
    }

    /** log instance. */
	protected final Logger log = LoggerFactory.getLogger(getClass());

	private String definitions = "";

	@Autowired  
    private ShiroFilterFactoryBean shiroFilterFactoryBean;  
	@Autowired
	private ISysResourceService sysResourceService;
    @Autowired
	private ISysGroupResourceService groupResourceService;
}
