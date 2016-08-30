package com.appheader.DingQuPostman.ui.main.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.appheader.DingQuPostman.R;
import com.appheader.DingQuPostman.application.GlobalVars;
import com.appheader.DingQuPostman.application.MyApplication;
import com.appheader.DingQuPostman.ui.main.activity.AboutActivity;
import com.appheader.DingQuPostman.ui.main.activity.CommonProblemActivity;
import com.appheader.DingQuPostman.ui.main.activity.ApplyBusinessActivity;
import com.appheader.DingQuPostman.ui.main.activity.LoginActivity;
import com.appheader.DingQuPostman.ui.main.activity.ServiceNavActivity;
import com.appheader.DingQuPostman.ui.main.activity.ShareFriendActivity;
import com.appheader.DingQuPostman.ui.main.merchant.MerchantMapActivity;
import com.appheader.DingQuPostman.ui.main.myinterface.SomeConstant;
import com.appheader.DingQuPostman.user.CurrentUserManager;
import com.appheader.DingQuPostman.user.UserInfo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SlideFragment extends Fragment {
    private final Context context;
    private static final String TAG = "SlideFragment";
    // TODO: Rename parameter arguments, choose names that match
    private OnFragmentInteractionListener mListener;
    @Bind(R.id.iv_user_photo)ImageView userPhoto;
    @Bind(R.id.tv_user_name)TextView userName;
    @Bind(R.id.lv_slide)ListView lvSlide;

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what==1){
                Toast.makeText(context, msg.obj.toString(), Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    });
    private String[] slideItem={"我的业务","我的消息","我的预约","项目进度",
            "我的订制","我的评价","设置"};
    private int[] slideImage={R.mipmap.ico_menu_01,R.mipmap.ico_menu_02,R.mipmap.ico_menu_03,R.mipmap.ico_menu_04,
            R.mipmap.ico_menu_05,R.mipmap.ico_menu_06,R.mipmap.ico_menu_08};
    private UserInfo userInfo;
    private Object resImageId;//保存头像
    private String txtName;//姓名

    public SlideFragment() {
        // Required empty public constructor
        this.context= MyApplication.getContext();
        userInfo=CurrentUserManager.getCurrentUser();
    }

    //使用该fragment的fragment必须实现OnFragmentInteractionListener否则抛出异常

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*//初始化账户余额
        Log.d(TAG, "initData: "+getActivity());
        */
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slide, container, false);
        ButterKnife.bind(this,view);
        initData();
        initView();
        return view;
    }

    private void initView() {
        resImageId=userInfo==null?R.mipmap.ic_status_bar_photo: GlobalVars.getAppServerUrl()+userInfo.getHeadImg();
        txtName=userInfo==null?"请先登录":userInfo.getPetName();
        //圆角图片
        Glide.with(this).load(resImageId).asBitmap().centerCrop().into(new BitmapImageViewTarget(userPhoto){
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                userPhoto.setImageDrawable(circularBitmapDrawable);
            }
        });
        userName.setText(txtName);
        BaseAdapter baseAdapter=new BaseAdapter() {
            @Override
            public int getCount() {
                return slideItem.length;
            }

            @Override
            public Object getItem(int position) {
                return slideItem[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view=LayoutInflater.from(getActivity()).inflate(R.layout.lv_slide_item, null);
                TextView txItemName= (TextView) view.findViewById(R.id.tx_mid);
                ImageView ivHead= (ImageView) view.findViewById(R.id.iv_item_head);
                ivHead.setImageResource(slideImage[position]);
                txItemName.setText(slideItem[position]);

                return view;
            }
        };
        lvSlide.setAdapter(baseAdapter);
        lvSlide.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        //onButtonPressed(0);
                        /*Intent in=new Intent(context,ServiceNavActivity.class);
                        startActivity(in);*/
                        Log.d(TAG, "onItemClick: "+slideItem[position]);
                        break;
                    case 1:
                        Intent ns=new Intent(context,MerchantMapActivity.class);
                        startActivity(ns);
                        Log.d(TAG, "onItemClick: "+slideItem[position]);
                        break;
                    case 2:
                        Intent re=new Intent(context,ShareFriendActivity.class);
                        startActivity(re);
                        Log.d(TAG, "onItemClick: "+slideItem[position]);
                        break;
                    case 3:
                        //onButtonPressed(1);
                        Intent pr=new Intent(context,CommonProblemActivity.class);
                        startActivity(pr);
                        Log.d(TAG, "onItemClick: "+slideItem[position]);
                        break;
                    case 4:
                        startActivity(new Intent(MyApplication.getContext(), ApplyBusinessActivity.class));
                        Log.d(TAG, "onItemClick: "+slideItem[position]);
                        break;
                    case 5:
                        //onButtonPressed(1);
                        startActivity(new Intent(MyApplication.getContext(), LoginActivity.class));                       /* Intent up=new Intent(context,ReviewUpdateActivity.class);
                        startActivity(up);*/
                        Log.d(TAG, "onItemClick: "+slideItem[position]);
                        break;
                    case 6:
                        Intent ab=new Intent(context,AboutActivity.class);
                        ab.putExtra("activity", SomeConstant.ABOUT_US_ACTIVITY);
                        startActivity(ab);
                        Log.d(TAG, "onItemClick: "+slideItem[position]);
                        break;
                }
            }
        });
    }

    private void initData() {
    }

    public void onButtonPressed(int position) {
        if (mListener != null) {
            mListener.replaceFragment(position);
        }
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void  replaceFragment(int position);
    }
    @OnClick(R.id.btn_quit_login)
    public void quitLogin(){
        Toast.makeText(context, "退出登录", Toast.LENGTH_SHORT).show();
        CurrentUserManager.clearCurrentUser();
        startActivity(new Intent(context,LoginActivity.class));
        getActivity().finish();
    }
    /*private void openApp() {
        PackageInfo pi = null;
        try {
            PackageManager pm=getActivity().getPackageManager();
            pi = pm.getPackageInfo(packageName, 0);
            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            resolveIntent.setPackage(pi.packageName);
            List<ResolveInfo> apps = pm.queryIntentActivities(resolveIntent, 0);
            ResolveInfo ri = apps.iterator().next();
            if (ri != null ) {
                String packageName = ri.activityInfo.packageName;
                String className = ri.activityInfo.name;
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                ComponentName cn = new ComponentName(packageName, className);

                intent.setComponent(cn);
                startActivity(intent);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


    }*/

}
