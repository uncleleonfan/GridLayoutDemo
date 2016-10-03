package com.itheima.gridlayoutdemo;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

/**
 * 创建者: Leon
 * 创建时间: 2016/10/2 23:59
 * 描述： TODO
 */
public class ChannelSelector extends RelativeLayout {
    private static final String TAG = "ChannelSelector";


    private GridLayout mSelectedGridLayout;

    private GridLayout mCandidateGridLayout;

    private int mGridItemMargin;

    private View mDragView;

    private Rect[] mRects;//gridlayout里面的子条目的所有矩形

    private List<String> mCandidates;
    private List<String> mSelected;

    public ChannelSelector(Context context) {
        this(context, null);
    }

    public ChannelSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_channel_selector, this);
        mSelectedGridLayout = (GridLayout) findViewById(R.id.selected_grid_layout);
        mCandidateGridLayout = (GridLayout) findViewById(R.id.candicate_grid_layout);
        mSelectedGridLayout.setOnDragListener(mOnDragListener);
        mGridItemMargin = getResources().getDimensionPixelOffset(R.dimen.grid_item_margin);
//        initSelectedItems();
//        initCandidateItems();
    }

    public void setChannels(List<String> selected, List<String> candidates) {
        mSelected = selected;
        mCandidates = candidates;
        initCandidateItems();
        initSelectedItems();
    }

    public void setChannels(String[] selected, String[] candidates) {
        mSelected = Arrays.asList(selected);
        mCandidates = Arrays.asList(candidates);
        initCandidateItems();
        initSelectedItems();
    }

    private void initCandidateItems() {
        for (String item : mCandidates) {
            addCandidateItem(item);
        }
    }

    private void initSelectedItems() {
        for (String item : mSelected) {
            addSelectedItem(item);
        }
    }

    private void addCandidateItem(String text) {
        TextView textView = createTextView();
        textView.setText(text);
        textView.setOnClickListener(onCandidateListener);
        mCandidateGridLayout.addView(textView);
    }

    private View.OnClickListener onCandidateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mCandidateGridLayout.removeView(v);
            addSelectedItem(((TextView)v).getText().toString());
        }
    };

    private View.OnClickListener mSelectedListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSelectedGridLayout.removeView(v);
            addCandidateItem(((TextView)v).getText().toString());
        }
    };

    private void addSelectedItem(String text) {
        TextView textView = createTextView();
        textView.setText(text);
        textView.setOnLongClickListener(mOnLongClickListener);
        textView.setOnClickListener(mSelectedListener);
        mSelectedGridLayout.addView(textView);
    }

    @NonNull
    private TextView createTextView() {
        TextView textView = new TextView(getContext());
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
        layoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.width = getResources().getDisplayMetrics().widthPixels / mSelectedGridLayout.getColumnCount() - 2 * mGridItemMargin;
        layoutParams.setMargins(mGridItemMargin, mGridItemMargin, mGridItemMargin, mGridItemMargin);
        textView.setLayoutParams(layoutParams);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundResource(R.drawable.item_bg);
        textView.setTextColor(getResources().getColorStateList(R.color.grid_item_color_selector));
        textView.setClickable(true);
        return textView;
    }


    private View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            mDragView = v;
            v.startDrag(null, new View.DragShadowBuilder(v), null, 0);
            mDragView.setSelected(true);
            return true;
        }
    };

    private View.OnDragListener mOnDragListener = new View.OnDragListener() {

        /**
         * ACTION_DRAG_STARTED  当开启拖拽事件，阴影已创建，该方法执行一次
         * ACTION_DRAG_ENDED   当拖拽事件结束时，该方法执行一次
         * ACTION_DRAG_ENTERED  当手指进入监听拖拽事件的视图范围内（gridlayout），该发现执行一次
         * ACTION_DRAG_EXITED   当手指离开监听拖拽事件的视图范围时（gridlayout），该发现执行一次
         * ACTION_DRAG_LOCATION  当手指在监听拖拽事件的视图范围内移动时（gridlayout），该方法会执行多次
         * ACTION_DROP            当手指在  监听拖拽事件的视图范围内抬起时，执行一次
         */
        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    Log.d(TAG, "onDrag: ACTION_DRAG_STARTED");
                    initRects();
                    break;
                case DragEvent.ACTION_DRAG_LOCATION:
                    Log.d(TAG, "onDrag: ACTION_DRAG_LOCATION");
                    int index = getTouchIndex(event);
                    View targetView = mSelectedGridLayout.getChildAt(index);
                    if (index > -1 &&  targetView != mDragView) {
                        mSelectedGridLayout.removeView(mDragView);
                        mSelectedGridLayout.addView(mDragView, index);
                    }
                    break;
                case DragEvent.ACTION_DROP:
                    Log.d(TAG, "onDrag: ACTION_DROP");
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    Log.d(TAG, "onDrag: ACTION_DRAG_ENDED");
                    mDragView.setSelected(false);
                    break;
            }
            return true;
        }

    };

    private void initRects() {
        mRects = new Rect[mSelectedGridLayout.getChildCount()];
        for (int i = 0; i < mSelectedGridLayout.getChildCount(); i++) {
            View child = mSelectedGridLayout.getChildAt(i);
            Rect rect = new Rect(child.getLeft(), child.getTop(), child.getRight(), child.getBottom());
            mRects[i] = rect;
        }
    }

    //实时判断，当前手指点是否进入了相应的子条目，如果进入了就返回位置
    private int getTouchIndex(DragEvent event) {
        for (int i = 0; i < mRects.length; i++) {
            if (mRects[i].contains((int) event.getX(), (int) event.getY())) {
                return  i;
            }
        }
        return  -1;
    }
}
