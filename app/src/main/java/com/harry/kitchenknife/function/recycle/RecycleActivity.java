package com.harry.kitchenknife.function.recycle;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.harry.kitchenknife.R;
import com.harry.kitchenknife.app_final.ConstantFinal;
import com.harry.kitchenknife.base.BaseActivity;
import com.harry.kitchenknife.base.presenter.BasePresenter;
import com.harry.kitchenknife.function.recycle_pay.RecyclePayActivity;
import com.harry.kitchenknife.utils.SerialPortUtils;

import java.io.IOException;

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

    private SerialPortUtils serialPortUtils;

    @Override
    protected int setupView() {
        return R.layout.activity_recycle;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        openSerial();
    }

    /**
     * 打开串口. 完成串口读取
     */
    private void openSerial() {
        serialPortUtils = new SerialPortUtils();
        try {
            serialPortUtils.openSerialPort(ConstantFinal.PATH2, ConstantFinal.BAUD_RATE2);

            serialPortUtils.setOnDataReceiveListener(new SerialPortUtils.OnDataReceiveListener() {
                @Override
                public void onDataReceive(byte[] buffer, int size) {
                    //获取菜刀编号
                    String s = new String(buffer);
                    if (!TextUtils.isEmpty(s)) {
                        //获取编号成功

                    }
                }
            });
        } catch (IOException e) {
            ToastUtils.showShort("打开读取端口失败");
            e.printStackTrace();
        } catch (SecurityException e) {
            ToastUtils.showShort("未打开正确的端口");
            e.printStackTrace();
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        serialPortUtils.closeSerialPort();
    }
}
