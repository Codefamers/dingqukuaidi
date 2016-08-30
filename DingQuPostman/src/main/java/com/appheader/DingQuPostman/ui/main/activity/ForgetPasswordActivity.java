package com.appheader.DingQuPostman.ui.main.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.appheader.DingQuPostman.R;
import com.appheader.DingQuPostman.common.activity.BaseActivity;
import com.appheader.DingQuPostman.common.network.RequestCallback;
import com.appheader.DingQuPostman.common.network.RequestHelper;
import com.appheader.DingQuPostman.common.network.ResponseEntity;
import com.appheader.DingQuPostman.common.network.UrlConstants;
import com.appheader.DingQuPostman.common.utils.ParamBuilder;

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

public class ForgetPasswordActivity extends BaseActivity {
    @Bind(R.id.et_user_name)
    EditText etUserName;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.et_confirm_password)
    EditText etConfirmPassword;
    @Bind(R.id.et_verification_code)
    EditText etVerificationCode;
    @Bind(R.id.btn_get_code)
    Button btnGetCode;
    @Bind(R.id.btn_login)
    Button btnLogin;
    private TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        title.setText("忘记密码");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @OnClick({R.id.btn_get_code,R.id.btn_login})
    public void clickButton(View view){
        String userName=etUserName.getText().toString();
        switch (view.getId()) {
            case R.id.btn_get_code:
                getCodeRequest(userName,this);
                break;
            case R.id.btn_login:
                break;
        }
    }
    public void getCodeRequest(String userName, final Activity activity) {

        userName=etUserName.getText().toString();
        if (TextUtils.isEmpty(userName)||userName.length()!=11) {
            Toast.makeText(activity, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        Observable.create(new Observable.OnSubscribe<Integer>(){
            @Override
            public void call(final Subscriber<? super Integer> subscriber) {
                 timerTask = new TimerTask() {
                    int i = 60;
                    @Override
                    public void run() {
                        if (i == 0) {
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
                Log.d(activity.toString(), "onNext: subscribe"+integer);
                btnGetCode.setEnabled(false);
                btnGetCode.setText(String.format("%s秒", integer));
            }
            @Override
            public void onCompleted() {
                btnGetCode.setText("获取验证码");
                timerTask.cancel();//取消定时任务
                btnGetCode.setEnabled(true);
            }
            @Override
            public void onError(Throwable e) {
                Log.d(activity.toString(), "Error!");
            }
        });
        HashMap<String, String> paramsMap = ParamBuilder.buildParam().append("username", userName).toHashMap();
        RequestHelper.sendAsyncRequest(this, UrlConstants.YAN_ZHENG_MA, paramsMap, new RequestCallback() {
            @Override
            public void onSuccess(ResponseEntity response) {
                if (response.isSuccess()) {
                    //登录成功
                    Toast.makeText(activity, "获取成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(activity, "获取失败", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Exception ex) {
                Toast.makeText(activity, "网络出错", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        });

    }
}
