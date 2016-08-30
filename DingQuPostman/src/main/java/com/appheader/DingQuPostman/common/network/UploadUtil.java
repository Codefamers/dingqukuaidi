package com.appheader.DingQuPostman.common.network;


import android.util.Log;

import com.appheader.DingQuPostman.application.GlobalVars;
import com.appheader.DingQuPostman.common.utils.LogUtil;
import com.appheader.DingQuPostman.ui.main.myinterface.SomeConstant;
import com.appheader.DingQuPostman.user.CurrentUserManager;

import org.apache.http.util.NetUtils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

/**
 * 文件上传util，直接上传不带更新进步的功能
 */
public class UploadUtil {
	private static final String BOUNDARY =  UUID.randomUUID().toString(); // 边界标识 随机生成
	private static UploadUtil uploadUtil;

	private UploadUtil() {

	}

	public static UploadUtil getInstance() {
		if (null == uploadUtil) {
			uploadUtil = new UploadUtil();
		}
		return uploadUtil;
	}

	public interface OnProgressUpdateListener {
		void onUpdate(int progress);
	}

	public interface ResponseCallback {
		void onResponse(String responseData);
	}
	
	public String uploadFile(String filePath, String requestURL) throws Exception {
		File file = new File(filePath);
		FileInputStream fis = new FileInputStream(file);
		return uploadFile(file, GlobalVars.getAppServerUrl() + requestURL);
	}

	public String uploadFile(File file, String requestURL) throws Exception {
		//FileInputStream inputStream = new FileInputStream(file);
		String CHARSET = "utf-8"; //设置编码
		String BOUNDARY = UUID.randomUUID().toString();
		String PREFIX = "--" , LINE_END = "\r\n"; //边界标识 随机生成
		String CONTENT_TYPE = "multipart/form-data"; //内容类型

		StringBuilder paramStr = new StringBuilder();
		//String sessionId = CurrentUserManager.getCurrentUser().getSession_id();
		//requestURL + paramStr.toString()
		Log.d("aa", "uploadFile: "+requestURL);
		URL url = new URL(requestURL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setConnectTimeout(1000 * 10);
		conn.setReadTimeout(60 * 1000 * 2);
		//conn.addRequestProperty("session_id", sessionId);
		conn.addRequestProperty("platform", "android");
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Connection", "Keep-Alive");
		conn.setRequestProperty("Charset", CHARSET);
		conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);

		DataOutputStream ds = new DataOutputStream(conn.getOutputStream());
		//上传图片
		StringBuffer sb = new StringBuffer();
		sb.append(PREFIX);
		sb.append(BOUNDARY); sb.append(LINE_END);
		/**
		 * 这里重点注意：
		 * name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
		 * filename是文件的名字，包含后缀名的 比如:abc.png
		 */
		sb.append("Content-Disposition: form-data; name=\"img\"; filename=\""+file.getName()+"\""+LINE_END);
		sb.append("Content-Type: application/octet-stream; charset="+CHARSET+LINE_END);
		sb.append(LINE_END);

		ds.write(sb.toString().getBytes());
		InputStream is = new FileInputStream(file);
		byte[] bytes = new byte[1024];
		int len = 0;
		while((len=is.read(bytes))!=-1)
		{
			ds.write(bytes, 0, len);
		}
		is.close();
		ds.write(LINE_END.getBytes());
		byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();
		ds.write(end_data);
		ds.flush();
		/*int bufferSize = 2048;
		byte[] buffer = new byte[bufferSize];

//		long sendTotalLength = 0;

		int length = -1;
		while ((length = inputStream.read(buffer)) != -1) {
			ds.write(buffer, 0, length);
//			sendTotalLength += length;
//			listener.onUpdate(Float.valueOf(((float) sendTotalLength / totalLength) * 100).intValue());
		}

		inputStream.close();
		ds.flush();*/

		// 解析返回数据
		InputStream responseIs = conn.getInputStream();
		int ch;
		StringBuffer b = new StringBuffer();
		while ((ch = responseIs.read()) != -1) {
			b.append((char) ch);
		}

		LogUtil.debug("UploadUtil", "upload complete! response: " + b.toString());
		ds.close();

		return b.toString();
	}
}
