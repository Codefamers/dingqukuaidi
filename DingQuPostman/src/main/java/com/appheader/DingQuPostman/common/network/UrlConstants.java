package com.appheader.DingQuPostman.common.network;

import android.text.TextUtils;


import com.appheader.DingQuPostman.application.GlobalVars;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

/**
 * 服务器接口url常量类，所有访问服务器的url必须在此类中定义常量。
 * 当res/raw/config.properties文件中的“useSimulateData
 * ”为true时，调用RequestHelper访问数据时，则不访问网络，而是读取本地模拟数据的txt文件
 * txt文件放在assets/simulate_data目录下，文件名位常量名+.txt
 * 
 * @author alaowan
 * 
 */
public class UrlConstants {

	// 存放url与常量名映射关系的map，key为服务器接口url，value为该url在本类中对应的常量名称
	public static HashMap<String, String> urlMap;

	static {
		urlMap = new HashMap<String, String>();

		// 获取此类中定义的全部变量
		Field[] fields = UrlConstants.class.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			// 过滤出类型为String，且有public和static修饰符的变量
			if (fields[i].getType() == String.class
					&& Modifier.isPublic(fields[i].getModifiers())
					&& Modifier.isStatic(fields[i].getModifiers())) {
				try {
					String fieldValue = (String) fields[i].get(null);
					String fieldName = fields[i].getName();
					// 放入map中，key为url，value为变量名
					urlMap.put(fieldValue, fieldName);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 生成读取资源文件流的URL
	 * 
	 * @param resourceId
	 * @return
	 */
	public static String getResourceUrl(String resourceId) {
		if(!TextUtils.isEmpty(resourceId)){
			if (resourceId.startsWith("demo:")) {
				return "assets://demo_pics/" + resourceId.substring(5);
			} else if (resourceId.startsWith("http")) {
				return resourceId;
			}
		}
		return GlobalVars.getAppServerUrl() + READ_RESOURCE_STREAM
				+ "&resourceId=" + resourceId;
	}

	/**
	 * 生成读取图片缩略图文件流的URL
	 * 
	 * @param resourceId
	 * @return
	 */
	public static String getImageThumbUrl(String resourceId) {
		if (resourceId.startsWith("demo:")) {
			return "assets://demo_pics/" + resourceId.substring(5);
		} else if (resourceId.startsWith("http")) {
			return resourceId;
		}
		return GlobalVars.getAppServerUrl() + READ_IMAGE_THUMB_STREAM
				+ "&resourceId=" + resourceId;
	}

	/**
	 * 文件图片资源相关 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	 */

	// 读取文件资源
	public static final String READ_RESOURCE_STREAM = "/resource/client.do?method=read";
	// 读取图片缩略图
	public static final String READ_IMAGE_THUMB_STREAM = "/resource/client.do?method=readImageThumb";
	// 上传文件（image/audio）
	public static final String UPLOAD_FILE_RESOURSE = "/resource/client.do?method=upload";
	//获取验证码
	public static final String SEND_LOGIN_SMS="/rest/login/login.do?method=sendLoginSMS";
	//登录接口
	public static final String POST_MAN_LOGIN="/rest/login/login.do?method=postmanLogin";
	//收支列表
	public static final String GET_FLOW_LIST="/rest/business/postman/postmanAccount.do?method=getFlowList";
	//获取消费记录
	public static final String GET_CONSUME_RECORD="/rest/business/postman/postman.do?method=getConsumeRecord";
	//获取快件列表
	public static final String GET_PACKAGE_LIST="/rest/business/postPackage/postPackage.do?method=getPackageList";
	//获取快递员审核信息
	public static final String GET_POSTMAN_REVIEW="/rest/business/postman/postman.do?method=getPostmanReview";
	//获取所有快递公司列表
	public static final String GET_COMPANY_LIST="/rest/express_company/company.do?method=getCompanyList";
	//提交认证信息
	public static final String SUBMIT_POSTMAN="/rest/business/postman/postman.do?method=submitPostman";
	//查询余额
	public static final String GET_ACCOUNT="/rest/business/postman/postmanAccount.do?method=getAccount";
	//增加快件
	public static final String ADD_POST_POCKAGE="/rest/business/postPackage/postPackage.do?method=addPostPackage";
	//删除快件
	public static final String REMOVE_POST_PACKAGE="/rest/business/postPackage/postPackage.do?method=removePostPackage";
	//登录
	public static final String DENG_LU="denglu.call";
	//注册
	public static final String ZHU_CE="zhuce.call";
	//获取验证码
	public static final String YAN_ZHENG_MA="yanzhengma.call";
	//修改密码
	public static final String XIU_GAI_MI_MA="xiugaimima.call";
	//提交任务
	public static final String RENWU_DENGJI="renwudengji.call";
	//我的任务
	public static final String WODE_RENWU="woderenwu.call";
	//上传图片
	public static final String SHANGCHUANTUPIAN="shangchuantupian.call";
	//文章发布
	public static final String WENZHANGFABU="wenzhangfabu.call";
	//发布通知
	public static final String TONGZHIFABU="tongzhifabu.call";
	//查看通知
	public static final String TONGZHIYULAN="tongzhiyilan.call";
	//文章预览
	public static final String WENZHANGYULAN="wenzhangyilan.call";
	//评论提交
	public static final String PINGLUNTIJIAO="pingluntijiao.call";
	//评论预览
	public static final String PINGLUNYILAN="pinglunyilan.call";
}
