package com.pre.roc.grow.util;

/**
 *
 * @file StringUtils.java
 * @dateTime 2017年8月1日 下午3:42:04
 */
public class StringUtils {
	/**
	 * @param cs
	 * @return
	 */
	public static boolean isEmpty(final CharSequence cs) {
		return cs == null || cs.length() == 0;
	}

	/**
	 * @param cs
	 * @return
	 */
	public static boolean isNotEmpty(final CharSequence cs) {
		return !isEmpty(cs);
	}
}
