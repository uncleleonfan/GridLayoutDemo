package com.itheima.gridlayoutdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private GridLayout mGridLayout;

    private int mGridItemMargin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_calculator);
        setContentView(R.layout.activity_main);
        mGridLayout = (GridLayout) findViewById(R.id.grid_layout);
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
        mGridLayout.addView(textView);
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
        return textView;
    }

    public void onAddItem(View view) {
        addItem(100);
    }
}
