package com.demo.cnnews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.cnnews.activity.GuideActivity;
import com.demo.cnnews.activity.MainActivity;
import com.demo.cnnews.utils.CacheUtils;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends Activity implements View.OnClickListener{

    /**
     * 静态常量
     */
    public static final String START_MAIN = "START_MAIN";
    private RelativeLayout rl_splahs_root;

    private int recLen = 5;//跳过倒计时提示5秒
    private TextView tv;
    Timer timer = new Timer();
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        //定义全屏参数
        int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        getWindow().setFlags(flag, flag);
        initView();
        timer.schedule(task, 1000, 1000);//等待时间一秒，停顿时间一秒
        /**
         * 正常情况下不点击跳过
         */
        handler = new Handler();
        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                //判断是否进入过主页面
                boolean isStartMain = CacheUtils.getBoolean(SplashActivity.this, START_MAIN);
                Intent intent;
                if(isStartMain){ //如果进入过，直接进入主页面
                    intent = new Intent(SplashActivity.this,MainActivity.class);
                }else{           //若果没有进入过，进入引导页面
                    intent = new Intent(SplashActivity.this,GuideActivity.class);
                }
                startActivity(intent);
                //关闭Splash页面
                finish();
            }
        }, 5000);//延迟5S后发送handler信息


    }


    private void initView() {
        tv = findViewById(R.id.tv);//跳过
        tv.setOnClickListener(this);//跳过监听
    }


    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() { // UI thread
                @Override
                public void run() {
                    recLen--;
                    tv.setText("跳过 " + recLen);
                    if (recLen < 0) {
                        timer.cancel();
                        tv.setVisibility(View.GONE);//倒计时到0隐藏字体
                    }
                }
            });
        }
    };


    /**
     * 点击跳过
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv:
                //从闪屏界面跳转到首界面
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                if (runnable != null) {
                    handler.removeCallbacks(runnable);
                }
                break;
            default:
                break;
        }
    }



}
