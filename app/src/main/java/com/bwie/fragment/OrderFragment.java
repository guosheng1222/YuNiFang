package com.bwie.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.adapter.MyOrderAdapter;
import com.bwie.database.SqliteDao;
import com.bwie.utils.CommonUtils;
import com.bwie.view.MyListView;
import com.bwie.yunifang.R;

import java.util.ArrayList;

/**
 * Created by PC on 2016/12/26.
 */

public class OrderFragment extends Fragment {

    private MyListView myList;
    private SqliteDao dao;
    private ArrayList<String> orderIdList=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= CommonUtils.inflate(R.layout.orderfragment);
        myList = (MyListView) view.findViewById(R.id.order_myList);
        dao = new SqliteDao(getActivity());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String title = getArguments().getString("title", null);
        if(title.equals("全部"))
        {
            orderIdList=dao.queryOrderId(null);
        }else
        {
            orderIdList=dao.queryOrderId(title);
        }
        myList.setAdapter(new MyOrderAdapter(orderIdList,getActivity()));
    }

    public static android.support.v4.app.Fragment getFragment(String title)
    {
        Fragment f1=new OrderFragment();
        Bundle bundle=new Bundle();
        bundle.putString("title",title);
        f1.setArguments(bundle);
        return f1;
    }

}
