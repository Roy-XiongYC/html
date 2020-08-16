package com.person.framework.web.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.person.framework.utils.SpringContextUtil;
import com.person.framework.utils.StringUtil;


public class SelectTag extends SimpleTagSupport{
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	public void doTag() throws JspException, IOException {
		try {
			
			datasource = SpringContextUtil.getBean(JdbcTemplate.class);
			String html = null;
			
			html = doSelectTag(getResult());
			
			JspWriter out = this.getJspContext().getOut();

			out.write(html);

		} catch (Exception e) {
			log.error(" Translate tag falut.", e);
		}
		super.doTag();
	}
	
	/**
	 * 组装sql
	 * @return
	 */
	private String generateSql() {
		params = new ArrayList<String>();

		String sql = " select " + this.getDescCol() + " as DESC_COL " //
				+ ", " + this.getValueCol() + " as VALUE_COL " //
				+ " from " + this.getTableName()//
				+ " where 1=1";
		if (!StringUtil.isNullOrBlank(this.getSelectedKey()) && !StringUtil.isNullOrBlank(this.getSelectedValue())) {
			sql += " and " + this.getSelectedKey() + " = ? ";
			params.add(this.getSelectedValue());
		}

		if (!StringUtil.isNullOrBlank(this.getDisplayValue())) {

			String[] displayValues = this.getDisplayValue().split(",");
			sql += " and " + this.getValueCol() + " not in ( ";
			for (int i = 0; i < displayValues.length; i++) {
				sql += "?,";
				params.add(displayValues[i]);
			}
			sql = sql.substring(0, sql.length() - 1);

			sql += " ) ";

		}
		
		if (!StringUtil.isNullOrBlank(this.getGroupBy())) {
			sql += " group by " + this.getGroupBy();
		}
		if (!StringUtil.isNullOrBlank(this.getOrderBy())) {
			sql += " order by " + this.getOrderBy();
		}
		
		return sql;
	}
	
	/**
	 * List<String>转换成List<Map<String, Object>>
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unused")
	private List<Map<String, Object>> StringToMap(List<String> list) {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (String str : list) {
			String[] s = str.split("\\|");
			map = new HashMap<String, Object>();
			map.put("DESC_COL", s[0]);
			map.put("VALUE_COL", s[1]);
			mapList.add(map);
		}
		return mapList;
	}
	
	public List<Map<String, Object>> getResult() {
		this.sql = generateSql();
		
//		List<String> listString = RedisServiceUtil.getJedisList(
//				RedisServiceUtil.SERVICE_REDIS_COMMON, RedisConstant.GROUP_SELECT_NAME);
//		if (listString != null && listString.size() > 0) {
//			return StringToMap(listString);
//		}

		List<Map<String, Object>> result = datasource.queryForList(sql,
				this.params.toArray());
		List<String> list = new ArrayList<String>();
		for (Map<String, Object> map : result) {
			String s = map.get("DESC_COL") + "|" + map.get("VALUE_COL");
			list.add(s);
		}

//		RedisServiceUtil.setJedisList(RedisServiceUtil.SERVICE_REDIS_COMMON,
//				RedisConstant.GROUP_SELECT_NAME, list);

		return result;

	}
	/**
	 * 组装返回select
	 * @param result
	 * @return
	 */
	public String doSelectTag(List<Map<String, Object>> result) {

		String htmlAttirbute = this.htmlAttribute != null ? this.htmlAttribute
				: "";
		if (result == null || result.isEmpty()) {
			return String.format(HTML_TEMPLATE__SELECT, htmlAttirbute, "");
		}

		String options = "";
		
		if(this.pleaseSelect!=null){
			options+=String.format(HTML_TEMPLATE__OPTION,"", "",this.getPleaseSelect());
		}

		for (Map<String, Object> row : result) {
			String desc = (String) row.get("DESC_COL");
			String value = (String) row.get("VALUE_COL");
			
			String selected = "";

			if (!StringUtil.isNullOrBlank(this.getDefaultSelect())
					&& this.getDefaultSelect().equals(value)) {
				selected = "selected='selected'";
			}

			options += String.format(HTML_TEMPLATE__OPTION, value, selected,
					desc);
		}

		return String.format(HTML_TEMPLATE__SELECT, htmlAttirbute, options);
	}
	
	/**
	 * 查询参数
	 */
	private List<String> params;
	/**
	 * sql
	 */
	private String sql;
	/**
	 * jdbc
	 */
	JdbcTemplate datasource;
	/**
	 * 表名
	 */
	private String tableName;
	/**
	 * 排序
	 */
	private String orderBy;
	/**
	 * 分组
	 */
	private String groupBy;
	/**
	 * 隐藏值  多个,分隔
	 */
	private String displayValue;
	/**
	 * html 标签代码
	 */
	private String htmlAttribute;
	/**
	 * 默认选中值
	 */
	private String defaultSelect;
	/**
	 * 是否需要请选择
	 */
	private String pleaseSelect;
	/**
	 * 显示value 值
	 */
	private String valueCol;
	/**
	 * 显示text 值
	 */
	private String descCol;
	/**
	 * 查询参数
	 */
	private String selectedKey;
	/**
	 * 查询参数值
	 */
	private String selectedValue;
	
	public String getPleaseSelect() {
		return pleaseSelect;
	}

	public void setPleaseSelect(String pleaseSelect) {
		this.pleaseSelect = pleaseSelect;
	}

	public List<String> getParams() {
		return params;
	}


	public void setParams(List<String> params) {
		this.params = params;
	}


	public String getSelectedKey() {
		return selectedKey;
	}


	public void setSelectedKey(String selectedKey) {
		this.selectedKey = selectedKey;
	}


	public String getSelectedValue() {
		return selectedValue;
	}


	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}


	public String getValueCol() {
		return valueCol;
	}

	public void setValueCol(String valueCol) {
		this.valueCol = valueCol;
	}

	public String getDescCol() {
		return descCol;
	}

	public void setDescCol(String descCol) {
		this.descCol = descCol;
	}

	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getDisplayValue() {
		return displayValue;
	}
	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}
	public String getHtmlAttribute() {
		return htmlAttribute;
	}
	public void setHtmlAttribute(String htmlAttribute) {
		this.htmlAttribute = htmlAttribute;
	}
	public String getDefaultSelect() {
		return defaultSelect;
	}
	public void setDefaultSelect(String defaultSelect) {
		this.defaultSelect = defaultSelect;
	}
	public String getGroupBy() {
		return groupBy;
	}
	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	private static final String HTML_TEMPLATE__SELECT = "<span class=\"select-box inline\"><select class=\"select\" size=\"1\"  %s> %s</select></span>";
	
	private static final String HTML_TEMPLATE__OPTION = "<option value='%s' %s> %s</option>";

//	private static final String CACHED_KEY_TEMPLATE = "desc_col[%s]val_col[%s]table_name[%s]where[%s]order_by[%s]";
}
