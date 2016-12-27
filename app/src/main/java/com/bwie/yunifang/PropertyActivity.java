package com.bwie.yunifang;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.adapter.CommonAdapter;
import com.bwie.adapter.ViewHolder;
import com.bwie.base.BaseData;
import com.bwie.bean.BeanChild;
import com.bwie.utils.URLUtils;
import com.bwie.view.MyGridView;
import com.bwie.view.Myheader;
import com.bwie.view.ShowingPage;
import com.google.gson.Gson;
import com.liaoinstan.springview.widget.SpringView;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;

public class PropertyActivity extends AutoLayoutActivity {

    private String title;
    private String id;
    ArrayList<BeanChild.DataBean> list=new ArrayList<>();
    private MyGridView myGridView;
    private ImageView back;
    private SpringView spring;
    private TextView titleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_property);
        initView();
        titleView.setText(title);
        onload(id);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, R.anim.login_out);
            }
        });
        spring.setHeader(new Myheader());
        spring.setType(SpringView.Type.FOLLOW);
        spring.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        spring.onFinishFreshAndLoad();
                        onload(id);
                    }
                }, 1000);
            }
            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    }
                }, 1000);
            }
        });
        //设置条目点击监听
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in=new Intent(PropertyActivity.this, InfoActivity.class);
                in.putExtra("id",list.get(position).getId());
                startActivity(in);
                overridePendingTransition(R.anim.login_in,0);
            }
        });
    }

    private void initView() {
        title = getIntent().getStringExtra("title");
        id = getIntent().getStringExtra("id");
        spring = (SpringView) findViewById(R.id.property_spring);
        back = (ImageView) findViewById(R.id.property_back);
        myGridView = (MyGridView) findViewById(R.id.property_mygridview);
        titleView = (TextView) findViewById(R.id.property_title);
    }

    private void onload(String id) {
        new BaseData() {
            @Override
            public void setResultData(String data) {
                Gson gson=new Gson();
                BeanChild beanChild = gson.fromJson(data, BeanChild.class);
                list= (ArrayList<BeanChild.DataBean>) beanChild.getData();
                myGridView.setAdapter(new CommonAdapter<BeanChild.DataBean>(PropertyActivity.this,list,R.layout.category_grid_item,list.size()) {
                    @Override
                    public void convert(ViewHolder holder, BeanChild.DataBean item) {
                        holder.setImageByUrl(R.id.category_grid_image,item.getGoods_img());
                        holder.setImageByUrl(R.id.category_grid_image2,item.getWatermarkUrl());
                        holder.setText(R.id.category_grid_title,item.getGoods_name());
                        holder.setText(R.id.category_grid_info,item.getEfficacy());
                        holder.setText(R.id.category_grid_nowPrice,"¥"+item.getShop_price());
                        holder.setText(R.id.category_grid_afterPrice,"¥"+item.getMarket_price());
                        holder.setTextLine(R.id.category_grid_afterPrice);
                    }
                });
            }

            @Override
            protected void setResultError(ShowingPage.StateType stateLoadError) {

            }
        }.getData(URLUtils.childUrl,URLUtils.childArgs+id,0, BaseData.NOTIME);
    }
}
