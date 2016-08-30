package com.appheader.DingQuPostman.ui.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appheader.DingQuPostman.R;
import com.appheader.DingQuPostman.application.MyApplication;
import com.appheader.DingQuPostman.ui.main.activity.AlreadySaveActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;


public class AlreadySaveDetailAdapter extends RecyclerView.Adapter<AlreadySaveDetailAdapter.NormalTextViewHolder> {
    private Context context;
    private NormalTextViewHolder normalTextViewHolder;
    private JSONArray arrayData;

    public AlreadySaveDetailAdapter(JSONArray arrayData) {
        this.context = MyApplication.getContext();
        this.arrayData = arrayData;
    }

    //获取视图
    @Override
    public NormalTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return normalTextViewHolder = new NormalTextViewHolder(LayoutInflater.from(
                context).inflate(R.layout.view_already_save_detail_data, parent,
                false));
    }

    //绑定数据
    @Override
    public void onBindViewHolder(NormalTextViewHolder holder, int position) {
               /* holder.txtTel.setText(arrayData.optString("receiver_phone"));
                holder.txtExNum.setText(arrayData.optString("express_number"));*/
                holder.txtPosition.setText("团结路津工超市");


    }

    //数据数量
    @Override
    public int getItemCount() {
        return arrayData.length();
    }

    //数据缓存
    public class NormalTextViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_call_phone)
        ImageView ivCallPhone;
        @Bind(R.id.txt_express_id)
        TextView  txtExNum;
        @Bind(R.id.txt_telephone)
        TextView txtTel;
        @Bind(R.id.txt_position)
        TextView txtPosition;
        public NormalTextViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ivCallPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "拨打电话", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
