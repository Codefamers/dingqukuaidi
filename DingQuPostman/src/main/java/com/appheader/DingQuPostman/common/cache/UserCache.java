package com.appheader.DingQuPostman.common.cache;

import com.appheader.DingQuPostman.application.GlobalVars;
import com.appheader.DingQuPostman.user.CurrentUserManager;

import java.io.File;

/**
 * 用户个人信息和私有数据缓存
 * 
 * @author qzp
 * 
 */
public class UserCache extends BaseCache {

	private String mCurrentUserId;

	public UserCache() {
		mCurrentUserId = CurrentUserManager.getCurrentUser().getId();
	}

	public String getUserId() {
		return mCurrentUserId;
	}

	@Override
	protected String getCacheDir() {
		return GlobalVars.getAppFilesDir() + File.separator + "userprofile" + File.separator + mCurrentUserId;
	}

	@Override
	protected String getTAG() {
		return "UserCache";
	}

}
