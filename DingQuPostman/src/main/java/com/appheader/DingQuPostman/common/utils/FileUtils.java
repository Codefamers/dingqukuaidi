package com.appheader.DingQuPostman.common.utils;

import android.content.res.AssetManager;
import android.os.Environment;


import com.appheader.DingQuPostman.application.GlobalVars;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {
	
	private static final String TAG = FileUtils.class.getName();

	public static void copyFileFromAssets(String resourceFileName, String targetFile) {
		AssetManager am = GlobalVars.getContext().getAssets();
		try {
			InputStream fis = am.open(resourceFileName);
			OutputStream os = new FileOutputStream(new File(targetFile));

			byte[] buff = new byte[1024];
			int len = 0;
			while ((len = fis.read(buff)) != -1) {
				os.write(buff, 0, len);
			}
			os.flush();
			os.close();
			fis.close();
		} catch (IOException e) {
			LogUtil.error(TAG, e);
		}
	}

	public static boolean copyFile(File from, File to) {
		if (!from.isFile() || !to.isFile())
			return false;

		InputStream in = null;
		OutputStream out = null;

		try {
			in = new FileInputStream(from);
			out = new FileOutputStream(to);

			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
		} catch (Exception e) {
			LogUtil.error(TAG, e);
			return false;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					/* Ignore */
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					/* Ignore */
				}
			}
		}
		return true;
	}

	public static boolean writeFile(byte[] data, File file) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			fos.write(data);
			fos.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 读取assets中的文本文件内容
	 * @param resourceFileName
	 * @return
	 */
	public static String readTextFromAssets(String resourceFileName) {
		AssetManager am = GlobalVars.getContext().getAssets();
		InputStream fis = null;
		ByteArrayOutputStream os = null;
		try {
			fis = am.open(resourceFileName);
			os = new ByteArrayOutputStream();

			byte[] buff = new byte[1024];
			int len = 0;
			while ((len = fis.read(buff)) != -1) {
				os.write(buff, 0, len);
			}
			os.flush();
			byte[] byteArray = os.toByteArray();
			return new String(byteArray, "UTF-8");
		} catch (IOException e) {
			LogUtil.error(TAG, e);
		} finally{
			try {
				os.close();
				fis.close();
			} catch (IOException e) {}
		}
		
		return null;
	}

	public static String getSDApplictionDir() {
		boolean hasSd = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		if (!hasSd) {
			return null;
		}
		String appPath = Environment.getExternalStorageDirectory() + "/TJWTC";
		File dir = new File(appPath);
		if (!dir.exists()) {
			if (!dir.mkdir()) {
				return null;
			} else {
				return appPath + "/";
			}
		} else {
			return appPath + "/";
		}
	}
}
