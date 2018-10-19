package com.harry.kitchenknife.app_final;

/**
 * Created by Harry on 2018/9/19.
 */
public class URLFinal {
    /**
     * BaseUrl
     */
//    public static final String BASE_URL = "http://192.168.1.10:8080/chopperAPP/";
    public static final String BASE_URL = "http://47.92.226.61/chopperAPP/";

    /**
     * 贩卖机主页显示(控制主页显示)
     */
    public static final String MAIN_PAGE = "vendingmachines/mainPage";
    /**
     * 获取设备存储的菜刀个数
     */
    public static final String GET_KNIFE_COUNT = "vendingmachines/getProductClassificationInformation";
    /**
     * 支付宝支付
     */
    public static final String ALI_PAY = "alipay/createOrder";
    /**
     * 登录
     */
    public static final String LOGIN = "vmLogin";
    /**
     * 贩卖机出刀之后上传菜刀编号
     */
    public static final String UPLOAD_KNIFE_NUM = "uploadCommodityId";

}
