package com.appheader.DingQuPostman.ui.main.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.appheader.DingQuPostman.R;
import com.appheader.DingQuPostman.common.activity.BaseActivity;
import com.appheader.DingQuPostman.common.utils.ToastUtil;
import com.appheader.DingQuPostman.ui.main.fragment.MainFragment;
import com.appheader.DingQuPostman.ui.main.fragment.SlideFragment;
import com.appheader.DingQuPostman.user.UserInfo;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    private MainFragment mMainFragment;
    @Bind(R.id.drawer)
    DrawerLayout drawer;
    @Bind(R.id.ll_left)
    LinearLayout llLeft;
    //@Bind(R.id.re_main)RelativeLayout reMain;
    // 上一次点击back键的时间
    private long mLastPressBackTime;
    private static final String TAG = "MainActivity";
    private UserInfo useInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();//初始化页面数据
        initView();//初始化页面视图
        toolbar.setVisibility(View.GONE);
    }

    private void initData() {
        Boolean result = getIntent().getBooleanExtra("user_info", false);
        if (result) {
           //初始化数据
        }
    }

    private void initView() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mMainFragment = new MainFragment();
        SlideFragment mSlidingFragment = new SlideFragment();
        transaction.replace(R.id.ml_fragment, mMainFragment, "mMainFragment");
        transaction.replace(R.id.sl_fragment, mSlidingFragment);
        transaction.commit();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mLastPressBackTime < 2000) {
            finish();
        } else {
            ToastUtil.showShort(MainActivity.this, "再按一次退出" + getResources().getString(R.string.app_name));
        }
        mLastPressBackTime = System.currentTimeMillis();
    }

}
