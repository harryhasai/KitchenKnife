package com.harry.kitchenknife.function.buy_pay;

import android.content.Intent;
import android.graphics.Bitmap;
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
import com.harry.kitchenknife.event_bus.SendMessageEvent;
import com.harry.kitchenknife.function.pay_success.PaySuccessActivity;
import com.harry.kitchenknife.network.entity.PayEntity;
import com.harry.kitchenknife.utils.DeviceUtil;
import com.harry.kitchenknife.utils.OkHttpHelper;
import com.harry.kitchenknife.utils.QRCodeUtil;
import com.harry.kitchenknife.utils.SPUtils;
import com.harry.kitchenknife.utils.SerialPortUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
 * 购买的支付页面
 */
public class BuyPayActivity extends BaseActivity {

    @BindView(R.id.iv_cover)
    ImageView ivCover;
    @BindView(R.id.tv_name_of_number)
    TextView tvNameOfNumber;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_deposit)
    TextView tvDeposit;
    @BindView(R.id.iv_ali_pay)
    ImageView ivAliPay;

    /**
     * 菜刀个数
     */
    private String number;
    /**
     * 设备种类编号
     */
    private String commodityTypeNumber;
    /**
     * 菜刀名称
     */
    private String commodityTypeTitle;
    /**
     * 菜刀金额
     */
    private double commodityTypeSellingPrice;

    @Override
    protected int setupView() {
        return R.layout.activity_buy_pay;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        number = getIntent().getStringExtra("number");
        commodityTypeNumber = getIntent().getStringExtra("commodityTypeNumber");
        commodityTypeTitle = getIntent().getStringExtra("commodityTypeTitle");
        commodityTypeSellingPrice = getIntent().getDoubleExtra("commodityTypeSellingPrice", 0);

        initViewParams();
    }

    private void initViewParams() {
        tvNameOfNumber.setText("编号: " + commodityTypeNumber);
        tvName.setText("名称: " + commodityTypeTitle);
        tvNumber.setText("数量: " + number);
        tvDeposit.setText("金额: ¥" + Integer.valueOf(number) * commodityTypeSellingPrice);

        getAliPay();
    }

    /**
     * 获取支付宝支付链接
     */
    private void getAliPay() {
        Map<String, String> params = new HashMap<>();
        params.put("phone", SPUtils.getString(ConstantFinal.USER_PHONE, ""));
        params.put("type", "0");
        params.put("number", number);
        params.put("equipmentNumber", DeviceUtil.getDeviceID());
        params.put("commodityTypeNumber", commodityTypeNumber);
        params.put("payType", "1");

//        String url = URLFinal.BASE_URL + URLFinal.ALI_PAY;
        String url = "http://47.92.226.61/chopperAPP/alipay/createOrder";
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
                    PayEntity payEntity = gson.fromJson(s, PayEntity.class);
                    if (payEntity.code == 100) {
                        String payURL = payEntity.extend.TwoDimensionalCode;
                        final Bitmap qrCodeBitmap = QRCodeUtil.createQRCodeBitmap(payURL, 300, 300);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ivAliPay.setImageBitmap(qrCodeBitmap);
                            }
                        });
                    } else {
                        if (TextUtils.isEmpty(payEntity.extend.msg)) {
                            ToastUtils.showShort(payEntity.msg);
                        } else {
                            ToastUtils.showShort(payEntity.extend.msg);
                        }
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveMessage(SendMessageEvent sendMessageEvent) {
        String message = sendMessageEvent.getMessage();
        //success,xxxxxxxxx
        if (message.contains("success")) {
            //支付成功
            String[] split = message.split(",");
            Intent intent = new Intent(BuyPayActivity.this, PaySuccessActivity.class);
            intent.putExtra("payID", split[1]);
            startActivity(intent);
        } else if (message.contains("fail")){
            //支付失败
            ToastUtils.showShort("支付失败");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
