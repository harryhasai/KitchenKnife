package com.harry.kitchenknife.function.main;

import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.harry.kitchenknife.R;
import com.harry.kitchenknife.app_final.ConstantFinal;
import com.harry.kitchenknife.app_final.URLFinal;
import com.harry.kitchenknife.base.BaseActivity;
import com.harry.kitchenknife.base.presenter.BasePresenter;
import com.harry.kitchenknife.event_bus.ReceiveMessageEvent;
import com.harry.kitchenknife.function.buy.BuyActivity;
import com.harry.kitchenknife.function.recycle.RecycleActivity;
import com.harry.kitchenknife.function.renting.RentingActivity;
import com.harry.kitchenknife.function.service.SocketService;
import com.harry.kitchenknife.function.share.ShareActivity;
import com.harry.kitchenknife.network.entity.MainEntity;
import com.harry.kitchenknife.utils.OkHttpHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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
    private Intent serviceIntent;

    @Override
    protected int setupView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        serviceIntent = new Intent(this, SocketService.class);
        startService(serviceIntent);
        Intent intent = new Intent();
        intent.setAction(ConstantFinal.BROADCAST_ACTION);
        intent.putExtra(ConstantFinal.BROADCAST_ACTION, getDeviceID());
        sendBroadcast(intent);

        //初始化先GONE掉, 然后请求网络获取是否显示的状态
        flBuy.setVisibility(View.GONE);
        flRecycle.setVisibility(View.GONE);
        flRenting.setVisibility(View.GONE);

        mainPageDisplay();
    }

    /**
     * 通过服务器获取当前页面怎样显示
     */
    private void mainPageDisplay() {

        Map<String, String> params = new HashMap<>();
        params.put("equipmentNumber", getDeviceID());
//        params.put("equipmentNumber", "dadadada");

        OkHttpHelper.post(URLFinal.BASE_URL + URLFinal.MAIN_PAGE, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastUtils.showShort("网络连接错误");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                if (!TextUtils.isEmpty(s)) {
                    Gson gson = new Gson();
                    MainEntity mainEntity = gson.fromJson(s, MainEntity.class);
                    if (mainEntity.code == 100) {
                        List<MainEntity.ExtendBean.HomesBean.EquipmentRolesBean> mList = mainEntity.extend.homes.equipmentRoles;
                        for (int i = 0; i < mList.size(); i++) {
                            MainEntity.ExtendBean.HomesBean.EquipmentRolesBean bean = mList.get(i);
                            switch (bean.equipmentRoleId) {
                                case 1:
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            flRenting.setVisibility(View.VISIBLE);
                                        }
                                    });
                                    break;
                                case 2:
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            flBuy.setVisibility(View.VISIBLE);
                                        }
                                    });
                                    break;
                                case 3:
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            flRecycle.setVisibility(View.VISIBLE);
                                        }
                                    });
                                    break;
                            }
                        }
                    } else {
                        //code == 200
                        ToastUtils.showShort("请求码200");
                    }

                }
            }
        });
    }

    /**
     * @return 获取设备编号
     */
    private String getDeviceID() {
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class, String.class);
            String serialNum = (String) (get.invoke(c, "ro.serialno", "unknown"));
            return serialNum;
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @OnClick({R.id.fl_share, R.id.fl_renting, R.id.fl_buy, R.id.fl_recycle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_share: //二维码
                startActivity(new Intent(this, ShareActivity.class));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(serviceIntent);
    }

}
