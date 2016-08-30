package com.appheader.DingQuPostman.ui.main.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appheader.DingQuPostman.R;
import com.appheader.DingQuPostman.application.MyApplication;
import com.appheader.DingQuPostman.common.activity.ActivityManager;
import com.appheader.DingQuPostman.common.activity.BaseActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/2 0002.
 */
class ServeStarAdapter extends RecyclerView.Adapter<ServeStarAdapter.MyViewHolder> {


    private int count;
    private Activity activity;

    public ServeStarAdapter(int count, Activity activity) {
        this.activity = activity;
        this.count = count;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                MyApplication.getContext()).inflate(R.layout.item_serve_star, parent,
                false));
        return holder;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // holder.tv.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return count;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img_server_photo)
        ImageView imgServerPhoto;
        @Bind(R.id.img_server_star)
        ImageView imgServerStar;
        @Bind(R.id.img_server_grade)
        TextView imgServerGrade;
        @Bind(R.id.txt_server_name)
        TextView txtServerName;
        @Bind(R.id.img_server_position)
        TextView imgServerPosition;
        @Bind(R.id.ll_server_rough_info)
        RelativeLayout llServerRoughInfo;
        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            Glide.with(activity).load(R.mipmap.ic_server_photo).asBitmap().centerCrop().into(new BitmapImageViewTarget(imgServerPhoto){
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(activity.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    imgServerPhoto.setImageDrawable(circularBitmapDrawable);
                }
            });
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(activity, "点击" + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}


