package com.bwie.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bwie.utils.CommonUtils;
import com.bwie.utils.ImageLoaderUtils;
import com.bwie.yunifang.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by PC on 2016/12/9.
 */
public class InfoViewPagerAdapter extends PagerAdapter {
    private ArrayList<String> imageUrlList;

    public InfoViewPagerAdapter(ArrayList<String> imageUrlList) {
        this.imageUrlList = imageUrlList;
    }

    @Override
    public int getCount() {
        return imageUrlList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view= CommonUtils.inflate(R.layout.roolviewpager_item);
        ImageView image = (ImageView) view.findViewById(R.id.iv_roolimage);
        image.setScaleType(ImageView.ScaleType.FIT_XY);
        DisplayImageOptions options = ImageLoaderUtils.initOptions();
        ImageLoader.getInstance().displayImage(imageUrlList.get(position%imageUrlList.size()),image,options);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView((View) object);
    }
}
