package com.harry.kitchenknife.function.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.harry.kitchenknife.R;
import com.harry.kitchenknife.app_final.ConstantFinal;
import com.harry.kitchenknife.app_final.URLFinal;
import com.harry.kitchenknife.base.BaseActivity;
import com.harry.kitchenknife.base.presenter.BasePresenter;
import com.harry.kitchenknife.function.buy.BuyActivity;
import com.harry.kitchenknife.function.recycle.RecycleActivity;
import com.harry.kitchenknife.function.renting.RentingActivity;
import com.harry.kitchenknife.network.entity.LoginEntity;
import com.harry.kitchenknife.utils.DeviceUtil;
import com.harry.kitchenknife.utils.MD5Util;
import com.harry.kitchenknife.utils.OkHttpHelper;
import com.harry.kitchenknife.utils.SPUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Harry on 2018/10/16.
 * 登录页面
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_scanning_login)
    TextView tvScanningLogin;
    /**
     * 判断是由哪个页面跳转到此页面的
     */
    private String name;

    @Override
    protected int setupView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        name = getIntent().getStringExtra("name");

    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @OnClick({R.id.btn_login, R.id.tv_scanning_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                go2Login();
                break;
            case R.id.tv_scanning_login:

                break;
        }
    }

    private void go2Login() {
        final String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            ToastUtils.showShort("用户名或密码不能为空");
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("equipmentNumber", DeviceUtil.getDeviceID());
        params.put("phone", username);
        params.put("password", MD5Util.MD5Encode("capua" + password+ "capua", "utf-8"));

        OkHttpHelper.post(URLFinal.BASE_URL + URLFinal.LOGIN, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastUtils.showShort("网络连接错误");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                if (!TextUtils.isEmpty(s)) {
                    Gson gson = new Gson();
                    LoginEntity loginEntity = gson.fromJson(s, LoginEntity.class);
                    if (loginEntity.code == 100) {
                        //登录成功, 跳转到后续的页面
                        switch (name) {
                            case "renting"://出租
                                startActivity(new Intent(LoginActivity.this, RentingActivity.class));
                                break;
                            case "buy"://购买
                                startActivity(new Intent(LoginActivity.this, BuyActivity.class));
                                break;
                            case "recycle"://回收
                                startActivity(new Intent(LoginActivity.this, RecycleActivity.class));
                                break;
                        }
                        SPUtils.putString(ConstantFinal.USER_PHONE, username);
                        finish();
                    } else {
                        ToastUtils.showShort(loginEntity.msg);
                    }
                }
            }
        });

    }
}
