package com.bwie.yunifang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.utils.DataClearManager;
import com.zhy.autolayout.AutoLayoutActivity;

import java.io.File;

public class SettingActivity extends AutoLayoutActivity implements View.OnClickListener {

    private ImageView back;
    private TextView setup_cache;
    private RelativeLayout clear_cache;
    private RelativeLayout qrcode;
    private File file;
    private Button back_login;
    private SharedPreferences.Editor edit;
    private SharedPreferences sp;
    private RelativeLayout setup_call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting);
        initView();
        sp = getSharedPreferences("login",MODE_PRIVATE);
        edit = sp.edit();
        String user_name = sp.getString("user_name", null);
        if(user_name==null)
        {
            back_login.setVisibility(View.GONE);
        }
        else
        {
            back_login.setVisibility(View.VISIBLE);
        }
        setup_call.setOnClickListener(this);
        back_login.setOnClickListener(this);
        back.setOnClickListener(this);
        clear_cache.setOnClickListener(this);
        qrcode.setOnClickListener(this);
        file = this.getCacheDir();
        try {
            setup_cache.setText(DataClearManager.getCacheSize(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        setup_call = (RelativeLayout) findViewById(R.id.setup_call);
        back = (ImageView) findViewById(R.id.setting_back);
        back_login = (Button) findViewById(R.id.back_login);
        setup_cache = (TextView) findViewById(R.id.setup_cache);
        clear_cache = (RelativeLayout) findViewById(R.id.clear_cache);
        qrcode = (RelativeLayout) findViewById(R.id.qrcode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            //返回
            case R.id.setting_back:
                finish();
                overridePendingTransition(0, R.anim.login_out);
                break;
            //清除缓存
            case R.id.clear_cache:
                try {
                    Toast.makeText(this, "清除" + DataClearManager.getCacheSize(file), Toast.LENGTH_SHORT).show();
                    DataClearManager.cleanInternalCache(SettingActivity.this);
                    setup_cache.setText(DataClearManager.getCacheSize(file));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            //关于我们——二维码
            case R.id.qrcode:
                Intent in_setting=new Intent(SettingActivity.this, QrcodeActivity.class);
                startActivity(in_setting);
                overridePendingTransition(R.anim.login_in,0);
                break;
            case R.id.back_login:
                edit.clear();
                edit.commit();
                Toast.makeText(this, "已退出登录", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.setup_call:
              Intent in=new Intent();
                //ACTION_CALL:打电话的应用的action
                in.setAction(Intent.ACTION_CALL);
                //设置电话号码
                in.setData(Uri.parse("tel:4006880900"));
                //跳转
                startActivity(in);
                break;
        }
    }
}
