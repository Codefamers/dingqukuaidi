package com.appheader.DingQuPostman.common.utils.json;

import org.json.JSONObject;

public interface ItemConvertor<T> {

	T convert(JSONObject jsonObject);
}
