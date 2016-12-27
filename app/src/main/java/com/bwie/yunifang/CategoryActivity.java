package com.bwie.yunifang;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bwie.bean.BeanCate;
import com.bwie.fragment.CateFragment;
import com.bwie.view.ViewPagerIndicator;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;

public class CategoryActivity extends AutoLayoutActivity {
    ArrayList<BeanCate.DataBean.CategoryBean.ChildrenBean> list=new ArrayList<>();
    private LinearLayout lin;
    private ViewPager vp;
    private ViewPagerIndicator viewPagerIndicator;
    private TextView title;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_category);
        initView();
        list= (ArrayList<BeanCate.DataBean.CategoryBean.ChildrenBean>) getIntent().getExtras().getSerializable("children");
        int page = getIntent().getIntExtra("page", 0);
        if(list.get(0).getCat_name().contains("面膜"))
        {
            title.setText("面膜");
        }
        if(list.get(0).getCat_name().contains("补水"))
        {
            title.setText("按功效");
        }
        if(list.get(0).getCat_name().contains("肤质"))
        {
            title.setText("按肤质");
        }
        FragmentPagerAdapter pagerAdapter =  new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return list.size();
            }
            @Override
            public Fragment getItem(int position) {
                CateFragment f1=CateFragment.getFragment(list.get(position).getId());
                return f1;
            }
            //给每个页面标题
            @Override
            public CharSequence getPageTitle(int position) {
                return list.get(position).getCat_name();
            }
        };
        //设置适配器
        vp.setAdapter(pagerAdapter);
        viewPagerIndicator.setViewPager(vp);
        //设置当前页面
        vp.setCurrentItem(page);
        //返回监听事件
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, R.anim.login_out);
            }
        });
    }

    private void initView() {
        viewPagerIndicator = (ViewPagerIndicator) findViewById(R.id.category_vpIndicator);
        vp = (ViewPager) findViewById(R.id.category_viewPager);
        back = (ImageView) findViewById(R.id.category_back);
        title = (TextView) findViewById(R.id.category_title);
    }

    @Override
    protected void onStop() {
        super.onStop();
        list.clear();
    }
}
