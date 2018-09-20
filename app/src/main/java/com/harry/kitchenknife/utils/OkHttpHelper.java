package com.harry.kitchenknife.utils;

import android.support.annotation.NonNull;

import java.util.Map;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Harry on 2018/8/13.
 * 对OkHttp的封装
 */
public class OkHttpHelper {

    /**
     * @param url 网络访问路径
     * @param callback 网络访问回调
     */
    public static void get(@NonNull String url, Callback callback) {
        // 创建OKHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * @param url 网络访问路径
     * @param params 网络访问参数
     * @param callback 网络访问回调
     */
    public static void post(@NonNull String url, @NonNull Map<String, String> params, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();

        for (Map.Entry<String, String> map : params.entrySet()) {
            String key = map.getKey();
            String value;
            /**
             * 判断值是否是空的
             */
            if (map.getValue() == null) {
                value = "";
            } else {
                value = map.getValue();
            }
            /**
             * 把key和value添加到formBody中
             */
            builder.add(key, value);
        }

        RequestBody requestBody = builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
