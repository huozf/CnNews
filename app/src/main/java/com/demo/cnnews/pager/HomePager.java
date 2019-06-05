package com.demo.cnnews.pager;

import android.content.Context;
import android.graphics.Color;
import android.os.SystemClock;
import android.text.TextUtils;
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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 主页面
 */
public class HomePager extends BasePager {

    /**
     * 左侧菜单对应的数据集合
     */
    private List<NewsCenterPagerEntity.DetailPagerData> data;

    /**
     * 详情页面的集合
     */
    private ArrayList<MenuDetaiBasePager> detaiBasePagers;




    public HomePager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        ib_menu.setVisibility(View.VISIBLE);
        //1、设置标题
        tv_title.setText("新闻中心");
        //2、联网请求，得到数据
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);
        //3、把子视图添加到BasePager的FragmentLayout中
        fl_content.addView(textView);
        //4、绑定数据
        textView.setText("新闻中心内容");


        String saveJson = CacheUtils.getString(context,Constants.NEWSCENTER_PAGER_URL);//""
        if(!TextUtils.isEmpty(saveJson)){
            processData(saveJson);
        }
        getDataFromNet();
        //swichPager(0);

    }





    /**
     * 使用xUtils3联网请求数据
     */
    private void getDataFromNet() {
        RequestParams params = new RequestParams(Constants.NEWSCENTER_PAGER_URL);

        LogUtil.e("即将使用xUtils3联网请求=="+params.toString());

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("使用xUtils3联网请求成功==" + result);
                //缓存数据
                CacheUtils.putString(context,Constants.NEWSCENTER_PAGER_URL,result);
                processData(result);
                //设置适配器
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

    public void processData(String json) {
        NewsCenterPagerEntity entity = analyJson(json);
        String title2 = entity.getData().get(0).getChildren().get(1).getTitle();
        LogUtil.e("使用Gson 2 解析数据成功-----"+title2);
        //给左侧菜单初始数据
        data = entity.getData();

        MainActivity mainActivity = (MainActivity) context;
        //得到左侧菜单
        LeftmenuFragment leftmenuFragment = mainActivity.getLeftmenuFragment();

        //添加详情页面
        detaiBasePagers = new ArrayList<>();
        detaiBasePagers.add(new NewsMenuDetailPager(context,data.get(0)));//新闻详情页面
        detaiBasePagers.add(new TopicsMenuDetailPager(context,Constants.TOPIC_PAGER_URL));//专题详情页面
        detaiBasePagers.add(new PhotosMenuDetailPager(context,Constants.PHOTO_PAGER_URL));//图组详情页面


        //把数据传递给左侧菜单
        leftmenuFragment.setData(data);
    }


    //根据位置切换详情页面
    public void swichPager(int position) {
        LogUtil.e(data.get(position).getTitle());
        //1.设置标题
        tv_title.setText(data.get(position).getTitle());
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
    }



    //使用Android系统自带的API解析json数据
    private NewsCenterPagerEntity analyJson(String json) {
        NewsCenterPagerEntity entity = new NewsCenterPagerEntity();
        try {
            JSONObject object = new JSONObject(json);
            int retcode = object.optInt("retcode");
            entity.setRetcode(retcode);//retcode解析结果

            JSONArray data = object.optJSONArray("data");

            if (data!=null && data.length()>0){
                List<NewsCenterPagerEntity.DetailPagerData> detailPagerDatas = new ArrayList<>();
                //设置列表数据
                entity.setData(detailPagerDatas);
                //for循环，解析每条数据
                for (int i=0;i<data.length();i++){
                    JSONObject jsonObject = (JSONObject) data.get(i);

                    NewsCenterPagerEntity.DetailPagerData detailPagerData = new NewsCenterPagerEntity.DetailPagerData();

                    int id = jsonObject.optInt("id");
                    detailPagerData.setId(id);
                    int type = jsonObject.optInt("type");
                    detailPagerData.setType(type);
                    String title = jsonObject.optString("title");
                    detailPagerData.setTitle(title);
                    String url = jsonObject.optString("url");
                    detailPagerData.setUrl(url);
                    String url1 = jsonObject.optString("url1");
                    detailPagerData.setUrl1(url1);
                    String dayurl = jsonObject.optString("dayurl");
                    detailPagerData.setDayurl(dayurl);
                    String excurl = jsonObject.optString("excurl");
                    detailPagerData.setExcurl(excurl);
                    String weekurl = jsonObject.optString("weekurl");
                    detailPagerData.setWeekurl(weekurl);

                    //添加到集合中
                    detailPagerDatas.add(detailPagerData);


                    JSONArray children = jsonObject.optJSONArray("children");
                    if (children != null && children.length() > 0) {

                        List<NewsCenterPagerEntity.DetailPagerData.ChildrenData> childrenDatas  = new ArrayList<>();

                        //设置集合-ChildrenData
                        detailPagerData.setChildren(childrenDatas);

                        for (int j = 0; j < children.length(); j++) {
                            JSONObject childrenitem = (JSONObject) children.get(j);


                            NewsCenterPagerEntity.DetailPagerData.ChildrenData childrenData = new NewsCenterPagerEntity.DetailPagerData.ChildrenData();
                            //添加到集合中
                            childrenDatas.add(childrenData);


                            int childId = childrenitem.optInt("id");
                            childrenData.setId(childId);
                            String childTitle = childrenitem.optString("title");
                            childrenData.setTitle(childTitle);
                            String childUrl = childrenitem.optString("url");
                            childrenData.setUrl(childUrl);
                            int childType = childrenitem.optInt("type");
                            childrenData.setType(childType);

                        }
                    }
                }

            }

        }catch (JSONException e){
            e.printStackTrace();
        }

        return entity;
    }

}
