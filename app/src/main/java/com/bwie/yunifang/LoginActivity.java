package com.bwie.yunifang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.utils.CommonUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.Map;

public class LoginActivity extends AutoLayoutActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private ImageView iv_back;
    private TextView otherLogin;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    backgroundAlpha((float) msg.obj);
                    break;
            }
        }
    };
    private RadioGroup rg_login;
    private EditText password;
    private LinearLayout phone_lin;
    private TextView remove_password;
    private ImageButton login_qq;
    private ImageButton login_weixin;
    private TextView tv_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        initView();
        rg_login.setOnCheckedChangeListener(this);
    }
    //查找空间
    private void initView() {
        iv_back = (ImageView) findViewById(R.id.login_back);
        otherLogin = (TextView) findViewById(R.id.otherLogin);
        otherLogin.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        rg_login = (RadioGroup) findViewById(R.id.rg_login);
        password = (EditText) findViewById(R.id.phone_login_password);
        remove_password = (TextView) findViewById(R.id.remove_password);
        phone_lin = (LinearLayout) findViewById(R.id.phone_login_lin);
        tv_register = (TextView) findViewById(R.id.tv_register);
        iv_back.setOnClickListener(this);
        otherLogin.setOnClickListener(this);
        tv_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_back:
                //返回按钮
                RadioButton rb = (RadioButton) MainActivity.rg.getChildAt(3);
                rb.setChecked(true);
                finish();
                overridePendingTransition(0, R.anim.login_out);
                break;
            //第三方登录
            case R.id.otherLogin:
                View view = CommonUtils.inflate(R.layout.login_pop);
                PopupWindow pop = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                pop.setOutsideTouchable(true);
                pop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                pop.setAnimationStyle(R.style.Popupwindow);
                pop.showAtLocation(otherLogin, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, -10);
                login_qq = (ImageButton) view.findViewById(R.id.login_qq);
                login_weixin = (ImageButton) view.findViewById(R.id.login_weixin);
                login_qq.setOnClickListener(this);
                login_weixin.setOnClickListener(this);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        float alpha = 0.9f;
                        while (alpha > 0.5f) {
                            try {
                                //4是根据弹出动画时间和减少的透明度计算
                                Thread.sleep(4);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Message msg = mHandler.obtainMessage();
                            msg.what = 1;
                            //每次减少0.01，精度越高，变暗的效果越流畅
                            alpha -= 0.01f;
                            msg.obj = alpha;
                            mHandler.sendMessage(msg);
                        }
                    }

                }).start();
                pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                //此处while的条件alpha不能<= 否则会出现黑屏
                                float alpha = 0.5f;
                                while (alpha < 0.99f) {
                                    try {
                                        Thread.sleep(4);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    Log.d("HeadPortrait", "alpha:" + alpha);
                                    Message msg = mHandler.obtainMessage();
                                    msg.what = 1;
                                    alpha += 0.01f;
                                    msg.obj = alpha;
                                    mHandler.sendMessage(msg);
                                }
                            }

                        }).start();
                    }
                });
                break;
            case R.id.login_qq:
                //登录QQ
                UMShareAPI mShareAPI = UMShareAPI.get(LoginActivity.this);
                mShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, umAuthListener);
                break;
        }
    }
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT).show();
            //登陆成功后获取信息
            SharedPreferences sp=getSharedPreferences("login",MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            String screen_name = data.get("screen_name");
            String profile_image_url = data.get("profile_image_url");
            edit.putString("user_name",screen_name);
            edit.putString("user_icon",profile_image_url);
            edit.commit();
            finish();
            RadioButton rb = (RadioButton) MainActivity.rg.getChildAt(3);
            rb.setChecked(true);
        }
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText( getApplicationContext(), "登录失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText( getApplicationContext(), "取消登录", Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
    //屏幕透明度
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch(checkedId)
        {
            case R.id.rg_login:
                for (int i = 0; i < rg_login.getChildCount(); i++) {
                    RadioButton rb = (RadioButton) rg_login.getChildAt(i);
                    if (rb.getId() == checkedId) {
                        rb.setChecked(true);
                        rb.setTextColor(Color.parseColor("#FC6B87"));
                        rb.setBackgroundResource(R.drawable.shape_login);
                    } else {
                        rb.setChecked(false);
                        rb.setTextColor(Color.BLACK);
                        rb.setBackgroundColor(Color.TRANSPARENT);
                    }
                    if (checkedId == R.id.rb_ynf_login) {

                        password.setVisibility(View.VISIBLE);
                        remove_password.setText("忘记密码");
                        phone_lin.setVisibility(View.GONE);
                    } else if (checkedId == R.id.rb_phone_login) {
                        password.setVisibility(View.GONE);
                        remove_password.setText("");
                        phone_lin.setVisibility(View.VISIBLE);
                    }
                }
                break;
        }
    }
}

