package com.pre.roc.grow.comity.jazz.file;

import java.io.File;

/**
 *
 * @file FileTypeUtil.java
 * @author CMCC.HPE.Pactera.Zh
 * @dateTime 2018年4月12日 下午3:35:58
 */
public class FileTypeUtil {

	public static final String PATH_SEPARATOR = File.separator;

	/**
	 * @param path
	 * @return
	 */
	public static boolean isFile(String path) {

		return visibleFile(path) == null ? false : true;

	}

	/**
	 * @param path
	 * @return
	 */
	public static File visibleFile(String path) {

		File f = new File(path);

		if (f.exists())
		{
			return f;
		}
		else
		{
			return null;
		}
	}

	/**
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static String fileNamePath(String path, String fileName) {

		return path.endsWith(PATH_SEPARATOR) ? path + fileName : path + PATH_SEPARATOR + fileName;
	}
}
