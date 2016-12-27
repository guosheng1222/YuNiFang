package com.bwie.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.adapter.CommonAdapter;
import com.bwie.adapter.ViewHolder;
import com.bwie.base.BaseData;
import com.bwie.base.BaseFragment;
import com.bwie.bean.BeanCate;
import com.bwie.bean.BeanCategory;
import com.bwie.utils.CommonUtils;
import com.bwie.utils.URLUtils;
import com.bwie.view.MyGridView;
import com.bwie.view.Myheader;
import com.bwie.view.ShowingPage;
import com.bwie.yunifang.AllgoodsActivity;
import com.bwie.yunifang.CategoryActivity;
import com.bwie.yunifang.InfoActivity;
import com.bwie.yunifang.PropertyActivity;
import com.bwie.yunifang.R;
import com.google.gson.Gson;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;

/**
 * Created by PC on 2016/11/28.
 */
public class CategoryFragment extends BaseFragment implements View.OnClickListener {
    private MyGridView myGridView;
    private BeanCategory beanCategory;
    private SpringView mySpringView;
    private TextView lookAll;
    private ImageView classify_emollient_water;
    private ImageView classify_facial_cleanser;
    private ImageView classify_facial_mask;
    private ImageView classify_other;
    private ImageView classify_body_lotion;
    private ImageView classify_kit;
    ArrayList<BeanCate.DataBean.CategoryBean.ChildrenBean> children = new ArrayList<>();
    public ArrayList<BeanCate.DataBean.CategoryBean> cateList = new ArrayList<>();
    private ImageButton classify_soothing;
    private ImageButton classify_hydrating;
    private ImageButton classify_firming;
    private ImageButton classify_control_oil;
    private ImageButton classify_whitening;
    ArrayList<BeanCate.DataBean.CategoryBean.ChildrenBean> gongxiao = new ArrayList<>();
    private Button fuzhi0;
    private Button fuzhi1;
    private Button fuzhi2;
    private Button fuzhi3;
    private Button fuzhi4;
    private Button fuzhi5;
    ArrayList<BeanCate.DataBean.CategoryBean.ChildrenBean> fuzhi = new ArrayList<>();
    @Override
    protected View createSuccessView() {
        View view = initView();
        initGridView();
        onclick();
        lookAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), AllgoodsActivity.class);
                startActivity(in);
                getActivity().overridePendingTransition(R.anim.login_in, 0);
            }
        });
        mySpringView.setHeader(new Myheader());
        mySpringView.setType(SpringView.Type.FOLLOW);
        mySpringView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mySpringView.onFinishFreshAndLoad();
                        onload();
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

    private void onclick() {
        //按属性 点击事件
        classify_body_lotion.setOnClickListener(this);
        classify_emollient_water.setOnClickListener(this);
        classify_facial_cleanser.setOnClickListener(this);
        classify_facial_mask.setOnClickListener(this);
        classify_kit.setOnClickListener(this);
        classify_other.setOnClickListener(this);

        //按功效  点击事件
        classify_soothing.setOnClickListener(this);
        classify_hydrating.setOnClickListener(this);
        classify_firming.setOnClickListener(this);
        classify_control_oil.setOnClickListener(this);
        classify_whitening.setOnClickListener(this);

        //按肤质  点击事件
        fuzhi0.setOnClickListener(this);
        fuzhi1.setOnClickListener(this);
        fuzhi2.setOnClickListener(this);
        fuzhi3.setOnClickListener(this);
        fuzhi4.setOnClickListener(this);
        fuzhi5.setOnClickListener(this);
    }

    private void initGridView() {
        //为GridView设置适配器
        myGridView.setAdapter(new CommonAdapter<BeanCategory.DataBean.GoodsBriefBean>(getActivity(), beanCategory.getData().getGoodsBrief(), R.layout.category_grid_item, 10) {
            @Override
            public void convert(ViewHolder holder, BeanCategory.DataBean.GoodsBriefBean item) {
                holder.setImageByUrl(R.id.category_grid_image, item.getGoods_img());
                holder.setImageByUrl(R.id.category_grid_image2, item.getWatermarkUrl());
                holder.setText(R.id.category_grid_title, item.getGoods_name());
                holder.setText(R.id.category_grid_info, item.getEfficacy());
                holder.setText(R.id.category_grid_nowPrice, "¥" + item.getShop_price());
                holder.setText(R.id.category_grid_afterPrice, "¥" + item.getMarket_price());
                holder.setTextLine(R.id.category_grid_afterPrice);
            }
        });
        //设置点击监听事件
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(getActivity(), InfoActivity.class);
                in.putExtra("id", beanCategory.getData().getGoodsBrief().get(position).getId());
                startActivity(in);
                getActivity().overridePendingTransition(R.anim.login_in, 0);
            }
        });
    }

    private View initView() {
        //查找控件
        View view = CommonUtils.inflate(R.layout.categoryfragment_main);
        mySpringView = (SpringView) view.findViewById(R.id.category_SpringView);
        myGridView = (MyGridView) view.findViewById(R.id.category_MyGridView);
        lookAll = (TextView) view.findViewById(R.id.lookAll);
        //属性
        classify_emollient_water = (ImageView) view.findViewById(R.id.classify_emollient_water);
        classify_facial_cleanser = (ImageView) view.findViewById(R.id.classify_facial_cleanser);
        classify_facial_mask = (ImageView) view.findViewById(R.id.classify_facial_mask);
        classify_other = (ImageView) view.findViewById(R.id.classify_other);
        classify_body_lotion = (ImageView) view.findViewById(R.id.classify_body_lotion);
        classify_kit = (ImageView) view.findViewById(R.id.classify_kit);
        //功效
        classify_control_oil = (ImageButton) view.findViewById(R.id.classify_control_oil);
        classify_firming = (ImageButton) view.findViewById(R.id.classify_firming);
        classify_hydrating = (ImageButton) view.findViewById(R.id.classify_hydrating);
        classify_soothing = (ImageButton) view.findViewById(R.id.classify_soothing);
        classify_whitening = (ImageButton) view.findViewById(R.id.classify_whitening);
        //肤质
        fuzhi0 = (Button) view.findViewById(R.id.fuzhi0);
        fuzhi1 = (Button) view.findViewById(R.id.fuzhi1);
        fuzhi2 = (Button) view.findViewById(R.id.fuzhi2);
        fuzhi3 = (Button) view.findViewById(R.id.fuzhi3);
        fuzhi4 = (Button) view.findViewById(R.id.fuzhi4);
        fuzhi5 = (Button) view.findViewById(R.id.fuzhi5);
        return view;
    }
    @Override
    protected void onload() {
        new BaseData() {
            @Override
            public void setResultData(String data) {
                Gson gson = new Gson();
                beanCategory = gson.fromJson(data, BeanCategory.class);
                CategoryFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
            }

            @Override
            protected void setResultError(ShowingPage.StateType stateLoadError) {
                CategoryFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_ERROR);
            }
        }.getData(URLUtils.cateGoryUrl, URLUtils.cateGoryArgs, 0, BaseData.NORMALTIME);
        new BaseData() {
            @Override
            public void setResultData(String data) {
                Gson gson = new Gson();
                BeanCate beanCate = gson.fromJson(data, BeanCate.class);
                cateList.addAll(beanCate.getData().getCategory());
                for (int i = 0; i < cateList.get(0).getChildren().size(); i++) {
                    gongxiao.add(cateList.get(0).getChildren().get(i));
                }
                for (int i = 0; i < cateList.get(2).getChildren().size(); i++) {
                    fuzhi.add(cateList.get(2).getChildren().get(i));
                }
            }

            @Override
            protected void setResultError(ShowingPage.StateType stateLoadError) {

            }
        }.getData(URLUtils.cateUrl, URLUtils.cateArgs, 0, BaseData.NORMALTIME);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.classify_emollient_water:
                Intent water_in=new Intent(getActivity(), PropertyActivity.class);
                water_in.putExtra("title","润肤水");
                water_in.putExtra("id","39");
                startActivity(water_in);
                getActivity().overridePendingTransition(R.anim.login_in, 0);
                break;
            case R.id.classify_body_lotion:
                Intent body_in=new Intent(getActivity(), PropertyActivity.class);
                body_in.putExtra("title","润肤乳");
                body_in.putExtra("id","40");
                startActivity(body_in);
                getActivity().overridePendingTransition(R.anim.login_in, 0);
                break;
            case R.id.classify_facial_cleanser:
                Intent clean_in=new Intent(getActivity(), PropertyActivity.class);
                clean_in.putExtra("title","洁面乳");
                clean_in.putExtra("id","24");
                startActivity(clean_in);
                getActivity().overridePendingTransition(R.anim.login_in, 0);
                break;
            case R.id.classify_kit:
                Intent kit_in=new Intent(getActivity(), PropertyActivity.class);
                kit_in.putExtra("title","实惠套装");
                kit_in.putExtra("id","33");
                startActivity(kit_in);
                getActivity().overridePendingTransition(R.anim.login_in, 0);
                break;
            case R.id.classify_other:
                Intent other_in=new Intent(getActivity(), PropertyActivity.class);
                other_in.putExtra("title","其他");
                other_in.putExtra("id","35");
                startActivity(other_in);
                getActivity().overridePendingTransition(R.anim.login_in, 0);
                break;
            case R.id.classify_facial_mask:
                Intent in = new Intent(getActivity(), CategoryActivity.class);
                Bundle bundle = new Bundle();
                children.add(cateList.get(1).getChildren().get(6));
                children.add(cateList.get(1).getChildren().get(7));
                children.add(cateList.get(1).getChildren().get(8));
                bundle.putSerializable("children", children);
                in.putExtras(bundle);
                startActivity(in);
                getActivity().overridePendingTransition(R.anim.login_in, 0);
                break;
            //功效
            case R.id.classify_soothing:
                Intent in1 = new Intent(getActivity(), CategoryActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("children",gongxiao);
                in1.putExtras(bundle1);
                in1.putExtra("page",1);
                startActivity(in1);
                getActivity().overridePendingTransition(R.anim.login_in, 0);
                break;
            case R.id.classify_hydrating:
                Intent in2 = new Intent(getActivity(), CategoryActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("children",gongxiao);
                in2.putExtras(bundle2);
                in2.putExtra("page",0);
                startActivity(in2);
                getActivity().overridePendingTransition(R.anim.login_in, 0);
                break;
            case R.id.classify_firming:
                Intent in5 = new Intent(getActivity(), CategoryActivity.class);
                Bundle bundle5 = new Bundle();
                bundle5.putSerializable("children",gongxiao);
                in5.putExtras(bundle5);
                in5.putExtra("page",4);
                startActivity(in5);
                getActivity().overridePendingTransition(R.anim.login_in, 0);
                break;
            case R.id.classify_control_oil:
                Intent in3 = new Intent(getActivity(), CategoryActivity.class);
                Bundle bundle3 = new Bundle();
                bundle3.putSerializable("children",gongxiao);
                in3.putExtras(bundle3);
                in3.putExtra("page",2);
                startActivity(in3);
                getActivity().overridePendingTransition(R.anim.login_in, 0);
                break;
            case R.id.classify_whitening:
                Intent in4 = new Intent(getActivity(), CategoryActivity.class);
                Bundle bundle4 = new Bundle();
                bundle4.putSerializable("children",gongxiao);
                in4.putExtras(bundle4);
                in4.putExtra("page",3);
                startActivity(in4);
                getActivity().overridePendingTransition(R.anim.login_in, 0);
                break;
            case R.id.fuzhi0:
                Intent f0 = new Intent(getActivity(), CategoryActivity.class);
                Bundle b0 = new Bundle();
                b0.putSerializable("children",fuzhi);
                f0.putExtras(b0);
                f0.putExtra("page",0);
                startActivity(f0);
                getActivity().overridePendingTransition(R.anim.login_in, 0);
                break;
            case R.id.fuzhi1:
                Intent f1 = new Intent(getActivity(), CategoryActivity.class);
                Bundle b1 = new Bundle();
                b1.putSerializable("children",fuzhi);
                f1.putExtras(b1);
                f1.putExtra("page",1);
                startActivity(f1);
                getActivity().overridePendingTransition(R.anim.login_in, 0);
                break;
            case R.id.fuzhi2:
                Intent f2 = new Intent(getActivity(), CategoryActivity.class);
                Bundle b2 = new Bundle();
                b2.putSerializable("children",fuzhi);
                f2.putExtras(b2);
                f2.putExtra("page",2);
                startActivity(f2);
                getActivity().overridePendingTransition(R.anim.login_in, 0);
                break;
            case R.id.fuzhi3:
                Intent f3 = new Intent(getActivity(), CategoryActivity.class);
                Bundle b3 = new Bundle();
                b3.putSerializable("children",fuzhi);
                f3.putExtras(b3);
                f3.putExtra("page",3);
                startActivity(f3);
                getActivity().overridePendingTransition(R.anim.login_in, 0);
                break;
            case R.id.fuzhi4:
                Intent f4 = new Intent(getActivity(), CategoryActivity.class);
                Bundle b4 = new Bundle();
                b4.putSerializable("children",fuzhi);
                f4.putExtras(b4);
                f4.putExtra("page",4);
                startActivity(f4);
                getActivity().overridePendingTransition(R.anim.login_in, 0);
                break;
            case R.id.fuzhi5:
                Intent f5 = new Intent(getActivity(), CategoryActivity.class);
                Bundle b5 = new Bundle();
                b5.putSerializable("children",fuzhi);
                f5.putExtras(b5);
                f5.putExtra("page",5);
                startActivity(f5);
                getActivity().overridePendingTransition(R.anim.login_in, 0);
                break;
        }
    }
}
