/*
package com.appheader.DingQuPostman.ui.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appheader.DingQuPostman.R;
import com.appheader.DingQuPostman.application.MyApplication;
import com.appheader.DingQuPostman.common.activity.BaseActivity;
import com.appheader.DingQuPostman.common.network.ResponseEntity;
import com.appheader.DingQuPostman.ui.common.qrcode.CaptureActivity;
//import com.appheader.DingQuPostman.ui.main.adapter.WaitTakeAdapter;
import com.appheader.DingQuPostman.ui.main.myinterface.SuccessRequestListener;
import com.appheader.DingQuPostman.user.CurrentUserManager;
import com.appheader.DingQuPostman.user.UserInfo;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddExpressActivity extends BaseActivity {
    @Bind(R.id.txt_express_id)
    TextView txtExpress;
    @Bind(R.id.txt_telephone_num)
    EditText telephone;
    private static final String TAG = "AddExpressActivity";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Toast.makeText(AddExpressActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(AddExpressActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_express);
        ButterKnife.bind(this);
        title.setText("添加快件");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txtExpress.setText(getIntent().getStringExtra("scan_result"));
    }

    @OnClick({R.id.btn_scan_complete, R.id.btn_continue_add})
    public void onClickButton(View view) {
        final String telphone = telephone.getText().toString();
        final UserInfo userInfo = CurrentUserManager.getCurrentUser();
        switch (view.getId()) {
            case R.id.btn_scan_complete:
                if (!TextUtils.isEmpty(telephone.getText()) && telphone.length() == 11) {
                    userInfo.addExpress(this, txtExpress.getText().toString(), telphone, new SuccessRequestListener() {
                        Message msg = new Message();
                        @Override
                        public void opreData(ResponseEntity response) {
                            Log.d(TAG, "opreData: "+response.isSuccess()+userInfo.getId());
                            Boolean result = response.isSuccess();
                            if (result) {
                                msg.what = 1;
                                msg.obj = "添加成功";
                               // addExpress();

                            } else {
                                msg.what = 0;
                                msg.obj = response.getErrorMessage();
                            }
                            handler.sendMessage(msg);



                        }
                    });
                    finish();
                } else {
                    Toast.makeText(AddExpressActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_continue_add:
                if (!TextUtils.isEmpty(telphone) || telphone.length() != 11) {
                    // postmanId: 快递员id  expressCompanyName：快递公司 expressNumber：快递单号  phone：收件人手机号
                    userInfo.addExpress(this, txtExpress.getText().toString(), telphone, new SuccessRequestListener() {
                        Message msg = new Message();
                        @Override
                        public void opreData(ResponseEntity response) {
                            Boolean result = response.isSuccess();
                            Log.d(TAG, "opreData: "+response.isSuccess()+userInfo.getId());
                            if (result) {
                                msg.what = 1;
                                msg.obj = "添加成功";
                                //addExpress();
                            } else {
                                msg.what = 0;
                                msg.obj =  response.getErrorMessage();
                            }
                            handler.sendMessage(msg);
                        }
                    });
                    Intent in = new Intent(AddExpressActivity.this, CaptureActivity.class);
                    startActivity(in);
                    finish();
                } else {
                    Toast.makeText(AddExpressActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

   */
/* private void addExpress() {
        MyApplication myApplication = (MyApplication) getApplication();
        WaitTakeAdapter adapter = myApplication.getWaitTakeAdapter();
        adapter.changeData();
    }*//*

}
*/
