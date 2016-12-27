package com.bwie.yunifang;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import com.bwie.adapter.CommonAdapter;
import com.bwie.adapter.ViewHolder;
import com.bwie.base.BaseData;
import com.bwie.bean.BeanAll;
import com.bwie.utils.URLUtils;
import com.bwie.view.MyGridView;
import com.bwie.view.ShowingPage;
import com.google.gson.Gson;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.Collections;
import java.util.List;

public class AllgoodsActivity extends AutoLayoutActivity implements View.OnClickListener {

    private ImageView back;
    private RadioGroup all_rg;
    private BeanAll beanAll;
    private MyGridView myGridView;
    private List<BeanAll.DataBean> list;
    private CommonAdapter<BeanAll.DataBean> commonAdapter;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_allgoods);
        scrollView = (ScrollView) findViewById(R.id.all_ScrollView);
        back = (ImageView) findViewById(R.id.all_back);
        all_rg = (RadioGroup) findViewById(R.id.all_rg);
        myGridView = (MyGridView) findViewById(R.id.all_MyGridView);
        back.setOnClickListener(this);
        onload();
        myGridView.setFocusable(false);
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in=new Intent(AllgoodsActivity.this, InfoActivity.class);
                in.putExtra("id",beanAll.getData().get(position).getId());
                startActivity(in);
               overridePendingTransition(R.anim.login_in,0);
            }
        });
        all_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < all_rg.getChildCount(); i++) {
                    RadioButton rb = (RadioButton) all_rg.getChildAt(i);
                    if(rb.getId()==checkedId)
                    {
                        rb.setTextColor(Color.parseColor("#FC6B87"));
                        rb.setBackgroundResource(R.drawable.shape_all_check);
                        if(i==0)
                        {
                            onload();
                            commonAdapter.notifyDataSetChanged();
                        }
                        else if(i==1)
                        {
                            Collections.sort(list);
                            commonAdapter.notifyDataSetChanged();
                        }
                        else if(i==2)
                        {
                            Collections.sort(list);
                            Collections.reverse(list);
                            commonAdapter.notifyDataSetChanged();
                        }
                    }
                    else
                    {
                        rb.setTextColor(Color.parseColor("#3C3C3C"));
                     rb.setBackgroundResource(R.drawable.shape_all_normal);
                    }
                }
            }
        });

    }

    private void onload() {
        new BaseData() {
            @Override
            public void setResultData(String data) {
                Gson gson=new Gson();
                beanAll = gson.fromJson(data, BeanAll.class);
                initList();
            }
            @Override
            protected void setResultError(ShowingPage.StateType stateLoadError) {

            }
        }.getData(URLUtils.allUrl,URLUtils.allArgs,0, BaseData.NORMALTIME);
    }
    //设置适配器
    private void initList() {
        list = beanAll.getData();
        commonAdapter = new CommonAdapter<BeanAll.DataBean>(AllgoodsActivity.this, list, R.layout.category_grid_item, list.size()) {
            @Override
            public void convert(ViewHolder holder, BeanAll.DataBean item) {
                holder.setImageByUrl(R.id.category_grid_image, item.getGoods_img());
                holder.setImageByUrl(R.id.category_grid_image2, item.getWatermarkUrl());
                holder.setText(R.id.category_grid_title, item.getGoods_name());
                holder.setText(R.id.category_grid_info, item.getEfficacy());
                holder.setText(R.id.category_grid_nowPrice, "¥" + item.getShop_price());
                holder.setText(R.id.category_grid_afterPrice, "¥" + item.getMarket_price());
                holder.setTextLine(R.id.category_grid_afterPrice);
            }
        };
        myGridView.setAdapter(commonAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.all_back:
                finish();
                overridePendingTransition(0, R.anim.login_out);
                break;
        }
    }
}
