package com.appheader.DingQuPostman.ui.main.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appheader.DingQuPostman.R;
import com.appheader.DingQuPostman.application.MyApplication;
import com.appheader.DingQuPostman.ui.main.activity.AboutActivity;
import com.appheader.DingQuPostman.ui.main.activity.ApplyBusinessActivity;
import com.appheader.DingQuPostman.ui.main.activity.CommunicationActivity;
import com.appheader.DingQuPostman.ui.main.activity.LoginActivity;
import com.appheader.DingQuPostman.ui.main.activity.MainActivity;
import com.appheader.DingQuPostman.ui.main.activity.ServiceNavActivity;
import com.appheader.DingQuPostman.ui.main.myinterface.SomeConstant;
import com.appheader.DingQuPostman.user.CurrentUserManager;
import com.appheader.DingQuPostman.user.UserInfo;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/2 0002.
 */
class UseEleRecyclerAdapter extends RecyclerView.Adapter<UseEleRecyclerAdapter.MyViewHolder> {

    private String[] itemName;//保存名称
    private int[] itemIcon;//保存图标
    private Activity activity;
    private static final String TAG = "UseEleRecyclerAdapter";
    private int flagParent = -1;
    private Context context;
    public UseEleRecyclerAdapter(String[] itemName, int[] itemIcon, int flagParent, Activity activity) {
        this.itemName = itemName;
        this.itemIcon = itemIcon;
        this.flagParent = flagParent;
        this.activity = activity;
        context=MyApplication.getContext();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                MyApplication.getContext()).inflate(R.layout.item_use_electricity_business, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + holder.name);
        holder.name.setText(itemName[position]);
        Log.d(TAG, "onBindViewHolder: " + itemName);
        if (itemIcon!=null){
            holder.icon.setImageResource(itemIcon[position]);
        }

    }

    @Override
    public int getItemCount() {
        return itemName.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txt_item_name)
        TextView name;
        @Bind(R.id.img_item_icon)
        ImageView icon;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (flagParent) {
                        case SomeConstant.USE_ELECTRICITY_BUSINESS:
                            UserInfo userInfo= CurrentUserManager.getCurrentUser();
                            if (userInfo!=null)
                                dealUseEleBusiness();
                            else
                                activity.startActivity(new Intent(activity, LoginActivity.class));
                            break;
                        case SomeConstant.SERVE_NAVIGATION:
                            dealServeNavigation();
                            break;
                        case SomeConstant.ALL_SEARCH:
                            dealAllSearch();
                            break;
                        case SomeConstant.WARM_HOME:
                            dealWarmHome();
                            break;
                        case SomeConstant.COMMUNICATION_EACH:
                            dealComEach();
                            break;
                        default:
                            Toast.makeText(activity, "逻辑出错", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });
        }

        private void dealComEach() {
            Intent in=new Intent(activity, CommunicationActivity.class);
            switch (getAdapterPosition()) {
                case 0:
                   // Toast.makeText(activity, "技术交流", Toast.LENGTH_SHORT).show();
                    in.putExtra("activity",SomeConstant.COMMUNICATION_TECHNIQUE);
                    break;
                case 1:
                    //Toast.makeText(activity, "生活交流", Toast.LENGTH_SHORT).show();
                    in.putExtra("activity",SomeConstant.COMMUNICATION_LIFE);
                    break;
            }
            activity.startActivity(in);
        }

        private void dealWarmHome() {
            Toast.makeText(activity, "点击温馨小家", Toast.LENGTH_SHORT).show();
        }

        private void dealAllSearch() {
            Intent in=new Intent(activity, AboutActivity.class);
            switch (getAdapterPosition()) {
                case 0:
                    Toast.makeText(activity, "停电查询", Toast.LENGTH_SHORT).show();
                    in.putExtra("activity",SomeConstant.NO_POWER_SEARCH_ACTIVITY);
                    break;
                case 1:
                    Toast.makeText(activity, "营业厅信息查询", Toast.LENGTH_SHORT).show();
                    in.putExtra("activity",SomeConstant.NET_INFOMATION_SEARCH_ACTIVITY);
                    break;
            }
            activity.startActivity(in);
        }
        private void dealServeNavigation() {
            Intent in=new Intent(context, ServiceNavActivity.class);
            switch (getAdapterPosition()) {
                case 0:
                    Toast.makeText(activity, "业务流程", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(activity, "信息公开", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(activity, "电力百科", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(activity, "服务指南", Toast.LENGTH_SHORT).show();
                    break;
            }
            in.putExtra("serveNav",getAdapterPosition());
            in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(in);
        }

        private void dealUseEleBusiness() {
            Intent in=new Intent(context, ApplyBusinessActivity.class);
            in.putExtra("business",getAdapterPosition());
            in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(in);
        }
    }
}

/*switch (getAdapterPosition()) {
        case 0:
        //  Toast.makeText(activity, "高压新装", Toast.LENGTH_SHORT).show();

        break;
        case 1:
        //Toast.makeText(activity, "低压居民新装", Toast.LENGTH_SHORT).show();
        break;
        case 2:
        // Toast.makeText(activity, "个人充电桩", Toast.LENGTH_SHORT).show();

        break;
        case 3:
        //Toast.makeText(activity, "企业充电桩", Toast.LENGTH_SHORT).show();

        break;
        case 4:
        //  Toast.makeText(activity, "低压非居民新装", Toast.LENGTH_SHORT).show();

        break;
        case 5:
        Toast.makeText(activity, "个人分布式电源", Toast.LENGTH_SHORT).show();

        break;
        case 6:
        Toast.makeText(activity, "企业分布式电源", Toast.LENGTH_SHORT).show();

        break;
        case 7:
        Toast.makeText(activity, "装表临时用电", Toast.LENGTH_SHORT).show();

        break;
        case 8:
        Toast.makeText(activity, "高压增容", Toast.LENGTH_SHORT).show();

        break;
        case 9:
        Toast.makeText(activity, "低压非居民增容", Toast.LENGTH_SHORT).show();
        break;
        }*/
