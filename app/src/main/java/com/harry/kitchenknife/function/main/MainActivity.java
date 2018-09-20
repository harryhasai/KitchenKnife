package com.harry.kitchenknife.function.main;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.harry.kitchenknife.R;
import com.harry.kitchenknife.base.BaseActivity;
import com.harry.kitchenknife.base.presenter.BasePresenter;
import com.harry.kitchenknife.function.buy.BuyActivity;
import com.harry.kitchenknife.function.recycle.RecycleActivity;
import com.harry.kitchenknife.function.renting.RentingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.fl_share)
    FrameLayout flShare;
    @BindView(R.id.fl_renting)
    LinearLayout flRenting;
    @BindView(R.id.fl_buy)
    LinearLayout flBuy;
    @BindView(R.id.fl_recycle)
    LinearLayout flRecycle;

    @Override
    protected int setupView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);


    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @OnClick({R.id.fl_share, R.id.fl_renting, R.id.fl_buy, R.id.fl_recycle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_share: //二维码
                break;
            case R.id.fl_renting://出租
                startActivity(new Intent(this, RentingActivity.class));
                break;
            case R.id.fl_buy://购买
                startActivity(new Intent(this, BuyActivity.class));
                break;
            case R.id.fl_recycle://回收
                startActivity(new Intent(this, RecycleActivity.class));
                break;
        }
    }
}
