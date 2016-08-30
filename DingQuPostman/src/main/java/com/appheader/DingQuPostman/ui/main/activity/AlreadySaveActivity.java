package com.appheader.DingQuPostman.ui.main.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.appheader.DingQuPostman.R;
import com.appheader.DingQuPostman.common.activity.BaseActivity;
import com.appheader.DingQuPostman.common.network.RequestCallback;
import com.appheader.DingQuPostman.common.network.RequestHelper;
import com.appheader.DingQuPostman.common.network.ResponseEntity;
import com.appheader.DingQuPostman.common.network.UrlConstants;
import com.appheader.DingQuPostman.common.utils.ParamBuilder;
import com.appheader.DingQuPostman.ui.main.adapter.AlreadySaveAdapter;
import com.appheader.DingQuPostman.ui.main.adapter.AlreadySaveDetailAdapter;
import com.appheader.DingQuPostman.ui.main.adapter.AlreadyTakeAdapter;
//import com.appheader.DingQuPostman.ui.main.adapter.WaitTakeAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AlreadySaveActivity extends BaseActivity {
    @Bind(R.id.rec_detail_already_take)RecyclerView recAlTake;
    private static final String TAG = "AlreadySaveActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_already_save);
        ButterKnife.bind(this);
        title.setText("已存件");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //已存件
        HashMap<String,String> wtTakeMap= ParamBuilder.buildParam().append("postmanId","577365b1483cc3044b68b795").append("status","200").append("pageSize","10").append("pagenum","1").toHashMap();
        RequestHelper.sendAsyncRequest(this, UrlConstants.GET_PACKAGE_LIST, wtTakeMap, new RequestCallback() {
            @Override
            public void onSuccess(ResponseEntity response) {
                if (response.isSuccess()) {
                    JSONArray arr = response.getDataObject().optJSONArray("list");
                    Log.d(TAG, "onSuccess:已取件数据 "+arr);
                    AlreadySaveDetailAdapter wtAdapter = new AlreadySaveDetailAdapter(arr);
                    recAlTake.setAdapter(wtAdapter);//控制recyclerV显示的内容和数据iew
                    recAlTake.setLayoutManager(new LinearLayoutManager(AlreadySaveActivity.this));

                }
            }
            @Override
            public void onFailure(Exception ex) {
                Log.d(TAG, "onFailure: "+ex);
            }

            @Override
            public void onComplete() {

            }
        });

    }
}
