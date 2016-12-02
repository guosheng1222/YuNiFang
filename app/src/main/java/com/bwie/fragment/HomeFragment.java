package com.bwie.fragment;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bwie.adapter.HomeGrid1Adapter;
import com.bwie.base.BaseData;
import com.bwie.base.BaseFragment;
import com.bwie.bean.BeanMain;
import com.bwie.utils.CommonUtils;
import com.bwie.utils.LogUtils;
import com.bwie.utils.URLUtils;
import com.bwie.view.MyRoolViewPager;
import com.bwie.view.ShowingPage;
import com.bwie.yunifang.R;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by PC on 2016/11/28.
 */
public class HomeFragment extends BaseFragment {
    private String s;

    ArrayList<String> imageUrlList=new ArrayList<>();
    ArrayList<ImageView> dotList=new ArrayList<>();

    private BeanMain beanMain;
    int[] dotArray=new int[]{R.mipmap.page_indicator_focused,R.mipmap.page_indicator_unfocused};
    private LinearLayout lin;
    private MyRoolViewPager vp;
    private GridView grid1;

    @Override
    protected View createSuccessView() {
        View view = initView();
        initRoolView();
        initGridView1();
        return view;
    }

    private void initGridView1() {
        ArrayList<BeanMain.DataBean.Ad5Bean> ad5 = (ArrayList<BeanMain.DataBean.Ad5Bean>) beanMain.getData().getAd5();
        grid1.setAdapter(new HomeGrid1Adapter(ad5,getActivity()));
    }

    private void initRoolView() {
        ArrayList<BeanMain.DataBean.Ad1Bean> ad1 = (ArrayList<BeanMain.DataBean.Ad1Bean>) beanMain.getData().getAd1();
        for (int i = 0; i < ad1.size(); i++) {
            imageUrlList.add(ad1.get(i).getImage());
        }
        initDot(ad1);
        vp.initData(imageUrlList,dotArray,dotList);
        vp.setAdapter();
        vp.setOnPageClickListener(new MyRoolViewPager.OnPageClickListener() {
            @Override
            public void setOnPage(int position) {
                Toast.makeText(getActivity(),"我要跳转到详情了",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initDot(ArrayList<BeanMain.DataBean.Ad1Bean> adlList) {
        dotList.clear();
        lin.removeAllViews();
        for (int i = 0; i < adlList.size(); i++) {
            ImageView imageView = new ImageView(getActivity());
            if(i==0)
            {
                imageView.setImageResource(dotArray[0]);
            }
            else
            {
                imageView.setImageResource(dotArray[1]);
            }
            dotList.add(imageView);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10,20,8,5);
            lin.addView(imageView,params);
        }
    }

    @NonNull
    private View initView() {
        View view = CommonUtils.inflate(R.layout.homefragment_main);
        vp = (MyRoolViewPager) view.findViewById(R.id.myRoolViewPager);
        lin = (LinearLayout) view.findViewById(R.id.roolView_lin);
        grid1 = (GridView) view.findViewById(R.id.home_grid1);
        return view;
    }

    @Override
    protected void onload() {
       new BaseData() {
           @Override
           public void setResultData(String data) {
               HomeFragment.this.s=data;

               Gson gson=new Gson();
               beanMain = gson.fromJson(s,BeanMain.class);
               LogUtils.i("AAAAA","************"+beanMain.getCode());
               HomeFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
           }
           @Override
           protected void setResultError(ShowingPage.StateType stateLoadError) {
               HomeFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_ERROR);
           }
       }.getData(URLUtils.homeUrl,URLUtils.homeArgs,0,BaseData.NORMALTIME);
    }
}
