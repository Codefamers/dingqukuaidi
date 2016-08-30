package com.appheader.DingQuPostman.application;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.telephony.TelephonyManager;

import com.appheader.DingQuPostman.common.data.DataKeeper;
import com.appheader.DingQuPostman.common.utils.Tools;
import com.appheader.DingQuPostman.ui.main.adapter.AlreadyTakeAdapter;
//import com.appheader.DingQuPostman.ui.main.adapter.WaitTakeAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

/**
 * Created by qzp on 15/12/31.
 */
public class MyApplication extends Application{
    public static Context sContext;
    public static DataKeeper sDataKeeper;
    private String configJson;
    public static Typeface TypeFaceYaHei;
    private static TelephonyManager sTelephonyManager;
    //private WaitTakeAdapter waitTakeAdapter;

    public AlreadyTakeAdapter getAlreadyTakeAdapter() {
        return alreadyTakeAdapter;
    }

    public void setAlreadyTakeAdapter(AlreadyTakeAdapter alreadyTakeAdapter) {
        this.alreadyTakeAdapter = alreadyTakeAdapter;
    }

    private AlreadyTakeAdapter alreadyTakeAdapter;
    public String getConfigJson() {
        return configJson;
    }

    public void setConfigJson(String configJson) {
        this.configJson = configJson;
    }

    public static Context getContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        sDataKeeper = new DataKeeper(this, "qzp");
        sTelephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

        GlobalVars.init(this);
//        ImageLoadUtil.initialize(this);
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                ex.printStackTrace();
                System.exit(0);
            }
        });
        TypeFaceYaHei = Typeface.createFromAsset(getAssets(), "fonts/ltxh2.TTF");
        try {
            Field field = Typeface.class.getDeclaredField("SERIF");
            field.setAccessible(true);
            field.set(null, TypeFaceYaHei);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
//        readConfigFile("config/app.manifest.json");
    }

    private void readConfigFile(String configFileName){
        if(configFileName == null || configFileName == ""){
            configJson = null;
            return;
        }
        AssetManager assetManager = getApplicationContext().getAssets();
        InputStream is = null;
        try {
            is = assetManager.open(configFileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuffer stringBuffer = new StringBuffer();
            String str = null;
            while((str = br.readLine())!=null){
                stringBuffer.append(str);
            }
            String jsonStr =  stringBuffer.toString();
            try {
                JSONObject obj = new JSONObject(jsonStr);
                String rootpage = obj.optString("rootpage");
                jsonStr = Tools.strReplace("{root}", rootpage, jsonStr);
            } catch (JSONException e){
                e.printStackTrace();
            }
            setConfigJson(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static TelephonyManager getTelephonyManager() {
        return sTelephonyManager;
    }


    /*public WaitTakeAdapter getWaitTakeAdapter() {
        return waitTakeAdapter;
    }

    public void setWaitTakeAdapter(WaitTakeAdapter waitTakeAdapter) {
        this.waitTakeAdapter = waitTakeAdapter;
    }*/
}
