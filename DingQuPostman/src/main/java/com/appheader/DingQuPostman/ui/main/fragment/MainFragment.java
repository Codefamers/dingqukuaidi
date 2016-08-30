
package com.appheader.DingQuPostman.ui.main.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appheader.DingQuPostman.R;
import com.appheader.DingQuPostman.application.GlobalVars;
import com.appheader.DingQuPostman.application.MyApplication;
import com.appheader.DingQuPostman.common.activity.BaseActivity;
import com.appheader.DingQuPostman.ui.common.qrcode.CaptureActivity;
import com.appheader.DingQuPostman.ui.main.activity.LoginActivity;
import com.appheader.DingQuPostman.ui.main.activity.PublishNotificationActivity;
import com.appheader.DingQuPostman.ui.main.activity.PublishTextActivity;
import com.appheader.DingQuPostman.ui.main.adapter.MainUIAdapter;
import com.appheader.DingQuPostman.user.CurrentUserManager;
import com.appheader.DingQuPostman.user.UserInfo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainFragment extends Fragment {
    private static final String TAG = "AdministratorFragment";
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private MainUIAdapter muAdapter;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.img_toolbar_navigation)
    ImageView toolbarNai;
    @Bind(R.id.img_toolbar_scan)
    ImageView tlNotification;
    private List<View> lvTabView;
    private Context context;
    private DrawerLayout drawer;
    private View tabView;
    private UserInfo userInfo;
    private BaseActivity baseActivity;
    private MyApplication myApp;
    private Object resImageId;
    public MainFragment() {
        this.context = MyApplication.getContext();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userInfo = CurrentUserManager.getCurrentUser();
        baseActivity = (BaseActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);
        ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    private void initData() {

    }

    private void initView() {
        if (userInfo!=null&&Integer.parseInt(userInfo.getPermission())==1) {
            tlNotification.setVisibility(View.VISIBLE);
        }
        initToolBar();
        initMainUi();
    }

    private void initMainUi() {
        //向viewpager中填充布局
        lvTabView = new ArrayList<>();
        lvTabView.add(LayoutInflater.from(context).inflate(R.layout.view_home_page, null));
        lvTabView.add(LayoutInflater.from(context).inflate(R.layout.view_about_us, null));
        lvTabView.add(LayoutInflater.from(context).inflate(R.layout.view_each_communication, null));
        muAdapter = new MainUIAdapter(lvTabView, tabLayout, (BaseActivity) getActivity());
        viewPager.setAdapter(muAdapter);
        tabLayout.setupWithViewPager(viewPager);
        //设置table
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //加载自定义tabView
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(muAdapter.getTabView(i));
        }
        //对tablayout进行监听动态改变tablayout中的布局
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //选中那个tab就跳到那个tab对应的page下并改变tab布局
                viewPager.setCurrentItem(tab.getPosition());
                ImageView img = (ImageView) tab.getCustomView().findViewById(R.id.imageView);
                img.setColorFilter(Color.parseColor("#006F6B"));
                TextView tx = (TextView) tab.getCustomView().findViewById(R.id.textView);
                tx.setTextColor(Color.parseColor("#006F6B"));//后期可能重构
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ImageView img = (ImageView) tab.getCustomView().findViewById(R.id.imageView);
                img.setColorFilter(Color.GRAY);
                TextView tx = (TextView) tab.getCustomView().findViewById(R.id.textView);
                tx.setTextColor(Color.rgb(0,0,0));//后期可能重构
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //让主页面随着侧边栏的滑动而滑动
        final FrameLayout relay = (FrameLayout) getActivity().findViewById(R.id.ml_fragment);
        drawer.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                float xDuration = (getResources().getDimension(R.dimen.x250) * slideOffset);//侧边栏滑动距离
                relay.scrollTo(-(int) xDuration, 0);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
    }

    //初始化状态栏布局
    private void initToolBar() {
        //初始化tab栏布局
        tabView = LayoutInflater.from(context).inflate(R.layout.tab_item, null);
        drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer);
        //除去主页面的阴影
        drawer.setScrimColor(0x00000000);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }

        });
        //圆角图片
        resImageId=userInfo==null?R.mipmap.ic_status_bar_photo: GlobalVars.getAppServerUrl()+userInfo.getHeadImg();
        Glide.with(this).load(resImageId).asBitmap().centerCrop().into(new BitmapImageViewTarget(toolbarNai){
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                toolbarNai.setImageDrawable(circularBitmapDrawable);
            }
        });
        //用户姓名

    }

    @OnClick({R.id.img_toolbar_scan, R.id.img_toolbar_search, R.id.img_toolbar_navigation})
    public void toolBarOnclick(View view) {
        switch (view.getId()) {
            case R.id.img_toolbar_navigation:
                drawer.openDrawer(Gravity.LEFT);
                break;
            case R.id.img_toolbar_search:
                if (userInfo!=null) {
                    Intent search = new Intent(context, PublishTextActivity.class);
                    startActivity(search);
                }else {
                    Intent search = new Intent(context, LoginActivity.class);
                    startActivity(search);
                }

                break;
            case R.id.img_toolbar_scan:
                Intent in = new Intent(context, PublishNotificationActivity.class);
                startActivity(in);
                break;
        }
    }
}
