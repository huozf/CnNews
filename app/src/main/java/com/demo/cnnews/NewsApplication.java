package com.demo.cnnews;

import android.app.Application;
import com.demo.cnnews.volley.VolleyManager;
import org.xutils.x;

/**
 * 代表整个软件
 */
public class NewsApplication extends Application {

    /**
     * 所有组件被创建之前执行
     */
    @Override
    public void onCreate() {
        super.onCreate();

        //初始化xUtils3
        x.Ext.setDebug(true);
        x.Ext.init(this);

        //初始化Volley
        VolleyManager.init(this);

    }
}
