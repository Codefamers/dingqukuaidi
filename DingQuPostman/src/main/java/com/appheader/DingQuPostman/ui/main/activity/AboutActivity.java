package com.appheader.DingQuPostman.ui.main.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.appheader.DingQuPostman.R;
import com.appheader.DingQuPostman.common.activity.BaseActivity;
import com.appheader.DingQuPostman.ui.main.myinterface.SomeConstant;

import butterknife.Bind;
import butterknife.ButterKnife;


public class AboutActivity extends BaseActivity {
    @Bind(R.id.web_about)WebView abWeb;
    @Bind(R.id.txt_toolbar_main_title)
    TextView txtTitle;//主标题
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        int position=getIntent().getIntExtra("activity",-1);
        initView(position);


    }

    private void initView(int position) {
        switch (position) {
            case SomeConstant.NO_POWER_SEARCH_ACTIVITY:
                initWebView("停电查询",SomeConstant.OUTAGE_INFORMATION);
                break;
            case SomeConstant.NET_INFOMATION_SEARCH_ACTIVITY:
                initWebView("网点查询",SomeConstant.SERVICE_NETWORK);
                break;
            case SomeConstant.ABOUT_US_ACTIVITY:
                initWebView("关于我们",SomeConstant.ABOUT_US);
                break;
        }
    }

    private void initWebView(String title,String url) {
        txtTitle.setText(title);
        WebSettings settings=abWeb.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        abWeb.setWebViewClient(new WebViewClient());
        //abWeb.setWebChromeClient(new WebChromeClient());
        abWeb.loadUrl(url);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



}
