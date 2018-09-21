package com.harry.kitchenknife.function.buy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.harry.kitchenknife.R;
import com.harry.kitchenknife.base.BaseActivity;
import com.harry.kitchenknife.base.presenter.BasePresenter;
import com.harry.kitchenknife.function.buy_pay.BuyPayActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    protected int setupView() {
        return R.layout.activity_buy;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        number = Integer.parseInt(tvNumber.getText().toString().trim());

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
                number++;
                tvNumber.setText(String.valueOf(number));
                break;
            case R.id.btn_commit:
                startActivity(new Intent(this, BuyPayActivity.class));
                break;
        }
    }
}
