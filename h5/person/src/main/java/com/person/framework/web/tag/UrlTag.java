package com.person.framework.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.person.framework.constant.UrlConstant;

/**
 * 自定义标签获取url路径:包括css、js、images、自定义等
 * 
 * @author 
 *
 */
public class UrlTag extends SimpleTagSupport {

	@Override
	public void doTag() throws JspException, IOException {

		try {

			String path = "";
			String rootPath = "";

			if ("js".equals(type)) { // js地址
				rootPath = UrlConstant.STATIC_RESOURCE_URL;
			} else if ("images".equals(type)) { // image地址
				rootPath = UrlConstant.STATIC_RESOURCE_URL;
			} else if ("css".equals(type)) { // css地址
				rootPath = UrlConstant.STATIC_RESOURCE_URL;
			} else { // 默认地址,定义上传目录
				rootPath = UrlConstant.RESOURCE_URL;
			}

			path += rootPath;

			if (StringUtils.isNotBlank(directory)) {
				path += directory;
			}

			path += name;

			JspWriter out = this.getJspContext().getOut();

			out.write(path);

		} catch (Exception e) {
			log.error(" css and js tag falut.", e);
		}

		super.doTag();
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	// 文件名称
	private String name;
	// 目录(如有则追加)
	private String directory;
	// 类型
	private String type;

	protected final Logger log = LoggerFactory.getLogger(getClass());
}
