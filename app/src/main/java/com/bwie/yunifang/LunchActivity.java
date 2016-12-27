package com.bwie.yunifang;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

import com.zhy.autolayout.AutoLayoutActivity;

public class LunchActivity extends AutoLayoutActivity {


Handler handler=new Handler()
{
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if(msg.what==0)
        {
            Intent in=new Intent(LunchActivity.this,Lunch1Activity.class);
            startActivity(in);
            overridePendingTransition(R.anim.login_in,0);
        }
    }
};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lunch);
        //停止5秒  自动跳转
        new Thread(){
           @Override
           public void run() {
               super.run();
               try {
                   sleep(1500);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               handler.obtainMessage(0,0).sendToTarget();
           }
        }.start();
    }
}
