package com.appheader.DingQuPostman.ui.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.appheader.DingQuPostman.R;


/**
 * 提示对话框
 */
public class CommonDialog extends Dialog {
	private TextView mDialogTitle, mDialogContents;
	private Button mDialogCancel, mDialogOk;
	private Context mContext;
	private View.OnClickListener mOkClick, mCancelClick;
	private String mTitle="",mContents="";
	private	String mCancel="取消",mOk = "确定";
	public CommonDialog(Context context, String title, String contents, View.OnClickListener click) {
		super(context, R.style.CommonDialog);
		mContext = context;
		mOkClick = click;
		mTitle = title;
		mContents = contents;
	}
	public CommonDialog(Context context, String title, String cancel, String ok, String contents, View.OnClickListener okClick) {
		super(context, R.style.CommonDialog);
		mContext = context;
		mOkClick = okClick;
		mTitle = title;
		mContents = contents;
		mCancel = cancel;
		mOk = ok;
	}

	public CommonDialog(Context context, String title, String cancel, String ok, String contents, View.OnClickListener okClick, View.OnClickListener cancelClick) {
		super(context, R.style.CommonDialog);
		mContext = context;
		mOkClick = okClick;
		mCancelClick = cancelClick;
		mTitle = title;
		mContents = contents;
		mCancel = cancel;
		mOk = ok;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_common);
		setCanceledOnTouchOutside(true);
		mDialogTitle = (TextView) findViewById(R.id.dialog_title);
		mDialogContents = (TextView) findViewById(R.id.dialog_contents);
		mDialogCancel = (Button) findViewById(R.id.dialog_cancel);
		mDialogOk = (Button) findViewById(R.id.dialog_ok);
		mDialogCancel.setOnClickListener(mCancelClick == null ? new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				cancel();
			}
		} : mCancelClick);
		mDialogCancel.setText(mCancel);
		mDialogOk.setText(mOk);
		mDialogOk.setOnClickListener(mOkClick);
		mDialogTitle.setText(mTitle);
		mDialogContents.setText(mContents);
	}
}
