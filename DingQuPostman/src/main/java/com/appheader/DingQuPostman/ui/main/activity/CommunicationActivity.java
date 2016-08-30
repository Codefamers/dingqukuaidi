package com.appheader.DingQuPostman.ui.main.activity;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
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
import com.appheader.DingQuPostman.ui.main.adapter.DivideItemDecoratior;
import com.appheader.DingQuPostman.ui.main.adapter.FineArticleAdapter;
import com.appheader.DingQuPostman.ui.main.myinterface.SomeConstant;

import org.json.JSONArray;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
public class CommunicationActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge);
        ButterKnife.bind(this);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        int position=getIntent().getIntExtra("activity",-1);
        switch (position) {
            case SomeConstant.COMMUNICATION_LIFE:
                title.setText("生活交流");
                break;
            case SomeConstant.COMMUNICATION_TECHNIQUE:
                title.setText("技术交流");
                break;
        }
        initAdapter(position);
    }


    private void initAdapter(int position) {
        final HashMap<String, String> paramsMapTwo = ParamBuilder.buildParam().append("page", "1").append("rows", "3").append("category",String.valueOf(position)).toHashMap();
        RequestHelper.sendAsyncRequest( this, UrlConstants.WENZHANGYULAN, paramsMapTwo, new RequestCallback() {
            @Override
            public void onSuccess(ResponseEntity response) {
                JSONArray array=null;
                if (response.isSuccess()) {
                    array = response.getDataObject().optJSONArray("list");
                } else
                    Toast.makeText(CommunicationActivity.this, response.getErrorMessage(), Toast.LENGTH_SHORT).show();
                RecyclerView fineView = (RecyclerView)findViewById(R.id.rec_fine_article);
                FineArticleAdapter fineAdapter = new FineArticleAdapter(CommunicationActivity.this, 6,array);
                fineView.setAdapter(fineAdapter);
                fineView.setLayoutManager(new LinearLayoutManager(CommunicationActivity.this));
                fineView.addItemDecoration(new DivideItemDecoratior(CommunicationActivity.this, DivideItemDecoratior.VERTICAL_LIST));
            }
            @Override
            public void onFailure(Exception ex) {
                ex.printStackTrace();
            }
            @Override
            public void onComplete() {
            }
        });
    }
   /* @OnClick({R.id.btn_charge_complete,R.id.ibt_choose_zhifubao,R.id.ibt_choose_weixin})
    public void chargeComplete(View view){
        switch (view.getId()){
            case R.id.btn_charge_complete:
                Toast.makeText(CommunicationActivity.this, "点击完成", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ibt_choose_zhifubao:
                changeImage(ibtZhifubao);
                break;
            case R.id.ibt_choose_weixin:
                changeImage(ibtWeixin);
                break;
        }
    }*/


}
