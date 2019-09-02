package com.example.usercwq.weekend_homework;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeiNvActivity extends AppCompatActivity {

    @BindView(R.id.iv_image)
    ImageView ivImage;
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mei_nv);
        ButterKnife.bind(this);
        mUrl = getIntent().getStringExtra("url");
        Glide.with(this).load(mUrl).into(ivImage);
    }
}
