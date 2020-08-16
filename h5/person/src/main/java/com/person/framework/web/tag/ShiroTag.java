package com.person.framework.web.tag;

import java.util.List;
import java.util.Map;

import org.apache.shiro.web.tags.RoleTag;
import org.springframework.jdbc.core.JdbcTemplate;

import com.person.framework.utils.SpringContextUtil;
import com.person.framework.web.AppAdminContext;
import com.person.framework.web.AppAdminUserInfo;

/**
 * 验证是否有某个权限
 * @author wangy
 *
 */
public class ShiroTag extends RoleTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * jdbc
	 */
	JdbcTemplate datasource;

	@Override
	protected boolean showTagBody(String resourceId) {
		return checkAuth(resourceId);
	}
	/**
	 * 验证权限
	 * @param resourceId
	 * @return
	 */
	public boolean checkAuth(String resourceId){
		datasource = SpringContextUtil.getBean(JdbcTemplate.class);
		AppAdminUserInfo user = AppAdminContext.getUserInfo();
		List<Map<String, Object>> list = datasource
				.queryForList("select * from t_sys_group_resource where resource_id='"
						+ resourceId
						+ "' and group_id in (select group_id from t_sys_group where group_name ='"
						+ user.getGroupName() + "' )");
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	};
}
