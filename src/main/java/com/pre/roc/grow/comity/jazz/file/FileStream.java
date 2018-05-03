package com.pre.roc.grow.comity.jazz.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pre.roc.grow.mate.Platforms;

/**
 * @file FileUtil.java
 * @dateTime 2017年7月26日 下午5:24:00
 */
public class FileStream {

	private static final Logger log = LoggerFactory.getLogger(FileStream.class);

	/**
	 * 创建目录
	 * 
	 * @param dir
	 */
	public static void mkdir(String dir) {
		try
		{
			String dirTemp = dir;
			File dirPath = new File(dirTemp);
			if (!dirPath.exists())
			{
				dirPath.mkdir();
			}
		}
		catch (Exception e)
		{
			log.error("创建目录操作出错: " + e.getMessage(), e);
		}
	}

	/**
	 * 新建文件
	 * 
	 * @param filePathName
	 * @param content
	 * 
	 */
	public static void createNewFile(String filePathName, String content) {

		try
		{
			File filePath = new File(filePathName);
			if (!filePath.exists())
			{
				filePath.createNewFile();
			}
			FileWriter fileWriter = new FileWriter(filePath);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.println(content);
			printWriter.flush();
			printWriter.close();
			fileWriter.close();
		}
		catch (Exception e)
		{
			log.error("新建文件操作出错: " + e.getMessage(), e);
		}

	}

	/**
	 * 删除文件
	 * 
	 * @param filePathName
	 */
	public static void delFile(String filePathName) {

		try
		{
			new File(filePathName).delete();
		}
		catch (Exception e)
		{
			log.error("删除文件操作出错: " + e.getMessage(), e);
		}
	}

	/**
	 * 删除文件夹
	 * 
	 * @param folderPath
	 */
	public static void delFolder(String folderPath) {
		try
		{
			delAllFile(folderPath);
			new File(folderPath).delete();
		}
		catch (Exception e)
		{
			log.error("删除文件夹操作出错" + e.getMessage(), e);
		}
	}

	/**
	 * 删除文件夹里面的所有文件
	 * 
	 * @param path
	 *            文件夹路径
	 */
	public static void delAllFile(String path) {

		File file = new File(path);
		if (!file.exists())
		{
			return;
		}
		if (!file.isDirectory())
		{
			return;
		}
		String[] childFiles = file.list();
		File temp = null;

		for (int i = 0; i < childFiles.length; i++)
		{
			if (path.endsWith(File.separator))
			{
				temp = new File(path + childFiles[i]);
			}
			else
			{
				temp = new File(path + File.separator + childFiles[i]);
			}
			if (temp.isFile())
			{
				temp.delete();
			}
			if (temp.isDirectory())
			{
				delAllFile(path + File.separator + childFiles[i]);
				delFolder(path + File.separator + childFiles[i]);
			}
		}
	}

	/**
	 * 复制单个文件
	 * 
	 * @param srcFile
	 *            包含路径的源文件 如：../abc.txt
	 * @param dirDest
	 *            目标文件目录；若文件目录不存在则自动创建 如：../dest
	 * @throws IOException
	 */
	public static void copyFile(String srcFile, String dirDest) {
		try
		{
			mkdir(dirDest);

			FileInputStream in = new FileInputStream(srcFile);
			FileOutputStream out = new FileOutputStream(dirDest + File.separator + new File(srcFile).getName());

			int len;
			byte buffer[] = new byte[1024];
			while ((len = in.read(buffer)) != -1)
			{
				out.write(buffer, 0, len);
			}
			out.flush();
			out.close();
			in.close();
		}
		catch (Exception e)
		{
			log.error("复制文件操作出错:" + e.getMessage(), e);
		}
	}

