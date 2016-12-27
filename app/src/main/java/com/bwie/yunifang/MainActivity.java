package com.bwie.yunifang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bwie.factory.FragmentFactory;
import com.bwie.view.LazyViewPager;
import com.bwie.view.ShowingPage;
import com.zhy.autolayout.AutoLayoutActivity;

public class MainActivity extends AutoLayoutActivity {

    private ShowingPage showingPage;
    public static LazyViewPager vp;
    public static  RadioGroup rg;
    private String user_name;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences("login",MODE_PRIVATE);
        vp = (LazyViewPager) findViewById(R.id.vp_main);
        rg = (RadioGroup) findViewById(R.id.rg_main);
        RadioButton rb = (RadioButton) rg.getChildAt(0);
        rb.setChecked(true);
        //设置适配器
        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return FragmentFactory.getFragment(position);
            }

            @Override
            public int getCount() {
                return 4;
            }
        });
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home_page:
                        vp.setCurrentItem(0);
                        break;
                    case R.id.rb_category:
                        vp.setCurrentItem(1);
                        break;
                    case R.id.rb_cart:
                        user_name = sp.getString("user_name", null);
                        if(user_name!=null)
                        {
                            //判断是否登录
                            vp.setCurrentItem(2);
                        }
                        else
                        {
                            //跳转到登录界面
                            Intent in=new Intent(MainActivity.this,LoginActivity.class);
                            startActivity(in);
                            overridePendingTransition(R.anim.login_in,0);
                        }
                        break;
                    case R.id.rb_mine:
                        vp.setCurrentItem(3);
                        break;

                }
            }
        });
    }
}
