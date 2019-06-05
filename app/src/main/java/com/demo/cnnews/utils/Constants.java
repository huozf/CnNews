package com.demo.cnnews.utils;

public class Constants {
    /**
     * 联网请求的ip和端口
     */
    public static final String BASE_URL = "http://188.131.149.208:8080/web_home";
    //模拟器访问本地tomcat资源
    //public static final String BASE_URL = "http://10.0.2.2:8080/web_home";

    //新闻中心的网络地址
    public static final String NEWSCENTER_PAGER_URL = BASE_URL+"/static/api/news/categories.json";

    //专题中心
    public static final String TOPIC_PAGER_URL = BASE_URL+"/static/api/news/10002/list1_1.json";

    //组图链接
    public static final String PHOTO_PAGER_URL = BASE_URL+"/static/api/news/10003/list_1.json";
}
