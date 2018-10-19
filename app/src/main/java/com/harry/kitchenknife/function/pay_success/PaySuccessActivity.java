package com.harry.kitchenknife.function.pay_success;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.harry.kitchenknife.R;
import com.harry.kitchenknife.app_final.ConstantFinal;
import com.harry.kitchenknife.app_final.URLFinal;
import com.harry.kitchenknife.base.BaseActivity;
import com.harry.kitchenknife.base.presenter.BasePresenter;
import com.harry.kitchenknife.network.entity.PaySuccessEntity;
import com.harry.kitchenknife.utils.DeviceUtil;
import com.harry.kitchenknife.utils.OkHttpHelper;
import com.harry.kitchenknife.utils.SPUtils;
import com.harry.kitchenknife.utils.SerialPortUtils;

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
 * Created by Harry on 2018/9/21.
 * 支付成功页面
 */
public class PaySuccessActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_prompt)
    TextView tvPrompt;
    @BindView(R.id.tv_result)
    TextView tvResult;

    private String path = "/dev/ttyMT0";
    int baudrate = 57600;
    private SerialPortUtils serialPortUtils;
    private String payID;

    @Override
    protected int setupView() {
        return R.layout.activity_pay_success;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        tvPrompt.setText("请等待出刀");
        tvResult.setText("支付成功");
        payID = getIntent().getStringExtra("payID");

        openSerialPort();

    }

    private void openSerialPort() {
        serialPortUtils = new SerialPortUtils();
        try {
            serialPortUtils.openSerialPort(path, baudrate);

            serialPortUtils.sendSerialPort(ConstantFinal.SHIPMENT);//贩卖机出货
        } catch (IOException e) {
            ToastUtils.showShort("打开串口异常");
            e.printStackTrace();
        }

        serialPortUtils.setOnDataReceiveListener(new SerialPortUtils.OnDataReceiveListener() {
            @Override
            public void onDataReceive(byte[] buffer, int size) {
                String s = new String(buffer);
                String knifeNumber = "unknown";
                if (!TextUtils.isEmpty(s)) {
                    //获取菜刀编号
                    knifeNumber = s;


                }
                if (s.equals(ConstantFinal.SHIPMENT_SUCCESS)) {
                    //出货成功
                    //提交参数到服务器
                    uploadKnifeNum(knifeNumber);
                } else if (s.equals(ConstantFinal.FAULT_UPLOAD)) {
                    ToastUtils.showShort("出货失败");
                }

            }
        });
    }

    private void uploadKnifeNum(String knifeNumber) {
        Map<String, String> params = new HashMap<>();
        params.put("billId", payID);
        params.put("commodityNumber", knifeNumber);

        String url = URLFinal.BASE_URL + URLFinal.UPLOAD_KNIFE_NUM;
        OkHttpHelper.post(url, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastUtils.showShort("网络连接错误");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                if (!TextUtils.isEmpty(s)) {
                    Gson gson = new Gson();
                    PaySuccessEntity paySuccessEntity = gson.fromJson(s, PaySuccessEntity.class);
                    if (paySuccessEntity.code == 100) {
                        ToastUtils.showShort("出货成功");
                        finish();
                    } else {
                        ToastUtils.showShort(paySuccessEntity.extend.msg);
                    }
                }
            }
        });
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @OnClick({R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        serialPortUtils.closeSerialPort();
    }
}
