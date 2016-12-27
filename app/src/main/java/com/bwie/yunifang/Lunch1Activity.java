package com.bwie.yunifang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.zhy.autolayout.AutoLayoutActivity;

import java.util.Timer;
import java.util.TimerTask;

public class Lunch1Activity extends AutoLayoutActivity {

    private Button jump;
    int i=4;
    Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0)
            {
                int i= (int) msg.obj;
                jump.setText("跳转"+i+"s");
                if(i==1)
                {
                    if(sp.getBoolean("boolean",false))
                    {
                        Intent in=new Intent(Lunch1Activity.this,MainActivity.class);
                        startActivity(in);
                        overridePendingTransition(R.anim.login_in,0);
                        timer.cancel();
                    }
                    else
                    {
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putBoolean("boolean",true);
                        edit.commit();
                        Intent in=new Intent(Lunch1Activity.this,Lunch2Activity.class);
                        startActivity(in);
                        overridePendingTransition(R.anim.login_in,0);
                        timer.cancel();
                    }
                }
            }
        }
    };
    private SharedPreferences sp;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lunch1);
        //查找控件
        jump = (Button) findViewById(R.id.lunch_jump);
        sp = getSharedPreferences("lunch",MODE_PRIVATE);
        //timer静止3秒
        timer = new Timer();
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                i--;
                handler.obtainMessage(0,i).sendToTarget();
            }
        };
        timer.schedule(task,0,1000);
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Lunch1Activity.this,MainActivity.class);
                startActivity(in);
                overridePendingTransition(R.anim.login_in,0);
                timer.cancel();
            }
        });
        }
}
