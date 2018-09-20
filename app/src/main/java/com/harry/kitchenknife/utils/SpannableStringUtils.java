package com.harry.kitchenknife.utils;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

/**
 * Created by Harry on 2018/8/18.
 */
public class SpannableStringUtils {

    /**
     * 设置文本内容某一段字体的颜色, 从开始角标开始, 包含开始角标
     * @param text 要设置的文本内容
     * @param startPosition 开始角标
     * @param endPosition 结束角标
     * @return spannableString
     */
    public static SpannableString setTextColor(String text, int startPosition, int endPosition, @ColorInt int color) {
        SpannableString spannableString = new SpannableString(text);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
        spannableString.setSpan(colorSpan, startPosition, endPosition, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
