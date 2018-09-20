package com.harry.kitchenknife.utils;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Harry on 2017/7/7.
 */

public class DateFormatUtils {

    public static String PATTERN_1 = "yyyy-MM-dd HH:mm";
    public static String PATTERN_2 = "yyyy.MM.dd HH:mm:ss";
    public static String PATTERN_3 = "yyyy-MM-dd";
    public static String PATTERN_4 = "MM.dd";
    public static String PATTERN_5 = "MM-dd HH:mm";
    public static String PATTERN_6 = "MM月dd日HH:mm";

    public static String getFormatedDateTime(String pattern, long dateTime) {

        SimpleDateFormat format = new SimpleDateFormat(pattern);
        String date = format.format(new Date(dateTime));
        return date;
    }

    public static String getFormatedNewsTime(long dateTime) {
        if (dateTime == 0) {
            return "";
        }
        String time = "";
        //取当前时间
        long currentTime = System.currentTimeMillis();
        //计算相差时间
        long interval = currentTime - dateTime;

        //不足一小时，取分钟
        if (interval < 60 * 60 * 1000) {
            long date = interval / (60 * 1000);
            if (date < 1) {
                date = 1;
            }
            time = date + "分钟前";
            return time;
        }

        //不足一天，取小时
        if (interval < 60 * 60 * 24 * 1000) {
            long date = interval / (60 * 60 * 1000);
            if (date < 1) {
                date = 1;
            }
            time = date + "小时前";
            return time;
        }

        //不足七天，取天数
        if (interval < 60 * 60 * 24 * 7 * 1000) {
            long date = interval / (60 * 60 * 24 * 1000);
            if (date < 1) {
                date = 1;
            }
            time = date + "天前";
            return time;
        }

        //超过三天 返回日期
        SimpleDateFormat format = new SimpleDateFormat(PATTERN_1);
        time = format.format(new Date(dateTime));
        return time;
    }

    /**
     * 创建一组年份集合，根据当前系统时间开始计算,字符串版，结尾拼接 "年"
     */
    public static List<String> createYear2String() {
        int createCount = 3;//创建个数 例如 createCount = 3 ，就是从当前年份+往前推两年 总共三年
        long time = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);

        int year = calendar.get(Calendar.YEAR);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < createCount; i++) {
            list.add(year - i + "年");
        }

        return list;
    }

    /**
     * 创建一组年份集合，根据当前系统时间开始计算
     */
    public static List<Integer> createYear2Int() {
        int createCount = 3;//创建个数 例如 createCount = 3 ，就是从当前年份+往前推两年 总共三年
        long time = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);

        int year = calendar.get(Calendar.YEAR);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < createCount; i++) {
            list.add(year - i);
        }

        return list;
    }

    /**
     * 创建一组月份集合，数量为12个月,字符串版，结尾拼接 "月"
     */
    public static List<String> createMonth2String() {
        List<String> list = new ArrayList<>();
        list.add("不限");
        for (int i = 1; i <= 12; i++) {
            list.add(i + "月");
        }

        return list;
    }

    /**
     * 创建一组月份集合，数量为12个月
     */
    public static List<Integer> createMonth2Int() {
        List<Integer> list = new ArrayList<>();
        list.add(0);
        for (int i = 1; i <= 12; i++) {
            list.add(i);
        }

        return list;
    }

    /**
     * 创建一组当前年份到的一月到当前月的集合
     */
    public static List<Integer> createCurrentMonth2Int() {
        long time = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);

        int month = calendar.get(Calendar.MONTH) + 1;
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= month; i++) {
            list.add(i);
        }

        return list;
    }

    /**
     * 创建一组当前年份到的一月到当前月的集合,字符串版，结尾拼接 "月"
     */
    public static List<String> createCurrentMonth2String() {
        long time = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);

        int month = calendar.get(Calendar.MONTH) + 1;
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= month; i++) {
            list.add(i + "月");
        }

        return list;
    }


}
