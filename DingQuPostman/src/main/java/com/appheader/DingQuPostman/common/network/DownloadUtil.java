package com.appheader.DingQuPostman.common.network;

import android.text.TextUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Map;

/**
 * 下载文件Util，新建该对象要传入更新进步的对象，和控制对象
 * @author alaowan
 *
 */
public class DownloadUtil {
	
	private int fileSize;
	
	private DownloadListener listener;
	
	private CancelLock cancelLock;
	
	public DownloadUtil(DownloadListener listener, CancelLock cancelLock){
		this.listener = listener;
		this.cancelLock = cancelLock;
	}
	
	public String downloadFile(String url, String savePath, String specFileName, Map<String, String> headers) throws IOException, DownloadException {
		int downLoadFileSize = 0;
		URL myURL = new URL(url);
		URLConnection conn = myURL.openConnection();
		if(headers != null && !headers.isEmpty()){
			conn.setAllowUserInteraction(true);
			Iterator<String> iterator = headers.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				conn.addRequestProperty(key, headers.get(key));
			}
		}
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.connect();
		InputStream is = conn.getInputStream();
		this.fileSize = conn.getContentLength();// 根据响应获取文件大小
		if (is == null)
			throw new IOException("stream is null");
		
		String fileName = null;
		//如果未指定文件名，则取responseHeader里面的文件名
		if(specFileName == null){
			String contentDispostion = conn.getHeaderField("Content-Disposition");
			if(!TextUtils.isEmpty(contentDispostion) && contentDispostion.indexOf("filename=")!=-1){
				contentDispostion = URLDecoder.decode(contentDispostion, "UTF-8");
				fileName = contentDispostion.substring(contentDispostion.indexOf("filename=") + 9);
			}
		}else{
			fileName = specFileName;
		}
		
		if(fileName == null){
			throw new DownloadException("文件信息不完整");
		}
		String fileFullPath = savePath + (savePath.endsWith("/")? "" : "/") + fileName;
		FileOutputStream fos = new FileOutputStream(fileFullPath);
		// 把数据存入路径+文件名
		byte buf[] = new byte[1024];
		listener.sendProgress("fileSize", fileSize);
		do {
			// 循环读取
			int numread = is.read(buf);
			if (numread == -1) {
				break;
			}
			fos.write(buf, 0, numread);
			downLoadFileSize += numread;

			// 更新进度条
			listener.sendProgress("progress", downLoadFileSize);
		} while (!cancelLock.isCancelFlag());
		try {
			is.close();
			fos.close();
		} catch (Exception ex) {
		}
		return cancelLock.isCancelFlag() ? null : fileFullPath;

	}
	
	public boolean isCanceled(){
		return this.cancelLock.isCancelFlag();
	}
	
	public static class CancelLock{
		private boolean cancelFlag;

		public boolean isCancelFlag() {
			return cancelFlag;
		}

		public void setCancelFlag(boolean cancelFlag) {
			this.cancelFlag = cancelFlag;
		}
		
	}
	
	public interface DownloadListener{
		
		void sendProgress(Object... objects);
	}
	
	public static class DownloadException extends Exception {
		private static final long serialVersionUID = -8434344288763060179L;
		
		public DownloadException(String msg){
			super(msg);
		}
		
	}
}
