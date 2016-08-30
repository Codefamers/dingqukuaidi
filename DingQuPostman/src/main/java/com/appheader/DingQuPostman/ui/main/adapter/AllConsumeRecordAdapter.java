package com.appheader.DingQuPostman.ui.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appheader.DingQuPostman.R;
import com.appheader.DingQuPostman.application.MyApplication;

import org.json.JSONArray;

import butterknife.Bind;
import butterknife.ButterKnife;


public class AllConsumeRecordAdapter extends RecyclerView.Adapter<AllConsumeRecordAdapter.NormalTextViewHolder> {
    private Context context;
    private NormalTextViewHolder normalTextViewHolder;
    private JSONArray arrayData;
    public AllConsumeRecordAdapter( JSONArray arrayData) {
        this.context= MyApplication.getContext();
        this.arrayData=arrayData;
    }
    //获取视图
    @Override
    public NormalTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return normalTextViewHolder = new NormalTextViewHolder(LayoutInflater.from(
                context).inflate(R.layout.all_consume_recycle_item, parent,
                false));
    }
    //绑定数据
    @Override
    public void onBindViewHolder(NormalTextViewHolder holder, int position) {

    }
    //数据数量
    @Override
    public int getItemCount() {
        return arrayData.length();
    }
    //数据缓存
    public class NormalTextViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txt_trans_time)TextView txtTrans;
        public NormalTextViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            txtTrans.setText(arrayData.optJSONObject(0).optString("create_time"));
        }
    }
}
