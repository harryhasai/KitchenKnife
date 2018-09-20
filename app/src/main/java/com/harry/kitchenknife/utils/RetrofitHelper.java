package com.harry.kitchenknife.utils;

import com.harry.kitchenknife.app_final.URLFinal;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Harry on 2017/7/7.
 * Retrofit的帮助类
 */

public class RetrofitHelper {

    public static RetrofitHelper helper;

    private Retrofit retrofit;
    private final static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response response = chain.proceed(chain.request());
                    String s = response.header("Set-Cookie");
                    SPUtils.putString("cookie", s);
                    return response;
                }
            })
            .connectTimeout(15000, TimeUnit.MILLISECONDS)
            .readTimeout(15000, TimeUnit.MILLISECONDS)
            .build();

    public RetrofitHelper() {
        initRetrofit();
    }

    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(URLFinal.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(ScalarsConverterFactory.create())//这里是String解析的
                .build();
    }

    public static synchronized RetrofitHelper getInstance() {
        if (helper == null) {
            helper = new RetrofitHelper();
        }
        return helper;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
