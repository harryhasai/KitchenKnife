package com.harry.kitchenknife.function.buy;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.harry.kitchenknife.R;
import com.harry.kitchenknife.app_final.URLFinal;
import com.harry.kitchenknife.base.BaseActivity;
import com.harry.kitchenknife.base.presenter.BasePresenter;
import com.harry.kitchenknife.function.buy_pay.BuyPayActivity;
import com.harry.kitchenknife.network.entity.GetKnifeCountEntity;
import com.harry.kitchenknife.network.entity.MainEntity;
import com.harry.kitchenknife.utils.DeviceUtil;
import com.harry.kitchenknife.utils.OkHttpHelper;

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
 * Created by Harry on 2018/9/20.
 * 购买页面
 */
public class BuyActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_cover)
    ImageView ivCover;
    @BindView(R.id.tv_name_of_number)
    TextView tvNameOfNumber;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.tv_reduce)
    TextView tvReduce;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_plus)
    TextView tvPlus;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    /**
     * 记录加减的数量
     */
    private int number;

    /**
     * 菜刀个数
     */
    private int knifeCount;
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
        return R.layout.activity_buy;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        number = Integer.parseInt(tvNumber.getText().toString().trim());

        getKnifeCount();
    }

    private void getKnifeCount() {
        Map<String, String> params = new HashMap<>();
        params.put("equipmentNumber", DeviceUtil.getDeviceID());
        params.put("equipmentRoleId", "2");

        OkHttpHelper.post(URLFinal.BASE_URL + URLFinal.GET_KNIFE_COUNT, params, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                ToastUtils.showShort("网络连接错误");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                if (!TextUtils.isEmpty(s)) {
                    Gson gson = new Gson();
                    GetKnifeCountEntity getKnifeCountEntity = gson.fromJson(s, GetKnifeCountEntity.class);
                    if (getKnifeCountEntity.code == 100) {
                        GetKnifeCountEntity.ExtendBean.CommodityTypesBean bean = getKnifeCountEntity.extend.commodityTypes.get(0);
                        knifeCount = bean.count;
                        commodityTypeNumber = bean.commodityTypeNumber;
                        commodityTypeTitle = bean.commodityTypeTitle;
                        commodityTypeSellingPrice = bean.commodityTypeSellingPrice;
                    } else {
                        ToastUtils.showShort("请求码200");
                    }
                }
            }
        });
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @OnClick({R.id.iv_back, R.id.tv_reduce, R.id.tv_plus, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_reduce:
                if (number != 0) {
                    number--;
                    tvNumber.setText(String.valueOf(number));
                }
                break;
            case R.id.tv_plus:
                if (number <= knifeCount) {
                    number++;
                    tvNumber.setText(String.valueOf(number));
                } else {
                    tvNumber.setText(String.valueOf(number));
                }

                break;
            case R.id.btn_commit:
                if (!TextUtils.isEmpty(commodityTypeNumber)) {
                    Intent intent = new Intent(this, BuyPayActivity.class);
                    String number = tvNumber.getText().toString().trim();
                    intent.putExtra("number", number);
                    intent.putExtra("commodityTypeNumber", commodityTypeNumber);
                    intent.putExtra("commodityTypeTitle", commodityTypeTitle);
                    intent.putExtra("commodityTypeSellingPrice", commodityTypeSellingPrice);
                    startActivity(intent);
                }
                break;
        }
    }
}
