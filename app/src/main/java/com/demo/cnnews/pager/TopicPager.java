package com.demo.cnnews.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.demo.cnnews.activity.MainActivity;
import com.demo.cnnews.base.BasePager;
import com.demo.cnnews.base.MenuDetaiBasePager;
import com.demo.cnnews.domain.NewsCenterPagerEntity;
import com.demo.cnnews.fragment.LeftmenuFragment;
import com.demo.cnnews.menudatailpager.NewsMenuDetailPager;
import com.demo.cnnews.menudatailpager.PhotosMenuDetailPager;
import com.demo.cnnews.menudatailpager.TopicsMenuDetailPager;
import com.demo.cnnews.utils.AnalyJsonUtil;
import com.demo.cnnews.utils.CacheUtils;
import com.demo.cnnews.utils.Constants;
import com.demo.cnnews.utils.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * 专题页面
 */
public class TopicPager extends BasePager {

    /**
     * 左侧菜单对应的数据集合
     */
    private List<NewsCenterPagerEntity.DetailPagerData> data;

    /**
     * 详情页面的集合
     */
    private ArrayList<MenuDetaiBasePager> detaiBasePagers;

    private RequestParams params;


    public TopicPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        ib_menu.setVisibility(View.GONE);
        //1、设置标题
        tv_title.setText("专题");
        //2、联网请求，得到数据
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);
        //3、把子视图添加到BasePager的FragmentLayout中
        fl_content.addView(textView);
        //4、绑定数据
        textView.setText("专题面内容");


        //swichPager(1);
        //2.移除内容
        fl_content.removeAllViews();
        //3.添加新内容
        MenuDetaiBasePager detaiBasePager = new TopicsMenuDetailPager(context,Constants.TOPIC_PAGER_URL);
        View rootView = detaiBasePager.rootView;
        detaiBasePager.initData();//初始化数据
        fl_content.addView(rootView);

    }






}
