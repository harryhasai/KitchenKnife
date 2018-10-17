package com.harry.kitchenknife.function.main;

import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
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
import com.harry.kitchenknife.event_bus.SendMessageEvent;
import com.harry.kitchenknife.function.buy.BuyActivity;
import com.harry.kitchenknife.function.login.LoginActivity;
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

    private String receiveMessage = null;

    @Override
    protected int setupView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        serviceIntent = new Intent(this, SocketService.class);
        startService(serviceIntent);
        Intent intent = new Intent();
        intent.setAction(ConstantFinal.BROADCAST_ACTION);
        intent.putExtra(ConstantFinal.BROADCAST_ACTION, getDeviceID());
        sendBroadcast(intent);

        flRecycle.setEnabled(false);
        flRenting.setEnabled(false);
        flBuy.setEnabled(false);

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
                        final List<MainEntity.ExtendBean.HomesBean.EquipmentRolesBean> mList = mainEntity.extend.homes.equipmentRoles;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (mList.size() == 1) {
                                    if (mList.get(0).equipmentRoleId == 1) {
                                        flBuy.setVisibility(View.GONE);
                                        flRecycle.setVisibility(View.GONE);
                                    } else if (mList.get(0).equipmentRoleId == 2) {
                                        flRenting.setVisibility(View.GONE);
                                        flRecycle.setVisibility(View.GONE);
                                    } else {
                                        flBuy.setVisibility(View.GONE);
                                        flRenting.setVisibility(View.GONE);
                                    }
                                } else if (mList.size() == 2) {
                                    if (mList.get(0).equipmentRoleId == 1 && mList.get(1).equipmentRoleId == 2) {
                                        flRecycle.setVisibility(View.GONE);
                                    } else if (mList.get(0).equipmentRoleId == 1 && mList.get(1).equipmentRoleId == 3) {
                                        flBuy.setVisibility(View.GONE);
                                    } else if (mList.get(0).equipmentRoleId == 2 && mList.get(1).equipmentRoleId == 3) {
                                        flRenting.setVisibility(View.GONE);
                                    }
                                }
                            }
                        });
                    } else {
                        //code == 200
                        ToastUtils.showShort(mainEntity.msg);
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
                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra("name", "renting");
                startActivity(intent);
                break;
            case R.id.fl_buy://购买
                Intent intent1 = new Intent(this, LoginActivity.class);
                intent1.putExtra("name", "buy");
                startActivity(intent1);
                break;
            case R.id.fl_recycle://回收
                Intent intent2 = new Intent(this, LoginActivity.class);
                intent2.putExtra("name", "recycle");
                startActivity(intent2);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(serviceIntent);
        EventBus.getDefault().unregister(this);
    }

    private boolean isFirst = true;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveMessage(SendMessageEvent sendMessageEvent) {
        //EventBus接收长连接反馈数据的方法, 用于获取ID
        receiveMessage = sendMessageEvent.getMessage();
        Log.i("MainActivity", "receiveMessage: " + sendMessageEvent.getMessage());
        if (!TextUtils.isEmpty(receiveMessage)) {
            flRecycle.setEnabled(true);
            flRenting.setEnabled(true);
            flBuy.setEnabled(true);
            if (isFirst) {
                mainPageDisplay();
                isFirst = false;
            }
        }

    }

}
