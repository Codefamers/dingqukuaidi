package com.appheader.DingQuPostman.common.cache;

import com.appheader.DingQuPostman.user.CurrentUserManager;

public class CacheManager {

	private static GlobalCache sGlobalCache;
	private static PublicDataCache sPublicDataCache;
	private static UserCache sUserCache;

	public static GlobalCache getGlobalCache() {
		if (sGlobalCache == null) {
			sGlobalCache = new GlobalCache();
			sGlobalCache.init();
		}
		return sGlobalCache;
	}

	public static PublicDataCache getPublicDataCache() {
		if (sPublicDataCache == null) {
			sPublicDataCache = new PublicDataCache();
			sPublicDataCache.init();
		}
		return sPublicDataCache;
	}

	public static UserCache getUserCache() {
		if (sUserCache == null) {
			sUserCache = new UserCache();
			sUserCache.init();
		} else {
			if (!sUserCache.getUserId().equals(CurrentUserManager.getCurrentUser().getId())) {
				sUserCache.close();
				sUserCache = null;
				sUserCache = new UserCache();
				sUserCache.init();
			}
		}
		return sUserCache;
	}
}
