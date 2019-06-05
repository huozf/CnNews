package com.demo.cnnews.base;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.demo.cnnews.R;
import com.demo.cnnews.activity.MainActivity;
import com.demo.cnnews.domain.NewsCenterPagerEntity;
import com.demo.cnnews.menudatailpager.NewsMenuDetailPager;
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
 * 各个页面的基类
 */
public class BasePager {

    //上下文
    public final Context context;
    //视图，代表不同的页面
    public View rootView;

    //显示标题
    public TextView tv_title;

    //点击侧滑的
    public ImageButton ib_menu;

    //加载各个子页面
    public FrameLayout fl_content;

    //ListView和GridView切换按钮
    public ImageButton ib_swich_list_grid;

   /* //菜单对应的数据集合
    public List<NewsCenterPagerEntity.DetailPagerData> info;
    //菜单对应的详情页面集合
    public ArrayList<MenuDetaiBasePager> detaiBasePagers;*/


    public BasePager(Context context){
        this.context = context;
        //构造方法一执行，视图就被初始化了
        rootView = initView();
        //gainData();
    }

    /**
     * 用于初始化公共部分视图，并且初始化加载子视图的FrameLayout
     * @return
     */
    private View initView() {
        //基类的页面
        View view = View.inflate(context, R.layout.base_pager,null);
        //页面标题
        tv_title = view.findViewById(R.id.tv_title);
        //ListView与GridView切换按钮
        ib_swich_list_grid = view.findViewById(R.id.ib_swich_list_grid);
        //侧滑按钮
        ib_menu = view.findViewById(R.id.ib_menu);
        ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.getSlidingMenu().toggle(); //关<->开
            }
        });
        fl_content = (FrameLayout) view.findViewById(R.id.fl_content);

        return view;
    }

    // 初始化数据;当孩子需要初始化数据;或者绑定数据;联网请求数据并且绑定的时候，重写该方法
    public void initData(){

    }

    /****************************************************************/

    /*public void gainData(){
        String saveJson = CacheUtils.getString(context,Constants.NEWSCENTER_PAGER_URL);
        if (!TextUtils.isEmpty(saveJson)){
            processData(saveJson);
        }

        //使用xUtils3联网请求数据
        getDataFromNet();
    }


    *//**
     * 解析json数据
     * @param json
     *//*
    public void processData(String json) {
        NewsCenterPagerEntity entity = AnalyJsonUtil.analyJson(json);
        String title2 = entity.getData().get(0).getChildren().get(1).getTitle();
        LogUtil.e("使用Gson 2 解析数据成功-----"+title2);
        //给左侧菜单初始数据
        info = entity.getData();

        //添加详情页面
        detaiBasePagers = new ArrayList<>();
        detaiBasePagers.add(new NewsMenuDetailPager(context,info.get(0)));//新闻详情页面
        detaiBasePagers.add(new TopicsMenuDetailPager(context,info.get(1)));//专题详情页面
        detaiBasePagers.add(new PhotosMenuDetailPager(context,info.get(2)));//图组详情页面
    }

    //使用xUtils3联网请求数据
    public void getDataFromNet() {
        RequestParams params = new RequestParams(Constants.NEWSCENTER_PAGER_URL);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("使用xUtils3联网请求成功==" + result);
                processData(result);
                //缓存数据
                CacheUtils.putString(context,Constants.NEWSCENTER_PAGER_URL,result);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("使用xUtils3联网请求失败==" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("使用xUtils3-onCancelled==" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                LogUtil.e("使用xUtils3-onFinished");
            }

        });
    }

    //根据位置切换详情页面
    public void swichPager(int position) {
        LogUtil.e(info.toString());
        LogUtil.e(info.get(position).getTitle());
        //1.设置标题
        tv_title.setText(info.get(position).getTitle());
        //2.移除内容
        fl_content.removeAllViews();
        //3.添加新内容
        MenuDetaiBasePager detaiBasePager = detaiBasePagers.get(position);
        View rootView = detaiBasePager.rootView;
        detaiBasePager.initData();//初始化数据
        fl_content.addView(rootView);

        if (position == 2){
            //图组详情页面
            ib_swich_list_grid.setVisibility(View.VISIBLE);
            //设置点击事件
            ib_swich_list_grid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //1.得到图组详情页面对象
                    PhotosMenuDetailPager detailPager = (PhotosMenuDetailPager) detaiBasePagers.get(2);
                    //2.调用图组对象的切换ListView和GridView的方法
                    detailPager.swichListAndGrid(ib_swich_list_grid);
                }
            });

        }else{
            ib_swich_list_grid.setVisibility(View.GONE);
        }
    }*/


}
