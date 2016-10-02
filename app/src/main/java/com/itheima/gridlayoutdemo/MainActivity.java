package com.itheima.gridlayoutdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private GridLayout mGridLayout;

    private int mGridItemMargin;

    private View mDragView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_calculator);
        setContentView(R.layout.activity_main);
        mGridLayout = (GridLayout) findViewById(R.id.grid_layout);
        mGridLayout.setOnDragListener(mOnDragListener);
        mGridItemMargin = getResources().getDimensionPixelOffset(R.dimen.grid_item_margin);
        initItems();
    }

    private void initItems() {
        for (int i = 0; i < 20; i++) {
            addItem(i);
        }
    }

    private void addItem(int i) {
        TextView textView = createTextView(i);
        mGridLayout.addView(textView, 0);
//        mGridLayout.addView(textView);
    }

    @NonNull
    private TextView createTextView(int i) {
        TextView textView = new TextView(this);
        textView.setText("item " + i);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
        layoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.width = getResources().getDisplayMetrics().widthPixels / mGridLayout.getColumnCount() - 2 * mGridItemMargin;
        layoutParams.setMargins(mGridItemMargin, mGridItemMargin, mGridItemMargin, mGridItemMargin);
        textView.setLayoutParams(layoutParams);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundResource(R.drawable.item_bg);
        textView.setTextColor(getResources().getColorStateList(R.color.grid_item_color_selector));
        textView.setClickable(true);
        textView.setOnLongClickListener(mOnLongClickListener);
        return textView;
    }

    public void onAddItem(View view) {
        addItem(100);
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
                    break;
                case DragEvent.ACTION_DRAG_LOCATION:
                    Log.d(TAG, "onDrag: ACTION_DRAG_LOCATION");
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
}
