package com.person.framework.web;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class JsonResult implements Serializable {

	public static JsonResult newInstance() {
		return new JsonResult();
	}

	public JsonResult declareFailure(String message) {
		this.success=false;
		this.message = message;

		return this;
	}
	
	public JsonResult success(String message) {
		this.message = message;

		return this;
	}

	public JsonResult addContent(String key, Object val) {
		this.content.put(key, val);
		return this;
	}

	public JsonResult addAll(Map<String, Object> content) {

		if (content == null || content.isEmpty()) {
			return this;
		}

		this.content.putAll(content);

		return this;
	}

	public JsonResult declareSuccess() {
		this.success = true;

		return this;
	}

	public Map<String, Object> getContent() {
		return content;
	}

	public JsonResult setContent(Map<String, Object> content) {
		this.content = content;

		return this;
	}

	public String getMessage() {
		return message;
	}

	public boolean getSuccess() {
		return success;
	}

	private static final long serialVersionUID = 8951241890462664051L;

	private String message;

	private boolean success = false;

	private Map<String, Object> content = new HashMap<String, Object>();

}
