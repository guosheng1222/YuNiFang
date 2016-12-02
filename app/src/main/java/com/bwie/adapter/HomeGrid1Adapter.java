package com.bwie.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.bean.BeanMain;
import com.bwie.utils.CommonUtils;
import com.bwie.yunifang.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by PC on 2016/12/2.
 */
public class HomeGrid1Adapter extends BaseAdapter {
    ArrayList<BeanMain.DataBean.Ad5Bean> list;
    Context context;

    public HomeGrid1Adapter(ArrayList<BeanMain.DataBean.Ad5Bean> list, Context context) {
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
        if (convertView == null) {
            convertView = CommonUtils.inflate(R.layout.home_grid1_item);
            holder=new ViewHolder();

            holder.image= (ImageView) convertView.findViewById(R.id.home_grid1_image);
            holder.title= (TextView) convertView.findViewById(R.id.home_grid1_text);

            convertView.setTag(holder);
        }
        else
        {
            holder= (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(list.get(position).getImage(),holder.image);
        holder.title.setText(list.get(position).getTitle());
        return convertView;
    }

    public class ViewHolder {
        public ImageView image;
        public TextView title;
    }
}
