/*
package com.appheader.DingQuPostman.ui.main.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appheader.DingQuPostman.R;
import com.appheader.DingQuPostman.application.MyApplication;
import com.appheader.DingQuPostman.common.activity.BaseActivity;
import com.appheader.DingQuPostman.ui.main.myinterface.GetExpressListener;
import com.appheader.DingQuPostman.user.CurrentUserManager;
import com.appheader.DingQuPostman.user.UserInfo;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.Date;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler2;

*/
/**
 * Created by Administrator on 2016/7/18 0018.
 *//*

public class MyUtils {
    private static final String TAG = "MyUtils";
    private static Context context=MyApplication.getContext();
    private static UserInfo userInfo= CurrentUserManager.getCurrentUser();

    //下拉刷新
    public static void pullToRef(final MyApplication myApplication, final BaseActivity activity, final PtrClassicFrameLayout ptrFrame, final int status, final int page, final RecyclerView.Adapter adapter) {
        //初始化头布局与脚布局

        final LinearLayout footView= (LinearLayout) LayoutInflater.from(context).inflate(R.layout.layout_refresh_foot,null);
        final RelativeLayout headView= (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.view_refresh_header,null);
        ptrFrame.setFooterView(footView);
        ptrFrame.setHeaderView(headView);
        //记录上次刷新时间
        final TextView txtTime= (TextView) headView.findViewById(R.id.updated_at);
        //描述状态
        final TextView des= (TextView) headView.findViewById(R.id.description);
        //刷新箭头
        final ImageView arrow=(ImageView)headView.findViewById(R.id.arrow);
        //格式化时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd.hh.mm");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        final String str = formatter.format(curDate);

        //对刷新状态进行监听
        ptrFrame.setPtrHandler(new PtrHandler2() {//需要上拉加载 new PtrHandler2,不需要new PtrHandler
            @Override
            public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
                return true;
            }

            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                // TODO: 16/4/test_two 加载更多网络数据
                Log.d(TAG, "onLoadMoreBegin: 下拉加载更多");

                ptrFrame.refreshComplete();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {//如果返回true,可以下拉,如果返回false 则没有下来
                Log.d(TAG, "checkCanDoRefresh: "+header.getHeight()+"\n"+frame.getY()+"\n"+frame.getPivotY());
                frame.getOffsetToKeepHeaderWhileLoading();
                while (frame.getHeaderHeight()<=header.getHeight()){
                    des.setText("下拉刷新");
                    return true;
                }
                des.setText("松开刷新");
                return true;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                // TODO: 16/4/test_two 下拉刷新获取网络数据
                des.setText("正在刷新");
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        userInfo.getExpressData(activity, status, 10, 1, new GetExpressListener() {
                            @Override
                            public void getSuccess(JSONArray arr) {
                                switch (status) {
                                    case 1:
                                        String lastWtReTime=userInfo.getWtTime();
                                        if (lastWtReTime!=null){
                                            txtTime.setVisibility(View.VISIBLE);
                                            txtTime.setText(str);
                                        }
                                       // myApplication.getWaitTakeAdapter().refreshData(arr);
                                        break;
                                    case 100:
                                        break;
                                    case 200:
                                        String lastAlReTime=userInfo.getWtTime();
                                        if (lastAlReTime!=null){
                                            txtTime.setVisibility(View.VISIBLE);
                                            txtTime.setText(str);
                                        }
                                        myApplication.getAlreadyTakeAdapter().refreshData(arr);
                                        break;
                                }
                                userInfo.setWtTime(str);

                            }
                        });
                    }
                },1500);
                ptrFrame.refreshComplete();
                des.setText("刷新完成");

            }
        });

    }
}
*/
