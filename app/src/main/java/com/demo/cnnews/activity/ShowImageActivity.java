package com.demo.cnnews.activity;

import android.app.Activity;
import android.os.Bundle;

import com.demo.cnnews.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ShowImageActivity extends Activity {

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showimage);
        url = getIntent().getStringExtra("url");

        PhotoView photoView = (PhotoView) findViewById(R.id.iv_photo);

        final PhotoViewAttacher attacher = new PhotoViewAttacher(photoView);

        Picasso.with(this)
                .load(url)
                .into(photoView, new Callback() {
                    @Override
                    public void onSuccess() {
                        attacher.update();
                    }

                    @Override
                    public void onError() {
                    }
                });
    }
}
