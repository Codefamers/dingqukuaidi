package com.appheader.DingQuPostman.common.utils;

import android.util.Log;

import org.apache.commons.lang.exception.ExceptionUtils;

/**
 * Logcat工具类
 * 
 * @author alaowan
 *
 */
public class LogUtil {
	private static final boolean isDebug = true;
	public static void error(String tag, Throwable e) {
		if(LogUtil.isDebug){
			Log.e(tag, ExceptionUtils.getFullStackTrace(e));
		}
	}
	
	public static void error(String tag, String errorMsg) {
		if (errorMsg == null)
			return;
		if(LogUtil.isDebug){
			Log.e(tag, errorMsg);
		}
	}

	public static void info(String tag, String message) {
		if (message == null)
			return;
		if(LogUtil.isDebug){
			Log.i(tag, message);
		}
	}

	public static void debug(String tag, String message) {
		if (message == null)
			return;
		if(LogUtil.isDebug){
			Log.d(tag, message);
		}
	}

}
