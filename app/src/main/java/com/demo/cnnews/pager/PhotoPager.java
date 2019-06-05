package com.demo.cnnews.pager;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import com.demo.cnnews.base.BasePager;
import com.demo.cnnews.base.MenuDetaiBasePager;
import com.demo.cnnews.domain.NewsCenterPagerEntity;
import com.demo.cnnews.menudatailpager.PhotosMenuDetailPager;
import com.demo.cnnews.menudatailpager.TopicsMenuDetailPager;
import com.demo.cnnews.utils.AnalyJsonUtil;
import com.demo.cnnews.utils.CacheUtils;
import com.demo.cnnews.utils.Constants;
import com.demo.cnnews.utils.LogUtil;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;
import java.util.ArrayList;
import java.util.List;

/**
 * 组图页面
 */
public class PhotoPager extends BasePager {

    private PhotosMenuDetailPager detailPager;
    public PhotoPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        //1、设置标题
        tv_title.setText("组图");
        //2、联网请求，得到数据
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);
        //3、把子视图添加到BasePager的FragmentLayout中
        fl_content.addView(textView);
        //4、绑定数据
        textView.setText("组图页面内容");

        //swichPager(2);
        fl_content.removeAllViews();
        //3.添加新内容
        detailPager = new PhotosMenuDetailPager(context,Constants.PHOTO_PAGER_URL);
        View rootView = detailPager.rootView;
        detailPager.initData();//初始化数据
        fl_content.addView(rootView);


        ib_swich_list_grid.setVisibility(View.VISIBLE);
        //设置点击事件
        ib_swich_list_grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.得到图组详情页面对象
                //2.调用图组对象的切换ListView和GridView的方法
                detailPager.swichListAndGrid(ib_swich_list_grid);
            }
        });

    }



}
