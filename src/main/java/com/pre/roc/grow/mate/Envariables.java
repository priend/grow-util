package com.pre.roc.grow.mate;

import java.util.HashMap;
import java.util.Map;

import com.pre.roc.grow.util.StringUtils;

/**
 *
 * @file Envariables.java
 * @author CMCC.HPE.Pactera.Zh
 * @dateTime 2018年4月23日 下午4:52:05
 */
public class Envariables {

	private static Map<String, String> ENVAS_MAP = new HashMap<String, String>();

	private static class EnvariablesInstance {

		private static final Envariables ENVARIABLES = new Envariables();
	}

	/**
	 * @return
	 */
	public static Envariables getInstance() {

		return EnvariablesInstance.ENVARIABLES;
	}

	/**
	 * @param key
	 * @param value
	 */
	public void setSystemProperty(String key, String value) {

		if (StringUtils.isEmpty(ENVAS_MAP.get(key)))
		{
			ENVAS_MAP.put(key, value);
		}
		{
			System.setProperty(key, ENVAS_MAP.get(key));
		}
	}

	/**
	 * @param key
	 * @return
	 */
	public String getUserSystemProperty(String key) {

		if (StringUtils.isEmpty(ENVAS_MAP.get(key)))
		{
			return null;
		}
		else
		{
			return ENVAS_MAP.get(key);
		}
	}
}
