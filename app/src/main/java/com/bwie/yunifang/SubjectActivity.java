package com.bwie.yunifang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.adapter.CommonAdapter;
import com.bwie.adapter.ViewHolder;
import com.bwie.bean.BeanMain;
import com.bwie.view.MyGridView;
import com.zhy.autolayout.AutoLayoutActivity;

public class SubjectActivity extends AutoLayoutActivity {

    private MyGridView myGridView;
    private TextView info;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_subject);
        final BeanMain.DataBean.SubjectsBean subject = (BeanMain.DataBean.SubjectsBean) getIntent().getExtras().getSerializable("subject");
        back = (ImageView) findViewById(R.id.subject_back);
        myGridView = (MyGridView) findViewById(R.id.subject_myGridView);
        info = (TextView) findViewById(R.id.subject_info);
        //设置适配器
        myGridView.setAdapter(new CommonAdapter<BeanMain.DataBean.SubjectsBean.GoodsListBean>(this, subject.getGoodsList(),R.layout.home_grid_item, subject.getGoodsList().size()) {
            @Override
            public void convert(ViewHolder holder, BeanMain.DataBean.SubjectsBean.GoodsListBean item) {
                holder.setImageByUrl(R.id.home_grid_image, item.getGoods_img());
                holder.setText(R.id.home_grid_title, item.getGoods_name());
                holder.setText(R.id.home_grid_info, item.getEfficacy());
                holder.setText(R.id.home_grid_nowPrice, "¥" + item.getShop_price());
                holder.setText(R.id.home_grid_afterPrice, "¥" + item.getMarket_price());
                holder.setTextLine(R.id.home_grid_afterPrice);
            }
        });
        info.setText("#"+subject.getTitle()+"#\r\n"+subject.getDetail());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, R.anim.login_out);
            }
        });
        myGridView.setFocusable(false);
        //设置条目点击事件
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in=new Intent(SubjectActivity.this, InfoActivity.class);
                in.putExtra("id",subject.getGoodsList().get(position).getId());
                startActivity(in);
                overridePendingTransition(R.anim.login_in,0);
            }
        });
    }
}