	/**
	 * 复制文件夹
	 * 
	 * @param oldPath
	 *            String 源文件夹路径 如：../src
	 * @param newPath
	 *            String 目标文件夹路径 如：..p/dest
	 * @return boolean
	 */
	public static void copyFolder(String oldPath, String newPath) {
		try
		{
			mkdir(newPath);
			File file = new File(oldPath);
			String[] files = file.list();
			File temp = null;
			for (int i = 0; i < files.length; i++)
			{
				if (oldPath.endsWith(File.separator))
				{
					temp = new File(oldPath + files[i]);
				}
				else
				{
					temp = new File(oldPath + File.separator + files[i]);
				}
				if (temp.isFile())
				{
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath + "/" + (temp.getName()).toString());
					byte[] buffer = new byte[1024 * 2];
					int len;
					while ((len = input.read(buffer)) != -1)
					{
						output.write(buffer, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory())
				{
					copyFolder(oldPath + File.separator + files[i], newPath + File.separator + files[i]);
				}
			}
		}
		catch (Exception e)
		{
			log.error("复制文件夹操作出错:" + e.getMessage(), e);
		}
	}

	/**
	 * 移动文件到指定目录
	 * 
	 * @param oldPath
	 *            包含路径的文件名 如：../ljq.txt
	 * @param newPath
	 *            目标文件目录 如：../dest
	 */
	public static void moveFile(String oldPath, String newPath) {
		copyFile(oldPath, newPath);
		delFile(oldPath);
	}

	/**
	 * 移动文件到指定目录，不会删除文件夹
	 * 
	 * @param oldPath
	 *            源文件目录 如：E:/phsftp/src
	 * @param newPath
	 *            目标文件目录 如：E:/phsftp/dest
	 */
	public static void moveFiles(String oldPath, String newPath) {
		copyFolder(oldPath, newPath);
		delAllFile(oldPath);
	}

	/**
	 * 移动文件到指定目录，会删除文件夹
	 * 
	 * @param oldPath
	 *            源文件目录 如：E:/phsftp/src
	 * @param newPath
	 *            目标文件目录 如：E:/phsftp/dest
	 */
	public static void moveFolder(String oldPath, String newPath) {
		copyFolder(oldPath, newPath);
		delFolder(oldPath);
	}

	/**
	 * 压缩文件
	 * 
	 * @param srcDir
	 *            压缩前存放的目录
	 * @param destDir
	 *            压缩后存放的目录
	 * @throws Exception
	 */
	public static void yaSuoZip(String srcDir, String destDir) throws Exception {

		String tempFileName = null;
		int len;
		byte[] buf = new byte[1024 * 2];
		File[] files = new File(srcDir).listFiles();
		if (files != null)
		{
			for (File file : files)
			{
				if (file.isFile())
				{
					FileInputStream fis = new FileInputStream(file);
					BufferedInputStream bis = new BufferedInputStream(fis);
					if (destDir.endsWith(File.separator))
					{
						tempFileName = destDir + file.getName() + ".zip";
					}
					else
					{
						tempFileName = destDir + File.separator + file.getName() + ".zip";
					}
					FileOutputStream fos = new FileOutputStream(tempFileName);
					BufferedOutputStream bos = new BufferedOutputStream(fos);
					ZipOutputStream zos = new ZipOutputStream(bos);

					ZipEntry ze = new ZipEntry(file.getName());
					zos.putNextEntry(ze);

					while ((len = bis.read(buf)) != -1)
					{
						zos.write(buf, 0, len);
						zos.flush();
					}
					bis.close();
					zos.close();
				}
			}
		}
	}

	/**
	 * 读取数据
	 * 
	 * @param inSream
	 * @param charsetName
	 * @return
	 * @throws Exception
	 */
	public static String readData(InputStream inSream, String charsetName) throws Exception {

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = inSream.read(buffer)) != -1)
		{
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inSream.close();

		return new String(data, charsetName);
	}

	/**
	 * 一行一行读取文件，适合字符读取，若读取中文字符时会出现乱码
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static Set<String> readFile(String path) throws Exception {
		Set<String> datas = new HashSet<String>();
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		String line = null;
		while ((line = br.readLine()) != null)
		{
			datas.add(line);
		}
		br.close();
		fr.close();
		return datas;
	}

	/**
	 * 打开文件为InputStream
	 */
	public static InputStream asInputStream(File file) throws IOException {
		return new FileInputStream(file);
	}

	/**
	 * 打开文件为OutputStream
	 */
	public static OutputStream asOututStream(File file) throws IOException {
		return new FileOutputStream(file);
	}

	/**
	 * 判断目录是否存在, from Jodd
	 */
	public static boolean isDirExists(File dir) {
		if (dir == null)
		{
			return false;
		}
		return dir.exists() && dir.isDirectory();
	}

	/**
	 * 确保目录存在, 如不存在则创建
	 */
	public static void makesureDirExists(File file) throws IOException {
		if (file.exists())
		{
			if (!file.isDirectory())
			{
				throw new IOException("There is a file exists " + file);
			}
		}
		else
		{
			file.mkdirs();
		}
	}

	/**
	 * 判断文件是否存在, from Jodd
	 */
	public static boolean isFileExists(File file) {
		if (file == null)
		{
			return false;
		}
		return file.exists() && file.isFile();
	}

	/**
	 * 在临时目录创建临时文件，命名为tmp-${random.nextLong()}.tmp
	 */
	public static File createTempFile() throws IOException {
		return File.createTempFile("tmp-", ".tmp");
	}

	/**
	 * 在临时目录创建临时文件，命名为${prefix}${random.nextLong()}${suffix}
	 */
	public static File createTempFile(String prefix, String suffix) throws IOException {
		return File.createTempFile(prefix, suffix);
	}

	/**
	 * 获取文件名(不包含路径)
	 */
	public static String getFileName(String fullName) {
		int last = fullName.lastIndexOf(Platforms.FILE_PATH_SEPARATOR_CHAR);
		return fullName.substring(last + 1);
	}

}
