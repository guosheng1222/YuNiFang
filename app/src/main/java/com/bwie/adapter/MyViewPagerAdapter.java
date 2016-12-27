package com.bwie.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bwie.bean.BeanMain;
import com.bwie.utils.ImageLoaderUtils;
import com.bwie.yunifang.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by PC on 2016/12/4.
 */
public class MyViewPagerAdapter extends PagerAdapter {
    Context context;
    ArrayList<BeanMain.DataBean.ActivityInfoBean.ActivityInfoListBean> list;

    public MyViewPagerAdapter(Context context, ArrayList<BeanMain.DataBean.ActivityInfoBean.ActivityInfoListBean> list) {
        this.context = context;
        this.list = list;
    }

    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final ImageView image=new ImageView(context);
        image.setPadding(1,1,1,1);
        image.setBackgroundResource(R.drawable.vp_image_bg);
       image.setScaleType(ImageView.ScaleType.FIT_XY);
        DisplayImageOptions options = ImageLoaderUtils.initOptions();
        ImageLoader.getInstance().displayImage(list.get(position%list.size()).getActivityImg(),image,options);
        container.addView(image);
        if (mOnItemClickLitener != null)
        {
           image.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mOnItemClickLitener.onItemClick(image,position%list.size());
                }
            });

        }
        return image;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
