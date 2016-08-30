package com.appheader.DingQuPostman.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * 数据库操作基类 各个业务模块操作数据库的类都集成此类，数据库的实例对象可直接引用该类中的mDbInstance
 * @author qzp
 * 
 */
public class DBHelper {

	protected static SQLiteDatabase mDbInstance;

	/**
	 * 打开数据库实例，在登录成功后和Application启动时调用
	 */
	public static void openDbInstance() {
//		if (CurrentUserManager.getCurrentUser() != null && mDbInstance == null)
//			mDbInstance = DBManager.getUserDatabase(CurrentUserManager.getCurrentUser().getUserId());
	}

	/**
	 * 关闭数据库实例，在退出登录后调用
	 */
	public static void closeDbInstance() {
		if (mDbInstance != null) {
			mDbInstance.close();
			mDbInstance = null;
		}
	}
}
