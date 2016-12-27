package com.bwie.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bwie.bean.BeanOrder;
import com.bwie.database.SqliteDao;
import com.bwie.utils.CommonUtils;
import com.bwie.view.MyListView;
import com.bwie.yunifang.R;

import java.util.ArrayList;

import static com.bwie.yunifang.R.id.order_condition;

/**
 * Created by PC on 2016/12/27.
 */

public class MyOrderAdapter extends BaseAdapter {
    ArrayList<String> list;
    Context context;
    private SqliteDao dao;

    public MyOrderAdapter(ArrayList<String> list, Context context) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        dao = new SqliteDao(context);
        int number=0;
        double price=0.0;
        ArrayList<BeanOrder> OrdersList = dao.queryOrder(list.get(position));
        if(convertView==null)
        {
            holder=new ViewHolder();
            convertView= CommonUtils.inflate(R.layout.orderfragment_recycler_item);
            holder.condition= (TextView) convertView.findViewById(order_condition);
            holder.number_momery= (TextView) convertView.findViewById(R.id.sumNumber_sumMoney_tv);
            holder.listView= (MyListView) convertView.findViewById(R.id.OrderFragment_recyclerView_lv);
            convertView.setTag(holder);
        }
        else
        {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.condition.setText(OrdersList.get(0).getCondition());
        for (int i = 0; i < OrdersList.size(); i++) {
            number+= OrdersList.get(i).getNumber();
            price+=Double.parseDouble(OrdersList.get(i).getPrice());
        }
        String format = String.format("%.2f", price);
        holder.number_momery.setText("共计"+number+"件商品 :"+format);
        holder.listView.setAdapter(new OrderItemAdapter(OrdersList,context));
        return convertView;
    }
    class ViewHolder
    {
        TextView condition;
        TextView number_momery;
        Button cancle;
        Button pay;
        MyListView listView;
    }
}

