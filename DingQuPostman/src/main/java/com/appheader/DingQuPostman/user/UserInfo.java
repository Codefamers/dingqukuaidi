package com.appheader.DingQuPostman.user;

import android.os.Build;
import android.util.Log;
import android.widget.TextView;

import com.appheader.DingQuPostman.application.MyApplication;
import com.appheader.DingQuPostman.common.activity.BaseActivity;
import com.appheader.DingQuPostman.common.network.RequestCallback;
import com.appheader.DingQuPostman.common.network.RequestHelper;
import com.appheader.DingQuPostman.common.network.ResponseEntity;
import com.appheader.DingQuPostman.common.network.UrlConstants;
import com.appheader.DingQuPostman.common.utils.ParamBuilder;
import com.appheader.DingQuPostman.common.utils.json.annotation.JSONEntity;
import com.appheader.DingQuPostman.common.utils.json.annotation.JSONField;
import com.appheader.DingQuPostman.ui.main.myinterface.GetExpressListener;
import com.appheader.DingQuPostman.ui.main.myinterface.RemoveListener;
import com.appheader.DingQuPostman.ui.main.myinterface.SearchListener;
import com.appheader.DingQuPostman.ui.main.myinterface.SuccessRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;

@JSONEntity
public class UserInfo implements Serializable {
    @JSONField
    private String session_id="5779c6b92a06b30f5d6268e5";
    //快递员id
    @JSONField
    private String id="577365b1483cc3044b68b795";
    //用户名
    @JSONField
    private String username;
    //用户昵称
    @JSONField
    private String petName;
    //用户头像
    @JSONField
    public String headImg;
    //用户权限
    @JSONField
    public String permission;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //用户密码
    @JSONField
    public String password;

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

/* @JSONEntity
    public static class Contents implements Serializable {
        @JSONField
        private String clerkNumber;//工牌号
        @JSONField
        private String clerkNumberPic;//工牌号照片
        @JSONField
        private String companyName;//公司名称
        }


    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Contents getContents() {
        return contents;
    }

    public void setContents(Contents contents) {
        this.contents = contents;
    }*/

    /**
     * 获取设备信息（设备型号 + 系统版本）
     *
     * @return
     */
    public static String getDeviceInfo() {
        return Build.MODEL + ";  Android SDK LEVEL:" + Build.VERSION.SDK_INT;
    }

    public static String getImei() {
        return MyApplication.getTelephonyManager().getDeviceId();
    }

    public static boolean isLogined() {
        return CurrentUserManager.getCurrentUser() != null;
    }


}
