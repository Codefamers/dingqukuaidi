package com.appheader.DingQuPostman.ui.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.TimerTask;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    @Bind(R.id.iv_logo)
    ImageView ivLogo;
    @Bind(R.id.et_user_name)
    EditText etUserName;
    @Bind(R.id.et_user_password)
    EditText etUserPassword;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.txt_forget_password)
    TextView txtForgetPassword;
    @Bind(R.id.txt_register_user)
    TextView txtRegisterUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        title.setText("登录");
        imageView.setVisibility(View.GONE);
    }
    @OnClick({R.id.txt_register_user,R.id.txt_forget_password,R.id.btn_login})
    public void clickButton(View v){
        switch (v.getId()) {
            case R.id.txt_register_user:
               // Toast.makeText(LoginActivity.this, "注册新账号", Toast.LENGTH_SHORT).show();
                Intent register=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(register);
                break;
            case R.id.txt_forget_password:
                Intent forget=new Intent(LoginActivity.this,ForgetPasswordActivity.class);
                startActivity(forget);
                break;
            case R.id.btn_login:
                String userName = etUserName.getText().toString();//手机号
                String userPassword = etUserPassword.getText().toString();//验证码
                if (TextUtils.isEmpty(userName)||userName.length()!=11) {
                    Toast.makeText(LoginActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(userPassword)) {
                    Toast.makeText(LoginActivity.this, "请输密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                HashMap<String, String> paramsMap = ParamBuilder.buildParam().append("username", userName).append("password", userPassword).toHashMap();
                RequestHelper.sendAsyncRequest(this, UrlConstants.DENG_LU, paramsMap, new RequestCallback() {
                    @Override
                    public void onSuccess(ResponseEntity response) {
                        if (response.isSuccess()) {
                            //登录成功
                            JSONObject arr = response.getDataObject();
                            UserInfo userInfo = JSONUtil.fromJSON(arr, UserInfo.class);
                            CurrentUserManager.setCurrentUser(userInfo);//保存用户信息
                            Intent forget=new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(forget);
                        }else{
                            Toast.makeText(LoginActivity.this, "账户或密码不正确请重新输入", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Exception ex) {
                        Log.d(TAG, "onFailure: " + ex);
                        Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() {
                    }
                });
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
   // private TimerTask timerTask;
 /* private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    btnGetCode.setText("获取验证码");
                    timerTask.cancel();//取消定时任务
                    isCanGetVer = true;//允许再次获取验证码
                    break;
                case SomeConstant.EOOR_CODE:
                    Toast.makeText(LoginActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                case SomeConstant.BG_CLOSE_CODE:
                    Toast.makeText(LoginActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    btnGetCode.setText(msg.what + "s后重新获取");
                    isCanGetVer = false;//不允许再次获取验证码
                    break;
            }

        }
    };*/
 /*//初始化toolbar
        toolbar.setVisibility(View.GONE);
        //对验证码焦点进行监听
        etVerCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() < 4) {
                    btnLogin.setClickable(false);
                } else {
                    btnLogin.setClickable(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/


    /* //登录
     @OnClick(R.id.btn_login)
     public void login() {
         String mobile = etTelNum.getText().toString();//手机号
         String smsCode = etVerCode.getText().toString();//验证码
         if (TextUtils.isEmpty(mobile) || mobile.length() != 11) {
             Toast.makeText(LoginActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
             return;
         }
         if (TextUtils.isEmpty(smsCode)) {
             Toast.makeText(LoginActivity.this, "请输验证码", Toast.LENGTH_SHORT).show();
             return;
         }

         //mobile: 手机号码
         //smsCode：验证码

         HashMap<String, String> paramsMap = ParamBuilder.buildParam().append("mobile", mobile).append("smsCode", smsCode).toHashMap();
         RequestHelper.sendAsyncRequest(this, UrlConstants.POST_MAN_LOGIN, paramsMap, new RequestCallback() {
             @Override
             public void onSuccess(ResponseEntity response) {
                 Log.d(TAG, "onSuccess: " + response);
                 *//*Intent success = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(success);
                finish();*//*
                if (response.isSuccess()) {
                    //登录成功
                    JSONObject arr = response.getDataObject();
                    Log.d(TAG, "onSuccess: " + arr);

                    UserInfo userInfo= JSONUtil.fromJSON(arr, UserInfo.class);
                    CurrentUserManager.setCurrentUser(userInfo);//保存用户信息
                    Log.d(TAG, "onSuccess: "+userInfo.getId());
                    Log.d(TAG, "onSuccess: 是否存在"+userInfo.reviewStatus+"\n"+userInfo.getReviewStatus());
                    int reviewStatus=userInfo.reviewStatus==null?-100:arr.optInt("reviewStatus");
                    Log.d(TAG, "onSuccess:认证码 " + arr.optInt("status") + "审核码" + arr.optInt("reviewStatus"));
                    int code = CurrentUserManager.isCommitInfo(arr.optInt("status"), arr.optInt("reviewStatus"));
                    String telPhone=etTelNum.getText().toString();
                    switch (code) {
                        case SomeConstant.NO_IDENTIFICATION://没有认证1
                            Log.d(TAG, "onSuccess: " + "没有认证");
                            Intent noID = new Intent(LoginActivity.this, ApplyBusinessActivity.class);
                            noID.putExtra("check_status", SomeConstant.NO_IDENTIFICATION);
                            noID.putExtra("check_tel",telPhone);
                            startActivity(noID);
                            finish();
                            break;
                        case SomeConstant.BG_CLOSE://后台强制关闭5
                            Message msg = new Message();
                            msg.what = SomeConstant.BG_CLOSE_CODE;
                            msg.obj = "账户已被禁用，请联系管理员";
                            handler.sendMessage(msg);
                            break;
                        case SomeConstant.NO_CHECKED://审核未通过4
                            Log.d(TAG, "onSuccess: " + "审核未通过");
                            Intent fail = new Intent(LoginActivity.this, ApplyBusinessActivity.class);
                            fail.putExtra("check_status", SomeConstant.NO_CHECKED);
                            fail.putExtra("check_tel",telPhone);
                            startActivity(fail);
                            finish();
                            break;
                        case SomeConstant.CHECKED://审核通过3
                            Log.d(TAG, "onSuccess: " + "审核通过");
                            Intent success = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(success);
                            finish();
                            break;
                        case SomeConstant.CHECKING://审核中4
                            Log.d(TAG, "onSuccess: " + "审核中");
                            Intent checking = new Intent(LoginActivity.this, ApplyBusinessActivity.class);
                            checking.putExtra("check_status", SomeConstant.CHECKING);
                            checking.putExtra("check_tel",telPhone);
                            startActivity(checking);
                            finish();
                            break;

                        default:
                            Toast.makeText(LoginActivity.this, "未考虑到情况", Toast.LENGTH_SHORT).show();
                            break;
                    }


                } else {
                    //登录失败
                    UserInfo userInfo=new UserInfo();
                    CurrentUserManager.setCurrentUser(userInfo);//保存用户信息
                    Message msg = new Message();
                    msg.what = SomeConstant.EOOR_CODE;
                    msg.obj = "登录失败" + response.getErrorMessage();
                    handler.sendMessage(msg);
                    Intent success = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(success);
                    finish();
                }
            }

            @Override
            public void onFailure(Exception ex) {
                Log.d(TAG, "onFailure: " + ex);
                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    //验证码
    @OnClick(R.id.btn_get_code)
    public void getVerCode() {
        String etPhone = etTelNum.getText().toString();
        //对点击次数验证
        if (!isCanGetVer) {
            Toast.makeText(LoginActivity.this, "操作频繁，稍后再试", Toast.LENGTH_SHORT).show();
            return;
        }
        //对手机号输入框进行验证
        if (TextUtils.isEmpty(etPhone) || etPhone.length() != 11) {
            Toast.makeText(LoginActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
       *//* if (etPhone.length()!= 11) {
            Toast.makeText(LoginActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }*//*
        //改变获取验证码按钮状态
        timerTask = new TimerTask() {
            int i = 60;

            @Override
            public void run() {
                Message msg = new Message();
                msg.what = i--;
                handler.sendMessage(msg);
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 0, 1 * 1000);//0毫秒后每2分钟执行该任务一次


        //提交参数	mobile: 手机号码
        HashMap<String, String> paramsMap = ParamBuilder.buildParam().append("mobile", etPhone).toHashMap();
        RequestHelper.sendAsyncRequest(this, UrlConstants.SEND_LOGIN_SMS, paramsMap, new RequestCallback() {
            @Override
            public void onSuccess(ResponseEntity response) {
                if (response.isSuccess()) {
                    Toast.makeText(LoginActivity.this, "验证码发送成功", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Exception ex) {
                Log.d(TAG, "onFailure: " + ex);

            }

            @Override
            public void onComplete() {

            }
        });

    }*/