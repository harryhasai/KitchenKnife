package com.harry.kitchenknife.function.share;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.harry.kitchenknife.R;
import com.harry.kitchenknife.base.BaseActivity;
import com.harry.kitchenknife.base.presenter.BasePresenter;
import com.harry.kitchenknife.utils.QRCodeUtil;

/**
 * Created by Harry on 2018/9/21.
 * 首页 - 二维码分享页面
 */
public class ShareActivity extends BaseActivity {

    @Override
    protected int setupView() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView() {
        ImageView image = findViewById(R.id.image);

        String abc = "7897878,893898";

        Bitmap qrCodeBitmap = QRCodeUtil.createQRCodeBitmap(abc, 300, 300);
        image.setImageBitmap(qrCodeBitmap);
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }
}
