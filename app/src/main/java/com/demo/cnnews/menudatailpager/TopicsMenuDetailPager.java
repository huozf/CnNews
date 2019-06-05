package com.demo.cnnews.menudatailpager;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.demo.cnnews.R;
import com.demo.cnnews.base.MenuDetaiBasePager;
import com.demo.cnnews.domain.NewsCenterPagerEntity;
import com.demo.cnnews.domain.TopicDetailPagerBean;
import com.demo.cnnews.utils.CacheUtils;
import com.demo.cnnews.utils.Constants;
import com.demo.cnnews.utils.LogUtil;
import com.demo.cnnews.volley.VolleyManager;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

public class TopicsMenuDetailPager extends MenuDetaiBasePager {

    //private final NewsCenterPagerEntity.DetailPagerData detailPagerData;
    @ViewInject(R.id.listview)
    private ListView listview;
    private final String url;
    private List<TopicDetailPagerBean.DataEntity.TopicEntity> topics;
    private TopicMenuDetailPagerAdapter adapter;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.topic_menudetail_pager, null);
        x.view().inject(this,view);

        return view;
    }

    public TopicsMenuDetailPager(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("专题详情页面数据被初始化了......");
        //url = Constants.BASE_URL + detailPagerData.getUrl1();
        LogUtil.e("url===>"+url);
        String saveJson = CacheUtils.getString( context, url );
        if (!TextUtils.isEmpty(saveJson)){
            processData(saveJson);
        }
        getDataFromNet();
    }


    private void getDataFromNet() {
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                processData(result);
                //缓存数据
                CacheUtils.putString(context,url ,result);
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

    /**
     * 解析和显示数据
     * @param json
     */
    private void processData(String json) {
        TopicDetailPagerBean bean = parsedJson(json);
        //设置适配器
        topics = bean.getData().getTopic();
        adapter = new TopicMenuDetailPagerAdapter();
        listview.setAdapter(adapter);
    }

    static class ViewHolder{
        ImageView tp_icon;
        TextView tp_title;
        TextView tp_description;
    }

    class TopicMenuDetailPagerAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return topics.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TopicsMenuDetailPager.ViewHolder viewHolder;
            if(convertView == null){
                convertView = View.inflate(context,R.layout.item_topic_menudetail_pager,null);
                viewHolder = new TopicsMenuDetailPager.ViewHolder();
                viewHolder.tp_icon = convertView.findViewById(R.id.tp_icon);
                viewHolder.tp_title = convertView.findViewById(R.id.tp_title);
                viewHolder.tp_description = convertView.findViewById(R.id.tp_description);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (TopicsMenuDetailPager.ViewHolder) convertView.getTag();
            }

            //根据位置得到对应的数据
            TopicDetailPagerBean.DataEntity.TopicEntity topicEntity = topics.get(position);
            viewHolder.tp_title.setText(topicEntity.getTitle());
            viewHolder.tp_description.setText(topicEntity.getDescription());
            String imageUrl = Constants.BASE_URL+topicEntity.getListimage();
            //使用Volley请求图片-设置图片了
            loaderImager(viewHolder, imageUrl );
            return convertView;
        }
    }

    //加载图片
    private void loaderImager(final TopicsMenuDetailPager.ViewHolder viewHolder, String imageurl) {
        //设置tag
        viewHolder.tp_icon.setTag(imageurl);
        //直接在这里请求会乱位置
        ImageLoader.ImageListener listener = new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                if (imageContainer != null) {

                    if (viewHolder.tp_icon != null) {
                        if (imageContainer.getBitmap() != null) {
                            //设置图片
                            viewHolder.tp_icon.setImageBitmap(imageContainer.getBitmap());
                        } else {
                            //设置默认图片
                            viewHolder.tp_icon.setImageResource(R.drawable.home_scroll_default);
                        }
                    }
                }
            }
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.e("出错了......");
                //如果出错，则说明都不显示（简单处理），最好准备一张出错图片
                viewHolder.tp_icon.setImageResource(R.drawable.home_scroll_default);
            }
        };
        VolleyManager.getImageLoader().get(imageurl, listener);
    }

    //解析json数据
    private TopicDetailPagerBean parsedJson(String json) {
        return new Gson().fromJson(json, TopicDetailPagerBean.class);
    }

}
