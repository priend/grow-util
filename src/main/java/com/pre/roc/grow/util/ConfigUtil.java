package com.pre.roc.grow.util;

import java.util.Properties;

import com.pre.roc.grow.voice.Platforms;
import com.pre.roc.grow.voice.base.PropertiesLoader;

/**
 * @file ConfigUtil.java
 * @dateTime 2017年7月31日 上午10:15:33
 */
public class ConfigUtil extends Platforms {

	private static final String CONFIG_PROPERITES = "redo.properties";
	private static final String CONFIG = "config";

	public static final String USER_DIR = System.getProperty("user.dir");
	public static final String FILE_SEPARATOR = System.getProperty("file.separator");

	public static String REDO_FILE_PATH;
	public static String REDO_ERROR_CODE;

	private Properties properties;

	/**
	 * 
	 */
	public ConfigUtil() {
		properties = PropertiesLoader.fromFile(ConfigUtil.currentWorkPath().concat(CONFIG_PROPERITES));
	}

	/**
	 * 
	 */
	public void init() {
		REDO_FILE_PATH = properties.getProperty("redo.file.path");
		REDO_ERROR_CODE = properties.getProperty("redo.error.code");
	}

	/**
	 * @return
	 */
	public static String currentWorkPath() {

		// return
		// getParentClassPath(Platforms.getClassPath()).concat(CONFIG).concat(FILE_SEPARATOR);
		return getParentClassPath(USER_DIR).concat(CONFIG).concat(FILE_SEPARATOR);
	}

	/**
	 * @return
	 */
	public static String getRedoPath() {

		return getParentClassPath(currentWorkPath()).concat(REDO_FILE_PATH).concat(FILE_SEPARATOR);
	}

}
