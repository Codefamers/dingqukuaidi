package com.appheader.DingQuPostman.ui.main.myinterface;

/**
 * Created by Administrator on 2016/7/1 0001.
 */
public interface SomeConstant {
    //错误信息码
    int EOOR_CODE = -1;

    //未认证
    int NO_IDENTIFICATION = 1;
    //未审核通过
    int NO_CHECKED = 4;
    //审核通过
    int CHECKED = 3;
    //审核中
    int CHECKING = 2;
    //账号后台关闭
    int BG_CLOSE = 5;
    //账户被后台关闭提示信息code
    int BG_CLOSE_CODE = -5;

    //待存件
    int WAIT_SAVE = 1;
    //用电业务adapter标识
    int USE_ELECTRICITY_BUSINESS=0;
    //服务导航标识
    int SERVE_NAVIGATION=1;
    //综合查询标识
    int ALL_SEARCH=2;
    //温馨小家
    int WARM_HOME=3;
    //交流沟通
    int COMMUNICATION_EACH=4;
    //验证码
    int AUTH_CODE=100;
    //互动交流贴
    int EACH_COMMUNICATE=0;

    //技术交流
    int COMMUNICATION_TECHNIQUE=1;
    //生活交流
    int COMMUNICATION_LIFE=2;

    //停电查询
    int NO_POWER_SEARCH_ACTIVITY=1;
    String OUTAGE_INFORMATION="http://60.28.54.31:7003/web/sc/zmq/integrated_query/outage_information/init";
    //网点信息查询
    int NET_INFOMATION_SEARCH_ACTIVITY=2;
    String SERVICE_NETWORK="http://60.28.54.31:7003/web/sc/zmq/integrated_query/service_network/init";
    //关于我们
    int ABOUT_US_ACTIVITY=3;
    String ABOUT_US="http://60.28.54.31:7003/web/sc/zmq/account/login/home#";
    //高压新增
    String HIGH_PRESS_NEW_ADD="http://60.28.54.31:7003/web/sc/zmq/advisory_navigation/openinfo/showProcess?processNo=104";
    //高压增容
    String HIGH_PRESS_ADD_VOLUME="http://60.28.54.31:7003/web/sc/zmq/advisory_navigation/openinfo/showProcess?processNo=111";
    //公司介绍
    String COMPANY_INTRODUCE="http://60.28.54.31:7003/web/sc/zmq/advisory_navigation/openinfo/companyintroduction";
    //服务承诺
    String SERVICE_PROMISE="http://60.28.54.31:7003/web/sc/zmq/advisory_navigation/openinfo/servicepromise";
    //收费标准
    String CHARGE_STANDARD="http://60.28.54.31:7003/web/sc/zmq/advisory_navigation/openinfo/chargestandard";
    //政策法规
    String POLICY_REGULATIONS="http://60.28.54.31:7003/web/sc/zmq/advisory_navigation/openinfo/policyregulations";
    //安全用电
    String SAFE_ELECTRICITY="http://60.28.54.31:7003/web/sc/zmq/advisory_navigation/openinfo/safeelectricity";
    //节能常识
    String ENERY_SAVING="http://60.28.54.31:7003/web/sc/zmq/advisory_navigation/openinfo/energysaving";
    //阶梯电价
    String STEP_PRICE="http://60.28.54.31:7003/web/sc/zmq/advisory_navigation/openinfo/stepprice";
    //业务指南
    String BUSINESS_GUIDE="http://60.28.54.31:7003/web/sc/zmq/advisory_navigation/openinfo/businessguide";
    //便民服务
    String SERVICE_INFORMATION="http://60.28.54.31:7003/web/sc/zmq/advisory_navigation/openinfo/serviceinformation";
    //365服务
    String UNDERSTAND_365_SERVICE="http://60.28.54.31:7003/web/sc/zmq/advisory_navigation/openinfo/understand365service";
    //能效服务
    String EFFICIENCY_SERVICE="http://60.28.54.31:7003/web/sc/zmq/advisory_navigation/openinfo/efficiencyservice";
    //电动汽车服务
    String VEHICLE_SERVICE="http://60.28.54.31:7003/web/sc/zmq/advisory_navigation/openinfo/vehicleservice";
}
