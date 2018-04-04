package com.pre.roc.grow.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	private static final String LAST_TIME_DAY = " 23:59:59";

	/**
	 * yyyyMMdd
	 * 
	 * @param dateStr
	 * @return
	 * @throws ConsumerLogException
	 */
	public static Date parseDate(String dateStr) throws Exception {

		return new java.text.SimpleDateFormat("yyyyMMdd").parse(dateStr);
	}

	/**
	 * yyyyMMdd
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static String valueDate(Date date) throws Exception {

		return new java.text.SimpleDateFormat("yyyyMMdd").format(date);
	}

	/**
	 * yyyyMMddHHmmss
	 * 
	 * @param dateStr
	 * @return
	 * @throws Exception
	 */
	public static String currentDateTime() throws Exception {

		return new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}

	/**
	 * yyyyMMddHHmmss
	 * 
	 * @param dateStr
	 * @return
	 * @throws Exception
	 */
	public static Date parseDateTime(String dateStr) throws Exception {

		return new java.text.SimpleDateFormat("yyyyMMddHHmmss").parse(dateStr);
	}

	/**
	 * yyyyMMddHHmmss
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static String valueDateTime(Date date) throws Exception {

		return new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(date);
	}

	/**
	 * yyyy-MM-dd
	 * 
	 * @param dateStr
	 * @return
	 * @throws ConsumerLogException
	 */
	public static Date parseBarDate(String dateStr) throws Exception {

		return new java.text.SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
	}

	/**
	 * yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static String valueBarDate(Date date) throws Exception {

		return new java.text.SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	/**
	 * yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateStr
	 * @return
	 * @throws ConsumerLogException
	 */
	public static Date parseBarDateTime(String dateStr) throws Exception {

		return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
	}

	/**
	 * yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static String valueBarDateTime(Date date) throws Exception {

		return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	/**
	 * 获取当月最后一天的时间
	 * 
	 * @param dateTime
	 * @return
	 * @throws ConsumerLogException
	 */
	public static Date getBarLastDayTime(Date dateTime) throws Exception {

		return parseBarDateTime(new SimpleDateFormat("yyyy-MM-dd").format(getLastDayOfMonth(dateTime)).concat(LAST_TIME_DAY));
	}

	/**
	 * 获取当月最后一天
	 * 
	 * @param dateTime
	 * @return
	 */
	public static Date getLastDayOfMonth(Date dateTime) {
		Calendar g = Calendar.getInstance();
		g.setTime(dateTime);
		g.set(Calendar.DAY_OF_MONTH, g.getActualMaximum(Calendar.DAY_OF_MONTH));
		return g.getTime();
	}

	/**
	 * 获取增加月的日期
	 * 
	 * @param date
	 * @param number
	 * @return
	 */
	public static Date addMonthDate(Date dateTime, Integer number) {
		Calendar g = Calendar.getInstance();
		g.setTime(dateTime);
		g.add(Calendar.MONTH, number);
		return g.getTime();
	}
}
