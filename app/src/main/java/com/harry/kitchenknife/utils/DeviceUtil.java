package com.harry.kitchenknife.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Harry on 2018/10/15.
 */
public class DeviceUtil {

    /**
     * @return 获取设备编号
     */
    public static String getDeviceID() {
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
}
