package com.pre.roc.grow.util;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @file Log4jUtil.java
 * @dateTime 2017年7月25日 下午3:33:20
 */
public class Log4jUtil {

	private static final String LOG4J_PROPERITES = "log4j.properties";

	/**
	 * 
	 */
	public static void loadLogConfigurator() {

		PropertyConfigurator.configure(ConfigUtil.currentWorkPath().concat(LOG4J_PROPERITES));
	}

	/**
	 * @param debug
	 */
	public static void debug(Object debug) {

		StackTraceElement stack[] = (new Throwable()).getStackTrace();
		Logger logger = Logger.getLogger(stack[1].getClassName());
		logger.log(Log4jUtil.class.getName(), Level.DEBUG, debug, null);
	}

	/**
	 * @param info
	 */
	public static void info(Object info) {

		StackTraceElement stack[] = (new Throwable()).getStackTrace();
		Logger logger = Logger.getLogger(stack[1].getClassName());
		logger.log(Log4jUtil.class.getName(), Level.INFO, info, null);
	}

	/**
	 * @param error
	 */
	public static void error(Object error) {

		StackTraceElement stack[] = (new Throwable()).getStackTrace();
		Logger logger = Logger.getLogger(stack[1].getClassName());
		logger.log(Log4jUtil.class.getName(), Level.ERROR, error, null);
	}

	/**
	 * @param debug
	 * @param throwable
	 */
	public static void debug(Object debug, Throwable throwable) {

		StackTraceElement stack[] = (new Throwable()).getStackTrace();
		Logger logger = Logger.getLogger(stack[1].getClassName());
		logger.log(Log4jUtil.class.getName(), Level.DEBUG, debug, throwable);
	}

	/**
	 * @param info
	 * @param throwable
	 */
	public static void info(Object info, Throwable throwable) {

		StackTraceElement stack[] = (new Throwable()).getStackTrace();
		Logger logger = Logger.getLogger(stack[1].getClassName());
		logger.log(Log4jUtil.class.getName(), Level.INFO, info, throwable);
	}

	/**
	 * @param error
	 * @param throwable
	 */
	public static void error(Object error, Throwable throwable) {

		StackTraceElement stack[] = (new Throwable()).getStackTrace();
		Logger logger = Logger.getLogger(stack[1].getClassName());
		logger.log(Log4jUtil.class.getName(), Level.ERROR, error, throwable);
	}

}
