package com.appheader.DingQuPostman.ui.main.adapter;


import android.app.Activity;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appheader.DingQuPostman.R;
import com.appheader.DingQuPostman.application.GlobalVars;
import com.appheader.DingQuPostman.common.activity.ActivityManager;
import com.appheader.DingQuPostman.common.activity.BaseActivity;
import com.appheader.DingQuPostman.common.network.RequestCallback;
import com.appheader.DingQuPostman.common.network.RequestHelper;
import com.appheader.DingQuPostman.common.network.ResponseEntity;
import com.appheader.DingQuPostman.common.network.UrlConstants;
import com.appheader.DingQuPostman.common.utils.ParamBuilder;
import com.appheader.DingQuPostman.ui.main.myinterface.SomeConstant;
import com.appheader.DingQuPostman.user.CurrentUserManager;
import com.appheader.DingQuPostman.user.UserInfo;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/6/29 0029.
 */
public class MainUIAdapter extends PagerAdapter {
    private final UserInfo userInfo;
    private Activity currentActivity;
    private List<View> lvTabView;
    private TabLayout tabLayout;
    private static final String TAG = "MainUIAdapter";
    private boolean isInitHomepage = false;//保证待存件只被初始化一次
    private boolean isInitAboutUs = false;//保证已存件只被初始化一次
    private boolean isInitEach = false;//保证已取件只被初始化一次
    private int[] tabImages = {R.mipmap.ic_home_white_36dp, R.mipmap.ic_people_outline_white_36dp, R.mipmap.ic_repeat_white_36dp};
    private String[] tabTitles = {"首页", "关于我们", "互动学习"};
   /* Observable opretionObservable=Observable.create(new Observable.OnSubscribe<Subscriber>() {
        @Override
        public void call(Subscriber subscriber) {
            subscriber.onNext(longRunningOperation());
            subscriber.onCompleted();
        }
    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());*/
    Subscriber<String> subscriber = new Subscriber<String>() {
        @Override
        public void onNext(String s) {
            Log.d(TAG, "Item: " + s);
        }

        @Override
        public void onCompleted() {
            Log.d(TAG, "Completed!");
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, "Error!");
        }
    };
    public MainUIAdapter(List<View> lvTabView, TabLayout tabLayout, BaseActivity activity) {
        this.lvTabView = lvTabView;
        currentActivity = ActivityManager
                .getInstance().currentActivity();
        this.tabLayout = tabLayout;
        userInfo = CurrentUserManager.getCurrentUser();
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(lvTabView.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(lvTabView.get(position));
        View view = lvTabView.get(position);
        switch (position) {
            case 0:
                initHomePageView(view);
                TextView txtMoreUse= (TextView) view.findViewById(R.id.txt_more_use);
                txtMoreUse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(currentActivity, "更多体验", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case 1:
                initAboutUSView(view);
                TextView txtMoreServer= (TextView) view.findViewById(R.id.txt_more_server);
                txtMoreServer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(currentActivity, "更多人员", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case 2:
                initEachCommunication(view);

        }
        return view;
    }


    //初始化首页界面
    private void initHomePageView(View view) {
        if (!isInitHomepage) {//设置只让该页加载一次
            //初始化轮播图

            //初始化用电业务
            RecyclerView recView = (RecyclerView) view.findViewById(R.id.rec_use_electricity_business);
            String [] useEleItemName={"高压新装","低压居民新装","个人充电桩","企业充电桩","低压非居民新装",
                    "个人分布式电源","企业分布式电源","装表临时用电","高压增容","低压非居民增容"};
            int [] useEleItemIcon={R.mipmap.ic_high_press_add,R.mipmap.ic_resident_add,R.mipmap.ic_personal_charge,R.mipmap.ic_company_power
            ,R.mipmap.ic_no_resident_add,R.mipmap.ic_personal_power,R.mipmap.ic_company_charge,R.mipmap.ic_use_electricity_casual,
                    R.mipmap.ic_high_press_volume,R.mipmap.ic_no_resident_add_volume};
            UseEleRecyclerAdapter useEle = new UseEleRecyclerAdapter(useEleItemName,useEleItemIcon, SomeConstant.USE_ELECTRICITY_BUSINESS, currentActivity);
            recView.setAdapter(useEle);
            recView.setLayoutManager(new GridLayoutManager(currentActivity, 4));
            recView.addItemDecoration(new DividerGridItemDecoration(currentActivity));
            //初始化服务导航
            String[] serNavName={"业务流程","信息公开","电力百科","服务指南"};
            int[] serNavIcon={R.mipmap.ic_business_flow,R.mipmap.ic_message_display,R.mipmap.ic_eletricity_baike,R.mipmap.ic_serve_navigation};
            RecyclerView serNav = (RecyclerView) view.findViewById(R.id.rec_server_navigation);
            UseEleRecyclerAdapter serNavAdapter = new UseEleRecyclerAdapter(serNavName,serNavIcon,SomeConstant.SERVE_NAVIGATION, currentActivity);
            serNav.setAdapter(serNavAdapter);
            serNav.setLayoutManager(new GridLayoutManager(currentActivity, 4));
            //初始化综合查询
            int[] allSearchIcon={R.mipmap.ic_net_search,R.mipmap.ic_shop_info_search};
            String[] allSearchName={"网点查询","营业厅信息查询"};
            RecyclerView allSearch = (RecyclerView) view.findViewById(R.id.rec_power_search);
            UseEleRecyclerAdapter allSearchAdapter = new UseEleRecyclerAdapter(allSearchName,allSearchIcon,SomeConstant.ALL_SEARCH, currentActivity);
            allSearch.setAdapter(allSearchAdapter);
            allSearch.setLayoutManager(new GridLayoutManager(currentActivity, 2));
            allSearch.addItemDecoration(new DividerGridItemDecoration(currentActivity));
            isInitHomepage = true;
        }
    }



    //初始化关于我们界面
    private void initAboutUSView(View view) {
        if (userInfo!=null&&Integer.parseInt(userInfo.getPermission())==1) {
            LinearLayout llCompanyInside= (LinearLayout) view.findViewById(R.id.ll_company_inside);
            llCompanyInside.setVisibility(View.VISIBLE);
        }
        //初始化关于我们界面
        if (!isInitEach) {
            //初始化服务之星
            RecyclerView serveStarView = (RecyclerView) view.findViewById(R.id.rec_serve_star);
            ServeStarAdapter serStarAdapter = new ServeStarAdapter(4, currentActivity);
            serveStarView.setAdapter(serStarAdapter);
            serveStarView.setLayoutManager(new GridLayoutManager(currentActivity, 2));
            serveStarView.addItemDecoration(new DividerGridItemDecoration(currentActivity));
            //初始化温馨小家
            String[] warmHomeName={"1","2","3","4"};
            RecyclerView warmHomeView = (RecyclerView) view.findViewById(R.id.rec_warm_home);
            UseEleRecyclerAdapter warmHomeAdapter = new UseEleRecyclerAdapter(warmHomeName,null,SomeConstant.WARM_HOME, currentActivity);
            warmHomeView.setAdapter(warmHomeAdapter);
            warmHomeView.setLayoutManager(new GridLayoutManager(currentActivity, 2));
            warmHomeView.addItemDecoration(new DividerGridItemDecoration(currentActivity));
            //初始化交流沟通
            String[] comViewName={"技术交流","生活交流"};
            RecyclerView comView = (RecyclerView) view.findViewById(R.id.rec_communication);
            int [] comViewIcon={R.mipmap.ic_com_technoloy,R.mipmap.ic_com_live};
            UseEleRecyclerAdapter allSearchAdapter = new UseEleRecyclerAdapter(comViewName,comViewIcon,SomeConstant.COMMUNICATION_EACH, currentActivity);
            comView.setAdapter(allSearchAdapter);
            comView.setLayoutManager(new GridLayoutManager(currentActivity, 2));
            comView.addItemDecoration(new DividerGridItemDecoration(currentActivity));
            isInitEach = true;
        }
    }

    private void initEachCommunication(final View view) {
        //!GlobalVars.getIsInitAboutUs()
        if (true) {
            //初始化活动通知
            final List<Map<String, String>> notList = new ArrayList<>();
            final HashMap<String, String> paramsMap = ParamBuilder.buildParam().append("page", "1").append("rows", "3").toHashMap();
            RequestHelper.sendAsyncRequest((BaseActivity) currentActivity, UrlConstants.TONGZHIYULAN, paramsMap, new RequestCallback() {
                @Override
                public void onSuccess(ResponseEntity response) {
                    Log.d(TAG, "onSuccess: " + response);
                    if (response.isSuccess()) {
                        JSONArray array = response.getDataObject().optJSONArray("list");
                        if (array.length() == 0) {
                            Map<String, String> map = new HashMap<>();
                            map.put("notification", "暂无通知");
                            map.put("kind", "");
                            notList.add(map);
                        } else {
                            for (int i = 0; i < array.length(); i++) {
                                Map<String, String> map = new HashMap<>();
                                map.put("notification", array.optJSONObject(i).optString("title"));
                                map.put("kind", "new");
                                notList.add(map);
                            }
                        }
                    } else
                        Toast.makeText(currentActivity, response.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    RecyclerView actView = (RecyclerView) view.findViewById(R.id.rec_activity_notification);
                    ActivityNotificationAdapter actAdapter = new ActivityNotificationAdapter(currentActivity, notList);
                    actView.setAdapter(actAdapter);
                    actView.setLayoutManager(new LinearLayoutManager(currentActivity));
                }
                @Override
                public void onFailure(Exception ex) {
                    ex.printStackTrace();
                }
                @Override
                public void onComplete() {

                }
            });
            //初始化精品帖子
            final HashMap<String, String> paramsMapTwo = ParamBuilder.buildParam().append("page", "1").append("rows", "3").append("category",String.valueOf(SomeConstant.EACH_COMMUNICATE)).toHashMap();
            RequestHelper.sendAsyncRequest((BaseActivity) currentActivity, UrlConstants.WENZHANGYULAN, paramsMapTwo, new RequestCallback() {
                @Override
                public void onSuccess(ResponseEntity response) {
                    Log.d(TAG, "onSuccess: " + response);
                    JSONArray array=null;
                    if (response.isSuccess()) {
                       array = response.getDataObject().optJSONArray("list");
                    } else
                        Toast.makeText(currentActivity, response.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    RecyclerView fineView = (RecyclerView) view.findViewById(R.id.rec_fine_article);
                    FineArticleAdapter fineAdapter = new FineArticleAdapter(currentActivity, 6,array);
                    fineView.setAdapter(fineAdapter);
                    fineView.setLayoutManager(new LinearLayoutManager(currentActivity));
                    fineView.addItemDecoration(new DivideItemDecoratior(currentActivity, DivideItemDecoratior.VERTICAL_LIST));
                }
                @Override
                public void onFailure(Exception ex) {
                    ex.printStackTrace();
                }
                @Override
                public void onComplete() {

                }
            });

            GlobalVars.setIsInitAboutUs(true);
        }
    }

    //初始化tab视图
    public View getTabView(int position) {
        View tabView = LayoutInflater.from(currentActivity).inflate(R.layout.tab_item, null);
        ImageView imageView = (ImageView) tabView.findViewById(R.id.imageView);
        TextView tv = (TextView) tabView.findViewById(R.id.textView);
        if (position == 0) {
            imageView.setColorFilter(Color.parseColor("#006F6B"));
            tv.setTextColor(Color.parseColor("#006F6B"));
        }
        imageView.setImageResource(tabImages[position]);
        tv.setText(tabTitles[position]);
        return tabView;
    }


}
