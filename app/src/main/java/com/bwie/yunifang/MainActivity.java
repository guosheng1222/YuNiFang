package com.bwie.yunifang;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bwie.factory.FragmentFactory;
import com.bwie.view.ShowingPage;

public class MainActivity extends AppCompatActivity {

    private ShowingPage showingPage;
    private ViewPager vp;
    private RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        vp = (ViewPager) findViewById(R.id.vp_main);
        rg = (RadioGroup) findViewById(R.id.rg_main);
        RadioButton rb = (RadioButton) rg.getChildAt(0);
        rb.setChecked(true);
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
                switch (checkedId)
                {
                    case R.id.rb_home_page:
                        vp.setCurrentItem(0);
                        break;
                    case R.id.rb_category:
                        vp.setCurrentItem(1);
                        break;
                    case R.id.rb_cart:
                        vp.setCurrentItem(2);
                        break;
                    case R.id.rb_mine:
                        vp.setCurrentItem(3);
                        break;

                }
            }
        });
    }
}
