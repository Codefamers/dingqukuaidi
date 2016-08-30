package com.appheader.DingQuPostman.ui.main.myinterface;

import org.json.JSONArray;

/**
 * Created by Administrator on 2016/7/18 0018.
 */
public interface SearchListener {
    void success(JSONArray array);
    void fail(String string);
}
