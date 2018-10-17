package com.harry.kitchenknife.application;

import android.app.Activity;
import android.app.Application;

import com.blankj.utilcode.util.Utils;

import java.util.Stack;

/**
 * Created by Harry on 2018/9/19.
 */
public class KitchenKnifeApplication extends Application {

    private Stack<Activity> activityStack;
    private static KitchenKnifeApplication appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        //初始化AndroidUtils
        Utils.init(this);

    }

    public static KitchenKnifeApplication getAppContext() {
        return appContext;
    }

    /**
     * 返回栈中Activity的个数
     */
    public int getActivityCount() {
        if (activityStack == null) return 0;
        return activityStack.size();
    }

    /**
     * 添加Activity到栈中
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * @return 返回最后一个压入栈中的Activity(当前Activity)
     */
    public Activity getCurrentActivity() {
        return activityStack.lastElement();
    }

    /**
     * 结束掉指定的Activity
     *
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束掉指定类名的Activity
     *
     * @param cls
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                return;
            }
        }
    }

    /**
     * 获取指定类名的Activity
     *
     * @param cls
     */
    public Activity getActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                return activity;
            }
        }
        return null;
    }

    /**
     * 结束掉最后一个压入栈中的Activity(当前Activity)
     */
    public void finishCurrentActivity() {
        finishActivity(activityStack.lastElement());
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }
}
