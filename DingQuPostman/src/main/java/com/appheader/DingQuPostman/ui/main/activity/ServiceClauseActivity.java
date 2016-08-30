package com.appheader.DingQuPostman.ui.main.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.appheader.DingQuPostman.R;
import com.appheader.DingQuPostman.common.activity.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ServiceClauseActivity extends BaseActivity {
    @Bind(R.id.web_about)WebView abWeb;
    @Bind(R.id.txt_toolbar_main_title)
    TextView txtTitle;//主标题
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_clause);
        ButterKnife.bind(this);

        txtTitle.setText("服务条款");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        abWeb.setWebViewClient(new WebViewClient());
        //abWeb.setWebChromeClient(new WebChromeClient());
        abWeb.loadUrl("https://www.baidu.com/");
    }
}
