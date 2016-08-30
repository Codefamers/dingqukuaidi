package com.appheader.DingQuPostman.user;

import com.appheader.DingQuPostman.common.cache.CacheManager;

/**
 * 当前登录用户信息管理
 *
 * @author qzp
 */
public class CurrentUserManager {

    public static final String CACHE_KEY_CURRENT_USER = "current_user";

    /**
     * 清除当前登录用户（退出登录时调用）
     */
    public static void clearCurrentUser() {
        CacheManager.getGlobalCache().remove(CACHE_KEY_CURRENT_USER);
    }

    /**
     * 设置当前登录用户（登录成功后调用）
     *
     * @param
     * @param
     * @return
     */
    public static UserInfo setCurrentUser(UserInfo user) {
        CacheManager.getGlobalCache().putCache(CACHE_KEY_CURRENT_USER, user);
        return user;
    }

    /**
     * 获取当前登录用户信息
     *
     * @return
     */
    public static UserInfo getCurrentUser() {
        return (UserInfo) CacheManager.getGlobalCache().getCache(CACHE_KEY_CURRENT_USER);
    }

    public static int isCommitInfo(int status, int reviewStatus) {
        switch (status) {
            case 0://未认证
                //完善信息
                switch (reviewStatus) {
                    case 0://审核中
                        return 2;
                    case -1://拒绝
                        return 4;
                    case -100://未审核
                        return 1;
                }
            case 100://已认证
                return 3;
            case -1://后台强制关闭
                return 5;

        }
        return -100;
    }
}
