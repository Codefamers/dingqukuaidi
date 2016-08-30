package com.appheader.DingQuPostman.ui.main.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appheader.DingQuPostman.R;
import com.appheader.DingQuPostman.application.GlobalVars;
import com.appheader.DingQuPostman.application.MyApplication;
import com.appheader.DingQuPostman.ui.main.activity.ArticleDetailActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/2 0002.
 */
public class FineArticleAdapter extends RecyclerView.Adapter<FineArticleAdapter.MyViewHolder> {
    //private int count;
    private Activity activity;
    private JSONArray array;

    public FineArticleAdapter(Activity activity, int count, JSONArray array) {
        //this.count = count;
        this.activity = activity;
        this.array=array;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                MyApplication.getContext()).inflate(R.layout.item_article, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        JSONObject data=array.optJSONObject(position);
        //圆角图片
        Glide.with(activity).load(GlobalVars.getAppServerUrl()+data.optString("headImg")).asBitmap().
                placeholder(R.mipmap.ic_status_bar_photo).centerCrop().into(new BitmapImageViewTarget(holder.imgUserPhoto){
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(activity.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                holder.imgUserPhoto.setImageDrawable(circularBitmapDrawable);
            }
        });
        holder.txtArticleName.setText(data.optString("petName"));
        holder.txtArticleTime.setText(data.optString("time"));
        holder.txtTitle.setText(data.optString("title"));
        if (Integer.parseInt(data.optString("isMark"))==0);
            holder.btnArticleKind.setVisibility(View.INVISIBLE);
        JSONArray jsonArray=data.optJSONArray("replyImageUrl");
        switch (jsonArray.length()){
            case 3:
                holder.imgContentTwo.setVisibility(View.VISIBLE);
                Glide.with(activity).load(GlobalVars.getAppServerUrl()+jsonArray.optString(2)).
                        placeholder(R.mipmap.ic_status_bar_photo).into(holder.imgContentTwo);
            case 2:
                holder.imgContentOne.setVisibility(View.VISIBLE);
                Glide.with(activity).load(GlobalVars.getAppServerUrl()+jsonArray.optString(1)).
                        placeholder(R.mipmap.ic_status_bar_photo).into(holder.imgContentOne);
            case 1:
                holder.imgContentZero.setVisibility(View.VISIBLE);
                Glide.with(activity).load(GlobalVars.getAppServerUrl()+jsonArray.optString(0)).
                        placeholder(R.mipmap.ic_status_bar_photo).into(holder.imgContentZero);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return array==null?0:array.length();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img_user_photo)
        ImageView imgUserPhoto;
        @Bind(R.id.txt_article_name)
        TextView txtArticleName;
        @Bind(R.id.txt_article_time)
        TextView txtArticleTime;
        @Bind(R.id.img_comment_num)
        TextView imgCommentNum;
        @Bind(R.id.img_like_num)
        TextView imgLikeNum;
        @Bind(R.id.rel_article_title)
        RelativeLayout relArticleTitle;
        @Bind(R.id.txt_title)
        TextView txtTitle;
        @Bind(R.id.btn_article_kind)
        Button btnArticleKind;
        @Bind(R.id.rel_article_content)
        RelativeLayout relArticleContent;
        @Bind(R.id.img_content_zero)
        ImageView imgContentZero;
        @Bind(R.id.img_content_one)
        ImageView imgContentOne;
        @Bind(R.id.img_content_two)
        ImageView imgContentTwo;
        @Bind(R.id.ll_img_display)
        LinearLayout llDisplay;
        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(activity, "点击帖子" + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    Intent in=new Intent(activity,ArticleDetailActivity.class);
                    in.putExtra("cellID",array.optJSONObject(getAdapterPosition()).optString("cellID"));
                    activity.startActivity(in);
                }
            });
        }
    }
}


