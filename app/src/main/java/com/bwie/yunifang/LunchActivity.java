package com.bwie.yunifang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.bwie.adapter.LunchAdapter;

public class LunchActivity extends AppCompatActivity implements View.OnClickListener {

    int[] iv_lunch=new int[]{
            R.mipmap.xiaomi_guidance_1,R.mipmap.xiaomi_guidance_2,
            R.mipmap.xiaomi_guidance_3,R.mipmap.xiaomi_guidance_4
    };
    private ViewPager vp;
    private Button bt_jump;
    private ImageView iv1,iv2,iv3;
    private SharedPreferences sp;
    Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0)
            {
                final int a= (int) msg.obj;
                iv1.setVisibility(View.GONE);
                TranslateAnimation trans=new TranslateAnimation(Animation.RELATIVE_TO_PARENT,100,
                        Animation.RELATIVE_TO_PARENT,0,
                        Animation.RELATIVE_TO_PARENT,0,
                        Animation.RELATIVE_TO_PARENT,0
                );
                trans.setDuration(500);
                trans.setRepeatCount(0);
                trans.setRepeatMode(Animation.RESTART);
                iv2.startAnimation(trans);
                iv2.setVisibility(View.VISIBLE);
                new Thread()
                {
                    @Override
                    public void run() {
                        super.run();
                        for (int i = 2; i >=0 ; i--) {
                            try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if(a==1)
                            {
                                handler.obtainMessage(1,i).sendToTarget();
                            }
                            if(a==2)
                            {
                                handler.obtainMessage(2,i).sendToTarget();
                            }
                        }
                    }
                }.start();
            }
            if(msg.what==1)
            {
                int i = (int) msg.obj;
                bt_jump.setVisibility(View.VISIBLE);
                bt_jump.setText("跳转"+i+"s");
                if(i==0)
                {
                    bt_jump.setVisibility(View.GONE);
                    iv2.setVisibility(View.GONE);
                    TranslateAnimation trans=new TranslateAnimation(Animation.RELATIVE_TO_PARENT,100,
                            Animation.RELATIVE_TO_PARENT,0,
                            Animation.RELATIVE_TO_PARENT,0,
                            Animation.RELATIVE_TO_PARENT,0
                    );
                    trans.setDuration(500);
                    trans.setRepeatCount(0);
                    trans.setRepeatMode(Animation.RESTART);
                    vp.startAnimation(trans);
                    vp.setAdapter(new LunchAdapter(iv_lunch,LunchActivity.this));
                }
            }
            if(msg.what==2)
            {
                int i = (int) msg.obj;
                bt_jump.setVisibility(View.VISIBLE);
                bt_jump.setText("跳转"+i+"s");
                if(i==0)
                {
                    Intent in=new Intent(LunchActivity.this,MainActivity.class);
                    startActivity(in);
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lunch);
        vp = (ViewPager) findViewById(R.id.vp_lunch);
        iv1= (ImageView) findViewById(R.id.iv_lunch1);
        iv2= (ImageView) findViewById(R.id.iv_lunch2);
        iv3= (ImageView) findViewById(R.id.iv_lunch3);
        bt_jump = (Button) findViewById(R.id.bt_jump);
        sp = getSharedPreferences("lunch",MODE_PRIVATE);
        boolean flag = sp.getBoolean("boolean", false);
        bt_jump.setOnClickListener(this);
        iv3.setOnClickListener(this);

        if(flag==false)
        {
            SharedPreferences.Editor edit = sp.edit();
            edit.putBoolean("boolean",true);
            edit.commit();
            new Thread()
            {
                @Override
                public void run() {
                    super.run();
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.obtainMessage(0,1).sendToTarget();
                }
            }.start();
        }
        else
        {
            new Thread()
            {
                @Override
                public void run() {
                    super.run();
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.obtainMessage(0,2).sendToTarget();
                }
            }.start();
        }
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < vp.getAdapter().getCount(); i++) {
                    if(position==vp.getAdapter().getCount()-1)
                    {
                        iv3.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        iv3.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.bt_jump:
                Intent in=new Intent(LunchActivity.this,MainActivity.class);
                startActivity(in);
                handler.removeCallbacksAndMessages(null);
                break;
            case R.id.iv_lunch3:
                TranslateAnimation trans=new TranslateAnimation(Animation.RELATIVE_TO_PARENT,0,
                        Animation.RELATIVE_TO_PARENT,-1,
                        Animation.RELATIVE_TO_PARENT,0,
                        Animation.RELATIVE_TO_PARENT,0
                );
                trans.setDuration(1500);
                trans.setRepeatCount(0);
                trans.setFillAfter(!trans.getFillAfter());
                trans.setRepeatMode(Animation.RESTART);
                iv3.startAnimation(trans);
                trans.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent in=new Intent(LunchActivity.this,MainActivity.class);
                        startActivity(in);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                break;
        }
    }
}
