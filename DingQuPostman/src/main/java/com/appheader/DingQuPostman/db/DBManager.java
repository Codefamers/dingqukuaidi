package com.appheader.DingQuPostman.db;

import android.database.sqlite.SQLiteDatabase;

import com.appheader.DingQuPostman.common.files.ResourceFileManager;
import com.appheader.DingQuPostman.common.utils.FileUtil;

import java.io.File;

/**
 * 数据库管理器，负责数据库文件的创建、升级以及获取实例对象
 * 
 * @author qzp
 * 
 */
public class DBManager {

	/**
	 * 获取指定用户的私有数据库连接
	 * 
	 * @param userId
	 * @return
	 */
	public static SQLiteDatabase getUserDatabase(String userId) {
		// 获取数据库目录
		String databaseDir = ResourceFileManager.getPublicDatabaseDir();
		File userDb = new File(databaseDir + File.separator + userId + ".db");
		// 数据库文件不存在，从assets中拷贝模板，目标文件以userId命名
		if (!userDb.exists()) {
			FileUtil.copyFileFromAssets("database/user_profile.db", userDb.getAbsolutePath());
		}
		return SQLiteDatabase.openDatabase(userDb.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
	}

}
