package com.demo.cnnews.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.demo.cnnews.R;
import com.demo.cnnews.utils.ShareUtil;


public class NewsDetailActivity extends Activity implements View.OnClickListener{

    private TextView tvTitle;
    private ImageButton ibMenu;
    private ImageButton ibBack;
    private ImageButton ibTextsize;
    private ImageButton ibShare;
    private WebView webview;
    private ProgressBar pbLoading;

    private String url;
    private WebSettings webSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        findViews();
        getData();
    }

    private void findViews() {
        tvTitle = findViewById(R.id.tv_title);
        ibMenu = findViewById(R.id.ib_menu);
        ibBack = findViewById(R.id.ib_back);
        ibTextsize = findViewById(R.id.ib_textsize);
        ibShare = findViewById(R.id.ib_share);
        webview = findViewById(R.id.webview);
        pbLoading = findViewById(R.id.pb_loading);

        tvTitle.setVisibility(View.GONE);
        ibMenu.setVisibility(View.GONE);
        ibBack.setVisibility(View.VISIBLE);
        ibTextsize.setVisibility(View.VISIBLE);
        ibShare.setVisibility(View.VISIBLE);

        ibBack.setOnClickListener(this);
        ibTextsize.setOnClickListener(this);
        ibShare.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == ibBack) {
            // Handle clicks for ibBack
            finish();
        } else if (v == ibTextsize) {
            showChangeTextSizeDialog();
        } else if (v == ibShare) {
            //Toast.makeText(NewsDetailActivity.this, "分享", Toast.LENGTH_SHORT).show();
            ShareUtil.showShare(this);
        }
    }

    private int tempSize = 2;
    private int realSize = tempSize;

    private void showChangeTextSizeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置文字大小");
        String[] items = {"超大字体","大字体","正常字体","小字体","超小字体"};
        builder.setSingleChoiceItems(items, realSize, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tempSize = which;
            }
        });
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                realSize = tempSize;
                changeTextSize(realSize);
            }
        });
        builder.show();

    }

    private void changeTextSize(int realSize) {
        switch (realSize){
            case 0://超大字体
                webSettings.setTextZoom(200);
                break;
            case 1://大字体
                webSettings.setTextZoom(150);
                break;
            case 2://正常字体
                webSettings.setTextZoom(100);
                break;
            case 3://小字体
                webSettings.setTextZoom(75);
                break;
            case 4://超小字体
                webSettings.setTextZoom(50);
                break;
        }
    }


    private void getData() {
        url = getIntent().getStringExtra("url");

        //设置支持javaScript
        webSettings = webview.getSettings();
        //设置支持javaScript
        webSettings.setJavaScriptEnabled(true);
        //设置双击变大变小
        webSettings.setUseWideViewPort(true);
        //增加缩放按钮
        webSettings.setBuiltInZoomControls(true);
        //设置文字大小
        webSettings.setTextZoom(100);
        //不让从当前网页跳转到系统的浏览器中
        webview.setWebViewClient(new WebViewClient() {
            //当加载页面完成的时候回调
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pbLoading.setVisibility(View.GONE);
            }
        });
        webview.loadUrl(url);
        //webview.loadUrl("http://www.atguigu.com/teacher.shtml");

    }





}
