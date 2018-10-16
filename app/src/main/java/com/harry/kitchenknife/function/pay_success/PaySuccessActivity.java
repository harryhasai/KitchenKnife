package com.harry.kitchenknife.function.pay_success;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.harry.kitchenknife.R;
import com.harry.kitchenknife.app_final.ConstantFinal;
import com.harry.kitchenknife.base.BaseActivity;
import com.harry.kitchenknife.base.presenter.BasePresenter;
import com.harry.kitchenknife.utils.SerialPortUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    private String path = "/dev/ttyMT4";
    int baudrate = 57600;
    private SerialPortUtils serialPortUtils;

    @Override
    protected int setupView() {
        return R.layout.activity_pay_success;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        tvPrompt.setText("请等待出刀");
        tvResult.setText("支付成功");

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
                if (s.equals(ConstantFinal.SHIPMENT_SUCCESS)) {
                    //出货成功
                    ToastUtils.showShort("出货成功");
                    finish();
                } else {
                    if (!TextUtils.isEmpty(s)) {
                        //获取到刀具的编号, 上传给服务器

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
