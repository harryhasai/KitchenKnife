package com.harry.kitchenknife.function.recycle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.harry.kitchenknife.R;
import com.harry.kitchenknife.base.BaseActivity;
import com.harry.kitchenknife.base.presenter.BasePresenter;
import com.harry.kitchenknife.function.recycle_pay.RecyclePayActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Harry on 2018/9/20.
 * 回收页面
 */
public class RecycleActivity extends BaseActivity {

    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.btn_commit)
    Button btnCommit;

    @Override
    protected int setupView() {
        return R.layout.activity_recycle;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @OnClick({R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_commit:
                startActivity(new Intent(this, RecyclePayActivity.class));
                break;
        }
    }
}
