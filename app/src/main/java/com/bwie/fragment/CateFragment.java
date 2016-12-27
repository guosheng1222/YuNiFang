package com.bwie.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.bwie.adapter.CommonAdapter;
import com.bwie.adapter.ViewHolder;
import com.bwie.base.BaseData;
import com.bwie.bean.BeanChild;
import com.bwie.utils.CommonUtils;
import com.bwie.utils.URLUtils;
import com.bwie.view.MyGridView;
import com.bwie.view.Myheader;
import com.bwie.view.ShowingPage;
import com.bwie.yunifang.InfoActivity;
import com.bwie.yunifang.R;
import com.google.gson.Gson;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;

/**
 * Created by PC on 2016/12/13.
 */
public class CateFragment extends Fragment {

    private MyGridView myGridView;
    ArrayList<BeanChild.DataBean> list=new ArrayList<>();
    private SpringView spring;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = CommonUtils.inflate(R.layout.category_fragment);
        spring = (SpringView) view.findViewById(R.id.cate_spring);
        myGridView = (MyGridView) view.findViewById(R.id.cate_fragment_grid);
        final String id = getArguments().getString("id");
        onload(id);
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in=new Intent(getActivity(), InfoActivity.class);
                in.putExtra("id",list.get(position).getId());
                getActivity().startActivity(in);
                getActivity().overridePendingTransition(R.anim.login_in,0);
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
        return view;
    }
    private void onload(String id) {
        new BaseData() {
            @Override
            public void setResultData(String data) {
                Gson gson=new Gson();
                BeanChild beanChild = gson.fromJson(data, BeanChild.class);
                list= (ArrayList<BeanChild.DataBean>) beanChild.getData();
                myGridView.setAdapter(new CommonAdapter<BeanChild.DataBean>(getActivity(),list,R.layout.category_grid_item,list.size()) {
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

    public static CateFragment getFragment(String id)
    {
        CateFragment f1=new CateFragment();
        Bundle bun=new Bundle();
        bun.putString("id",id);
        f1.setArguments(bun);
        return f1;
    }

}
