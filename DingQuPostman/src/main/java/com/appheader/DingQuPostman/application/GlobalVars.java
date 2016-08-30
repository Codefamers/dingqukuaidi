package com.appheader.DingQuPostman.application;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import java.util.Properties;

/**
 * 应用全局变量
 * 
 * @author qzp
 * 
 */
public class GlobalVars {
	private static Properties sProps;
	private static Context sContext;
	public static boolean isDebug;
	// 是否使用本地模拟数据
	public static boolean useSimulateData;

	private static float sDensity = -1;
	private static int sScreenWidth = -1;
	private static int sScreenHeight = -1;
	//是否重新加载互动学习页面
	private static boolean isInitAboutUs = false;
	public static void init(Context c) {
		sContext = c;
		loadProperties();
		isDebug = Boolean.valueOf(sProps.getProperty("isDebug", "false"));
		useSimulateData = Boolean.valueOf(sProps.getProperty("useSimulateData", "false"));
	}

	public static Context getContext() {
		return sContext;
	}

	public static Properties getAppConfiguration() {
		return sProps;
	}

	public static boolean isDebug() {
		return isDebug;
	}

	/**
	 * 获取应用服务器URL
	 *
	 * @return
	 */
	public static String getAppServerUrl() {
		return sProps.getProperty("appserver.url", "");
	}
	/**
	 * 获取应用私有目录
	 * 
	 * @return
	 */
	@SuppressLint("SdCardPath")
	public static String getAppFilesDir() {
		return "/data/data/" + sContext.getPackageName();
	}

	public static int getVersionCode() {
		return getPackageInfo().versionCode;
	}

	public static String getVersionName() {
		return getPackageInfo().versionName;
	}

	/**
	 * 获取应用全局设置的preference
	 */
	public static SharedPreferences getSettingsPref() {
		return sContext.getSharedPreferences("settings", Context.MODE_PRIVATE);
	}

	/**
	 * 保存屏幕属性
	 * 
	 * @param density
	 * @param screenWidth
	 * @param screenHeight
	 */
	public static void saveScreenProps(float density, int screenWidth, int screenHeight) {
		SharedPreferences pref = getSettingsPref();
		Editor editor = pref.edit();
		editor.putFloat("density", density);
		editor.putInt("screen_width", screenWidth);
		editor.putInt("screen_height", screenHeight);
		editor.commit();
	}
	/**
	 	 * 密度Dpi转像素
	 	 * @param dip 密度Dpi值
	 	 * @return 像素值
	 	 */
	public static int dip2px( float dip){
	    return (int)(0.5F + dip * getDensity());
	}
	public static float getDensity() {
		if (sDensity == -1)
			sDensity = getSettingsPref().getFloat("density", -1);
		return sDensity;
	}

	public static int getScreenWidth() {
		if (sScreenWidth == -1)
			sScreenWidth = getSettingsPref().getInt("screen_width", -1);
		return sScreenWidth;
	}

	public static int getScreenHeight() {
		if (sScreenHeight == -1)
			sScreenHeight = getSettingsPref().getInt("screen_height", -1);
		return sScreenHeight;
	}

	/**
	 * 加载raw下的properties配置文件
	 */
	private static Properties loadProperties() {
		sProps = new Properties();
		try {
			int id = sContext.getResources().getIdentifier("config", "raw", sContext.getPackageName());
			sProps.load(sContext.getResources().openRawResource(id));
		} catch (Exception e) {
			Log.e(GlobalVars.class.getName(), "Could not find the properties file.", e);
		}
		return sProps;
	}

	public static PackageInfo getPackageInfo() {
		PackageManager pm = sContext.getPackageManager();
		PackageInfo info = null;
		try {
			info = pm.getPackageInfo(sContext.getPackageName(), PackageManager.GET_CONFIGURATIONS);
		} catch (NameNotFoundException e) {
		}
		return info;
	}

	/**
	 * 获取当前机器的屏幕信息对象<br/>
	 * 另外：通过android.os.Build类可以获取当前系统的相关信息
	 *
	 * @param context
	 * @return
	 */
	public static DisplayMetrics getScreenInfo(Context context) {
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(dm);

		//int w = dm.widthPixels;//寬度（像素）
		//int h = dm.heightPixels; //高度（像素）
		//float d = dm.density; //密度（0.75 / 1.0 / 1.5）
		//int densityDpi = dm.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
		return dm;
	}

	public static boolean getIsInitAboutUs() {
		return isInitAboutUs;
	}

	public static void setIsInitAboutUs(boolean isInitAboutUs) {
		GlobalVars.isInitAboutUs = isInitAboutUs;
	}
}
