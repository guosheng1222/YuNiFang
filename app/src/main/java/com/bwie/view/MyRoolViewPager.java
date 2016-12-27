package com.bwie.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
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
 * Created by PC on 2016/12/1.
 */
public class MyRoolViewPager extends ViewPager {

    private DisplayImageOptions imageOptios;
    private ArrayList<String> imageUrlList;
    private ArrayList<ImageView> docList;
    MyPageAdapter adapter=new MyPageAdapter();
    Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int currentItem = MyRoolViewPager.this.getCurrentItem();
            currentItem++;
            MyRoolViewPager.this.setCurrentItem(currentItem);
           this.sendEmptyMessageDelayed(0,2000);
        }
    };
    private OnPageClickListener onPageClickListener;

    public MyRoolViewPager(Context context) {
        super(context);
        init();
    }

    public MyRoolViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        imageOptios = ImageLoaderUtils.initOptions();
    }

    public void initData(ArrayList<String> imageUrlList, final int[] dotArray, final ArrayList<ImageView> docList) {
        this.imageUrlList = imageUrlList;
        this.docList = docList;
        this.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < docList.size(); i++) {
                    if(position%docList.size()==i)
                    {
                        docList.get(i).setImageResource(dotArray[0]);
                    }
                    else
                    {
                        docList.get(i).setImageResource(dotArray[1]);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    public void setAdapter()
    {
       if(adapter==null)
       {
           adapter=new MyPageAdapter();
       }
        this.setAdapter(adapter);
        handler.sendEmptyMessageDelayed(0,2000);
    }

    class MyPageAdapter extends PagerAdapter
    {

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
            View view= CommonUtils.inflate(R.layout.roolviewpager_item);
            ImageView image = (ImageView) view.findViewById(R.id.iv_roolimage);
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    // 设置图片在下载期间显示的图片
                    .showImageOnLoading(R.drawable.default_2)
                    // 设置图片Uri为空或是错误的时候显示的图片
                    .showImageOnFail(R.drawable.default_2)
                    // 设置下载的图片是否缓存在内存中
                    .cacheInMemory(true)
                    // 设置下载的图片是否缓存在SD卡中
                    .cacheOnDisc(true)
                    .build();
            ImageLoader.getInstance().displayImage(imageUrlList.get(position%imageUrlList.size()),image,options);
            container.addView(view);
            view.setOnTouchListener(new OnTouchListener() {

                private long downtime;
                private float y;
                private float x;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction())
                    {
                        case MotionEvent.ACTION_DOWN:
                            x = event.getX();
                            y = event.getY();
                            downtime = System.currentTimeMillis();
                            handler.removeCallbacksAndMessages(null);
                            break;
                        case MotionEvent.ACTION_UP:
                            float upX = event.getX();
                            float upY = event.getY();
                            if(upX==x&&upY==y&&System.currentTimeMillis()-downtime<1000)
                            {
                                if(onPageClickListener!=null){
                                    onPageClickListener.setOnPage(position);
                                }
                            }
                            break;
                        case MotionEvent.ACTION_MOVE:
                            handler.removeCallbacksAndMessages(null);
                            break;
                    }
                    return true;
                }
            });
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacksAndMessages(null);
    }

    public interface OnPageClickListener{
        public void setOnPage(int position);
    }
    //设置接口
    public void setOnPageClickListener(OnPageClickListener onPageClickListener){
        this.onPageClickListener = onPageClickListener;
    }

}
