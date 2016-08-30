package com.appheader.DingQuPostman.common.cache;

import com.appheader.DingQuPostman.application.GlobalVars;

import java.io.File;

/**
 * 应用全局cache，用于数据存储，一般情况下不清除。
 * 存放内容如应用状态、当前登录状态等。
 * @author qzp
 *
 */
public class GlobalCache extends BaseCache {

	@Override
	protected String getCacheDir() {
		return GlobalVars.getAppFilesDir() + File.separator + "cache" + File.separator + "global";
	}

	@Override
	protected String getTAG() {
		return "GlobalCache";
	}

}
