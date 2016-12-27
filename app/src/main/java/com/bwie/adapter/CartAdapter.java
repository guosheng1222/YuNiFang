package com.bwie.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bwie.bean.BeanCart;
import com.bwie.database.SqliteDao;
import com.bwie.fragment.CartFragment;
import com.bwie.utils.CommonUtils;
import com.bwie.yunifang.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by PC on 2016/12/15.
 */
public class CartAdapter extends BaseAdapter {
    public static int tag;
    ArrayList<BeanCart> list;
    Context context;
    int buy = 0;
    public static boolean t=false;
    private SqliteDao dao;


    public CartAdapter(ArrayList<BeanCart> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        dao = new SqliteDao(context);
        convertView = CommonUtils.inflate(R.layout.cart_list_item);
        ImageView image = (ImageView) convertView.findViewById(R.id.cart_list_image);
        TextView name = (TextView) convertView.findViewById(R.id.cart_list_name);
        TextView price = (TextView) convertView.findViewById(R.id.cart_list_price);
        final TextView num = (TextView) convertView.findViewById(R.id.cart_list_num);
        ImageView juan = (ImageView) convertView.findViewById(R.id.cart_list_juan);
        ImageView di = (ImageView) convertView.findViewById(R.id.cart_list_di);
        final CheckBox check = (CheckBox) convertView.findViewById(R.id.cart_list_check);
        final RelativeLayout lin = (RelativeLayout) convertView.findViewById(R.id.cart_list_lin);
        //加号
        final ImageView add_image = (ImageView) convertView.findViewById(R.id.cart_add_image);
        //减号
        final ImageView reduce_image = (ImageView) convertView.findViewById(R.id.cart_reduce_image);
        //购买数量
        final TextView buy_num = (TextView) convertView.findViewById(R.id.cart_buy_num);
        buy = list.get(position).getNumber();
        if (buy == list.get(position).getLimit_num()) {
            add_image.setImageResource(R.mipmap.add_unable);
        }
        if (buy > 1) {
            reduce_image.setImageResource(R.mipmap.reduce_able);
        }
        //减号监听
        reduce_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buy = Integer.parseInt(buy_num.getText().toString());
                sum();
                if (buy > 1) {
                    buy--;
                    buy_num.setText("" + buy);
                    dao.updateNum(buy, list.get(position).getName());
                    add_image.setImageResource(R.mipmap.add_able);
                    if (buy == 1) {
                        reduce_image.setImageResource(R.mipmap.reduce_unable);
                    } else if (buy == list.get(position).getLimit_num()) {
                        add_image.setImageResource(R.mipmap.add_unable);
                    }
                }
            }
        });
        //加号监听
        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buy = Integer.parseInt(buy_num.getText().toString());
                sum();
                if (buy < list.get(position).getLimit_num()) {
                    buy++;
                    buy_num.setText("" + buy);
                    dao.updateNum(buy, list.get(position).getName());
                    reduce_image.setImageResource(R.mipmap.reduce_able);
                    if (buy == list.get(position).getLimit_num()) {
                        add_image.setImageResource(R.mipmap.add_unable);
                        reduce_image.setImageResource(R.mipmap.reduce_able);
                    }
                }
            }
        });
        ImageLoader.getInstance().displayImage(list.get(position).getImage(), image);
        name.setText(list.get(position).getName());
        price.setText("¥ " + list.get(position).getPrice());
        num.setText("数量: " + list.get(position).getNumber() + "");
        if (list.get(position).getJuan().equals("false")) {
            juan.setVisibility(View.GONE);
        }
        if (list.get(position).getDi().equals("0")) {
            di.setVisibility(View.GONE);
        }
        if (t) {
            num.setVisibility(View.GONE);
            lin.setVisibility(View.VISIBLE);
            buy_num.setText(list.get(position).getNumber() + "");
        } else {
            num.setVisibility(View.VISIBLE);
            lin.setVisibility(View.GONE);
        }
        check.setChecked(list.get(position).isFlag());
        if(tag==list.size())
        {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setFlag(true);
            }
            CartFragment.all_check.setText("   取消全选");
            CartFragment.all_check.setChecked(true);
            sum();
        }
        else if(tag==0)
        {
            CartFragment.all_check.setText("   全选");
            check.setChecked(false);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setFlag(false);
            }
            sum();
        }
        //checkbox点击事件
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check.isChecked()) {
                    tag++;
                    check.setChecked(true);
                    list.get(position).setFlag(true);
                    sum();
                } else {
                    tag--;
                    list.get(position).setFlag(false);
                    CartFragment.all_check.setText("全选");
                    CartFragment.all_check.setChecked(false);
                    sum();
                }
                if (tag == list.size()) {
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setFlag(true);
                    }
                    CartFragment.all_check.setText("   取消全选");
                    CartFragment.all_check.setChecked(true);
                    sum();
                }else if(tag==0)
                {
                    CartFragment.all_check.setText("   全选");
                    check.setChecked(false);
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setFlag(false);
                    }
                    sum();
                }
            }
        });
        return convertView;
    }

    private void sum() {
        double SumPrice = 0.0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isFlag()) {
                SumPrice += Integer.parseInt(list.get(i).getNumber()+"") * Double.parseDouble(list.get(i).getPrice());
            }
        }
        String s = String.format("%.2f", SumPrice);
        CartFragment.sum_price.setText("总计 ¥:" + s);
    }


    public class ViewHolder {
        public ImageView reduceimage;
        public TextView buynum;
        public ImageView addimage;
    }
}
