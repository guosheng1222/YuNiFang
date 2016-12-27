package com.bwie.yunifang;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.autolayout.AutoLayoutActivity;

public class WebActivity extends AutoLayoutActivity {

    private WebView web;
    private TextView title;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        //查找控件
        initView();
        //设置页面标题
        title.setText(getIntent().getStringExtra("title"));
        //加载页面
        web.loadUrl(getIntent().getStringExtra("url"));

        web.setWebViewClient(new WebViewClient());
        web.getSettings().setUseWideViewPort(true);//web就是自己定义的窗口对象。
        web.getSettings().setLoadWithOverviewMode(true);
        //返回键监听事件
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, R.anim.login_out);
            }
        });
    }

    private void initView() {
        title = (TextView) findViewById(R.id.web_title);
        back = (ImageView) findViewById(R.id.web_back);
        web = (WebView) findViewById(R.id.webView);
    }
}
