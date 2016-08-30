package com.appheader.DingQuPostman.ui.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.appheader.DingQuPostman.R;
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
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RegisterActivity extends BaseActivity {

    @Bind(R.id.et_user_name)
    EditText etUserName;
    @Bind(R.id.et_nick_name)
    EditText etNickName;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.et_confirm_password)
    EditText etConfirmPassword;
    @Bind(R.id.et_verification_code)
    EditText etverificationCode;
    @Bind(R.id.btn_get_code)
    Button btnGetCode;
    @Bind(R.id.btn_is_agree_clause)
    ImageButton btnIsAgreeClause;
    @Bind(R.id.txt_agree_clause)
    TextView txtAgreeClause;
    @Bind(R.id.btn_login)
    Button btnLogin;
    private static final String TAG = "RegisterActivity";
    private boolean isClick;
    private String userName;
    private TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        title.setText("注册账号");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @OnClick({R.id.btn_login,R.id.btn_is_agree_clause,R.id.btn_get_code})
    public void clickButton(View view){
        switch (view.getId()) {
            case R.id.btn_login:
                registerRequest();
                break;
            case R.id.btn_is_agree_clause:
                if (isClick) {//未点击过
                    btnIsAgreeClause.setBackgroundResource(R.drawable.ico_select);
                    isClick = false;
                } else {
                    btnIsAgreeClause.setBackgroundResource(R.drawable.ico_unselect);
                    isClick = true;
                }
                break;
            case R.id.btn_get_code:
                getCodeRequest();
                break;
        }
    }

    private void registerRequest() {
        userName = etUserName.getText().toString();//手机号
        String userPassword = etPassword.getText().toString();//密码
        String userConfirmPassword=etConfirmPassword.getText().toString();//再次确认密码
        String nickName=etNickName.getText().toString();//昵称
        String etVerCode=etverificationCode.getText().toString();//验证码
        if (TextUtils.isEmpty(userName)||userName.length()!=11) {
            Toast.makeText(RegisterActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userPassword)) {
            Toast.makeText(RegisterActivity.this, "请输密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!userPassword.equals(userConfirmPassword)) {
            Toast.makeText(RegisterActivity.this, "俩次输入密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(nickName)) {
            Toast.makeText(RegisterActivity.this, "请输昵称", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(etVerCode)) {
            Toast.makeText(RegisterActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isClick) {
            Toast.makeText(RegisterActivity.this, "您未同意服务条款", Toast.LENGTH_SHORT).show();
            return;
        }
        //zhuce.call
        HashMap<String, String> paramsMap = ParamBuilder.buildParam().append("username", userName).
                append("password", userPassword).append("petName", nickName).append("code", etVerCode).toHashMap();
        RequestHelper.sendAsyncRequest(this, UrlConstants.ZHU_CE, paramsMap, new RequestCallback() {
            @Override
            public void onSuccess(ResponseEntity response) {
                //登录成功
                if (response.isSuccess()) {
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegisterActivity.this, response.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
                finish();
            }

            @Override
            public void onFailure(Exception ex) {
                Log.d(TAG, "onFailure: " + ex);
                Toast.makeText(RegisterActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        });


    }

    public void getCodeRequest() {
        userName=etUserName.getText().toString();
        if (TextUtils.isEmpty(userName)||userName.length()!=11) {
            Toast.makeText(RegisterActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        Observable.create(new Observable.OnSubscribe<Integer>(){
            @Override
            public void call(final Subscriber<? super Integer> subscriber) {
                timerTask = new TimerTask() {
                    int i = 60;
                    @Override
                    public void run() {
                        if (i==0) {
                            subscriber.onCompleted();
                        }
                        subscriber.onNext(Integer.valueOf(i--));
                    }
                };
                Timer timer = new Timer();
                timer.schedule(timerTask, 0, 1 * 1000);//0毫秒后每1秒执行该任务一次
            }}
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Integer>() {
            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext: subscribe"+integer);
                btnGetCode.setEnabled(false);
                btnGetCode.setText(String.format("%s秒", integer));
            }
            @Override
            public void onCompleted() {
                Log.d(TAG, "Completed!");
                btnGetCode.setText("获取验证码");
                timerTask.cancel();//取消定时任务
                //isCanGetVer = true;//允许再次获取验证码
                btnGetCode.setEnabled(true);
                Log.d(TAG, "handleMessage: 取消计时");
            }
            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "Error!");
            }
        });
        HashMap<String, String> paramsMap = ParamBuilder.buildParam().append("username", userName).toHashMap();
        RequestHelper.sendAsyncRequest(this, UrlConstants.YAN_ZHENG_MA, paramsMap, new RequestCallback() {
            @Override
            public void onSuccess(ResponseEntity response) {
                if (response.isSuccess()) {
                    //登录成功
                    Toast.makeText(RegisterActivity.this, "获取成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegisterActivity.this, "获取失败", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Exception ex) {
                Log.d(TAG, "onFailure: " + ex);
                Toast.makeText(RegisterActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
            }
        });

    }
}
