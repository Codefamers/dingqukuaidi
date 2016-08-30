package com.appheader.DingQuPostman.ui.main.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ApplyBusinessActivity extends BaseActivity {
    @Bind(R.id.et_telephone_number)
    EditText etTelephoneNumber;//用户名称
    @Bind(R.id.txt_choose_area)
    TextView txtChooseArea;//用电地址
    @Bind(R.id.et_detail_address)
    EditText etDetailAddress;
    @Bind(R.id.ll_use_position)
    LinearLayout llUsePosition;
    @Bind(R.id.txt_choose_papers_kind)
    TextView txtChoosePapersKind;
    @Bind(R.id.re_company)
    LinearLayout reCompany;
    @Bind(R.id.et_clerk_num)
    EditText etClerkNum;
    @Bind(R.id.ll_clerk_num)
    LinearLayout llClerkNum;
    @Nullable
    @Bind(R.id.re_oneself_photo)
    RelativeLayout reOneselfPhoto;
    @Bind(R.id.txt_title_one)
    TextView txtTitleOne;
    @Bind(R.id.et_title_one)
    EditText etTitleOne;
    @Bind(R.id.ll_title_one)
    LinearLayout llTitleOne;
    @Bind(R.id.txt_title_two)
    TextView txtTitleTwo;
    @Bind(R.id.et_title_two)
    EditText etTitleTwo;
    @Bind(R.id.ll_title_two)
    LinearLayout llTitleTwo;
    @Bind(R.id.txt_title_three)
    TextView txtTitleThree;
    @Bind(R.id.et_title_three)
    EditText etTitleThree;
    @Bind(R.id.ll_title_three)
    LinearLayout llTitleThree;
    @Bind(R.id.txt_title_four)
    TextView txtTitleFour;
    @Bind(R.id.txt_choose_four)
    TextView txtChooseFour;
    @Bind(R.id.ll_title_four)
    LinearLayout llTitleFour;
    @Bind(R.id.re_change_info)
    RelativeLayout reChangeInfo;
    @Bind(R.id.btn_choose_manager)
    Button btnChooseManager;
    @Bind(R.id.rec_choose_manager)
    RecyclerView recChooseManager;
    @Bind(R.id.re_high_install)
    RelativeLayout reHighInstall;
    @Bind(R.id.et_agent_name)
    EditText etAgentName;
    @Bind(R.id.ll_agent_name)
    LinearLayout llAgentName;
    @Bind(R.id.et_agent_telephone)
    EditText etAgentTelephone;
    @Bind(R.id.ll_agent_telephone)
    LinearLayout llAgentTelephone;
    @Bind(R.id.et_check_code)
    EditText etCheckCode;
    @Bind(R.id.ll_check_code)
    LinearLayout llCheckCode;
    @Bind(R.id.tv_oneself_title)
    TextView tvOneselfTitle;
    @Bind(R.id.tv_oneself_hint)
    TextView tvOneselfHint;
    @Bind(R.id.iv_oneself_photo)
    ImageView ivOneselfPhoto;
    @Bind(R.id.btn_get_code)
    Button btnGetCode;
    @Bind(R.id.btn_is_agree_clause)
    ImageButton btnIsAgreeClause;
    @Bind(R.id.txt_agree_clause)
    TextView txtAgreeClause;
    @Bind(R.id.btn_confirm)
    Button btnConfirm;
    @Bind(R.id.sco_main_info)
    ScrollView scrollView;
    @Bind(R.id.web)
    WebView abWeb;
    private String username;            //登陆账号
    private String category;            //分类

    private String companyName;            //用户名称，公司或企业名字
    private String area;                //所属区域&用电地区
    private String detailsAddress;        //详细地址&用电地址
    private String details;            //申请说明&申请原因
    private String applyTime;            //申请的时间
    private String file;                //上传附件
    private String idCategory;            //证件类型
    private String idNum;                //证件号码
    private String contacts;            //联系人
    private String contactTel;            //联系电话
    private String code;                //验证码
    private String personNum;            //居住人口
    private String livingArea;        //居住面积
    private String deviceList;        //设备清单
    private String property;            //用户产权属性
    private String carport;            //车位
    private String leaseTerm;            //租赁期限
    private String legalPerson;        //企业法人代表
    private String energyCategory;    //能源类型
    private String tempEleTimeBegin;    //临时用电开始
    private String tempEleTimeEnd;    //临时用电结束
    private String accountManager;    //客户经理
    private String applyCapacity;        //申请容量&报装容量
    private String addCapacity;        //增加容量
    private String powerSupply;        //供电电压
    private String chargeType;            //充电桩类型
    private String chargeNum;            //充电桩数
    private String imageArr;            //图片


    Subscriber<String> subscriber = new Subscriber<String>() {
        @Override
        public void onNext(String hintString) {
            Toast.makeText(ApplyBusinessActivity.this, hintString, Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onCompleted() {
            Log.d(TAG, "Completed!");
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, "Error!");
        }
    };
    private PhotoUploadHelper mUpload;
    //弹窗
    private PopupWindow popupWindow;
    private static final String TAG = "ApplyBusinessActivity";
    private boolean isClick = true;
    private boolean isUpload=false;//是否上传过图片
    private boolean isCommit=false;//是否提交过申请
    private int position;//点击业务的位置
    private TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_business);
        //初始化toolbar
        // toolbar.setVisibility(View.GONE);
        ButterKnife.bind(this);
        initUploadImage();
        initView();
    }

    private void initView() {
        position = getIntent().getIntExtra("business", -1);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        switch (position) {
            case 0:
                initViewData("高压新装", "申请电容", "申请说明", null, "供电电压", View.GONE, View.VISIBLE);
                break;
            case 1:
                initViewData("低压居民新装", "*居住人口", "*住房面积", null, null, View.GONE, View.GONE);
                break;
            case 2:
                initViewData("个人充电桩", "*车位详细地点", "*租赁日期", "*充电桩类型", "*用户产权属性", View.VISIBLE, View.VISIBLE);
                break;
            case 3:
                initViewData("企业充电桩", "*企业法人代表", "*报装容量：", "*充电桩桩数：", null, View.VISIBLE, View.GONE);

                break;
            case 4:
                initViewData("低压非居民新装", "住房面积", "申请原因：", null, null, View.GONE, View.GONE);
                break;
            case 5:
                initViewData("个人分布式电源", " ", " ", null, "*分布式能源类型：", View.GONE, View.VISIBLE);
                break;
            case 6:
                initViewData("企业分布式电源", " ", " ", null, "*分布式能源类型：", View.GONE, View.VISIBLE);
                break;
            case 7:
                initViewData("装表临时用电", "临时用电开始时间：", "临时用电结束时间：", null, null, View.GONE, View.GONE);
                break;
            case 8:
                title.setText("高压增容");
                initWeb("http://60.28.54.31:7003/web/sc/zmq/online_business_hall/order/newEnterprisePowerStep1");
                break;
            case 9:
                title.setText("低压非居民增容");
                initWeb("http://60.28.54.31:7003/web/sc/zmq/online_business_hall/order/newEnterprisePowerStep1");
                break;
            default:

                Log.d(TAG, "initView: "+"逻辑出错");
                break;
        }
    }

    private void initWeb(String url) {
        Log.d(TAG, "initWeb: hahah");
        abWeb.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
        WebSettings settings=abWeb.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        abWeb.setWebViewClient(new WebViewClient());
        //abWeb.setWebChromeClient(new WebChromeClient());
        abWeb.loadUrl(url);
    }

    private void initViewData(String pageTitle, String titleOne, String titleTwo, String titleThree, String titleFour, int visibleThree, int visibleFour) {
        title.setText(pageTitle);
        txtTitleOne.setText(titleOne);
        txtTitleTwo.setText(titleTwo);
        etTitleOne.setHint("请输入"+titleOne.replace("*","").replace(":",""));
        etTitleTwo.setHint("请输入"+titleTwo.replace("*","").replace(":",""));
        if (visibleThree == View.VISIBLE) {
            Log.d(TAG, "initViewData1: " + titleThree);
            llTitleThree.setVisibility(visibleThree);
            txtTitleThree.setText(titleThree);
            etTitleThree.setHint("请输入"+titleThree.replace("*","").replace(":",""));
        }
        if (visibleFour == View.VISIBLE) {
            llTitleFour.setVisibility(visibleFour);
            txtTitleFour.setText(titleFour);
        }
        Log.d(TAG, "initViewData:标题是否相等 " + pageTitle.equals("个人分布式电源"));
        if (pageTitle.equals("个人分布式电源") || pageTitle.equals("企业分布式电源")) {
            llTitleOne.setVisibility(View.GONE);
            llTitleTwo.setVisibility(View.GONE);
        }

    }

    @OnClick({R.id.txt_choose_four, R.id.txt_choose_papers_kind, R.id.btn_get_code, R.id.btn_is_agree_clause, R.id.txt_agree_clause, R.id.btn_confirm, R.id.iv_oneself_photo})
    public void confirmInfo(View view) {
        switch (view.getId()) {
            case R.id.iv_oneself_photo:
                mUpload.showUploadSelectDialog("上传头像", true, 1, 1, true, 1);
                break;
            case R.id.txt_agree_clause:
                startActivity(new Intent(ApplyBusinessActivity.this, ServiceClauseActivity.class));
                break;
            case R.id.btn_is_agree_clause:
                if (isClick) {//未点击过
                    btnIsAgreeClause.setBackgroundResource(R.drawable.ico_select);
                    isClick = false;
                } else {
                    btnIsAgreeClause.setBackgroundResource(R.drawable.ico_unselect);
                    isClick = true;
                }
                break;
            case R.id.btn_confirm:
                Log.d(TAG, "confirmInfo: "+isUpload);
                if (!isCommit)
                    judeIsNull();
                else
                    Toast.makeText(ApplyBusinessActivity.this, "你已经提交过该申请,请耐心等待", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_get_code:
                Observable.create(new Observable.OnSubscribe<Integer>(){
                    @Override
                    public void call(final Subscriber<? super Integer> subscriber) {
                        timerTask = new TimerTask() {
                            int i = 60;
                            @Override
                            public void run() {
                                if (i==0) {
                                    subscriber.onCompleted();
                                }
                                subscriber.onNext(Integer.valueOf(i--));
                            }
                        };
                        Timer timer = new Timer();
                        timer.schedule(timerTask, 0, 1 * 1000);//0毫秒后每1秒执行该任务一次
                    }}
                ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: subscribe"+integer);
                        btnGetCode.setEnabled(false);
                        btnGetCode.setText(String.format("%s秒", integer));
                    }
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Completed!");
                        btnGetCode.setText("获取验证码");
                        timerTask.cancel();//取消定时任务
                        //isCanGetVer = true;//允许再次获取验证码
                        btnGetCode.setEnabled(true);
                        Log.d(TAG, "handleMessage: 取消计时");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Error!");
                    }
                });
                break;
            case R.id.txt_choose_four:
                switch (position) {
                    case 0:
                        getPopupWindow(getResources().getStringArray(R.array.powerSupply),txtChooseFour);
                        break;
                    case 2:
                        getPopupWindow(getResources().getStringArray(R.array.property),txtChooseFour);
                        break;
                    case 5:
                        getPopupWindow(getResources().getStringArray(R.array.powerCategory),txtChooseFour);
                        break;
                    case 6:
                        getPopupWindow(getResources().getStringArray(R.array.powerCategory),txtChooseFour);
                        break;
                    default:
                        Log.d(TAG, "confirmInfo: 没有选中");
                        break;
                }
                popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.txt_choose_papers_kind:
                getPopupWindow(getResources().getStringArray(R.array.idCategory),txtChoosePapersKind);
                popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;
        }
    }

    //判断传参数是否有空
    private void judeIsNull() {
        //变量赋值
        //额外信息
        category = Integer.toString(position);
        username = "屈淮南";
        applyTime = "2016-08-08 12:56";
        area = "自贸区";
        //用户信息
        companyName = etTelephoneNumber.getText().toString();
        detailsAddress = etDetailAddress.getText().toString();
        idCategory = txtChoosePapersKind.getText().toString();
        idNum = etClerkNum.getText().toString();
        //代理人信息
        contacts = etAgentName.getText().toString();
        contactTel = etAgentTelephone.getText().toString();
        code = etCheckCode.getText().toString();

        //验证信息

        if (TextUtils.isEmpty(companyName) || companyName.length() != 11) {
            Toast.makeText(ApplyBusinessActivity.this, "请输入正确手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(detailsAddress)) {
            Toast.makeText(ApplyBusinessActivity.this, "请输入您的用电地址", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(contacts)) {
            Toast.makeText(ApplyBusinessActivity.this, "请输入办理人姓名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(contactTel) || contactTel.length() != 11) {
            Toast.makeText(ApplyBusinessActivity.this, "请输入办理人手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(code)) {
            Toast.makeText(ApplyBusinessActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isUpload) {
            Toast.makeText(ApplyBusinessActivity.this, "请上传证件", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isClick) {
            Toast.makeText(ApplyBusinessActivity.this, "您未同意服务条款", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (position) {
            case 0:
                applyCapacity = etTitleOne.getText().toString();
                powerSupply = txtChooseFour.getText().toString();
                details = etTitleTwo.getText().toString();
                if (TextUtils.isEmpty(applyCapacity)) {
                    Toast.makeText(ApplyBusinessActivity.this, "请选择申请电容", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(powerSupply)) {
                    Toast.makeText(ApplyBusinessActivity.this, "请选择供电电压", Toast.LENGTH_SHORT).show();
                    return;
                }
                HashMap<String, String> paramsMap = ParamBuilder.buildParam().append("category", category).append("username", username)
                        .append("applyTime", applyTime).append("area", area).append("companyName", companyName).append("idCategory", idCategory)
                        .append("idNum", idNum).append("contacts", contacts).append("contactTel", contactTel).append("code", code)
                        .append("applyCapacity", applyCapacity).append("powerSupply", powerSupply).append("details", details).toHashMap();
                applyBusiness(paramsMap);
                break;
            case 1:
                personNum  = etTitleOne.getText().toString();
                livingArea = etTitleTwo.getText().toString();
                if (TextUtils.isEmpty(personNum)) {
                    Toast.makeText(ApplyBusinessActivity.this, "请输入居住人口数量", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(livingArea)) {
                    Toast.makeText(ApplyBusinessActivity.this, "请输入住房面积", Toast.LENGTH_SHORT).show();
                    return;
                }
                HashMap<String, String> paramsOne = ParamBuilder.buildParam().append("category", category).append("username", username)
                        .append("applyTime", applyTime).append("area", area).append("companyName", companyName).append("idCategory", idCategory)
                        .append("idNum", idNum).append("contacts", contacts).append("contactTel", contactTel).append("code", code)
                        .append("personNum", personNum).append("livingArea", livingArea).toHashMap();
                applyBusiness(paramsOne);
                break;
            case 2:
                carport  = etTitleOne.getText().toString();
                leaseTerm = etTitleTwo.getText().toString();
                chargeType=etTitleThree.getText().toString();
                property=txtChooseFour.getText().toString();
                if (TextUtils.isEmpty(carport )) {
                    Toast.makeText(ApplyBusinessActivity.this, "请输入车位详细地点", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(livingArea)) {
                    Toast.makeText(ApplyBusinessActivity.this, "请输入租赁日期", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(chargeType)) {
                    Toast.makeText(ApplyBusinessActivity.this, "请输入充电类型", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(property)) {
                    Toast.makeText(ApplyBusinessActivity.this, "请选择用户产权属性", Toast.LENGTH_SHORT).show();
                    return;
                }
                HashMap<String, String> paramsTwo = ParamBuilder.buildParam().append("category", category).append("username", username)
                        .append("applyTime", applyTime).append("area", area).append("companyName", companyName).append("idCategory", idCategory)
                        .append("idNum", idNum).append("contacts", contacts).append("contactTel", contactTel).append("code", code)
                        .append("carport", carport).append("leaseTerm", leaseTerm ).append("chargeType", chargeType ).append("property", property ).toHashMap();
                applyBusiness(paramsTwo);
                break;
            case 3:
                legalPerson  = etTitleOne.getText().toString();
                applyCapacity = etTitleTwo.getText().toString();
                chargeNum=etTitleThree.getText().toString();

                if (TextUtils.isEmpty(legalPerson )) {
                    Toast.makeText(ApplyBusinessActivity.this, "请输入企业法人代表", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(applyCapacity)) {
                    Toast.makeText(ApplyBusinessActivity.this, "请输入报装容量", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(chargeNum)) {
                    Toast.makeText(ApplyBusinessActivity.this, "请输入充电桩数量", Toast.LENGTH_SHORT).show();
                    return;
                }

                HashMap<String, String> paramsThree = ParamBuilder.buildParam().append("category", category).append("username", username)
                        .append("applyTime", applyTime).append("area", area).append("companyName", companyName).append("idCategory", idCategory)
                        .append("idNum", idNum).append("contacts", contacts).append("contactTel", contactTel).append("code", code)
                        .append("carport", carport).append("leaseTerm", leaseTerm ).append("chargeType", chargeType ).append("property", property ).toHashMap();
                applyBusiness(paramsThree);
                break;
            case 4:
                livingArea = etTitleOne.getText().toString();
                details = etTitleTwo.getText().toString();
                HashMap<String, String> paramsMapFour = ParamBuilder.buildParam().append("category", category).append("username", username)
                        .append("applyTime", applyTime).append("area", area).append("companyName", companyName).append("idCategory", idCategory)
                        .append("idNum", idNum).append("contacts", contacts).append("contactTel", contactTel).append("code", code)
                        .append("livingArea", livingArea).append("details", details).toHashMap();
                applyBusiness(paramsMapFour);
                break;
            case 5:
                energyCategory=txtChooseFour.getText().toString();
                if (TextUtils.isEmpty(property)) {
                    Toast.makeText(ApplyBusinessActivity.this, "请选择分布式能源类型", Toast.LENGTH_SHORT).show();
                    return;
                }
                HashMap<String, String> paramsMapFive = ParamBuilder.buildParam().append("category", category).append("username", username)
                        .append("applyTime", applyTime).append("area", area).append("companyName", companyName).append("idCategory", idCategory)
                        .append("idNum", idNum).append("contacts", contacts).append("contactTel", contactTel).append("code", code)
                        .append("energyCategory",energyCategory).toHashMap();
                applyBusiness(paramsMapFive);
                break;
            case 6:
                energyCategory=txtChooseFour.getText().toString();
                if (TextUtils.isEmpty(property)) {
                    Toast.makeText(ApplyBusinessActivity.this, "请选择分布式能源类型", Toast.LENGTH_SHORT).show();
                    return;
                }
                HashMap<String, String> paramsMapSix = ParamBuilder.buildParam().append("category", category).append("username", username)
                        .append("applyTime", applyTime).append("area", area).append("companyName", companyName).append("idCategory", idCategory)
                        .append("idNum", idNum).append("contacts", contacts).append("contactTel", contactTel).append("code", code)
                        .append("energyCategory",energyCategory).toHashMap();
                applyBusiness(paramsMapSix);
                break;
            case 7:
                tempEleTimeBegin=etTitleOne.getText().toString();
                tempEleTimeEnd=etTitleTwo.getText().toString();
                if (TextUtils.isEmpty(tempEleTimeBegin)) {
                    Toast.makeText(ApplyBusinessActivity.this, "请输入临时用电开始时间", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(tempEleTimeEnd)) {
                    Toast.makeText(ApplyBusinessActivity.this, "请输入临时用电结束时间", Toast.LENGTH_SHORT).show();
                    return;
                }
                HashMap<String, String> paramsMapSeven = ParamBuilder.buildParam().append("category", category).append("username", username)
                        .append("applyTime", applyTime).append("area", area).append("companyName", companyName).append("idCategory", idCategory)
                        .append("idNum", idNum).append("contacts", contacts).append("contactTel", contactTel).append("code", code)
                        .append("tempEleTimeBegin",tempEleTimeBegin).append("tempEleTimeEnd",tempEleTimeEnd).toHashMap();
                applyBusiness(paramsMapSeven);
                break;
            case 8:
                break;
            case 9:
                break;

        }

    }

    private void applyBusiness(HashMap<String, String> paramsMap) {
        RequestHelper.sendAsyncRequest(this, UrlConstants.RENWU_DENGJI, paramsMap, new RequestCallback() {
            @Override
            public void onSuccess(ResponseEntity response) {
                Log.d(TAG, "onSuccess: " + response.isSuccess());
                Log.d(TAG, "onSuccess: " + response.getErrorMessage());
                //提交信息成功
                if (response.isSuccess()) {
                    isCommit=true;
                    Toast.makeText(ApplyBusinessActivity.this, "业务申请成功", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(ApplyBusinessActivity.this,response.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Exception ex) {
                Observable.just("网络出错").observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
            }
            @Override
            public void onComplete() {

            }
        });

    }

    /* @Override
     public void onBackPressed() {
         if (System.currentTimeMillis() - mLastPressBackTime < 2000) {
             finish();
         } else {
             ToastUtil.showShort(ApplyBusinessActivity.this,"再按一次退出" + getResources().getString(R.string.app_name));
         }
         mLastPressBackTime = System.currentTimeMillis();
     }*/
    private void initUploadImage() {
        mUpload = new PhotoUploadHelper(this, new PhotoUploadHelper.UploadCallback() {
            @Override
            public void success(JSONObject obj) {
               // avatorId = obj.optString("resourceId");
                switch (obj.optInt("flag")) {
                    case 1:
                      //  photo = avatorId;
                        Glide.with(ApplyBusinessActivity.this).load(obj.optString("imageUrl")).into(ivOneselfPhoto);
                        isUpload=true;
                        break;
                }
                Toast.makeText(ApplyBusinessActivity.this, "操作成功",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void failed(String str) {
                Toast.makeText(ApplyBusinessActivity.this, "操作失败",
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mUpload.doActResult(requestCode, resultCode, data);
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
        SimpleAdapter adapter = new SimpleAdapter(ApplyBusinessActivity.this, comList, R.layout.item_company, new String[]{"company_name"}, new int[]{R.id.txt_company_name});
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timerTask!=null){
            timerTask.cancel();
        }
    }


}
 /*if (comList.size() == 0) {
            HashMap<String, String> paramsMap = ParamBuilder.buildParam().append("pageSize", Integer.toString(10)).append("pageNum", Integer.toString(1)).toHashMap();
            RequestHelper.sendAsyncRequest(this, UrlConstants.GET_COMPANY_LIST, paramsMap, new RequestCallback() {
                @Override
                public void onSuccess(ResponseEntity response) {
                    Log.d(TAG, "onSuccess: " + response.getErrorMessage());
                    if (response.isSuccess()) {
                        //登录成功
                        JSONArray arr = response.getDataObject().optJSONArray("list");
                        Log.d(TAG, "onSuccess: " + response.getDataObject());
                        Log.d(TAG, "onSuccess: " + arr.length());
                        Log.d(TAG, "onSuccess: " + arr.optJSONObject(0).optString("name"));
                        for (int i = 0; i < arr.length(); i++) {
                            Map map = new HashMap();
                            map.put("company_name", arr.optJSONObject(i).optString("name"));
                            comList.add(map);
                        }

                    }
                }

                @Override
                public void onFailure(Exception ex) {
                }

                @Override
                public void onComplete() {

                }
            });
        } else {
            SimpleAdapter adapter = new SimpleAdapter(ApplyBusinessActivity.this, comList, R.layout.item_company, new String[]{"company_name"}, new int[]{R.id.txt_company_name});
            lvCompany.setAdapter(adapter);
        }*/
    //审核中设置文本信息
    /*public void setAuditInfo(final int status) {
        //postmanId: 快递员id
        final UserInfo useInfo = CurrentUserManager.getCurrentUser();
        HashMap<String, String> paramsMap = ParamBuilder.buildParam().append("postmanId", useInfo.getId()).toHashMap();
        Log.d(TAG, "setAuditInfo: id"+useInfo.getId());
        RequestHelper.sendAsyncRequest(this, UrlConstants.GET_POSTMAN_REVIEW, paramsMap, new RequestCallback() {
            @Override
            public void onSuccess(ResponseEntity response) {
                if (response.isSuccess()) {
                    final JSONObject arr = response.getDataObject();
                    Log.d(TAG, "onSuccess: "+arr);
                    if (status==SomeConstant.CHECKING) {
                        setValue(arr);
                        setAllEnable();
                    }if (status==SomeConstant.NO_CHECKED){
                        setValue(arr);
                        tvTitle.setText("您的资料审核未通过，请重新提交资料，点击查看原因");
                        tvTitle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CompleteInfoDialog comDialog=new CompleteInfoDialog(ApplyBusinessActivity.this,
                                        "拒绝原因",arr.optString("rejectReason"),null);
                                comDialog.show();
                            }
                        });
                        btnConfirm.setText("提交审核");
                    }
                } else {
                    //登录失败
                    uploadMainUI("登录失败" + response.getErrorMessage(), SomeConstant.EOOR_CODE);
                }
            }

            @Override
            public void onFailure(Exception ex) {
            }

            @Override
            public void onComplete() {
            }
        });
    }
    //设置所有控件属性不能修改
    private void setAllEnable() {
        postName.setEnabled(false);
        idcard.setEnabled(false);
        comName.setEnabled(false);
        clerkNum.setEnabled(false);
        onselfPhoto.setEnabled(false);
        idcardPhoto.setEnabled(false);
        itnClause.setEnabled(false);
        btnConfirm.setEnabled(false);
    }
    //给所有控件赋值
    private void setValue(JSONObject arr) {
        telPhone.setText(arr.optString("phoneNumber"));
        postName.setText(arr.optJSONObject("contents").optString("personName"));
        idcard.setText(arr.optJSONObject("contents").optString("idcardNumber"));
        comName.setText(arr.optJSONObject("contents").optString("companyName"));
        clerkNum.setText(arr.optJSONObject("contents").optString("clerkNumber"));
        Glide.with(ApplyBusinessActivity.this).load(arr.optJSONObject("contents").optString("thumbUrl")).into(onselfPhoto);
        Glide.with(ApplyBusinessActivity.this).load(arr.optJSONObject("contents").optString("thumbUrl")).into(idcardPhoto);
        Glide.with(ApplyBusinessActivity.this).load(arr.optJSONObject("contents").optString("thumbUrl")).into(workPhoto);
        itnClause.setBackgroundResource(R.drawable.ico_select);
        btnConfirm.setText("正在审核中");
    }*/




/* private void initView() {
        Intent in = getIntent();
        int code = getIntent().getIntExtra("check_status", -1000);
        telPhone.setText(getIntent().getStringExtra("check_tel"));
        switch (code) {
            case SomeConstant.NO_IDENTIFICATION:
                Toast.makeText(ApplyBusinessActivity.this, "未认证", Toast.LENGTH_SHORT).show();
                break;
            case SomeConstant.CHECKING:
                Toast.makeText(ApplyBusinessActivity.this, "审核中", Toast.LENGTH_SHORT).show();
                setAuditInfo(SomeConstant.CHECKING);
                break;
            case SomeConstant.NO_CHECKED:
                Toast.makeText(ApplyBusinessActivity.this, "审核未通过", Toast.LENGTH_SHORT).show();
                setAuditInfo(SomeConstant.NO_CHECKED);
                break;
            default:
                Toast.makeText(ApplyBusinessActivity.this, "逻辑出现漏洞", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @OnClick({R.id.btn_is_agree_clause,R.id.txt_agree_clause,R.id.btn_confirm, R.id.iv_oneself_photo, R.id.iv_id_card_photo, R.id.iv_work_card_photo, R.id.txt_company_name})
    public void confirmInfo(View view) {
        switch (view.getId()) {
            case R.id.iv_oneself_photo:
                mUpload.showUploadSelectDialog("上传头像", true, 1, 1, true,1);
                break;
            case R.id.iv_id_card_photo:
                mUpload.showUploadSelectDialog("上传头像", true, 1, 1, true,2);
                break;
            case R.id.iv_work_card_photo:
                mUpload.showUploadSelectDialog("上传头像", true, 1, 1, true,3);
                break;
            case R.id.txt_company_name:
                getPopupWindow();
                popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.txt_agree_clause:
                startActivity(new Intent(ApplyBusinessActivity.this,ServiceClauseActivity.class));
                break;
            case R.id.btn_is_agree_clause:
                if (isClick){//未点击过
                    itnClause.setBackgroundResource(R.drawable.ico_select);
                    isClick=false;
                }else {
                    itnClause.setBackgroundResource(R.drawable.ico_unselect);
                    isClick=true;
                }

                break;
            case R.id.btn_confirm:
                //postmanId: 快递员id  personName：姓名  idcardNumber：身份证号    clerkNumber：工牌号 photo：本人照片
                //idcardPic身份证照片   clerkNumberPic：工牌照片  companyName：快递公司    phoneNumber：手机号

                //定义变量
                String personName = postName.getText().toString();
                String idcardNumber = idcard.getText().toString();
                String clerkNumber = clerkNum.getText().toString();

                String companyName = comName.getText().toString();
                String phoneNumber = telPhone.getText().toString();
                //验证信息
                if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() != 11) {
                    Toast.makeText(ApplyBusinessActivity.this, "请输入正确手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(personName)) {
                    Toast.makeText(ApplyBusinessActivity.this, "请输入您的真实姓名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(idcardNumber)) {
                    Toast.makeText(ApplyBusinessActivity.this, "请输入您的身份证号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(clerkNumber)) {
                    Toast.makeText(ApplyBusinessActivity.this, "请输入您的工牌号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(companyName)) {
                    Toast.makeText(ApplyBusinessActivity.this, "请选择您所属的物流公司", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(photo)){
                    Toast.makeText(ApplyBusinessActivity.this, "请上传您本人的正面免冠照片", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(idcardPic)){
                    Toast.makeText(ApplyBusinessActivity.this, "请上传您本人的身份证正面照片", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(clerkNumberPic)){
                    Toast.makeText(ApplyBusinessActivity.this, "请上传您本人的工牌照片", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isClick){
                    Toast.makeText(ApplyBusinessActivity.this, "您未同意服务条款", Toast.LENGTH_SHORT).show();
                    return;
                }
                //提交审核信息
                final UserInfo userInfo = CurrentUserManager.getCurrentUser();
                Log.d(TAG, "confirmInfo: "+userInfo.getId());
                HashMap<String, String> paramsMap = ParamBuilder.buildParam().append("postmanId", userInfo.
                        getId()).append("personName", personName).append("idcardNumber", idcardNumber).
                        append("clerkNumber", clerkNumber).append("companyName", companyName).append("phoneNumber", phoneNumber).
                        append("photo", photo).append("idcardPic", idcardPic).append("clerkNumberPic", clerkNumberPic).append("idcardPic", idcardPic).append("clerkNumberPic", clerkNumberPic).toHashMap();
                RequestHelper.sendAsyncRequest(this, UrlConstants.SUBMIT_POSTMAN, paramsMap, new RequestCallback() {
                    @Override
                    public void onSuccess(ResponseEntity response) {
                        Log.d(TAG, "onSuccess: " + response.getErrorMessage());
                        if (response.isSuccess()) {
                            //提交信息成功
                            JSONObject arr = response.getDataObject();
                            btnConfirm.setText("正在审核中");
                            setAllEnable();
                            btnConfirm.setEnabled(false);
                            //allInfoReview(response.getErrorMessage());
                        }
                    }

                    @Override
                    public void onFailure(Exception ex) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });


        }

    }

    */
