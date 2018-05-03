package com.pre.roc.grow.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @file ConfigUtil.java
 * @dateTime 2017年7月31日 上午10:15:33
 */
public class PropertiesUtil {

	private static Map<String, String> propertiesMap = new HashMap<String, String>();

	private static class PropertiesInstance {

		private static final PropertiesUtil props = new PropertiesUtil();
	}

	/**
	 * @return
	 */
	public static PropertiesUtil getInstance() {
		return PropertiesInstance.props;
	}

	/**
	 * @param p
	 */
	public void addProperties(Properties p) {

		if (null == p)
		{
			return;
		}
		for (Enumeration<?> e = p.propertyNames(); e.hasMoreElements();)
		{
			String key = (String) e.nextElement();
			propertiesMap.put(key, p.getProperty(key));
		}
	}

	/**
	 * @param key
	 */
	public void getElement(String key) {

		propertiesMap.get(key);
	}
}
