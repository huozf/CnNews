package com.demo.cnnews.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.demo.cnnews.R;
import com.demo.cnnews.fragment.ContentFragment;
import com.demo.cnnews.fragment.LeftmenuFragment;
import com.demo.cnnews.utils.DensityUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {

    private int screeWidth;
    private int screeHeight;


    public static final String MAIN_CONTENT_TAG = "main_content_tag";
    public static final String LEFTMENU_TAG = "leftmenu_tag";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//设置没有标题
        super.onCreate(savedInstanceState);

        initSlidingMenu();

        initFragment();

    }


    private void initSlidingMenu() {
        //1.设置主页面
        setContentView(R.layout.activity_main);

        //2.设置左侧菜单
        setBehindContentView(R.layout.activity_leftmenu);


        //3.设置右侧菜单
        SlidingMenu slidingMenu = getSlidingMenu();

        //4.设置显示的模式：左侧菜单+主页，左侧菜单+主页面+右侧菜单；主页面+右侧菜单
        slidingMenu.setMode(SlidingMenu.LEFT);

        //5.设置滑动模式：滑动边缘，全屏滑动，不可以滑动
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        DisplayMetrics outmetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outmetrics);
        screeWidth = outmetrics.widthPixels;
        screeHeight = outmetrics.heightPixels;
        //6.设置主页占据的宽度
        slidingMenu.setBehindOffset( (int) (screeWidth*0.625) );
    }

    private void initFragment() {
        //1.得到FragmentManger
        FragmentManager fm = getSupportFragmentManager();
        //2.开启事务
        FragmentTransaction ft= fm.beginTransaction();
        //3.替换
        ft.replace(R.id.fl_leftmenu, new LeftmenuFragment(), LEFTMENU_TAG);         //左侧菜单
        ft.replace(R.id.fl_main_content,new ContentFragment(), MAIN_CONTENT_TAG);   //主页

        //4.提交
        ft.commit();
    }

    // 得到左侧菜单Fragment
    public LeftmenuFragment getLeftmenuFragment() {
        FragmentManager fm = getSupportFragmentManager();
        LeftmenuFragment leftmenuFragment = (LeftmenuFragment)fm.findFragmentByTag(LEFTMENU_TAG);
        return leftmenuFragment;
    }

    //得到正文Fragment
    public ContentFragment getContentFragment() {
        return (ContentFragment) getSupportFragmentManager().findFragmentByTag(MAIN_CONTENT_TAG);
    }
}
