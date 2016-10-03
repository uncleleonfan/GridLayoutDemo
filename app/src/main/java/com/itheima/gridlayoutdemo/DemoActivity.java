package com.itheima.gridlayoutdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * 创建者: Leon
 * 创建时间: 2016/10/3 9:07
 * 描述： TODO
 */
public class DemoActivity extends AppCompatActivity {
    private static final String TAG = "DemoActivity";

    private ChannelSelector mChannelSelector;

    private String[] mSelected = {"北京", "中国", "国际", "体育", "生活", "旅游", "科技", "军事"};

    private String[] mCandidates = {"时尚", "财经", "育儿", "汽车"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        mChannelSelector = (ChannelSelector) findViewById(R.id.channel_selector);
        mChannelSelector.setChannels(mSelected, mCandidates);
    }
}
