package com.appheader.DingQuPostman.ui.main.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.appheader.DingQuPostman.R;
import com.appheader.DingQuPostman.application.MyApplication;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/2 0002.
 */
class ActivityNotificationAdapter extends RecyclerView.Adapter<ActivityNotificationAdapter.MyViewHolder> {

    private List<Map<String,String>> notificationList;
    private Activity activity;

    public ActivityNotificationAdapter(Activity activity, List<Map<String,String>> notificationList) {
        this.notificationList = notificationList;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                MyApplication.getContext()).inflate(R.layout.item_activity_notification, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
         holder.notTitle.setText(notificationList.get(position).get("notification"));
            holder.notKind.setText(notificationList.get(position).get("kind"));

    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.txt_notification_title)
        TextView notTitle;
        @Bind(R.id.txt_notification_kind)
        TextView notKind;

        public MyViewHolder(View view) {
            super(view);
            //tv = (TextView) view.findViewById(R.id.id_num);
            ButterKnife.bind(this,view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(activity, "通知"+getAdapterPosition(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}


