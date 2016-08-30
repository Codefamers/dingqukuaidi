package com.appheader.DingQuPostman.common.network;

public interface RequestCallback {

	void onSuccess(ResponseEntity response);

	void onFailure(Exception ex);
	
	void onComplete();
}
