package com.harry.kitchenknife.function.renting;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.harry.kitchenknife.R;
import com.harry.kitchenknife.app_final.URLFinal;
import com.harry.kitchenknife.base.BaseActivity;
import com.harry.kitchenknife.base.presenter.BasePresenter;
import com.harry.kitchenknife.function.buy_pay.BuyPayActivity;
import com.harry.kitchenknife.function.renting_pay.RentingPayActivity;
import com.harry.kitchenknife.network.entity.GetKnifeCountEntity;
import com.harry.kitchenknife.utils.DeviceUtil;
import com.harry.kitchenknife.utils.OkHttpHelper;
import com.harry.kitchenknife.utils.SpannableStringUtils;

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
 * 出租页面
 */
public class RentingActivity extends BaseActivity {

    @BindView(R.id.iv_cover)
    ImageView ivCover;
    @BindView(R.id.tv_name_of_number)
    TextView tvNameOfNumber;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.tv_deposit)
    TextView tvDeposit;
    @BindView(R.id.tv_lease_expense)
    TextView tvLeaseExpense;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    /**
     * 记录加减的数量
     */
    private int number = 1;

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
     * 菜刀出租金额
     */
    private double commodityTypeRentalPrice;
    /**
     * 菜刀押金
     */
    private double commodityTypeRent;


    @Override
    protected int setupView() {
        return R.layout.activity_renting;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        initChildView();

    }

    private void initChildView() {
        number = Integer.parseInt(tvNumber.getText().toString().trim());
        getKnifeCount();
    }

    private void getKnifeCount() {
        Map<String, String> params = new HashMap<>();
        params.put("equipmentNumber", DeviceUtil.getDeviceID());
        params.put("equipmentRoleId", "1");

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
                        commodityTypeRentalPrice = bean.commodityTypeRentalPrice;
                        commodityTypeRent = bean.commodityTypeRent;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvTotal.setText("¥" + number * commodityTypeRentalPrice);
                                tvDeposit.setText("押金: ¥" + commodityTypeRent);
                                tvLeaseExpense.setText("租赁费用: ¥" + commodityTypeRentalPrice);
                            }
                        });
                    } else {
                        ToastUtils.showShort(getKnifeCountEntity.msg);
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
            case R.id.tv_reduce:    //减号
                if (number != 0) {
                    number--;
                    tvNumber.setText(String.valueOf(number));
                    tvTotal.setText("¥" + number * commodityTypeRentalPrice);
                }
                break;
            case R.id.tv_plus:      //加号
                if (number < knifeCount) {
                    number++;
                    tvNumber.setText(String.valueOf(number));
                    tvTotal.setText("¥" + number * commodityTypeRentalPrice);
                } else {
                    number = knifeCount;
                    tvNumber.setText(String.valueOf(number));
                    tvTotal.setText("¥" + number * commodityTypeRentalPrice);
                }
                break;
            case R.id.btn_commit:
                if (TextUtils.isEmpty(etPhone.getText().toString().trim())) {
                    ToastUtils.showShort("请输入电话号码");
                    return;
                }
                if (!TextUtils.isEmpty(commodityTypeNumber)) {
                    Intent intent = new Intent(this, RentingPayActivity.class);
                    String number = tvNumber.getText().toString().trim();
                    intent.putExtra("number", number);
                    intent.putExtra("phone", etPhone.getText().toString().trim());
                    intent.putExtra("commodityTypeNumber", commodityTypeNumber);
                    intent.putExtra("commodityTypeTitle", commodityTypeTitle);
                    intent.putExtra("commodityTypeRentalPrice", commodityTypeRentalPrice);
                    intent.putExtra("commodityTypeRent", commodityTypeRent);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }
}
