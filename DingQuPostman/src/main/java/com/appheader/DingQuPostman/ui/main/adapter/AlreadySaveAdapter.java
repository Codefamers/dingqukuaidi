package com.appheader.DingQuPostman.ui.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appheader.DingQuPostman.R;
import com.appheader.DingQuPostman.application.MyApplication;
import com.appheader.DingQuPostman.ui.main.activity.AlreadySaveActivity;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;


public class AlreadySaveAdapter extends RecyclerView.Adapter<AlreadySaveAdapter.NormalTextViewHolder> {
    private Context context;
    private NormalTextViewHolder normalTextViewHolder;
    private JSONObject arrayData;
    private static final String TAG = "AlreadySaveAdapter";
    public AlreadySaveAdapter(JSONObject arrayData) {

        this.context = MyApplication.getContext();
        this.arrayData = arrayData;
    }

    //获取视图
    @Override
    public NormalTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return normalTextViewHolder = new NormalTextViewHolder(LayoutInflater.from(
                context).inflate(R.layout.view_already_save_data, parent,
                false));
    }

    //绑定数据
    @Override
    public void onBindViewHolder(NormalTextViewHolder holder, int position) {
        switch (position) {
            case 0:
                holder.txtDate.setText("今天");
                holder.txtDetailDate.setText(arrayData.optString("today").substring(0, 10).replace('-', '.'));
                holder.dateIcon.setImageResource(R.mipmap.ico_day);
                holder.txtExNum.setText("共存件"+arrayData.optString("today_count")+"件");
                holder.txtConMoney.setText(arrayData.optString("today_count"));
                break;
            case 1:
                holder.txtDate.setText("本周");
                Log.d(TAG, "onBindViewHolder: "+arrayData.optString("week_start"));
                holder.txtDetailDate.setText(String.format("%s-%s", arrayData.optString("week_start")
                        .substring(0, 10).replace('-', '.'), arrayData.optString("week_end").substring(0, 10).replace('-', '.')));
                Log.d(TAG, "onBindViewHolder: ");
                holder.dateIcon.setImageResource(R.mipmap.ico_week);
                holder.txtExNum.setText("共存件"+arrayData.optString("week_num")+"件");
                holder.txtConMoney.setText(arrayData.optString("week_count"));
                break;
            case 2:
                holder.txtDate.setText("本月");
                holder.txtDetailDate.setText(arrayData.optString("month").substring(0, 7).replace('-', '.'));
                holder.dateIcon.setImageResource(R.mipmap.ico_month);
                holder.txtExNum.setText("共存件"+arrayData.optString("month_count")+"件");
                holder.txtConMoney.setText(arrayData.optString("month_count"));
                break;
            case 3:
                holder.txtDate.setText("最近三个月");
                holder.dateIcon.setImageResource(R.mipmap.ico_month);
                holder.txtExNum.setText("共存件"+arrayData.optString("three_month-count")+"件");
                holder.txtConMoney.setText(arrayData.optString("three_month_num"));
                //holder.txtDetailDate.setText(arrayData.optString("today"));
                break;
        }
    }

    //数据数量
    @Override
    public int getItemCount() {
        return 4;
    }

    //数据缓存
    public class NormalTextViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img_date_icon)
        ImageView dateIcon;
        @Bind(R.id.text_date)
        TextView txtDate;
        @Bind(R.id.text_detail_date)
        TextView txtDetailDate;
        @Bind(R.id.txt_express_num)
        TextView  txtExNum;
        @Bind(R.id.txt_consume_money)
        TextView txtConMoney;
        public NormalTextViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            // txtTrans.setText(arrayData.optJSONObject(0).optString("trans_time"));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in=new Intent(context,AlreadySaveActivity.class);
                    in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(in);
                }
            });
        }
    }
}
