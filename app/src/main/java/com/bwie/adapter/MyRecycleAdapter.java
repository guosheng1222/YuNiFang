package com.bwie.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.bean.BeanMain;
import com.bwie.utils.CommonUtils;
import com.bwie.utils.ImageLoaderUtils;
import com.bwie.yunifang.MainActivity;
import com.bwie.yunifang.R;
import com.bwie.yunifang.SubjectActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by PC on 2016/12/8.
 */
public class MyRecycleAdapter extends RecyclerView.Adapter<MyRecycleAdapter.ViewHolder> {
    Context context;
    ArrayList<BeanMain.DataBean.SubjectsBean.GoodsListBean> list;
    private BeanMain.DataBean.SubjectsBean subList;
    MainActivity activity;
    public MyRecycleAdapter(Context context, ArrayList<BeanMain.DataBean.SubjectsBean.GoodsListBean> list, BeanMain.DataBean.SubjectsBean subList,MainActivity activity) {
        this.context = context;
        this.list = list;
        this.subList = subList;
        this.activity=activity;
    }

    /**
     * 添加条目监听回调
     */
    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        public ViewHolder(View itemView) {
            super(itemView);
        }
        public ImageView image;
        public TextView title;
        public TextView nowPrice;
        public TextView afterPrice;
        public ImageView more;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= CommonUtils.inflate(R.layout.week_item);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.image= (ImageView) view.findViewById(R.id.week_item_image);
        viewHolder.title= (TextView) view.findViewById(R.id.week_item_title);
        viewHolder.nowPrice= (TextView) view.findViewById(R.id.week_item_nowPrice);
        viewHolder.afterPrice= (TextView) view.findViewById(R.id.week_item_afterPrice);
        viewHolder.more= (ImageView) view.findViewById(R.id.week_item_more);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        DisplayImageOptions options = ImageLoaderUtils.initOptions();
        ImageLoader.getInstance().displayImage(list.get(position).getGoods_img(),holder.image,options);
        holder.title.setText(list.get(position).getGoods_name());
        holder.nowPrice.setText("¥"+list.get(position).getShop_price());
        holder.afterPrice.setText("¥"+list.get(position).getMarket_price());
        holder.afterPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG |Paint.ANTI_ALIAS_FLAG);
        if(position==6)
        {
            holder.more.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.more.setVisibility(View.GONE);
        }
        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(context, SubjectActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("subject",subList);
                in.putExtras(bundle);
                context.startActivity(in);
                activity.overridePendingTransition(R.anim.login_in,0);
            }
        });
        if (mOnItemClickLitener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mOnItemClickLitener.onItemClick(holder.itemView,position);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return 7;
    }


}
