package com.person.framework.service.impl;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.person.framework.service.IMessageService;
import com.person.framework.utils.PropertiesUtil;

public class LoaclCachedMessageService implements IMessageService {

	public void init() {

		cachedMessage = new HashMap<String, String>();

		if (propFiles == null || propFiles.isEmpty()) {
			return;
		}

		loadAll();

	}

	public void loadAll() {
		if (propFiles == null || propFiles.isEmpty()) {
			return;
		}

		for (String propFileName : propFiles) {
			loadProperties(propFileName);
		}
	}

	public void loadProperties(String fileName) {
		Map<String, String> messages = PropertiesUtil
				.loadFromClassPath(fileName);

		if (messages != null && !messages.isEmpty()) {
			cachedMessage.putAll(messages);
		}
	}

	public String message(String key, Object... params) {
		if (!cachedMessage.containsKey(key)) {
			return key;
		}

		String pattern = cachedMessage.get(key);

		return MessageFormat.format(pattern, params);
	}

	public void setPropFiles(List<String> propFiles) {
		this.propFiles = propFiles;
	}

	private List<String> propFiles;

	private Map<String, String> cachedMessage;

}
