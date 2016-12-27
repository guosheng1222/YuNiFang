package com.bwie.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.utils.LogUtils;
import com.bwie.yunifang.R;
import com.liaoinstan.springview.container.BaseHeader;
import com.liaoinstan.springview.widget.SpringView;

/**
 * Created by PC on 2016/12/6.
 */
public class Myheader extends BaseHeader implements SpringView.DragHander{

    private ImageView image;
    private TextView text;

    //返回视图
    @Override
    public View getView(LayoutInflater inflater, ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.headview,viewGroup,true);
        image = (ImageView) view.findViewById(R.id.head_image);
        text = (TextView) view.findViewById(R.id.head_text);
        return view;
    }

    @Override
    public void onPreDrag(View rootView) {
        text.setText("松手加载   ");
    }

    @Override
    public void onDropAnim(View rootView, int dy) {

        LogUtils.i("AAAA","*****"+dy);
        RotateAnimation rotate=new RotateAnimation(dy,dy+5,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotate.setRepeatCount(1);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setRepeatMode(Animation.REVERSE);
        rotate.setDuration(100);
        rotate.setFillAfter(!rotate.getFillAfter());
        image.startAnimation(rotate);

    }

    @Override
    public void onLimitDes(View rootView, boolean upORdown) {

    }

    @Override
    public void onStartAnim() {
        text.setText("正在加载···");
        RotateAnimation rotate=new RotateAnimation(0,360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotate.setRepeatCount(10);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setRepeatMode(Animation.RESTART);
        rotate.setDuration(600);
        rotate.setFillAfter(!rotate.getFillAfter());
        image.startAnimation(rotate);
    }

    @Override
    public void onFinishAnim() {

    }
    //临界高度
    @Override
    public int getDragLimitHeight(View rootView) {
        return 100;
    }

    @Override
    public int getDragMaxHeight(View rootView) {
        return 400;
    }

}
