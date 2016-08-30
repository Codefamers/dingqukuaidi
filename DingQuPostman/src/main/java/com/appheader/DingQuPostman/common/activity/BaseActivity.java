package com.appheader.DingQuPostman.common.activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.appheader.DingQuPostman.R;
import com.appheader.DingQuPostman.ui.main.activity.LoginActivity;
import com.appheader.DingQuPostman.ui.main.myinterface.SomeConstant;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.List;

import butterknife.Bind;

public class BaseActivity extends AppCompatActivity {
	public TextView title;
	public Toolbar toolbar;
	public ImageView imageView;
	private LinearLayout rootLayout;
	public boolean ALIVE;
	public TextView subTitle;
	private static final String TAG = "BaseActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityManager.getInstance().pushActivity(this);
		ALIVE = true;
		//改动部分
		super.setContentView(R.layout.activity_base);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
			localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
		}
		initToolbar();
		/**
		 * http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/1122/3712.html
		 * 在BaseActivity.java里：我们通过判断当前sdk_int大于4.4(kitkat),则通过代码的形式设置status bar为透明
		 * (这里其实可以通过values-v19 的sytle.xml里设置windowTranslucentStatus属性为true来进行设置，但是在某些手机会不起效，所以采用代码的形式进行设置)。
		 * 还需要注意的是我们这里的AppCompatAcitivity是android.support.v7.app.AppCompatActivity支持包中的AppCompatAcitivity,也是为了在低版本的android系统中兼容toolbar。
		 */
		/*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			Window window = getWindow();
			// Translucent status bar
			window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}*/


	}

	public void initToolbar() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.base_toolbar);
		if (toolbar != null) {
			setSupportActionBar(toolbar);
		}
	}
	@Override
	public void setContentView(int layoutId) {
		setContentView(View.inflate(this, layoutId, null));
	}

	@Override
	public void setContentView(View view) {
		imageView= (ImageView) findViewById(R.id.img_toolbar_navigation);
		toolbar= (Toolbar) findViewById(R.id.base_toolbar);
		title= (TextView) findViewById(R.id.txt_toolbar_main_title);
		subTitle=(TextView) findViewById(R.id.txt_toolbar_subtitle);
		rootLayout = (LinearLayout) findViewById(R.id.root_layout);
		if (rootLayout == null) return;
		rootLayout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		initToolbar();
	}


	/**
	 * 设置标题栏文字
	 *
	 * @param text
	 */
	protected void setHeaderCenterText(String text) {
		TextView txtHeader = null;
		if ((txtHeader = (TextView) findViewById(R.id.header_center_txt)) != null) {
			txtHeader.setText(text);
		}
	}
	/**
	 * 设置标题栏返回按钮事件
	 *
	 * @param listener
	 */
	protected void setHeaderBackOnclick(boolean isVisiable, boolean isShowTxt, String text, boolean isShowImg, int img,View.OnClickListener listener) {
		View btnBack = null;
		TextView txtView=null;
		ImageView imgView=null;
		if ((btnBack = findViewById(R.id.header_left)) != null&&isVisiable) {
			btnBack.setOnClickListener(listener);
			if((txtView =(TextView)findViewById(R.id.header_left_txt))!= null && isShowTxt){
				txtView.setText(text);
			}
			if((imgView =(ImageView)findViewById(R.id.header_left_img))!= null && isShowImg){
				imgView.setImageResource(img);
			}
		}
	}
	/**
	 * 设置标题栏右边按钮
	 * @param
	 * @param text
	 * @param listener
	 */
	protected  void setHeaderRightOnClick( boolean isShowTxt, String text, boolean isShowImg, int img, View.OnClickListener listener){
		LinearLayout llRight = null;
		TextView txtView=null;
		ImageView imgView=null;
		if((llRight = (LinearLayout)findViewById(R.id.header_right)) != null){

			llRight.setOnClickListener(listener);
			if((txtView =(TextView)findViewById(R.id.header_right_txt))!= null && isShowTxt){
				txtView.setText(text);
			}
			if((imgView =(ImageView)findViewById(R.id.header_right_img))!= null && isShowImg){
				imgView.setImageResource(img);
			}
		}
	}

	/**
	 * 设置状态栏颜色
	 * 也就是所谓沉浸式状态栏
	 */
	public void setStatusBarColor(int color) {
		/**
		 * Android4.4以上  但是抽屉有点冲突，目前就重写一个方法暂时解决4.4的问题
		 */
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			SystemBarTintManager tintManager = new SystemBarTintManager(this);
			tintManager.setStatusBarTintEnabled(true);
			tintManager.setStatusBarTintResource(color);
		}
	}


	public void setStatusBarColorForKitkat(int color) {
		/**
		 * Android4.4
		 */
		if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
			this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			SystemBarTintManager tintManager = new SystemBarTintManager(this);
			tintManager.setStatusBarTintEnabled(true);
			tintManager.setStatusBarTintResource(color);
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();


	}

	@Override
	protected void onPause() {
		super.onPause();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ALIVE =false;
		ActivityManager.getInstance().popActivity(this);

	}

	@Override
	protected void onStop() {
		super.onStop();
		if (!isAppOnForeground()) {
//			Intent intent = new Intent(this, CloseReceiver.class);
//			intent.putExtra("msg","closeActivity");
//			PendingIntent sender = PendingIntent.getBroadcast(this, 0,intent, 0);
//			AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
//			long firstTime = SystemClock.elapsedRealtime();
//			am.set(AlarmManager.ELAPSED_REALTIME,1*60*1000+firstTime,sender);
		}

	}

	/**
	 * 程序是否在前台运行
	 *
	 * @return
	 */
	public boolean isAppOnForeground() {
		// Returns a list of application processes that are running on the
		// device
		android.app.ActivityManager activityManager= (android.app.ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
		String packageName = getApplicationContext().getPackageName();

		List<android.app.ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
		if (appProcesses == null)
			return false;

		for (android.app.ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
			// The name of the process that this object is associated with.
			if (appProcess.processName.equals(packageName)
					&& appProcess.importance == android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
				return true;
			}
		}

		return false;
	}


	/*@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		Log.d(TAG, "uncaughtException: "+ex.getMessage());
		Log.d(TAG, "uncaughtException: "+ex.getCause());
		ex.printStackTrace();
	}*/
}
