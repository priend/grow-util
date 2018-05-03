package com.pre.roc.grow.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

/**
 * LOG4J2
 *
 * @file Log4j2Util.java
 * @dateTime 2017年7月25日 下午4:15:37
 */
public class Log4j2Util {

	private static final String DEFAULT_LOG4J_2_PAHT = "log4j2.xml";

	/**
	 * 
	 */
	public static void loadLogConfigurator() {
		try
		{
			Configurator.initialize(null, new ConfigurationSource(new FileInputStream(new File(DEFAULT_LOG4J_2_PAHT))));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @param url
	 */
	public static void loadLogConfigurator(URL url) {

		try
		{
			Configurator.initialize(null, new ConfigurationSource(new FileInputStream(new File(url.getPath())), url));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @param path
	 */
	public static void loadLogConfigurator(String path) {

		try
		{
			File file = new File(path);
			Configurator.initialize(null, new ConfigurationSource(new FileInputStream(file), file));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

}
