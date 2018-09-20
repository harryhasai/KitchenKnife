package com.harry.kitchenknife.utils;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Created by Harry on 2017/4/19.
 * 设置EditText的空格(或者其他特殊字符)过滤
 */

public class EditTextFilterUtil {

    /**
     * 设置EditText的空格过滤
     */
    public static InputFilter[] addSpaceFiltering() {
        InputFilter[] filters = {new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.toString().matches("\\s+")) {
                    return "";
                } else {
                    return null;
                }
            }
        }};
        return filters;
    }

    /**
     * 设置EditText的空格过滤 , 以及下划线 中英文 数字的过滤
     */
    public static InputFilter[] setSpaceFiltering() {
        InputFilter[] filters = {new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.toString().matches("\\s+")) {
                    return "";
                } else {
                    return null;
                }
            }
        }, new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.toString().matches("^[a-zA-Z0-9_\\u4e00-\\u9fa5]+$")) {
                    //只能输入的是 中文 英文 数字 下划线
                    return source;
                } else {
                    return "";
                }
            }
        }};
        return filters;
    }

    /**
     * 设置EditText的空格过滤 , 以及英文 数字的过滤
     */
    public static InputFilter[] setSpaceAndNumberFiltering() {
        InputFilter[] filters = {new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.toString().matches("\\s+")) {
                    return "";
                } else {
                    return null;
                }
            }
        }, new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.toString().matches("^[a-zA-Z0-9]+$")) {
                    //只能输入的是 中文 英文 数字
                    return source;
                } else {
                    return "";
                }
            }
        }};
        return filters;
    }

}
