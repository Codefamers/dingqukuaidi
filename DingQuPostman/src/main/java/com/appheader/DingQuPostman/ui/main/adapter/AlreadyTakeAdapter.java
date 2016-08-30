package com.appheader.DingQuPostman.ui.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.appheader.DingQuPostman.R;
import com.appheader.DingQuPostman.application.MyApplication;
import com.appheader.DingQuPostman.common.activity.BaseActivity;
import com.appheader.DingQuPostman.ui.main.myinterface.GetExpressListener;
import com.appheader.DingQuPostman.ui.main.myinterface.SomeConstant;
import com.appheader.DingQuPostman.user.CurrentUserManager;
import com.appheader.DingQuPostman.user.UserInfo;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;


public class AlreadyTakeAdapter extends RecyclerView.Adapter {
    private final ArrayList<HashMap<String, String>> dataItem;
    private final ArrayList<HashMap<String, String>> dataTitle;
    private BaseActivity activity;
    private Context context;
    private NormalTextViewHolder normalTextViewHolder;
    private TitleTextViewHolder titleTextViewHolder;
    private static final String TAG = "WaitTakeAdapter";
    private UserInfo userInfo;
    private static final int TYPE_ITEM = 0;//item类型
    private static final int TYPE_TITLE = 1;//title类型
    private int itemPos = 0, itemTit;//记录已经展示item数量

    public AlreadyTakeAdapter(JSONArray arrayData, BaseActivity activity) {
        this.context = MyApplication.getContext();
        this.activity = activity;
        dataItem = new ArrayList<>();
        dataTitle = new ArrayList<>();
        initData(arrayData);
        userInfo = CurrentUserManager.getCurrentUser();
    }

    private void initData(JSONArray arrayData) {
        for (int i = 0; i < arrayData.length(); i++) {
            String time = null;
            HashMap<String, String> mapItem = new HashMap<>();
            HashMap<String, String> mapTitle = new HashMap<>();
            time = arrayData.optJSONObject(i).optString("create_time").substring(0, 10).replace('-', '.');
            //计算item
            mapItem.put("receiver_phone", arrayData.optJSONObject(i).optString("receiver_phone"));
            mapItem.put("express_number", arrayData.optJSONObject(i).optString("express_number"));
            mapItem.put("create_time", time);
            dataItem.add(mapItem);
            //计算title
            String lastTime = i == 0 ? null : dataItem.get(i - 1).get("create_time").substring(0, 10).replace('-', '.');
            Log.d(TAG, "WaitTakeAdapter: " + "添加数据111");
            Log.d(TAG, "WaitTakeAdapter: " + time + "\n" + lastTime);
            if (!time.equals(lastTime)) {
                mapTitle.put("create_time", time);
                Log.d(TAG, "WaitTakeAdapter: " + "添加数据222" + time);
                dataTitle.add(mapTitle);
            }

        }

    }

    /*//数据源发生改变
    public void changeData() {
        // dataItem.remove(position);
        userInfo.getExpressData(activity, SomeConstant.WAIT_SAVE, 10, 1, new GetExpressListener() {
            @Override
            public void getSuccess(JSONArray arr) {
                clearData();
                initData(arr);
                notifyDataSetChanged();
            }
        });

    }*/

    //刷新数据
    public void refreshData(JSONArray arr) {
        clearData();
        initData(arr);
        notifyDataSetChanged();
    }

    private void clearData() {
        itemPos = 0;
        itemTit = 0;
        dataItem.clear();
        dataTitle.clear();
    }

    //获取视图
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            normalTextViewHolder = new NormalTextViewHolder(LayoutInflater.from(
                    context).inflate(R.layout.view_already_take_data, parent,
                    false));
            return normalTextViewHolder;
        }
        if (viewType == TYPE_TITLE) {
            titleTextViewHolder = new TitleTextViewHolder(LayoutInflater.from(
                    context).inflate(R.layout.view_wait_take_data_title, parent,
                    false));
            return titleTextViewHolder;
        }
        return null;
    }


    //绑定数据
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HashMap<String, String> item, title;

        if (holder instanceof NormalTextViewHolder) {
            Log.d(TAG, "onBindViewHolder: " + itemPos);
            item = dataItem.get(itemPos);
            ((NormalTextViewHolder) holder).txtTel.setText(item.get("receiver_phone"));
            ((NormalTextViewHolder) holder).txtExID.setText(item.get("express_number"));
            ((NormalTextViewHolder) holder).txtDetailDate.setText(item.get("create_time"));

            itemPos++;
        }
        if (holder instanceof TitleTextViewHolder) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            String str = formatter.format(curDate);
            title = dataTitle.get(itemTit);
            String showTime = str.equals(title.get("create_time")) ? "今天" + title.get("create_time") : title.get("create_time");
            Log.d(TAG, "onBindViewHolder: 当前时间" + str + "\n" + title.get("create_time"));
            ((TitleTextViewHolder) holder).btnDate.setText(showTime);
            itemTit++;
        }
    }

    //数据数量
    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + dataItem + "\ndataTitle" + dataTitle);
        return dataItem.size() + dataTitle.size();
    }

    //返回数据类型
    @Override
    public int getItemViewType(int position) {
        Log.d(TAG, "getItemViewType: " + position + "\nitem" + itemPos + "\ntitle" + itemTit);
        String itemTime, titleTime;
        while (itemTit < dataTitle.size()) {
            itemTime = dataItem.get(itemPos).get("create_time");
            titleTime = dataTitle.get(itemTit).get("create_time");
            Log.d(TAG, "getItemViewType: " + itemTime + "\n" + titleTime);
            int type = itemTime.equals(titleTime) ? TYPE_TITLE : TYPE_ITEM;
            return type;
        }
        return TYPE_ITEM;
    }

    //数据缓存
    public class NormalTextViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txt_telephone)
        TextView txtTel;//电话号码
        @Bind(R.id.txt_complete_date)
        TextView txtDetailDate;//详细日期
        @Bind(R.id.txt_express_id)
        TextView txtExID;//快递单号

        public NormalTextViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //标题ViewHolder
    public class TitleTextViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.btn_date)
        Button btnDate;

        public TitleTextViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            btnDate.getBackground().setAlpha(35);

        }
    }
}
