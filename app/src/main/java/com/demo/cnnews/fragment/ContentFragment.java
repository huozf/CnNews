package com.demo.cnnews.fragment;

import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;
import com.demo.cnnews.R;
import com.demo.cnnews.activity.MainActivity;
import com.demo.cnnews.adapter.ContentFragmentAdapter;
import com.demo.cnnews.base.BaseFragment;
import com.demo.cnnews.base.BasePager;
import com.demo.cnnews.pager.HomePager;
import com.demo.cnnews.pager.TopicPager;
import com.demo.cnnews.pager.SettingPager;
import com.demo.cnnews.pager.PhotoPager;
import com.demo.cnnews.utils.LogUtil;
import com.demo.cnnews.view.NoScrollViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import java.util.ArrayList;

public class ContentFragment extends BaseFragment {

    //通过xUtils3初始化空间
    @ViewInject(R.id.viewpager)
    private NoScrollViewPager viewpager;
    @ViewInject(R.id.rg_main)
    private RadioGroup rg_main;

    //装四个页面的集合
    private ArrayList<BasePager> basePagers;

    @Override
    public View initView() {
        LogUtil.e("正文Fragment被初始化了");
        View view = View.inflate(context, R.layout.content_fragment,null);
        //1.把视图注入到框架中，让ContentFragment.this和View关联起来
        x.view().inject(ContentFragment.this,view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("正文页面数据被初始化了");

        //初始化四个页面，并且放入集合中
        basePagers = new ArrayList<>();
        basePagers.add(new HomePager(context));//主页面
        basePagers.add(new TopicPager(context));//专题页面
        basePagers.add(new PhotoPager(context));//组图页面
        basePagers.add(new SettingPager(context));//设置页面

        //设置ViewPager的适配器
        viewpager.setAdapter(new ContentFragmentAdapter(basePagers));
        //设置RadioGroup的选中状态改变的监听
        rg_main.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //监听某个页面被选中，初始对应的页面的数据
        viewpager.addOnPageChangeListener(new MyOnPageChangeListener());

        //设置默认选中首页
        rg_main.check(R.id.rb_home);
        basePagers.get(0).initData();

        //设置模式SlidingMenu不可以滑动
        isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);

    }

    /**
     * 得到新闻中心
     * @return
     */
    public HomePager getHomePager() {
        return (HomePager) basePagers.get(0);
    }



    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        /**
         * 当某个页面被选中的时候回调这个方法
         * @param position 被选中页面的位置
         */
        @Override
        public void onPageSelected(int position) {
            //调用被选中的页面的initData方法
            basePagers.get(position).initData();
        }

        @Override
        public void onPageScrollStateChanged(int state) { }
    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        /**
         * @param group RadioGroup
         * @param checkedId 被选中的RadioButton的id
         */
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rb_home://主页radioButton的id
                    viewpager.setCurrentItem(0,false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_FULLSCREEN);
                    break;
                case R.id.rb_topic://专题radioButton的id
                    viewpager.setCurrentItem(1,false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
                case R.id.rb_photo://组图radioButton的id
                    viewpager.setCurrentItem(2,false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
                case R.id.rb_setting://设置中心RadioButton的id
                    viewpager.setCurrentItem(3,false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
            }
        }
    }

    /**
     根据传入的参数设置是否让SlidingMenu可以滑动
     */
    private void isEnableSlidingMenu(int touchmodeFullscreen) {
        MainActivity mainActivity = (MainActivity) context;
        mainActivity.getSlidingMenu().setTouchModeAbove(touchmodeFullscreen);
    }



}
