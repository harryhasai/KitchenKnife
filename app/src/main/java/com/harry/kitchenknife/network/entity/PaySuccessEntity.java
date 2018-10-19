package com.harry.kitchenknife.network.entity;

/**
 * Created by Harry on 2018/10/18.
 */
public class PaySuccessEntity {

    /**
     * code : 100
     * msg : 处理成功！
     * extend : {}
     * object : null
     */

    public int code;
    public String msg;
    public ExtendBean extend;
    public Object object;

    public static class ExtendBean {
        public String msg;
    }
}
