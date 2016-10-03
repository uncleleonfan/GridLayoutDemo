package com.itheima.gridlayoutdemo;

import android.app.Dialog;
import android.content.Context;

/**
 * 创建者: Leon
 * 创建时间: 2016/10/3 9:36
 * 描述： TODO
 */
public class ChannelSelectorDialog extends Dialog{
    private static final String TAG = "ChannelSelectorDialog";

    private ChannelSelector mChannelSelector;

    public ChannelSelectorDialog(Context context) {
        this(context, -1);
    }

    public ChannelSelectorDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    private void init() {
        setContentView(R.layout.dialog_channel_selector);
        mChannelSelector = (ChannelSelector) findViewById(R.id.channel_selector);
    }

    public void setChannels(String[] selected, String[] candidates) {
        mChannelSelector.setChannels(selected, candidates);
    }


}
