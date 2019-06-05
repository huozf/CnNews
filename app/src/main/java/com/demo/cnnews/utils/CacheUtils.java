package com.demo.cnnews.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import com.demo.cnnews.SplashActivity;
import com.demo.cnnews.activity.GuideActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 缓存软件的一些参数和数据
 */
public class CacheUtils {
    /**
     * 得到缓存值
     * @param context 上下文
     * @param key 值
     * @return
     */
    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("demo",Context.MODE_PRIVATE);
        return sp.getBoolean(key,false);
    }

    /**
     * 保存软件参数
     * @param context
     * @param key
     * @param value
     */
    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences("demo", Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * 缓存联网请求数据
     * @param context
     * @param key
     * @param value
     */
    public static void putString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences("demo", Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();

    }

    /**
     * 得到缓存文本信息
     * @param context 上下文
     * @param key 值
     * @return
     */
    public static String getString(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("demo",Context.MODE_PRIVATE);
        return sp.getString(key,"");

    }
}
