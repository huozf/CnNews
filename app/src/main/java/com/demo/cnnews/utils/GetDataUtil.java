package com.demo.cnnews.utils;

import com.demo.cnnews.activity.MainActivity;
import com.demo.cnnews.base.MenuDetaiBasePager;
import com.demo.cnnews.domain.NewsCenterPagerEntity;
import com.demo.cnnews.fragment.LeftmenuFragment;
import com.demo.cnnews.menudatailpager.NewsMenuDetailPager;
import com.demo.cnnews.menudatailpager.PhotosMenuDetailPager;
import com.demo.cnnews.menudatailpager.TopicsMenuDetailPager;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class GetDataUtil {

    //使用xUtils3联网请求数据
    private void getDataFromNet() {
        RequestParams params = new RequestParams(Constants.NEWSCENTER_PAGER_URL);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
               // processData(result);
                //缓存数据
                //CacheUtils.putString(context,Constants.NEWSCENTER_PAGER_URL,result);
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

    private List<NewsCenterPagerEntity.DetailPagerData> info;
    // 详情页面的集合
    private ArrayList<MenuDetaiBasePager> detaiBasePagers;
    /**
     * 解析json数据
     * @param json
     */
    /*private void processData(String json) {
        NewsCenterPagerEntity entity = AnalyJsonUtil.analyJson(json);
        String title2 = entity.getData().get(0).getChildren().get(1).getTitle();
        LogUtil.e("使用Gson 2 解析数据成功-----"+title2);
        //给左侧菜单初始数据
        info = entity.getData();

        MainActivity mainActivity = (MainActivity)context;
        LeftmenuFragment leftmenuFragment = mainActivity.getLeftmenuFragment();

        //添加详情页面
        detaiBasePagers = new ArrayList<>();
        detaiBasePagers.add(new NewsMenuDetailPager(context,info.get(0)));//新闻详情页面
        detaiBasePagers.add(new TopicsMenuDetailPager(context,info.get(1)));//专题详情页面
        detaiBasePagers.add(new PhotosMenuDetailPager(context,info.get(2)));//图组详情页面

        //把数据传递给左侧菜单
        leftmenuFragment.setData(info);
    }*/

}
