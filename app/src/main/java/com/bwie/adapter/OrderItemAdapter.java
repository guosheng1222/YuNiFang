package com.bwie.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.bean.BeanOrder;
import com.bwie.utils.CommonUtils;
import com.bwie.yunifang.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by PC on 2016/12/27.
 */

public class OrderItemAdapter extends BaseAdapter {
    ArrayList<BeanOrder> list;
    Context context;

    public OrderItemAdapter(ArrayList<BeanOrder> list, Context context) {
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
        if(convertView==null)
        {
            holder=new ViewHolder();
            convertView= CommonUtils.inflate(R.layout.order_list_item);
            holder.image= (ImageView) convertView.findViewById(R.id.order_list_image);
            holder.name= (TextView) convertView.findViewById(R.id.order_list_name);
            holder.price= (TextView) convertView.findViewById(R.id.order_list_price);
            holder.number= (TextView) convertView.findViewById(R.id.order_list_num);
            convertView.setTag(holder);
        }
        else
        {
            holder= (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(list.get(position).getImage(),holder.image);
        holder.name.setText(list.get(position).getName());
        holder.price.setText("¥ "+list.get(position).getPrice());
        holder.number.setText("数量:"+list.get(position).getNumber());
        return convertView;
    }
    class ViewHolder
    {
        ImageView image;
        TextView name;
        TextView price;
        TextView number;
    }
}
