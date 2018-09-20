package com.harry.kitchenknife.base;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;

import com.harry.kitchenknife.R;
import com.harry.kitchenknife.application.KitchenKnifeApplication;
import com.harry.kitchenknife.base.presenter.BasePresenter;
import com.harry.kitchenknife.base.view.BaseActivityImpl;

/**
 * Created by Harry on 2018/8/13.
 */
public abstract class BaseActivity<P extends BasePresenter> extends BaseActivityImpl<P> {

    private KitchenKnifeApplication application;
    private AlertDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(setupView());
        application = (KitchenKnifeApplication) getApplication();
        application.addActivity(this);

        //只是手机竖屏显示
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initView();
    }

    /**
     * @return 布局文件的ID
     */
    protected abstract int setupView();

    /**
     * 初始化布局(例如findViewById)
     */
    protected abstract void initView();

    private void initDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.LoadingDialog);
        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setView(View.inflate(this, R.layout.dialog_base, null));
    }

    /**
     * 显示LoadingDialog
     */
    public void showDialog() {
        initDialog();
        dialog.show();
    }

    /**
     * 关闭LoadingDialog
     */
    public void dismissDialog() {
        if (dialog != null) {
            dialog.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        application.finishActivity(this);   //清除栈中的Activity
    }

}
