package com.bwie.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bwie.bean.BeanMain;
import com.bwie.utils.CommonUtils;
import com.bwie.yunifang.InfoActivity;
import com.bwie.yunifang.MainActivity;
import com.bwie.yunifang.R;
import com.bwie.yunifang.SubjectActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by PC on 2016/12/4.
 */
public class MylistViewAdapter extends BaseAdapter {
    Context context;
    BeanMain.DataBean list;
    MainActivity activity;
    public MylistViewAdapter(Context context, BeanMain.DataBean list, MainActivity activity) {
        this.context = context;
        this.list = list;
        this.activity=activity;
    }

    @Override
    public int getCount() {
        return 7;
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
        ViewHolder holder=null;
        if(convertView==null)
        {
            holder=new ViewHolder();
            convertView= CommonUtils.inflate(R.layout.home_mylist_item);
            holder.image= (ImageView) convertView.findViewById(R.id.home_mylist_image);
            holder.recyclerView= (RecyclerView) convertView.findViewById(R.id.myRecyclerView);
            //holder.scrollview= (MyHorizontalScrollView) convertView.findViewById(R.id.home_mylist_scrollview);
            convertView.setTag(holder);
        }
        else
        {
            holder= (ViewHolder) convertView.getTag();
        }
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                // 设置图片在下载期间显示的图片
                .showImageOnLoading(R.mipmap.white)
                // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.white)
                // 设置下载的图片是否缓存在内存中
                .cacheInMemory(true)
                // 设置下载的图片是否缓存在SD卡中
                .cacheOnDisc(true)
                .build();
        holder.image.setScaleType(ImageView.ScaleType.FIT_XY);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(context, SubjectActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("subject",list.getSubjects().get(position));
                in.putExtras(bundle);
                context.startActivity(in);
                activity.overridePendingTransition(R.anim.login_in,0);
            }
        });
       ImageLoader.getInstance().displayImage(list.getSubjects().get(position).getImage(),holder.image,options);
       LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
       linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
       holder.recyclerView.setLayoutManager(linearLayoutManager);
        //设置适配器
        final MyRecycleAdapter mAdapter = new MyRecycleAdapter(context, (ArrayList<BeanMain.DataBean.SubjectsBean.GoodsListBean>) list.getSubjects().get(position).getGoodsList(),list.getSubjects().get(position),activity);
        holder.recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new MyRecycleAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int p) {
                Intent in=new Intent(context, InfoActivity.class);
                in.putExtra("id",list.getSubjects().get(position).getGoodsList().get(p).getId());
                context.startActivity(in);
               activity.overridePendingTransition(R.anim.login_in,0);

            }
        });
        return convertView;
    }
    public class ViewHolder {
        public  ImageView image;
        //public MyHorizontalScrollView scrollview;
        public RecyclerView recyclerView;
    }
}
