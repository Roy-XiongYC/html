package com.person.framework.utils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

public class PropertiesUtil {

	public static Map<String, String> loadFromClassPath(String filename) {

		InputStream is = null;
		Properties props = new Properties();
		try {
			ClassPathResource resource = new ClassPathResource(filename);
			is = resource.getInputStream();
			props.load(is);

			if (props == null || props.isEmpty()) {
				return null;
			}

			Map<String, String> ret = new HashMap<String, String>();

			for (Entry<Object, Object> row : props.entrySet()) {
				ret.put((String) row.getKey(), (String) row.getValue());
			}

			return ret;

		} catch (Exception e) {
			log.error("load resource file fault.[" + filename + "]", e);
		}
		return null;
	}

	protected static final Logger log = LoggerFactory
			.getLogger(PropertiesUtil.class);
}
