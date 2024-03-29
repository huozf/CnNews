package com.demo.cnnews.menudatailpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.demo.cnnews.R;
import com.demo.cnnews.activity.MainActivity;
import com.demo.cnnews.base.MenuDetaiBasePager;
import com.demo.cnnews.domain.NewsCenterPagerEntity;
import com.demo.cnnews.menudatailpager.tabdetailpager.TabDetailPager;
import com.demo.cnnews.utils.LogUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻详情页面
 */
public class NewsMenuDetailPager extends MenuDetaiBasePager {

    @ViewInject(R.id.tabPageIndicator)
    private TabPageIndicator tabPageIndicator;

    @ViewInject(R.id.viewpager)
    private ViewPager viewPager;

    @ViewInject(R.id.ib_tab_next)
    private ImageButton ib_tab_next;
    //标签页面的数据集合
    private List<NewsCenterPagerEntity.DetailPagerData.ChildrenData> children;
    //页签页面的集合
    private ArrayList<TabDetailPager> tabDetailPagers;

    public NewsMenuDetailPager(Context context, NewsCenterPagerEntity.DetailPagerData detailPagerData) {
        super(context);
        children = detailPagerData.getChildren();
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.newsmenu_detail_pager,null);
        x.view().inject(NewsMenuDetailPager.this,view);
        //设置点击事件
        ib_tab_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);

            }
        });
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("新闻详情页面数据被初始化了......");

        //准备新闻详情页面的数据
        tabDetailPagers = new ArrayList<>();
        for (int i=0;i<children.size();i++){
            tabDetailPagers.add(new TabDetailPager(context,children.get(i)));
        }
        //设置适配器
        viewPager.setAdapter(new MyNewsMenuDetailPagerAdapter());

        //ViewPager和TabPageIndicator关联
        tabPageIndicator.setViewPager(viewPager);

        //监听页面变化
        tabPageIndicator.setOnPageChangeListener(new MyOnPageChangeListener());

    }

    /**
     根据传人的参数设置是否让SlidingMenu可以滑动
     */
    private void isEnableSlidingMenu(int touchmodeFullscreen) {
        MainActivity mainActivity = (MainActivity) context;
        mainActivity.getSlidingMenu().setTouchModeAbove(touchmodeFullscreen);
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position == 0){
                //可以全屏滑动
                isEnableSlidingMenu(SlidingMenu.TOUCHMODE_FULLSCREEN);
            }else{
                //可以全屏滑动
                isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }

    class MyNewsMenuDetailPagerAdapter extends PagerAdapter{

        @Override
        public CharSequence getPageTitle(int position) {
            return children.get(position).getTitle();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem( ViewGroup container, int position) {
            TabDetailPager tabDetailPager = tabDetailPagers.get(position);
            View rootView = tabDetailPager.rootView;
            tabDetailPager.initData(); //初始化数据
            container.addView(rootView);
            return rootView;
        }

        @Override
        public int getCount() {
            return tabDetailPagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
    }


}
