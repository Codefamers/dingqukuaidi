package com.appheader.DingQuPostman.common.network;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;


import com.appheader.DingQuPostman.R;
import com.appheader.DingQuPostman.application.GlobalVars;
import com.appheader.DingQuPostman.common.activity.ActivityManager;
import com.appheader.DingQuPostman.common.activity.BaseActivity;
import com.appheader.DingQuPostman.common.utils.AES;
import com.appheader.DingQuPostman.common.utils.ActivityUtil;
import com.appheader.DingQuPostman.common.utils.FileUtils;
import com.appheader.DingQuPostman.common.utils.LogUtil;
import com.appheader.DingQuPostman.common.utils.MD5;
import com.appheader.DingQuPostman.common.utils.Tools;
import com.appheader.DingQuPostman.user.CurrentUserManager;
import com.appheader.DingQuPostman.user.UserInfo;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Http请求公共类
 * 
 * @author alaowan
 * 
 */
public class RequestHelper {

	private static final String TAG = RequestHelper.class.getName();

	private static final int timeoutConnection = 30000;
	private static final int timeoutSocket = 40000;

	/**
	 * 向应用服务器发送请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public static ResponseEntity sendRequest(String url, Map<String, String> params) throws Exception {
		String responseText = null;
		if (params == null)
			params = new HashMap<String, String>();
		String fullUrl = getFullUrl(url);
		logReqeustParams(params, fullUrl);
		if (GlobalVars.useSimulateData)
			responseText = readSimulateData(url);
		else
			responseText = sendRequestForContent(fullUrl, params);
		if (GlobalVars.isDebug()) {
			LogUtil.info(TAG,
					"HttpResponse returned -------------------------------------");
			LogUtil.info(TAG, responseText);
			LogUtil.info(TAG,
					"<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		}
		return getResponseEntity(responseText);
	}

	/**
	 * 发送异步请求
	 * 
	 * @param url
	 * @param params
	 * @param callback
	 */
	public static void sendAsyncRequest(String url, Map<String, String> params,
										RequestCallback callback) {
		try {
			new AsyncRequestTask(url, params, callback).execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送异步请求
	 * 
	 * @param url
	 * @param params
	 * @param callback
	 */
	public static void sendAsyncRequest(BaseActivity activity, String url,
										Map<String, String> params, RequestCallback callback) {
		try {
			new AsyncRequestTask(activity, url, params, callback).execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String generateParamsMD5(Map<String, String> params) {
		StringBuffer paramsStr = new StringBuffer();
		if (params == null || params.isEmpty()) {
		} else {
			List<String> pNameList = new ArrayList<>();
			pNameList.addAll(params.keySet());
			Collections.sort(pNameList);
			for (int i = 0; i < pNameList.size(); i++) {
				String pName = pNameList.get(i);
				String pVal = params.get(pName);
				paramsStr.append(pName).append("=").append(pVal);
				if (i != pNameList.size() - 1)
					paramsStr.append("&");
			}
		}
		return MD5.encrypt(paramsStr.toString());
	}

	/**
	 * 加密参数
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	private static String generateSecureStr(String url,
											Map<String, String> params) {
		int base = (int) (Math.random() * 100000);
		String numberStr = String.valueOf(base * 7);
		return AES
				.encrypt((url + "|" + generateParamsMD5(params) + "|" + numberStr)
						.getBytes());
	}

	// public static void main(String[] args) {
	// Map<String, String> params = new HashMap<String, String>();
	// params.put("method", "getManifest");
	// params.put("carModel", "025-009-00001");
	// params.put("mileage", "5000");
	// params.put("cityCode", "120100");
	// System.out.println(generateSecureStr("http://127.0.0.1:8080/cjt/rest/business/maintain/reserve.do",
	// params));
	// }

	/**
	 * 发送Http请求，并返回服务器响应的原始字符串
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public static String sendRequestForContent(String url, Map<String, String> params) throws IOException {
		byte[] bytes = sendRequestForBytes(url, params);
		String resultJson = null;
		if (bytes != null && bytes.length > 0) {
			resultJson = new String(bytes, "UTF-8");
		}
		return resultJson;
	}

	private static void logReqeustParams(Map<String, String> params,
			String fullUrl) {
		if (GlobalVars.isDebug()) {
			LogUtil.info(TAG,
					"HttpRequest begin >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			LogUtil.info(TAG, fullUrl);
			if (!params.isEmpty()) {
				Iterator<String> iterator = params.keySet().iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					LogUtil.info(TAG, key + "=" + params.get(key));
				}
			} else {
				LogUtil.info(TAG, "parameters is empty.");
			}
		}
	}

	/**
	 * 根据server name获取完整的url
	 * 
	 * @param
	 * @return
	 */
	private static String getFullUrl(String url) {
		return GlobalVars.getAppServerUrl() + url;
	}


	//封装返回数据
	private static ResponseEntity getResponseEntity(String text) {
		ResponseEntity entity = null;
		if (text == null) {
			try {
				text = makeErrorJsonResult("获取数据失败");
			} catch (JSONException e) {
				LogUtil.error(TAG, e);
			}
		}
		if (!TextUtils.isEmpty(text)) {
			entity = new ResponseEntity();
			entity.setOriginalText(text);
			try {
				JSONObject json = new JSONObject(text);
				if (json.has("success")
						&& json.getString("success").equals("true"))
					entity.setSuccess(true);
				entity.setErrorCode(Tools.getJsonString(json, "errorCode"));
				// 会话失效，提示并跳转至登录
				/*if ("session_expired".equals(entity.getErrorCode())) {
					ActivityUtil.sessionTimeout(ActivityManager.getInstance()
							.currentActivity());
				} else if ("invalid_version".equals(entity.getErrorCode())) {// 当前客户端版本已失效，提示升级
					final Activity currentActivity = ActivityManager
							.getInstance().currentActivity();
					if (currentActivity != null) {
						currentActivity.runOnUiThread(new Runnable() {
							@Override
							public void run() {
							}
						});
					}
				}*/
				entity.setErrorMessage(Tools.getJsonString(json, "errorMsg"));
				if (json.has("data")) {
					entity.setDataObject(json.getJSONObject("data"));
				}
			} catch (Exception e) {
				LogUtil.error(TAG, e);
				Log.d(TAG, "getResponseEntity: "+GlobalVars.getContext().getString(
						R.string.pub_network_error));
				entity.setErrorMessage(GlobalVars.getContext().getString(
						R.string.pub_network_error));
			}
		}
		return entity;
	}

	/**
	 * 发送Http请求，并返回服务器响应的byte数据
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 */
	private static byte[] sendRequestForBytes(String url,
			Map<String, String> params) throws IOException {
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		Iterator<String> paramIterator = params.keySet().iterator();
		// 封装参数
		while (paramIterator.hasNext()) {
			String key = paramIterator.next();
			paramList.add(new BasicNameValuePair(key, params.get(key)));
		}
		// 创建httpclient对象
		BasicHttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters,
				timeoutConnection);
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		HttpClient httpclient = new DefaultHttpClient(httpParameters);
		// 创建HttpPost对象
		HttpPost httppost = new HttpPost(url);
		Map<String, String> defaultHeader = makeDefaultHeader(url, params);
		Iterator<String> iterator = defaultHeader.keySet().iterator();
		// 添加头部
		while (iterator.hasNext()) {
			String headerKey = iterator.next();
			httppost.addHeader(headerKey, defaultHeader.get(headerKey));
		}
		httppost.getParams().setParameter("http.protocol.cookie-policy",
				CookiePolicy.NETSCAPE);
		// 设置httpPost请求参数
		httppost.setEntity(new UrlEncodedFormEntity(paramList, HTTP.UTF_8));
		// 使用execute方法发送HTTP Post请求，并返回HttpResponse对象
		HttpResponse httpResponse = httpclient.execute(httppost);
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		if (statusCode == HttpStatus.SC_OK) {
			// 获得返回结果
			return EntityUtils.toByteArray(httpResponse.getEntity());
		}
		return null;
	}

	/**
	 * 生成默认的http请求header
	 * 
	 * @return
	 */
	public static Map<String, String> makeDefaultHeader(String url,
														Map<String, String> params) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("platform", "android");
		map.put("versionCode", String.valueOf(GlobalVars.getVersionCode()));
		map.put("clientType", "customer");
		map.put("ss", generateSecureStr(url, params));
		UserInfo currentUser = CurrentUserManager.getCurrentUser();
		if (currentUser != null)
			map.put("session_id", currentUser.getSession_id());
		if (GlobalVars.isDebug())
			LogUtil.info(TAG, "sessionId: " + map.get("session_id"));
		return map;
	}

	/**
	 * 处理本地模拟数据 ---------------------start
	 */

	/**
	 * 根据url读取本地模拟数据
	 * 
	 * @param url
	 * @return
	 */
	private static String readSimulateData(String url) {
		String resultJsonStr = null;
		String constantsFieldName = UrlConstants.urlMap.get(url);
		try {
			if (constantsFieldName != null) {
				String content = FileUtils.readTextFromAssets("simulate_data/"
						+ constantsFieldName + ".txt");
				if (content == null)
					resultJsonStr = makeErrorJsonResult("模拟数据错误：未找到常量名称对应的模拟数据文件（"
							+ constantsFieldName + ".txt）。");
				else {
					resultJsonStr = content;
					// Thread.sleep(1000);
				}
			} else {
				resultJsonStr = makeErrorJsonResult("模拟数据错误：未找到url对应的Field，请检查是否在UrlConstants类中定义了常量。");
			}
		} catch (Exception e) {
			LogUtil.error(TAG, e);
		}
		return resultJsonStr;
	}

	/**
	 * 拼装错误信息的json
	 * 
	 * @param errMsg
	 * @return
	 * @throws JSONException
	 */
	private static String makeErrorJsonResult(String errMsg)
			throws JSONException {
		JSONObject json = new JSONObject();
		json.put("success", "false");
		json.put("data", new JSONObject());
		json.put("errorMsg", errMsg);
		json.put("errCode", "");
		return json.toString();
	}

	/**
	 * 处理本地模拟数据 ---------------------end
	 */

	public static class AsyncRequestTask extends AsyncTask<Void, Void, Object> {

		private String url;
		private Map<String, String> params;
		private RequestCallback callback;
		private BaseActivity activity;

		public AsyncRequestTask(String url, Map<String, String> params,
								RequestCallback callback) {
			this.url = url;
			this.params = params;
			this.callback = callback;
		}

		public AsyncRequestTask(BaseActivity activity, String url,
								Map<String, String> params, RequestCallback callback) {
			this.url = url;
			this.params = params;
			this.callback = callback;
			this.activity = activity;
		}

		@Override
		protected Object doInBackground(Void... args) {
			try {
				return sendRequest(url, params);
			} catch (Exception e) {
				e.printStackTrace();
				return e;
			}
		}

		@Override
		protected void onPostExecute(Object result) {
			if (this.activity != null && !this.activity.ALIVE)
				return;
			callback.onComplete();
			if (result instanceof ResponseEntity)
				callback.onSuccess((ResponseEntity) result);
			else if (result instanceof Exception)
				callback.onFailure((Exception) result);
		}

	}
}
