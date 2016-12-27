package com.bwie.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bwie.yunifang.R;

/**
 * Created by PC on 2016/11/1.
 */
public class ViewPagerIndicator extends LinearLayout implements View.OnClickListener,ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    public void init() {

    }

    public ViewPagerIndicator(Context context) {
        super(context);
        init();
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    public void setViewPager(ViewPager vp)
    {
        this.removeAllViews();
        this.viewPager=vp;
        vp.setOnPageChangeListener(this);
        PagerAdapter adapter=vp.getAdapter();

        for (int i = 0; i < adapter.getCount(); i++) {
           TextView text=new TextView(getContext());
            text.setText(adapter.getPageTitle(i));
            text.setOnClickListener(this);
            text.setMaxEms(5);
            text.setMaxLines(1);
            text.setTextColor(Color.BLACK);
            text.setBackgroundColor(Color.WHITE);
            text.setTextSize(20);
            if(i==0)
            {
                text.setTextColor(Color.parseColor("#FC6B87"));
                text.setBackgroundResource(R.drawable.shape_all_check);
            }
            text.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1.0f);
            this.addView(text,params);
        }
    }
    @Override
    public void onClick(View v) {
        for (int i = 0; i < this.getChildCount(); i++) {
            TextView text= (TextView) this.getChildAt(i);
            text.setGravity(Gravity.CENTER);
            if (v==text)
            {
                viewPager.setCurrentItem(i);
                text.setTextColor(Color.parseColor("#FC6B87"));
                text.setBackgroundResource(R.drawable.shape_all_check);
            }
            else
            {
                text.setTextColor(Color.BLACK);
                text.setBackgroundColor(Color.WHITE);
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < this.getChildCount(); i++) {
            TextView text= (TextView) this.getChildAt(i);
            if (position==i)
            {
                text.setTextColor(Color.parseColor("#FC6B87"));
                text.setBackgroundResource(R.drawable.shape_all_check);
            }
            else
            {
                text.setTextColor(Color.BLACK);
                text.setBackgroundColor(Color.WHITE);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}

