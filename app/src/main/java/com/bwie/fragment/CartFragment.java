package com.bwie.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.adapter.CartAdapter;
import com.bwie.adapter.CommonAdapter;
import com.bwie.bean.BeanCart;
import com.bwie.database.SqliteDao;
import com.bwie.utils.CommonUtils;
import com.bwie.view.MyListView;
import com.bwie.yunifang.AddressActivity;
import com.bwie.yunifang.InfoActivity;
import com.bwie.yunifang.MainActivity;
import com.bwie.yunifang.R;

import java.util.ArrayList;

/**
 * Created by PC on 2016/11/28.
 */
public class CartFragment extends Fragment implements View.OnClickListener {

    private   MyListView myListView;
    private SqliteDao dao;
    ArrayList<BeanCart> list=new ArrayList<>();
    private  LinearLayout lin;
    private Button guang;
    private View viewitem;
    private CommonAdapter<BeanCart> commonAdapter;
    public static  CheckBox all_check;
    private CartAdapter adapter;
    public TextView bianji;
    public static  TextView sum_price;
    public static  Button state;
    private   RelativeLayout rel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= CommonUtils.inflate(R.layout.cartfragment_main);
        initView(view);
        return view;
    }
    //查找控件
    private void initView(View view) {
        viewitem = CommonUtils.inflate(R.layout.cart_list_item);
        myListView = (MyListView) view.findViewById(R.id.cart_myListView);
        rel = (RelativeLayout) view.findViewById(R.id.cart_rel);
        lin = (LinearLayout) view.findViewById(R.id.guangguang_lin);
        guang = (Button) view.findViewById(R.id.guangguang);
        bianji = (TextView) view.findViewById(R.id.bianji);
        sum_price = (TextView) view.findViewById(R.id.sum_price);
        state = (Button) view.findViewById(R.id.state);

        dao = new SqliteDao(getActivity());
        all_check = (CheckBox) view.findViewById(R.id.all_check);
        all_check.setOnClickListener(this);
        list=dao.query();
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter = new CartAdapter(list,getActivity());
        myListView.setAdapter(adapter);
        if(list.size()==0)
        {
            rel.setVisibility(View.GONE);
            lin.setVisibility(View.VISIBLE);
            myListView.setVisibility(View.GONE);
        }
        else
        {
            rel.setVisibility(View.VISIBLE);
            lin.setVisibility(View.GONE);
            myListView.setVisibility(View.VISIBLE);
        }
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in=new Intent(getActivity(), InfoActivity.class);
                in.putExtra("id",list.get(position).getId());
                getActivity().startActivity(in);
                getActivity().overridePendingTransition(R.anim.login_in,0);
            }
        });
        state.setOnClickListener(this);
        guang.setOnClickListener(this);
        bianji.setOnClickListener(this);
        list.clear();
        list=dao.query();
        adapter.notifyDataSetChanged();
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            //全选
            case R.id.all_check:
                if(all_check.isChecked())
                {
                    CartAdapter.tag=list.size();
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setFlag(true);
                    }
                    all_check.setText("取消全选");
                }
                else
                {
                    CartAdapter.tag=0;
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setFlag(false);
                    }
                    all_check.setText("全选");
                }
                adapter.notifyDataSetChanged();
                break;
            //逛一逛
            case R.id.guangguang:
                RadioButton rb = (RadioButton) MainActivity.rg.getChildAt(0);
                rb.setChecked(true);
                break;
            //编辑按钮
            case R.id.bianji:
                if(bianji.getText().toString().equals("编辑"))
                {
                    bianji.setText("完成");
                    CartAdapter.t=true;
                    state.setText("删除");
                    sum_price.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    bianji.setText("编辑");
                    CartAdapter.t=false;
                    state.setText("结算");
                    sum_price.setVisibility(View.VISIBLE);
                    list.clear();
                    list=dao.query();
                    adapter.notifyDataSetChanged();
                }
                break;
            //删除按钮
            case R.id.state:
                if (state.getText().toString().equals("删除")) {
                    for (int i =list.size()-1; i >=0; i--) {
                        if (list.get(i).isFlag()) {
                            dao.deleteCart(list.get(i).getName());
                            list.remove(i);
                            adapter.notifyDataSetChanged();
                        }
                    }
                    if(list.size()==0)
                    {
                        bianji.setText("编辑");
                        rel.setVisibility(View.GONE);
                        lin.setVisibility(View.VISIBLE);
                        myListView.setVisibility(View.GONE);
                    }
                    else
                    {
                        rel.setVisibility(View.VISIBLE);
                        lin.setVisibility(View.GONE);
                        myListView.setVisibility(View.VISIBLE);
                    }
                }
                if(state.getText().toString().equals("结算"))
                {
                    ArrayList<BeanCart> goods=new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).isFlag())
                        {
                            goods.add(list.get(i));
                        }
                    }
                    if(goods.size()==0)
                    {
                        Toast.makeText(getActivity(), "请选择一款商品", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent in = new Intent(getActivity(), AddressActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("goods", goods);
                        in.putExtras(bundle);
                        startActivity(in);
                        getActivity().overridePendingTransition(R.anim.login_in, 0);
                    }
                }
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        CartAdapter.t=false;
    }
}
