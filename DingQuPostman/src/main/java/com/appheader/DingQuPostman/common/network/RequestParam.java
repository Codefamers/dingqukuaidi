package com.appheader.DingQuPostman.common.network;

import java.util.HashMap;


public class RequestParam {

	private HashMap<String, String> map = new HashMap<String, String>();

	public HashMap<String, String> toHashMap() {
		return map;
	}

	public RequestParam append(String key, String value) {
		map.put(key, value);
		return this;
	}

}
