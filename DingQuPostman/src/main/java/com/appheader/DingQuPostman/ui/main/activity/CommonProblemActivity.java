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


public class CommonProblemActivity extends BaseActivity {
    @Bind(R.id.txt_toolbar_main_title)
    TextView txtTitle;//主标题
    @Bind(R.id.web_common_problem)WebView cpWeb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_problem);
        ButterKnife.bind(this);
        txtTitle.setText("常见问题");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cpWeb.setWebViewClient(new WebViewClient());
        cpWeb.loadUrl("https://www.baidu.com/");
    }

}
