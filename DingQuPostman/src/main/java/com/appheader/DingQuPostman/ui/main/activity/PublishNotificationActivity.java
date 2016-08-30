package com.appheader.DingQuPostman.ui.main.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PublishNotificationActivity extends BaseActivity {

    @Bind(R.id.et_edit_title)
    EditText etEditTitle;
    @Bind(R.id.et_edit_content)
    EditText etEditContent;
    private static final String TAG = "PublishNotificationActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_notification);
        ButterKnife.bind(this);
        title.setText("发布通知");
        subTitle.setText("发布");
        subTitle.setVisibility(View.VISIBLE);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        subTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PublishNotification();
            }
        });
    }

    private void PublishNotification() {
        String notTitle=etEditTitle.getText().toString();
        String notContent=etEditContent.getText().toString();
        if(TextUtils.isEmpty(notTitle))
            Toast.makeText(PublishNotificationActivity.this, "请输入通知标题", Toast.LENGTH_SHORT).show();
        if(TextUtils.isEmpty(notContent)){
            Toast.makeText(PublishNotificationActivity.this, "请输入通知内容", Toast.LENGTH_SHORT).show();
        }
        //title,time,imageUrl,details,username

        HashMap<String, String> paramsMap = ParamBuilder.buildParam().append("title", notTitle).append("time", "2016-08-20 15:36")
               .append("details", notContent).append("petName", "屈淮南").toHashMap();
        RequestHelper.sendAsyncRequest(this, UrlConstants.TONGZHIFABU, paramsMap, new RequestCallback() {
            @Override
            public void onSuccess(ResponseEntity response) {
                if (response.isSuccess()) {
                    Toast.makeText(PublishNotificationActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                    GlobalVars.setIsInitAboutUs(false);//刷新互动学习界面
                    finish();
                }else
                    Toast.makeText(PublishNotificationActivity.this, response.getErrorMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Exception ex) {
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
