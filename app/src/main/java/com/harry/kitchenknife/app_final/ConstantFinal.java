package com.harry.kitchenknife.app_final;

/**
 * Created by Harry on 2018/10/15.
 */
public class ConstantFinal {

    /**
     * 贩卖机出货指令
     */
    public static final String SHIPMENT = "8e 8e 04 01 02 03 05 0f 55";
    /**
     * 出货成功指令
     */
    public static final String SHIPMENT_SUCCESS = "8e 8e 04 02 01 02 03 0c 55";
    /**
     * 贩卖机故障上传
     */
    public static final String FAULT_UPLOAD = "8e 8e 04 0E 01 02 03 18 55";
    /**
     * 打开回收电磁阀
     */
    public static final String OPEN = "8e 8e 04 03 00 FF 00 06 55";

    /**
     * 用于在长连接的服务里接收广播
     */
    public static final String BROADCAST_ACTION = "BROADCAST_ACTION";
    /**
     * 用户电话号码
     */
    public static final String USER_PHONE = "USER_PHONE";
}
