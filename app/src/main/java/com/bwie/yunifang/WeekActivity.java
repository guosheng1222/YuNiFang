package com.bwie.yunifang;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bwie.bean.BeanMain;
import com.bwie.utils.CommonUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;

public class WeekActivity extends AutoLayoutActivity {

    private ListView lv;
    private ArrayList<BeanMain.DataBean.BestSellersBean> list;
    int[] hot=new int[]{R.mipmap.hot_rank_1,R.mipmap.hot_rank_2,R.mipmap.hot_rank_3};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_week);
        lv = (ListView) findViewById(R.id.week_list);
        ImageButton back = (ImageButton) findViewById(R.id.week_back);
        assert back != null;
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        list = (ArrayList<BeanMain.DataBean.BestSellersBean>) getIntent().getExtras().getSerializable("week");
        lv.setAdapter(new MyAdapter());
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in=new Intent(WeekActivity.this, InfoActivity.class);
                in.putExtra("id",list.get(0).getGoodsList().get(position).getId());
               startActivity(in);
               overridePendingTransition(R.anim.login_in,0);
            }
        });
    }

   class MyAdapter extends BaseAdapter {
        ImageView image;
        TextView title;
        TextView nowPrice;
        TextView afterPrice;
        ImageView image1;
        TextView text;
        @Override
        public int getCount() {
            return list.get(0).getGoodsList().size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = CommonUtils.inflate(R.layout.weekhot_item);
            image= (ImageView) convertView.findViewById(R.id.week_info_image);
            title= (TextView) convertView.findViewById(R.id.week_info_title);
            nowPrice= (TextView) convertView.findViewById(R.id.week_info_nowPrice);
            afterPrice= (TextView) convertView.findViewById(R.id.week_info_afterPrice);
            image1= (ImageView) convertView.findViewById(R.id.week_info_image1);
            text= (TextView) convertView.findViewById(R.id.week_info_text);

            ImageLoader.getInstance().displayImage(list.get(0).getGoodsList().get(position).getGoods_img(),image);
            title.setText(list.get(0).getGoodsList().get(position).getGoods_name());
            nowPrice.setText("¥"+list.get(0).getGoodsList().get(position).getShop_price());
            afterPrice.setText("¥"+list.get(0).getGoodsList().get(position).getMarket_price());
            afterPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG |Paint.ANTI_ALIAS_FLAG);


            int i=position+1;
            text.setText("NO."+i);
            if(i==1||i==2||i==3)
            {
                image1.setImageResource(hot[i-1]);
                text.setVisibility(View.GONE);
            }
            else
            {
                text.setVisibility(View.VISIBLE);
            }
            return convertView;
        }

    }
}
