package com.appheader.DingQuPostman.common.utils;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.appheader.DingQuPostman.R;
import com.appheader.DingQuPostman.common.activity.ActivityManager;
import com.appheader.DingQuPostman.db.DBHelper;
import com.appheader.DingQuPostman.ui.main.activity.LoginActivity;


public class ActivityUtil {

	public static void sessionTimeout(final Activity context) {
		context.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(context, R.string.session_timeout, Toast.LENGTH_LONG).show();
				logout(context);
			}
		});
	}
	
	/**
	 * 退出登录（关闭所有Activity并跳转至Login界面） 同时停止jpush服务，关闭用户数据库
	 * 
	 * @param context
	 */
	public static void logout(final Activity context) {
//		CurrentUserManager.clearCurrentUser();
//		JPushController.unRegister(context);
		DBHelper.closeDbInstance();
		ActivityManager.getInstance().popAllActivity();
		Intent intent = new Intent(context, LoginActivity.class);
		context.startActivity(intent);
		/*JPushInterface.setAlias(context, null, new TagAliasCallback() {

			@Override
			public void gotResult(int code, String alias, Set<String> tags) {
				JPushInterface.stopPush(context);
			}
		});*/

	}

}
