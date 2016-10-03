package com.itheima.gridlayoutdemo;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;

/**
 * 创建者: Leon
 * 创建时间: 2016/10/3 9:36
 * 描述： TODO
 */
public class ChannelSelectorDialog extends Dialog{
    private static final String TAG = "ChannelSelectorDialog";

    private ChannelSelector mChannelSelector;

    public ChannelSelectorDialog(Context context) {
        this(context, R.style.DialogTheme);
    }

    public ChannelSelectorDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    private void init() {
        setContentView(R.layout.dialog_channel_selector);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
        attributes.gravity = Gravity.TOP;
        getWindow().setAttributes(attributes);
        setCanceledOnTouchOutside(true);
        mChannelSelector = (ChannelSelector) findViewById(R.id.channel_selector);
    }

    public void setChannels(String[] selected, String[] candidates) {
        mChannelSelector.setChannels(selected, candidates);
    }


}
