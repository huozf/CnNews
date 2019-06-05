package com.demo.cnnews.utils;

import com.demo.cnnews.domain.NewsCenterPagerBean;
import com.demo.cnnews.domain.NewsCenterPagerEntity;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AnalyJsonUtil {

    //使用Android系统自带的API解析json数据
    public static NewsCenterPagerEntity analyJson(String json) {
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


    /**
     * 解析json数据：使用Gson
     * @param json
     * @return
     */
    public static NewsCenterPagerBean parsedJson(String json) {
        Gson gson = new Gson();
        NewsCenterPagerBean bean = gson.fromJson(json,NewsCenterPagerBean.class);
        return bean;
    }

}
