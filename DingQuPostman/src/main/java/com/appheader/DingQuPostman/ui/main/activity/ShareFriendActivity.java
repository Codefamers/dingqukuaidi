package com.appheader.DingQuPostman.ui.main.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.appheader.DingQuPostman.R;
import com.appheader.DingQuPostman.common.activity.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ShareFriendActivity extends BaseActivity {
    @Bind(R.id.base_toolbar)Toolbar toolbar;
    @Bind(R.id.txt_toolbar_main_title)TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_friend);
        ButterKnife.bind(this);
        title.setText("分享给好友");
        /*getSupportActionBar().setTitle("分享给好友");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        toolbar.inflateMenu(R.menu.base_toolbar_menu);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
