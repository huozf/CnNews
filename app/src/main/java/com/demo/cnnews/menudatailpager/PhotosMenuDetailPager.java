package com.demo.cnnews.menudatailpager;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.demo.cnnews.R;
import com.demo.cnnews.activity.ShowImageActivity;
import com.demo.cnnews.base.MenuDetaiBasePager;
import com.demo.cnnews.domain.NewsCenterPagerEntity;
import com.demo.cnnews.domain.PhotosMenuDetailPagerBean;
import com.demo.cnnews.utils.CacheUtils;
import com.demo.cnnews.utils.Constants;
import com.demo.cnnews.utils.LogUtil;
import com.demo.cnnews.volley.VolleyManager;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 图组详情页面
 */
public class PhotosMenuDetailPager extends MenuDetaiBasePager {

    //private final NewsCenterPagerEntity.DetailPagerData detailPagerData;
    @ViewInject(R.id.listview)
    private ListView listview;

    @ViewInject(R.id.gridview)
    private GridView gridview;

    private final String url;
    private PhotosMenuDetailPagerAdapter adapter;
    private List<PhotosMenuDetailPagerBean.DataEntity.NewsEntity> news;


    public PhotosMenuDetailPager(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.photos_menudetail_pager, null);
        x.view().inject(this,view);

        //设置点击某条的item的监听
        listview.setOnItemClickListener(new MyOnItemClickListener());
        gridview.setOnItemClickListener(new MyOnItemClickListener());

        return view;
    }

    class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            PhotosMenuDetailPagerBean.DataEntity.NewsEntity newsEntity = news.get(position);
            String imageUrl = Constants.BASE_URL+newsEntity.getLargeimage();
            Intent intent = new Intent(context, ShowImageActivity.class);
            intent.putExtra("url",imageUrl);
            context.startActivity(intent);
        }
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("图组详情页面数据被初始化了......");
        //url = Constants.BASE_URL + detailPagerData.getUrl();
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
                LogUtil.e("===使用xUtils3联网请求成功===");
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
        PhotosMenuDetailPagerBean bean = parsedJson(json);
        isShowListView = true;
        //设置适配器
        news = bean.getData().getNews();
        adapter = new PhotosMenuDetailPagerAdapter();
        listview.setAdapter(adapter);
    }



    class PhotosMenuDetailPagerAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return news.size();
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
            ViewHolder viewHolder;
            if(convertView == null){
                convertView = View.inflate(context,R.layout.item_photos_menudetail_pager,null);
                viewHolder = new ViewHolder();
                viewHolder.iv_icon = convertView.findViewById(R.id.iv_icon);
                viewHolder.tv_title = convertView.findViewById(R.id.tv_title);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }

            //根据位置得到对应的数据
            PhotosMenuDetailPagerBean.DataEntity.NewsEntity newsEntity = news.get(position);
            viewHolder.tv_title.setText(newsEntity.getTitle());
            String imageUrl = Constants.BASE_URL+newsEntity.getSmallimage();
            //使用Volley请求图片-设置图片了
            loaderImager(viewHolder, imageUrl );
            return convertView;
        }
    }

    static class ViewHolder{
        ImageView iv_icon;
        TextView tv_title;
    }

    //加载图片
    private void loaderImager(final ViewHolder viewHolder, String imageurl) {
        //设置tag
        viewHolder.iv_icon.setTag(imageurl);
        //直接在这里请求会乱位置
        ImageLoader.ImageListener listener = new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                if (imageContainer != null) {

                    if (viewHolder.iv_icon != null) {
                        if (imageContainer.getBitmap() != null) {
                            //设置图片
                            viewHolder.iv_icon.setImageBitmap(imageContainer.getBitmap());
                        } else {
                            //设置默认图片
                            viewHolder.iv_icon.setImageResource(R.drawable.home_scroll_default);
                        }
                    }
                }
            }
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.e("出错了......");
                //如果出错，则说明都不显示（简单处理），最好准备一张出错图片
                viewHolder.iv_icon.setImageResource(R.drawable.home_scroll_default);
            }
        };
        VolleyManager.getImageLoader().get(imageurl, listener);
    }

    private PhotosMenuDetailPagerBean parsedJson(String json) {
        return new Gson().fromJson(json, PhotosMenuDetailPagerBean.class);
    }


    /**
     * true,显示ListView，隐藏GridView
     * false,显示GridView,隐藏ListView
     */
    private boolean isShowListView = true;
    public void swichListAndGrid(ImageButton ib_swich_list_grid) {
        if(isShowListView){
            isShowListView = false;
            //显示GridView,隐藏ListView
            gridview.setVisibility(View.VISIBLE);
            adapter = new PhotosMenuDetailPagerAdapter();
            gridview.setAdapter(adapter);
            listview.setVisibility(View.GONE);
            //按钮显示--ListView
            ib_swich_list_grid.setImageResource(R.drawable.icon_pic_list_type);

        }else{
            isShowListView = true;
            //显示ListView，隐藏GridView
            listview.setVisibility(View.VISIBLE);
            adapter = new PhotosMenuDetailPagerAdapter();
            listview.setAdapter(adapter);
            gridview.setVisibility(View.GONE);
            //按钮显示--GridView
            ib_swich_list_grid.setImageResource(R.drawable.icon_pic_grid_type);
        }
    }

}
