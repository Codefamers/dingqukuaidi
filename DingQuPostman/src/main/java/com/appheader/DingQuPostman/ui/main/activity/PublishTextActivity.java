package com.appheader.DingQuPostman.ui.main.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.appheader.DingQuPostman.R;
import com.appheader.DingQuPostman.common.activity.BaseActivity;
import com.appheader.DingQuPostman.common.network.RequestCallback;
import com.appheader.DingQuPostman.common.network.RequestHelper;
import com.appheader.DingQuPostman.common.network.ResponseEntity;
import com.appheader.DingQuPostman.common.network.UrlConstants;
import com.appheader.DingQuPostman.common.utils.ParamBuilder;
import com.appheader.DingQuPostman.ui.common.upload.PhotoUploadHelper;
import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class PublishTextActivity extends BaseActivity {
    private static final String TAG = "PublishTextActivity";
    @Bind(R.id.et_edit_title)
    EditText etEditTitle;
    @Bind(R.id.img_cut_line_one)
    ImageView imgCutLineOne;
    @Bind(R.id.et_edit_content)
    EditText etEditContent;
    @Bind(R.id.img_cut_line_two)
    ImageView imgCutLineTwo;
    @Bind(R.id.txt_choose_publish_module_title)
    TextView txtChoosePublishModuleTitle;
    @Bind(R.id.txt_choose_publish_module)
    TextView txtChoosePublishModule;
    @Bind(R.id.img_cut_line)
    ImageView imgCutLine;
    @Bind(R.id.iv_photo_one)
    ImageView ivPhotoOne;
    @Bind(R.id.iv_photo_two)
    ImageView ivPhotoTwo;
    @Bind(R.id.iv_photo_three)
    ImageView ivPhotoThree;
    @Bind(R.id.ll_save_photo)
    LinearLayout llSavePhoto;
    @Bind(R.id.iv_photo_four)
    ImageView ivPhotoFour;
    private String articleTitle;
    private String articleContent;
    private String chooseMoudle;
    private PhotoUploadHelper mUpload;
    private int uploadNum;
    private String category="0";
    private StringBuilder imageUrl;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_text);
        ButterKnife.bind(this);
        title.setText("交流沟通");
        subTitle.setText("发表");
        subTitle.setVisibility(View.VISIBLE);
        imageUrl=new StringBuilder("");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        subTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishText();
            }
        });
        initUploadImage();
    }

    private void publishText() {
        //title,time,category,imageUrl,details,username
        articleTitle = etEditTitle.getText().toString();
        articleContent = etEditContent.getText().toString();
        chooseMoudle = txtChoosePublishModule.getText().toString();
        if ("技术交流".equals(chooseMoudle))
            category="1";
        else if ("生活交流".equals(chooseMoudle))
            category="2";
        if(TextUtils.isEmpty(articleTitle))
            Toast.makeText(PublishTextActivity.this, "请输入文章标题", Toast.LENGTH_SHORT).show();
        if(TextUtils.isEmpty(articleContent)){
            Toast.makeText(PublishTextActivity.this, "请输入文章内容", Toast.LENGTH_SHORT).show();
        }
        HashMap<String, String> paramsMap = ParamBuilder.buildParam().append("title", articleTitle).append("time", "2016-08-20 15:36")
                .append("category", category).append("imageUrl", imageUrl.toString()).append("details", articleContent).append("username", "屈淮南").toHashMap();
        RequestHelper.sendAsyncRequest(this, UrlConstants.TONGZHIFABU, paramsMap, new RequestCallback() {
            @Override
            public void onSuccess(ResponseEntity response) {
                if (response.isSuccess()) {
                    Toast.makeText(PublishTextActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                    finish();
                }else
                    Toast.makeText(PublishTextActivity.this, response.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Exception ex) {
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @OnClick({R.id.iv_photo_four,R.id.txt_choose_publish_module})
    public void clickView(View view) {
        switch (view.getId()) {
            case R.id.iv_photo_four:
                    mUpload.showUploadSelectDialog("选择照片", true, 1, 1, true, 1);
                break;
            case R.id.txt_choose_publish_module:
                Toast.makeText(PublishTextActivity.this, "选择发布版本", Toast.LENGTH_SHORT).show();
                getPopupWindow(getResources().getStringArray(R.array.publish_module),txtChoosePublishModule);
                popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;
        }
    }
    protected void initPopupWindow(String[] dataArray, final TextView chooseTextView) {
        final View popWindow = getLayoutInflater().inflate(R.layout.popwindow_choose_company, null, false);
        Log.d(TAG, "initPopupWindow: "+getWindowManager().getDefaultDisplay().getHeight()/3);
        popupWindow = new PopupWindow(popWindow, ViewGroup.LayoutParams.MATCH_PARENT,getWindowManager().getDefaultDisplay().getHeight()/3, true);
        //设置动画效果
        popupWindow.setAnimationStyle(R.style.dialogstyle);
        //设置背景
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        TextView cancel = (TextView) popWindow.findViewById(R.id.txt_cancel);
        TextView confirm = (TextView) popWindow.findViewById(R.id.txt_confirm);
        final ListView lvCompany = (ListView) popWindow.findViewById(R.id.lv_logistics_company);
        final List<Map<String, String>> comList = new ArrayList<>();
        for (int i = 0; i < dataArray.length; i++) {
            Map map = new HashMap();
            map.put("company_name", dataArray[i]);
            comList.add(map);
        }
        //提交参数 pageSize:10  pageNum:1
        //获取快递公司列表
        SimpleAdapter adapter = new SimpleAdapter(PublishTextActivity.this, comList, R.layout.item_company, new String[]{"company_name"}, new int[]{R.id.txt_company_name});
        lvCompany.setAdapter(adapter);

        //监听所选择公司 selCompany[0]记录所选公司，selCompany[1]记录上一次所选Item
        final String[] selCompany = {"", "-1"};
        lvCompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: " + "点击lv");
                ImageView img = (ImageView) view.findViewById(R.id.img_checked);
                //动态改变按钮的有无
                selCompany[0] = comList.get(position).get("company_name");
                if (selCompany[1] == "-1") {
                    selCompany[1] = Integer.toString(position);

                } else {
                    lvCompany.getChildAt(Integer.parseInt(selCompany[1])).findViewById(R.id.img_checked).setVisibility(View.INVISIBLE);
                }
                img.setVisibility(View.VISIBLE);
                selCompany[1] = position + "";
                Log.d(TAG, "onItemClick: " + selCompany[1]);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popWindow != null && popWindow.isShown()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadMainUI(selCompany[0], chooseTextView);
                Log.d(TAG, "onClick: " + lvCompany.getSelectedItemPosition()); //;
                if (popWindow != null && popWindow.isShown()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
            }
        });
    }

    //实例化弹出框
    private void getPopupWindow(String[] dataArray,TextView chooseTextView) {
        if (null != popupWindow) {
            popupWindow.dismiss();
            initPopupWindow(dataArray,chooseTextView);
            return;
        } else {
            initPopupWindow(dataArray,chooseTextView);
        }
    }
    private void initUploadImage() {
        mUpload = new PhotoUploadHelper(this, new PhotoUploadHelper.UploadCallback() {
            @Override
            public void success(JSONObject obj) {
                switch (obj.optInt("flag")) {
                    case 1:
                        switch (uploadNum) {
                            case 0:
                                ivPhotoOne.setVisibility(View.VISIBLE);
                                imageUrl.append(obj.optString("imageUrl"));
                                Glide.with(PublishTextActivity.this).load(obj.optString("imageUrl")).into(ivPhotoOne);
                                break;
                            case 1:
                                ivPhotoTwo.setVisibility(View.VISIBLE);
                                imageUrl.append("--").append(obj.optString("imageUrl"));
                                Glide.with(PublishTextActivity.this).load(obj.optString("imageUrl")).into(ivPhotoTwo);
                                break;
                            case 2:
                                ivPhotoThree.setVisibility(View.VISIBLE);
                                imageUrl.append("--").append(obj.optString("imageUrl"));
                                Glide.with(PublishTextActivity.this).load(obj.optString("imageUrl")).into(ivPhotoThree);
                                break;
                        }
                        break;

                }
                uploadNum++;
                Log.d(TAG, "success: "+imageUrl.toString());
                if (uploadNum > 2)
                    ivPhotoFour.setVisibility(View.GONE);
            }

            @Override
            public void failed(String str) {
                Toast.makeText(PublishTextActivity.this, "操作失败",
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mUpload.doActResult(requestCode, resultCode, data);
    }
    //从子线程中更新主线程UI
    private void uploadMainUI(final String upLoadInfo, final TextView upLoadView) {
        //Rx方式更新UI
        Observable.just(upLoadInfo).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onCompleted: "+s);
                upLoadView.setText(upLoadInfo);
            }


        });

    }
}


