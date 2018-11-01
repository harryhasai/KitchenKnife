package com.harry.kitchenknife.function.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;
import com.harry.kitchenknife.app_final.ConstantFinal;
import com.harry.kitchenknife.event_bus.ReceiveMessageEvent;
import com.harry.kitchenknife.event_bus.SendMessageEvent;
import com.harry.kitchenknife.utils.DeviceUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Created by Harry on 2018/10/15.
 */
public class SocketService extends Service {

    private static final String TAG = "SocketService";
    //心跳包频率
    private static final long HEART_BEAT_RATE = 15 * 1000;

//    public static final String HOST = "192.168.1.28";
    public static final String HOST = "47.92.226.61";
    public static final int PORT = 9502;
    public static final String HEART_BEAT_STRING = "00";//心跳包内容

    private ReadThread mReadThread;
    private WeakReference<Socket> mSocket;

    private boolean isSend = true;


    // For heart Beat
    private long sendTime = 0L;
    private Handler mHandler = new Handler();
    private Runnable heartBeatRunnable = new Runnable() {

        @Override
        public void run() {
            if (System.currentTimeMillis() - sendTime >= HEART_BEAT_RATE) {
                //就发送一个HEART_BEAT_STRING过去 如果发送失败，就重新初始化一个socket
                //发送设备编号到服务器
                sendMsg("AA " + DeviceUtil.getDeviceID());
                if (!isSend) {
                    mHandler.removeCallbacks(heartBeatRunnable);
                    mReadThread.release();
                    releaseLastSocket(mSocket);
                    new InitSocketThread().start();
                }
            }
            mHandler.postDelayed(this, HEART_BEAT_RATE);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter(ConstantFinal.BROADCAST_ACTION);
        new InitSocketThread().start();
    }

    /**
     * @param msg 发送消息到服务器
     * @return
     */
    public void sendMsg(final String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (null == mSocket || null == mSocket.get()) {
                    isSend = false;
                }
                Socket soc = mSocket.get();
                try {
                    if (!soc.isClosed() && !soc.isOutputShutdown()) {
                        OutputStream os = soc.getOutputStream();
                        String message = msg;
                        os.write(message.getBytes());
                        os.flush();
                        sendTime = System.currentTimeMillis();//每次发送成数据，就改一下最后成功发送的时间，节省心跳间隔时间
                        isSend = true;
                    } else {
                        isSend = false;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    isSend = false;
                }
            }
        }).start();
    }

    private void initSocket() {//初始化Socket
        try {
            Socket so = new Socket(HOST, PORT);
            mSocket = new WeakReference<Socket>(so);
            mReadThread = new ReadThread(so);
            mReadThread.start();
            mHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE);//初始化成功后，就准备发送心跳包
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void releaseLastSocket(WeakReference<Socket> mSocket) {
        try {
            if (null != mSocket) {
                Socket sk = mSocket.get();
                if (!sk.isClosed()) {
                    sk.close();
                }
                sk = null;
                mSocket = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class InitSocketThread extends Thread {
        @Override
        public void run() {
            super.run();
            initSocket();
        }
    }

    // Thread to read content from Socket
    class ReadThread extends Thread {
        private WeakReference<Socket> mWeakSocket;
        private boolean isStart = true;

        public ReadThread(Socket socket) {
            mWeakSocket = new WeakReference<Socket>(socket);
        }

        public void release() {
            isStart = false;
            releaseLastSocket(mWeakSocket);
        }

        @Override
        public void run() {
            super.run();
            Socket socket = mWeakSocket.get();
            if (null != socket) {
                try {
                    InputStream is = socket.getInputStream();
                    byte[] buffer = new byte[1024 * 4];
                    int length = 0;
                    while (!socket.isClosed() && !socket.isInputShutdown()
                            && isStart && ((length = is.read(buffer)) != -1)) {
                        if (length > 0) {
                            String message = new String(Arrays.copyOf(buffer,
                                    length)).trim();
                            Log.e(TAG, message);
                            //收到服务器过来的消息，就通过Broadcast发送出去
                            //收到服务器端传过来的消息text, 通过EventBus发送不同的消息
                            EventBus.getDefault().post(new SendMessageEvent(message));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSocket != null) {
            releaseLastSocket(mSocket);
        }
    }
}
