package com.bwie.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.adapter.BestSellAdapter;
import com.bwie.adapter.CommonAdapter;
import com.bwie.adapter.MyViewPagerAdapter;
import com.bwie.adapter.MylistViewAdapter;
import com.bwie.adapter.ViewHolder;
import com.bwie.base.BaseData;
import com.bwie.base.BaseFragment;
import com.bwie.bean.BeanMain;
import com.bwie.utils.CommonUtils;
import com.bwie.utils.URLUtils;
import com.bwie.view.MyGridView;
import com.bwie.view.MyListView;
import com.bwie.view.MyRoolViewPager;
import com.bwie.view.Myheader;
import com.bwie.view.ShowingPage;
import com.bwie.yunifang.AllgoodsActivity;
import com.bwie.yunifang.InfoActivity;
import com.bwie.yunifang.LoginActivity;
import com.bwie.yunifang.MainActivity;
import com.bwie.yunifang.R;
import com.bwie.yunifang.WebActivity;
import com.google.gson.Gson;
import com.liaoinstan.springview.widget.SpringView;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import java.util.ArrayList;

/**
 * Created by PC on 2016/11/28.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {
    String[] title1Array=new String[]{"","会员积分商城","兑换码","御泥坊产品-防伪查询"};
    String[] titleArray=new String[]{"周末秒杀","三人团列表","海量赠品任性选","","会员权益","晒单赢好礼","御泥坊美肤讲堂","御泥坊 我的御用面膜"};
    private String s;
    ArrayList<String> imageUrlList=new ArrayList<>();
    ArrayList<ImageView> dotList=new ArrayList<>();

    private BeanMain beanMain;
    int[] dotArray=new int[]{R.mipmap.page_indicator_focused,R.mipmap.page_indicator_unfocused};
    private LinearLayout lin;
    private MyRoolViewPager vp;
    private MyGridView grid1;

    private ArrayList<BeanMain.DataBean.ActivityInfoBean.ActivityInfoListBean> vpList;
    private ViewPager myviewPager;
    private ListView home_lv;
    private MyGridView myGridView;
    private SpringView mySpriView;
    private MyViewPagerAdapter myViewPagerAdapter;
    private MylistViewAdapter mylistViewAdapter;
    private TextView lookAll;
    private RecyclerView myrecycle;
    private SharedPreferences sp;
    private String user_name;

    @Override
    protected View createSuccessView() {
        View view = initView();
        sp = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        user_name = sp.getString("user_name", null);
        mySpriView.setHeader(new Myheader());
        mySpriView.setType(SpringView.Type.FOLLOW);
        mySpriView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mySpriView.onFinishFreshAndLoad();
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
        //轮播图
        initRoolView();
        //设置页面第一个GridView
        initGridView1();
        //设置优惠活动
        initMyviewPager();
        //专题
        initMylistView();
        //下方gridView
        initMyGridView();
        initMyRecycleView();
        //全部商品
        lookAll.setOnClickListener(this);
        return view;
    }

    private void initMyRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        myrecycle.setLayoutManager(linearLayoutManager);
        //设置适配器
        final BestSellAdapter mAdapter = new BestSellAdapter((ArrayList<BeanMain.DataBean.BestSellersBean>) beanMain.getData().getBestSellers(),getActivity(), (MainActivity) getActivity());
        myrecycle.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new BestSellAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int p) {
                Intent in=new Intent(getActivity(), InfoActivity.class);
                in.putExtra("id",beanMain.getData().getBestSellers().get(0).getGoodsList().get(p).getId());
                getActivity().startActivity(in);
                getActivity().overridePendingTransition(R.anim.login_in,0);
            }
        });
    }

    private void initMyGridView() {
        myGridView.setAdapter(new CommonAdapter<BeanMain.DataBean.DefaultGoodsListBean>(getActivity(),beanMain.getData().getDefaultGoodsList(),R.layout.home_grid_item,beanMain.getData().getDefaultGoodsList().size()) {
            @Override
            public void convert(ViewHolder holder, BeanMain.DataBean.DefaultGoodsListBean item) {
                holder.setImageByUrl(R.id.home_grid_image,item.getGoods_img());
                holder.setText(R.id.home_grid_title,item.getGoods_name());
                holder.setText(R.id.home_grid_info,item.getEfficacy());
                holder.setText(R.id.home_grid_nowPrice,"¥"+item.getShop_price());
                holder.setText(R.id.home_grid_afterPrice,"¥"+item.getMarket_price());
                holder.setTextLine(R.id.home_grid_afterPrice);
            }
        });
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in=new Intent(getActivity(), InfoActivity.class);
                in.putExtra("id",beanMain.getData().getDefaultGoodsList().get(position).getId());
                getActivity().startActivity(in);
                getActivity().overridePendingTransition(R.anim.login_in,0);
            }
        });
    }
    private void initMylistView() {
        mylistViewAdapter = new MylistViewAdapter(getActivity(), beanMain.getData(), (MainActivity) getActivity());
        home_lv.setAdapter(mylistViewAdapter);
    }
    //优惠活动方法
    private void initMyviewPager()
    {
        vpList= (ArrayList<BeanMain.DataBean.ActivityInfoBean.ActivityInfoListBean>) beanMain.getData().getActivityInfo().getActivityInfoList();
        myviewPager.setPageMargin(30);
        //设置缓存的页面数量
        myviewPager.setOffscreenPageLimit(vpList.size());
        myViewPagerAdapter = new MyViewPagerAdapter(getActivity(), vpList);
        myviewPager.setAdapter(myViewPagerAdapter);
        myviewPager.setPageTransformer(true,new ScaleInTransformer());
        myviewPager.setCurrentItem(300);
        myViewPagerAdapter.setOnItemClickLitener(new MyViewPagerAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                String activityData = beanMain.getData().getActivityInfo().getActivityInfoList().get(position % vpList.size()).getActivityData();
                if(activityData==null||activityData.equals("166"))
                {
                    Toast.makeText(getActivity(), "此功能仅限手机登录用户使用", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent in = new Intent(getActivity(), WebActivity.class);
                    in.putExtra("title", "御泥坊");
                    in.putExtra("url",activityData);
                    startActivity(in);
                    getActivity().overridePendingTransition(R.anim.login_in, 0);
                }
            }
        });
    }

    private void initGridView1() {
        ArrayList<BeanMain.DataBean.Ad5Bean> ad5 = (ArrayList<BeanMain.DataBean.Ad5Bean>) beanMain.getData().getAd5();
        grid1.setAdapter(new CommonAdapter<BeanMain.DataBean.Ad5Bean>(getActivity(),ad5,R.layout.home_grid1_item,ad5.size()) {
            @Override
            public void convert(ViewHolder holder, BeanMain.DataBean.Ad5Bean item) {
                holder.setText(R.id.home_grid1_text,item.getTitle());
                holder.setImageByUrl(R.id.home_grid1_image,item.getImage());
            }
        });
        //条目点击事件
        grid1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {
                    if(user_name==null)
                    {
                        Intent in=new Intent(getActivity(),LoginActivity.class);
                        startActivity(in);
                        getActivity().overridePendingTransition(R.anim.login_in,0);
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "此功能仅限手机登录可以使用", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Intent in=new Intent(getActivity(), WebActivity.class);
                    in.putExtra("title",title1Array[position]);
                    in.putExtra("url",beanMain.getData().getAd5().get(position).getAd_type_dynamic_data());
                    startActivity(in);
                    getActivity().overridePendingTransition(R.anim.login_in,0);
                }
            }
        });
    }

    private void initRoolView() {
        ArrayList<BeanMain.DataBean.Ad1Bean> ad1 = (ArrayList<BeanMain.DataBean.Ad1Bean>) beanMain.getData().getAd1();
        for (int i = 0; i < ad1.size(); i++) {
            imageUrlList.add(ad1.get(i).getImage());
        }
        initDot(ad1);
        vp.initData(imageUrlList,dotArray,dotList);
        vp.setAdapter();
        vp.setOnPageClickListener(new MyRoolViewPager.OnPageClickListener() {
            @Override
            public void setOnPage(int position) {
                if(position%imageUrlList.size()==3)
                {
                    //判断是否是登陆状态
                    if(user_name==null)
                    {
                        Intent in=new Intent(getActivity(),LoginActivity.class);
                        startActivity(in);
                        getActivity().overridePendingTransition(R.anim.login_in,0);
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "此功能仅限手机登录可以使用", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Intent in = new Intent(getActivity(), WebActivity.class);
                    in.putExtra("title",titleArray[position%imageUrlList.size()]);
                    in.putExtra("url",beanMain.getData().getAd1().get(position%imageUrlList.size()).getAd_type_dynamic_data());
                    startActivity(in);
                    getActivity().overridePendingTransition(R.anim.login_in,0);
                }
            }
        });
    }
    //初始化小点
    private void initDot(ArrayList<BeanMain.DataBean.Ad1Bean> adlList) {
        dotList.clear();
        lin.removeAllViews();
        for (int i = 0; i < adlList.size(); i++) {
            ImageView imageView = new ImageView(getActivity());
            if(i==0)
            {
                imageView.setImageResource(dotArray[0]);
            }
            else
            {
                imageView.setImageResource(dotArray[1]);
            }
            dotList.add(imageView);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10,20,8,5);
            lin.addView(imageView,params);
        }
    }
    //查找控件
    @NonNull
    private View initView() {
        View view = CommonUtils.inflate(R.layout.homefragment_main);
        vp = (MyRoolViewPager) view.findViewById(R.id.myRoolViewPager);
        lin = (LinearLayout) view.findViewById(R.id.roolView_lin);
        grid1 = (MyGridView) view.findViewById(R.id.home_grid1);
        myviewPager = (ViewPager) view.findViewById(R.id.myViewPager);
        home_lv = (MyListView) view.findViewById(R.id.myListView);
        myrecycle = (RecyclerView) view.findViewById(R.id.home_myRecycleView);
        myGridView = (MyGridView) view.findViewById(R.id.myGridView);
        mySpriView = (SpringView) view.findViewById(R.id.mySpringView);
        lookAll = (TextView) view.findViewById(R.id.lookAll);
        return view;
    }
    //加载网络方法
    @Override
    protected void onload() {
       new BaseData() {
           @Override
           public void setResultData(String data) {
               HomeFragment.this.s=data;
               Gson gson=new Gson();
               beanMain = gson.fromJson(s,BeanMain.class);
               HomeFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
           }
           @Override
           protected void setResultError(ShowingPage.StateType stateLoadError) {
               HomeFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_ERROR);
           }
       }.getData(URLUtils.homeUrl,URLUtils.homeArgs,0, BaseData.NORMALTIME);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.lookAll:
                Intent in=new Intent(getActivity(), AllgoodsActivity.class);
                startActivity(in);
                getActivity().overridePendingTransition(R.anim.login_in,0);
                break;
        }
    }
}
