package com.appheader.DingQuPostman.ui.main.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.appheader.DingQuPostman.R;
import com.appheader.DingQuPostman.common.activity.BaseActivity;
import com.appheader.DingQuPostman.ui.main.adapter.ServiceNavAdapter;
import com.appheader.DingQuPostman.ui.main.myinterface.SomeConstant;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ServiceNavActivity extends BaseActivity {
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    //tab上的text
    private List<String> tabTitles;
    //viewPager中的视图
    private List<Map<String,Object>> lvDataView;
    private List<String> urlList;//保存webviewUrl
    //private int ;
    private ServiceNavAdapter maAdapter;
    private static final String TAG = "ServiceNavActivity";
    private JSONArray arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_navigation);
        ButterKnife.bind(this);
        //初始化toolbar
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("服务导航");
        initView();
    }

    private void initView() {
        //填充viewpager中的视图
        lvDataView = new ArrayList<>();
        tabTitles = new ArrayList<>();
        urlList=new ArrayList<>();
        int position = getIntent().getIntExtra("serveNav", -1);
        switch (position) {
            case 0:
                tabTitles.add("高压新装");
                tabTitles.add("高压增容");
                urlList.add(SomeConstant.HIGH_PRESS_NEW_ADD);
                urlList.add(SomeConstant.HIGH_PRESS_ADD_VOLUME);
                break;
            case 1:
                tabTitles.add("公司介绍");
                tabTitles.add("服务承诺");
                tabTitles.add("收费标准");
                tabTitles.add("政策法规");
                urlList.add(SomeConstant.COMPANY_INTRODUCE);
                urlList.add(SomeConstant.SERVICE_PROMISE);
                urlList.add(SomeConstant.CHARGE_STANDARD);
                urlList.add(SomeConstant.POLICY_REGULATIONS);
                break;
            case 2:
                tabTitles.add("安全用电");
                tabTitles.add("节能常识");
                tabTitles.add("阶梯电价");
                tabTitles.add("业务指南");
                urlList.add(SomeConstant.SAFE_ELECTRICITY);
                urlList.add(SomeConstant.ENERY_SAVING);
                urlList.add(SomeConstant.STEP_PRICE);
                urlList.add(SomeConstant.BUSINESS_GUIDE);
                break;
            case 3:
                tabTitles.add("便民服务指南");
                tabTitles.add("365服务");
                tabTitles.add("能效服务");
                tabTitles.add("电动汽车服务");
                urlList.add(SomeConstant.SERVICE_INFORMATION);
                urlList.add(SomeConstant.UNDERSTAND_365_SERVICE);
                urlList.add(SomeConstant.ENERY_SAVING);
                urlList.add(SomeConstant.VEHICLE_SERVICE);
                break;
        }
        for (int i = 0; i < tabTitles.size(); i++) {
            Map map=new HashMap();
            map.put("view",LayoutInflater.from(this).inflate(R.layout.view_web_service, null));
            map.put("url",urlList.get(i));
            lvDataView.add(map);
        }
        maAdapter = new ServiceNavAdapter(this, tabTitles, lvDataView);
        viewPager.setAdapter(maAdapter);
        //绑定viewpager并且设置其滚动模式
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //加载自定义tabView
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(maAdapter.getTabView(i));

        }
        //初始化tablayout
        TabLayout.Tab tab = tabLayout.getTabAt(0);
        viewPager.setCurrentItem(tab.getPosition());
        TextView tx = (TextView) tab.getCustomView().findViewById(R.id.tabLayout_account_title);
        tx.setBackgroundResource(R.drawable.tab_account_bg);
        ImageView iv = (ImageView) tab.getCustomView().findViewById(R.id.img_checked);
        iv.setVisibility(View.VISIBLE);
        tx.setTextColor(Color.WHITE);
        //对tablayout进行监听动态改变tablayout中的布局

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //选中那个tab就跳到那个tab对应的page下并改变tab布局
                viewPager.setCurrentItem(tab.getPosition());
                TextView tx = (TextView) tab.getCustomView().findViewById(R.id.tabLayout_account_title);
                tx.setBackgroundResource(R.drawable.tab_account_bg);
                ImageView iv = (ImageView) tab.getCustomView().findViewById(R.id.img_checked);
                iv.setVisibility(View.VISIBLE);
                tx.setTextColor(Color.WHITE);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView tx = (TextView) tab.getCustomView().findViewById(R.id.tabLayout_account_title);
                tx.setBackgroundResource(R.drawable.btn_null_bg);
                tx.getBackground().setAlpha(0);
                ImageView iv = (ImageView) tab.getCustomView().findViewById(R.id.img_checked);
                iv.setVisibility(View.INVISIBLE);
                tx.setTextColor(Color.GRAY);
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}

