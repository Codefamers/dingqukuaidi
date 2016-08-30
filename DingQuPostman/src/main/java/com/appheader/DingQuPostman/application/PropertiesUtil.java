package com.appheader.DingQuPostman.application;

import java.util.Properties;

/**
 * 服务器配置 AppServer和XMPP的配置信息
 * 
 * @author qzp
 * 
 */
public class PropertiesUtil {

	private static Properties sProps;

	static {
		sProps = GlobalVars.getAppConfiguration();
	}

	/**
	 * 获取应用服务器URL
	 * 
	 * @return
	 */
	public static String getAppServerUrl() {
		return sProps.getProperty("appserver.url", "");
	}

}
