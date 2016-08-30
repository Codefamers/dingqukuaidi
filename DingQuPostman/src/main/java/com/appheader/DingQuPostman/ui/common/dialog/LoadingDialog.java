package com.appheader.DingQuPostman.ui.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.appheader.DingQuPostman.R;
import com.bumptech.glide.Glide;



public class LoadingDialog extends Dialog {

	@SuppressWarnings("unused")
	private Context mContext;
	ImageView mImageView;
	private AnimationDrawable animationDrawable;

	@SuppressWarnings("unused")
	private String mMsg;

	public LoadingDialog(Context context) {
		super(context, R.style.Dialog);
		mContext = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pub_loading_dialog);
		setCanceledOnTouchOutside(false);
		mImageView = (ImageView) findViewById(R.id.anim_img);
		Glide.with(mContext).load(R.drawable.loading).asGif().into(mImageView);
	}

	@Override
	public void show() {
		super.show();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				dismiss();
			}
		}, 5000);
	}
}
