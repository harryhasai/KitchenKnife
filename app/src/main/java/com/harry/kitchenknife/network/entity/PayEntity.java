package com.harry.kitchenknife.network.entity;

/**
 * Created by Harry on 2018/10/15.
 */
public class PayEntity {

    /**
     * code : 100
     * msg : 处理成功！
     * extend : {"TwoDimensionalCode":"https://qr.alipay.com/bax06279uwxvvecxijfs2053"}
     * object : null
     */

    public int code;
    public String msg;
    public ExtendBean extend;
    public Object object;

    public static class ExtendBean {
        /**
         * TwoDimensionalCode : https://qr.alipay.com/bax06279uwxvvecxijfs2053
         */

        public String TwoDimensionalCode;
        public String msg;
    }
}
