package com.appheader.DingQuPostman.ui.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.appheader.DingQuPostman.R;
import com.appheader.DingQuPostman.application.GlobalVars;
import com.appheader.DingQuPostman.common.activity.BaseActivity;
import com.appheader.DingQuPostman.common.network.RequestCallback;
import com.appheader.DingQuPostman.common.network.RequestHelper;
import com.appheader.DingQuPostman.common.network.ResponseEntity;
import com.appheader.DingQuPostman.common.network.UrlConstants;
import com.appheader.DingQuPostman.common.utils.ParamBuilder;
import com.appheader.DingQuPostman.common.utils.json.JSONUtil;
import com.appheader.DingQuPostman.user.CurrentUserManager;
import com.appheader.DingQuPostman.user.UserInfo;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {
    @Bind(R.id.re_home_bg)
    ImageView reHomeBG;
    @Bind(R.id.iv_logo_big)
    ImageView ivLogoBig;
    @Bind(R.id.iv_logo_small)
    ImageView ivLogoSmall;
    private CountDownTimer timer;
    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        toolbar.setVisibility(View.GONE);
        /**
         * 此Activity为程序启动后的第一个Activity，在此获取屏幕相关信息，存入GlobalVars，以便其他界面调用
         */
        if (GlobalVars.getDensity() == -1) {
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            GlobalVars.saveScreenProps(
                    getResources().getDisplayMetrics().density, dm.widthPixels,
                    dm.heightPixels);
        }
        ButterKnife.bind(this);
       /* //闪屏页动画
        final ScaleAnimation scale = new ScaleAnimation(1, 1.8f, 1, 1.8f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        scale.setDuration(3000);// 动画时间
        scale.setFillAfter(true);// 保持动画状态
        reHomeBG.setAnimation(scale);*/
        final TranslateAnimation animation = new TranslateAnimation(0, 320, 0, 0);
        animation.setDuration(1000);//设置动画持续时间
        animation.setRepeatCount(2);//设置重复次数
        animation.setRepeatMode(Animation.REVERSE);//设置反方向执行
        animation.setFillAfter(true);
        ivLogoBig.setAnimation(animation);
        timer = new CountDownTimer(1000, 80) {
            int i = 0;

            @Override
            public void onTick(long millisUntilFinished) {
                // TODO: 16/4开始下个activity之前要执行的操作
                Log.d(TAG, "onTick: " + "执行操作" + i++);
            }

            @Override
            public void onFinish() {
                final Intent in = new Intent(SplashActivity.this, MainActivity.class);
                UserInfo userInfo = CurrentUserManager.getCurrentUser();
                if (userInfo!=null)
                    loginRequest(in,userInfo);
                else {
                    startActivity(in);
                    finish();
                }
            }

        }.start();
    }

    private void loginRequest(final Intent in, UserInfo userInfo) {
        HashMap<String, String> paramsMap = ParamBuilder.buildParam().append("username", userInfo.getUsername()).append("password", userInfo.getPassword()).toHashMap();
        RequestHelper.sendAsyncRequest(SplashActivity.this, UrlConstants.DENG_LU, paramsMap, new RequestCallback() {
            @Override
            public void onSuccess(ResponseEntity response) {
                if (response.isSuccess()) {
                    //登录成功
                    JSONObject arr = response.getDataObject();
                    UserInfo userInfo = JSONUtil.fromJSON(arr, UserInfo.class);
                    CurrentUserManager.setCurrentUser(userInfo);//保存用户信息

                } else
                    Toast.makeText(SplashActivity.this, "账户或密码不正确请重新输入", Toast.LENGTH_SHORT).show();
                startActivity(in);
                finish();
            }

            @Override
            public void onFailure(Exception ex) {
                Log.d(TAG, "onFailure: " + ex);
                startActivity(in);
                finish();
                Toast.makeText(SplashActivity.this, "无网络", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        timer.cancel();
    }
}
