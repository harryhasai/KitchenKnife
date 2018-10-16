package com.harry.kitchenknife.function.serial_port_test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.harry.kitchenknife.R;
import com.harry.kitchenknife.utils.SerialPortUtils;

import java.io.IOException;

import android_serialport_api.SerialPort;

/**
 * Created by Harry on 2018/10/15.
 * 测试串口通讯的测试类(可删除, 可忽略)
 */
public class SerialPortTestActivity extends AppCompatActivity {

    private TextView text;
    private SerialPortUtils serialPortUtils = new SerialPortUtils();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serial_port_test);

        initView();
    }

    private void initView() {
        Button open = findViewById(R.id.open);
        Button close = findViewById(R.id.close);
        Button ok = findViewById(R.id.ok);
        final EditText input = findViewById(R.id.input);
        text = findViewById(R.id.text);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = input.getText().toString().trim();
                try {
                    serialPortUtils.sendSerialPort(inputText);
                } catch (IOException e) {
                    e.printStackTrace();
                }

//                SerialPortFinder serialPortFinder = new SerialPortFinder();
//                String[] allDevices = serialPortFinder.getAllDevices();
//                String[] allDevicesPath = serialPortFinder.getAllDevicesPath();
//                for (int i = 0; i < allDevices.length; i++) {
//                    Log.i("666", "设备为:" + allDevices[i] + "路径为: " + allDevicesPath[i]);
//                }
            }
        });

        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = "/dev/ttyMT0";//平板电脑的路径
                int baudrate = 57600;
                try {
                    SerialPort serialPort = serialPortUtils.openSerialPort(path, baudrate);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serialPortUtils.closeSerialPort();
            }
        });
        serialPortUtils.setOnDataReceiveListener(new SerialPortUtils.OnDataReceiveListener() {
            @Override
            public void onDataReceive(final byte[] buffer, int size) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        text.setText("size：" + String.valueOf(buffer.length) + "数据监听：" + new String(buffer));
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        serialPortUtils.closeSerialPort();
    }
}
