package com.bwie.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by PC on 2016/11/30.
 */
public class LunchAdapter extends PagerAdapter {
    int[] iv_lunch;
    Context context;
    private ImageView image;

    public LunchAdapter(int[] iv_lunch, Context context) {
        this.iv_lunch = iv_lunch;
        this.context = context;
    }

    @Override
    public int getCount() {
        return iv_lunch.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        image = new ImageView(context);
        image.setBackgroundResource(iv_lunch[position]);
        container.addView(image);
        return image;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
