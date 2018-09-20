package com.harry.kitchenknife.function.main;

import android.view.View;

import com.harry.kitchenknife.R;
import com.harry.kitchenknife.base.BaseActivity;
import com.harry.kitchenknife.base.presenter.BasePresenter;

public class MainActivity extends BaseActivity {

    @Override
    protected int setupView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }
}
