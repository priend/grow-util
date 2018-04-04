package com.pre.roc.grow.voice.base;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 加载Properties文件
 * 
 * @file PropertiesUtil.java
 * @dateTime 2017年7月19日 上午11:40:28
 */
public class PropertiesLoader {

	private static final Logger logger = LoggerFactory.getLogger(PropertiesLoader.class);

	/**
	 * 使用java.util.Properties类的load(InputStream in)方法加载
	 * 
	 * @param namePath
	 * @return
	 */
	public static Properties fromFile(String basePath) {

		Properties properties = new Properties();
		try {
			BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(basePath));
			properties.load(bufferedInputStream);
			bufferedInputStream.close();
		} catch (IOException ignored) {
			logger.error("Load property from " + basePath + " fail ", ignored);
		}
		return properties;
	}

	/**
	 * 使用java.util.ResourceBundle类的getBundle()方法 <br/>
	 * 例如com/util
	 * 
	 * @param baseName
	 * @return
	 */
	public static ResourceBundle fromResourceBundle(String baseName) {

		return ResourceBundle.getBundle(baseName);
	}

	/**
	 * 使用java.util.PropertyResourceBundle类的构造函数
	 * 
	 * @param basePath
	 * @return
	 */
	public static ResourceBundle fromResourceBundlePath(String basePath) {

		ResourceBundle resourceBundle = null;
		try {
			InputStream inputStream = new BufferedInputStream(new FileInputStream(basePath));
			resourceBundle = new PropertyResourceBundle(inputStream);
			inputStream.close();
		} catch (IOException ignored) {
			logger.error("Load property from " + basePath + " fail ", ignored);
		}
		return resourceBundle;
	}

	/**
	 * 使用java.lang.ClassLoader类的getSystemResourceAsStream()静态方法 <br/>
	 * 例如com/util
	 * 
	 * @param baseName
	 * @return
	 */
	public static Properties fromSystemResourceAsStream(String baseName) {

		Properties properties = new Properties();
		try {
			InputStream inputStream = ClassLoader.getSystemResourceAsStream(baseName);
			properties.load(inputStream);
			inputStream.close();
		} catch (IOException ignored) {
			logger.error("Load property from " + baseName + " fail ", ignored);
		}
		return properties;
	}

	/**
	 * 从字符串内容加载Properties
	 * 
	 * @param content
	 * @return
	 */
	public static Properties fromString(String content) {

		Properties properties = new Properties();
		try {
			Reader reader = new StringReader(content);
			properties.load(reader);
			reader.close();
		} catch (IOException ignored) {
			logger.error("Load property from " + content + " fail ", ignored);
		}
		return properties;
	}

}
