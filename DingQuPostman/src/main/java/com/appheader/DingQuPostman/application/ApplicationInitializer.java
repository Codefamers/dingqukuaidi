package com.appheader.DingQuPostman.application;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.appheader.DingQuPostman.common.files.ResourceFileManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 应用初始化 Application启动时执行doInitialize()方法
 * 从preference中读取上次安装的版本号，比对当前apk的版本号，如果当前版本号大于上次doInitialize时的版本号，则
 * 调用doUpdate方法进行升级操作
 * 
 * @author qzp
 * 
 */
public class ApplicationInitializer {

	private static final String KEY_LAST_INSTALLED_VERSION = "last_installed_version";

	private static SharedPreferences sSettings = GlobalVars.getSettingsPref();

	/**
	 * 执行初始化方法，在Application onCreate()中调用
	 */
	public static void doInitialize() {
		// 获取上次执行doInitialize时的版本号
		int currentVersion = GlobalVars.getVersionCode();
		int lastInstalledVersion = sSettings.getInt(KEY_LAST_INSTALLED_VERSION, currentVersion);
		// 如果当前版本号大于上次的版本号，则执行doUpdate
		if (currentVersion > lastInstalledVersion) {
			doUpdate(lastInstalledVersion, currentVersion);
		}
		// 将lastInstalledVersion值更新为当前版本号
		Editor editor = sSettings.edit();
		editor.putInt(KEY_LAST_INSTALLED_VERSION, currentVersion);
		editor.commit();
	}

	/**
	 * 应用升级 用于进行配置升级、数据库升级等
	 * 
	 * @param lastVersion
	 * @param currentVersion
	 */
	private static void doUpdate(int lastVersion, int currentVersion) {
//		if (lastVersion < 140627) {
//			List<File> databases = searchUserDatabaseFiles();
//			for (File file : databases) {
//				try {
//					updateTableTo_140627(file);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
	}

	private static boolean isUserDatabase(File file) {
		if (!file.getName().endsWith(".db"))
			return false;
		String name = file.getName().substring(0, file.getName().indexOf(".db"));
		return name.length() == 11 && TextUtils.isDigitsOnly(name);
	}

	private static List<File> searchUserDatabaseFiles() {
		List<File> result = new ArrayList<File>();
		File dbDir = new File(ResourceFileManager.getPublicDatabaseDir());
		File[] files = dbDir.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (isUserDatabase(files[i])) {
				result.add(files[i]);
			}
		}
		return result;
	}

//	private static void updateTableTo_140627(File dbFile) {
//		SQLiteDatabase database = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
//		database.execSQL("CREATE TABLE task_discuss_msg_read_log (task_id TEXT(100)  PRIMARY KEY DEFAULT NULL,latest_read_timestamp TEXT(50) DEFAULT NULL)");
//		database.close();
//	}
}
