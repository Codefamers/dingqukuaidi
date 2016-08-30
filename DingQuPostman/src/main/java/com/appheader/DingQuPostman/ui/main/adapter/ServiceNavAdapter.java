package com.appheader.DingQuPostman.ui.main.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;


import com.appheader.DingQuPostman.R;
import com.appheader.DingQuPostman.common.activity.BaseActivity;
import com.appheader.DingQuPostman.common.network.RequestCallback;
import com.appheader.DingQuPostman.common.network.RequestHelper;
import com.appheader.DingQuPostman.common.network.ResponseEntity;
import com.appheader.DingQuPostman.common.network.UrlConstants;
import com.appheader.DingQuPostman.common.utils.ParamBuilder;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/29 0029.
 */
public class ServiceNavAdapter extends PagerAdapter {
    private List<String> tabTitles;
    private Activity context;
    List<Map<String,Object>> lvDataView;
    //private WaitTakeAdapter wtAdapter;
    private AllConsumeRecordAdapter allConsumeAdapter;
    // private JSONArray arrayData;
    private static final String TAG = "ServiceNavAdapter";
    private WebView web;

    public ServiceNavAdapter(Context context, List<String> tabTitles, List<Map<String,Object>> lvDataView ) {
        this.tabTitles=tabTitles;
        this.lvDataView=lvDataView;
        this.context= (Activity) context;
    }

    @Override

    public int getCount() {
        return tabTitles.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) lvDataView.get(position).get("view"));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView((View) lvDataView.get(position).get("view"));
        View view=(View) lvDataView.get(position).get("view");
        web= (WebView) view.findViewById(R.id.web);
        initWeb((String) lvDataView.get(position).get("url"));
        return view;
    }

    private void initWeb(String url) {
        WebSettings settings=web.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        web.setWebViewClient(new WebViewClient());
        //abWeb.setWebChromeClient(new WebChromeClient());
        Log.d(TAG, "initWeb: "+url);
        web.loadUrl(url);
    }

    public View getTabView(int position) {
        View tabView = LayoutInflater.from(context).inflate(R.layout.tab_account_item, null);
        TextView tv = (TextView) tabView.findViewById(R.id.tabLayout_account_title);
        tv.setText(tabTitles.get(position));

        return tabView;
    }

}
