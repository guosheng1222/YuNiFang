package com.bwie.yunifang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.adapter.CommonAdapter;
import com.bwie.adapter.InfoViewPagerAdapter;
import com.bwie.adapter.ViewHolder;
import com.bwie.base.BaseData;
import com.bwie.bean.BeanCart;
import com.bwie.bean.BeanImage;
import com.bwie.bean.BeanInfo;
import com.bwie.database.SqliteDao;
import com.bwie.utils.CommonUtils;
import com.bwie.utils.URLUtils;
import com.bwie.view.MyListView;
import com.bwie.view.ShowingPage;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

public class InfoActivity extends AutoLayoutActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    int[] dotArray=new int[]{R.mipmap.page_indicator_focused,R.mipmap.page_indicator_unfocused};
    private BeanInfo beanInfo;
    private ArrayList<String> imageUrlList=new ArrayList<>();
    private ViewPager vp;
    ArrayList<ImageView> dotList=new ArrayList<>();
    private LinearLayout lin;
    private ArrayList<BeanInfo.DataBean.GoodsBean.GalleryBean> gallery;
    private TextView goodsTitle;
    private TextView info_nowPrice;
    private TextView info_afterPrice;
    private TextView sellCount;
    private TextView collection;
    private MyListView myListView;
    private RadioGroup rg;
    private MyListView lv;
    private RadioButton comments;
    private  ArrayList<BeanInfo.DataBean.GoodsBean.AttributesBean> attributeslist=new ArrayList<>();
    private ImageView back;
    private Button buyNow;
    private Button addCar;
    public String buy="立即购买";

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    backgroundAlpha((float) msg.obj);
                    break;
            }
        }
    };
    private ImageView juan;
    private ImageView di;
    private SqliteDao dao;
    private CheckBox check_collection;
    private int num=1;
    private ImageView info_share;
    private PopupWindow pop;
    private TextView buy_num;
    private ImageView add_image;
    private ImageView reduce_image;
    private int purchase_num;
    private SharedPreferences sp;
    private String user_name;
    private ImageView info_carts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_info);
        //查找控件
        initView();
        onload();
        //点击事件
        onClick();
        lv.setDivider(null);

    }

    private void onClick() {
        info_carts.setOnClickListener(this);
        info_share.setOnClickListener(this);
        buyNow.setOnClickListener(this);
        addCar.setOnClickListener(this);
        back.setOnClickListener(this);
        rg.setOnCheckedChangeListener(this);
    }

    private void initView() {
        dao = new SqliteDao(this);
        sp = getSharedPreferences("login",MODE_PRIVATE);
        user_name = sp.getString("user_name", null);
        info_carts = (ImageView) findViewById(R.id.info_carts);
        check_collection = (CheckBox) findViewById(R.id.check_collection);
        juan = (ImageView) findViewById(R.id.info_image_juan);
        di = (ImageView) findViewById(R.id.info_image_di);
        back = (ImageView) findViewById(R.id.info_back);
        lin = (LinearLayout) findViewById(R.id.info_lin);
        info_share = (ImageView) findViewById(R.id.info_share);
        vp = (ViewPager) findViewById(R.id.info_MyViewPager);
        goodsTitle = (TextView) findViewById(R.id.info_goodsTitle);
        info_nowPrice = (TextView) findViewById(R.id.info_nowPrice);
        info_afterPrice = (TextView) findViewById(R.id.info_afterPrice);
        sellCount = (TextView) findViewById(R.id.info_sellCount);
        collection = (TextView) findViewById(R.id.info_collection);
        myListView = (MyListView) findViewById(R.id.info_myListView);
        buyNow = (Button) findViewById(R.id.buyNow);
        addCar = (Button) findViewById(R.id.addCar);
        lv = (MyListView) findViewById(R.id.info_list);
        rg = (RadioGroup) findViewById(R.id.info_rg);
        comments = (RadioButton) findViewById(R.id.info_comments);
    }

    @Override
    protected void onDestroy() {
        //清空集合
        attributeslist.clear();
        imageUrlList.clear();
        finish();
        overridePendingTransition(0, R.anim.login_out);
        super.onDestroy();
    }

    private void initRoolView() {
        gallery = (ArrayList<BeanInfo.DataBean.GoodsBean.GalleryBean>) beanInfo.getData().getGoods().getGallery();
        for (int i = 0; i < gallery.size(); i++) {
            imageUrlList.add(gallery.get(i).getNormal_url());
        }
        initDot(gallery);
        //设置适配器
        vp.setAdapter(new InfoViewPagerAdapter(imageUrlList));
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotList.size(); i++) {
                    if(position%dotList.size()==i)
                    {
                        dotList.get(i).setImageResource(dotArray[0]);
                    }
                    else
                    {
                        dotList.get(i).setImageResource(dotArray[1]);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void initDot(ArrayList<BeanInfo.DataBean.GoodsBean.GalleryBean> gallery) {
        dotList.clear();
        lin.removeAllViews();
        for (int i = 0; i < gallery.size(); i++) {
            ImageView imageView = new ImageView(InfoActivity.this);
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

      //请求数据
    private void onload() {
        new BaseData() {
            @Override
            public void setResultData(String data) {
                Gson gson=new Gson();
                beanInfo = gson.fromJson(data, BeanInfo.class);
                initRoolView();
                initTv();
                initSales();
                myListView.setAdapter(new CommonAdapter<BeanInfo.DataBean.ActivityBean>(InfoActivity.this,beanInfo.getData().getActivity(),R.layout.info_list_item1,beanInfo.getData().getActivity().size()) {
                    @Override
                    public void convert(ViewHolder holder, BeanInfo.DataBean.ActivityBean item) {
                        holder.setText(R.id.infolist_item_title,item.getTitle());
                    }
                });
                initMylist();
            }
            @Override
            protected void setResultError(ShowingPage.StateType stateLoadError) {
            }
        }.getData(URLUtils.infoUrl,URLUtils.infoArgs+"&id="+getIntent().getStringExtra("id"),0, BaseData.NOTIME);
    }

    private void initMylist() {
        attributeslist= (ArrayList<BeanInfo.DataBean.GoodsBean.AttributesBean>) beanInfo.getData().getGoods().getAttributes();
        String goods_desc = beanInfo.getData().getGoods().getGoods_desc();
        Gson gson1=new Gson();
        BeanImage[] beanImages = gson1.fromJson(goods_desc, BeanImage[].class);
        List<BeanImage> list=new ArrayList<BeanImage>();
        for (int i = 0; i < beanImages.length; i++) {
            list.add(beanImages[i]);
        }
        lv.setAdapter(new CommonAdapter<BeanImage>(InfoActivity.this,list, R.layout.info_list_image,list.size()) {
            @Override
            public void convert(ViewHolder helper, BeanImage item) {
                helper.setImageByUrl(R.id.info_list1_image,item.getUrl());
            }
        });
    }

    //初始化销量，券，抵 等
    private void initSales() {
        int sales_volume = beanInfo.getData().getGoods().getSales_volume();

        if(beanInfo.getData().getGoods().isIs_coupon_allowed()==false)
        {
            juan.setVisibility(View.GONE);
        }
        if(beanInfo.getData().getGoods().getIs_allow_credit().equals("0"))
        {
            di.setVisibility(View.GONE);
        }
        if(sales_volume>10000)
        {
            String s = String.format("%.2f",sales_volume / 10000.0);
            sellCount.setText(s+"万");
        }
        else if(sales_volume<10000)
        {
            sellCount.setText(sales_volume+"");
        }
    }
    //设置评论，详情
    private void initTv() {
        comments.setText("评论("+beanInfo.getData().getCommentNumber()+")");
        goodsTitle.setText(beanInfo.getData().getGoods().getGoods_name());
        info_nowPrice.setText("¥"+beanInfo.getData().getGoods().getShop_price());
        info_afterPrice.setText("¥"+beanInfo.getData().getGoods().getMarket_price());
        info_afterPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG |Paint.ANTI_ALIAS_FLAG);
        collection.setText(beanInfo.getData().getGoods().getCollect_count()+"");
    }
    //单击事件
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.info_carts:
                if(user_name==null)
                {
                    //判断是否登陆
                    Intent in=new Intent(InfoActivity.this,LoginActivity.class);
                    startActivity(in);
                    overridePendingTransition(R.anim.login_in,0);
                }
                else
                {
                    //进入购物车界面
                    finish();
                    RadioButton rb = (RadioButton) MainActivity.rg.getChildAt(2);
                    rb.setChecked(true);
                }
                break;
            case R.id.info_back:
                finish();
                overridePendingTransition(0, R.anim.login_out);
                break;
            case R.id.buyNow:
                View view1= CommonUtils.inflate(R.layout.addcar_pop);
                initPop(view1);
                showPopup(view1,420);
                buy="立即购买";
                break;
            case R.id.addCar:
                //添加购物车
                View view = CommonUtils.inflate(R.layout.addcar_pop);
                initPop(view);
                showPopup(view,420);
                buy="加入购物车";
                break;
            case R.id.info_share:
                //右上角分享按钮
                View share_view=CommonUtils.inflate(R.layout.share_pop);
                showPopup(share_view,850);
                Button share_qq = (Button) share_view.findViewById(R.id.share_qq);
                Button cancle = (Button)share_view.findViewById(R.id.but_cancel);
                cancle.setOnClickListener(this);
                share_qq.setOnClickListener(this);
                break;
            //点击确定按钮   判断已登录 添加进购物车  未登录 跳转登录界面
            case R.id.addCar_pop_ok:
                if(buy.equals("加入购物车")) {
                    if (user_name == null) {
                        Intent in = new Intent(InfoActivity.this, LoginActivity.class);
                        startActivity(in);
                        overridePendingTransition(R.anim.login_in, 0);
                    } else {
                        dao.add(beanInfo.getData().getGoods().getId(), beanInfo.getData().getGoods().getGoods_name(), beanInfo.getData().getGoods().getGoods_img(), beanInfo.getData().getGoods().getShop_price() + "",
                                num, beanInfo.getData().getGoods().isIs_coupon_allowed() + "", beanInfo.getData().getGoods().getIs_allow_credit(), check_collection.isChecked() + "", beanInfo.getData().getGoods().getRestrict_purchase_num()
                        );
                    }
                }else if(buy.equals("立即购买"))
                {
                    if (user_name == null) {
                        Intent in = new Intent(InfoActivity.this, LoginActivity.class);
                        startActivity(in);
                        overridePendingTransition(R.anim.login_in, 0);
                    } else {
                        ArrayList<BeanCart> cartList=new ArrayList<>();
                        cartList.add(new BeanCart(beanInfo.getData().getGoods().getId(), beanInfo.getData().getGoods().getGoods_name(), beanInfo.getData().getGoods().getGoods_img(), beanInfo.getData().getGoods().getShop_price() + "",
                                num, beanInfo.getData().getGoods().isIs_coupon_allowed() + "", beanInfo.getData().getGoods().getIs_allow_credit(), check_collection.isChecked() + "", beanInfo.getData().getGoods().getRestrict_purchase_num()));
                        Intent in=new Intent(InfoActivity.this,AddressActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("goods",cartList);
                        in.putExtras(bundle);
                        startActivity(in);
                        overridePendingTransition(R.anim.login_in, 0);
                       /* dao.add(beanInfo.getData().getGoods().getId(), beanInfo.getData().getGoods().getGoods_name(), beanInfo.getData().getGoods().getGoods_img(), beanInfo.getData().getGoods().getShop_price() + "",
                                num, beanInfo.getData().getGoods().isIs_coupon_allowed() + "", beanInfo.getData().getGoods().getIs_allow_credit(), check_collection.isChecked() + "", beanInfo.getData().getGoods().getRestrict_purchase_num()
                        );*/
                    }
                }
                pop.dismiss();
                break;
            case R.id.addCar_pop_back:
                pop.dismiss();
                break;
            case R.id.reduce_image:
                //减号
                num=Integer.parseInt(buy_num.getText().toString());
                if(num>1)
                {
                    num--;
                    buy_num.setText(""+num);
                    add_image.setImageResource(R.mipmap.add_able);
                    if(num==1)
                    {
                        reduce_image.setImageResource(R.mipmap.reduce_unable);
                    }
                    else if(num==purchase_num)
                    {
                        add_image.setImageResource(R.mipmap.add_unable);
                    }
                }
                break;
            case R.id.add_image:
                //加号
                num=Integer.parseInt(buy_num.getText().toString());
                if(num< purchase_num)
                {
                    num++;
                    buy_num.setText(""+num);
                    reduce_image.setImageResource(R.mipmap.reduce_able);
                    if(num== purchase_num)
                    {
                        add_image.setImageResource(R.mipmap.add_unable);
                        reduce_image.setImageResource(R.mipmap.reduce_able);
                    }
                }
                break;
            case R.id.share_qq:
                //判断是否登录
                if(user_name==null)
                {
                    //跳转到登录界面
                    Intent in=new Intent(InfoActivity.this,LoginActivity.class);
                    startActivity(in);
                    overridePendingTransition(R.anim.login_in,0);
                }
                else
                {
                    //分享
                    new ShareAction(InfoActivity.this).setPlatform(SHARE_MEDIA.QQ)
                            .withTargetUrl("http://vip.yunifang.com/goods.html?id="+getIntent().getStringExtra("id"))
                            .setCallback(umShareListener)
                            .share();
                }
                break;
            case R.id.but_cancel:
                pop.dismiss();
                break;

        }
    }

    private void initPop(View view) {
        final int stock_num=beanInfo.getData().getGoods().getStock_number();
        purchase_num = beanInfo.getData().getGoods().getRestrict_purchase_num();
        Button ok = (Button)view.findViewById(R.id.addCar_pop_ok);
        ImageView back =  (ImageView) view.findViewById(R.id.addCar_pop_back);
        reduce_image = (ImageView) view.findViewById(R.id.reduce_image);
        add_image = (ImageView) view.findViewById(R.id.add_image);
        ImageView image = (ImageView)view.findViewById(R.id.addCar_pop_image);
        buy_num = (TextView)view.findViewById(R.id.buy_num);
        TextView price = (TextView) view.findViewById(R.id.addCar_pop_price);
        TextView kucun = (TextView) view.findViewById(R.id.addCar_pop_kucun);
        TextView xiangou = (TextView) view.findViewById(R.id.addCar_pop_xiangou);
        ImageLoader.getInstance().displayImage(gallery.get(0).getNormal_url(),image);
        kucun.setText("库存"+stock_num+"件");
        xiangou.setText("限购"+ purchase_num +"件");
        price.setText(""+beanInfo.getData().getGoods().getShop_price());
        buy_num.setText(""+num);
        ok.setOnClickListener(this);
        back.setOnClickListener(this);
        reduce_image.setOnClickListener(this);
        add_image.setOnClickListener(this);
        if(Integer.parseInt(buy_num.getText().toString())== purchase_num)
        {
            add_image.setImageResource(R.mipmap.add_unable);
        }
        if(Integer.parseInt(buy_num.getText().toString())== purchase_num)
        {
            reduce_image.setImageResource(R.mipmap.reduce_able);
        }
    }

    private void showPopup(View view,int height) {
        /**
         * 创建popupwindow
         */
        pop = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,height);
        //点击外部关闭popupwindow
        pop.setOutsideTouchable(true);
        //设置背景
        pop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop.setAnimationStyle(R.style.Popupwindow);
        pop.showAtLocation(myListView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, -10);
        new Thread(new Runnable() {
            @Override
            public void run() {
                float alpha = 0.9f;
                while (alpha > 0.5f) {
                    try {
                        //4是根据弹出动画时间和减少的透明度计算
                        Thread.sleep(4);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message msg = mHandler.obtainMessage();
                    msg.what = 1;
                    //每次减少0.01，精度越高，变暗的效果越流畅
                    alpha -= 0.01f;
                    msg.obj = alpha;
                    mHandler.sendMessage(msg);
                }
            }

        }).start();
        /**
         * popupwindow关闭监听事件
         */
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //此处while的条件alpha不能<= 否则会出现黑屏
                        float alpha = 0.5f;
                        while (alpha < 0.99f) {
                            try {
                                Thread.sleep(4);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Log.d("HeadPortrait", "alpha:" + alpha);
                            Message msg = mHandler.obtainMessage();
                            msg.what = 1;
                            alpha += 0.01f;
                            msg.obj = alpha;
                            mHandler.sendMessage(msg);
                        }
                    }

                }).start();
            }
        });
    }

    /**
     * 设置屏幕透明度
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }
    //QQ分享
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat","platform"+platform);

            Toast.makeText(InfoActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(InfoActivity.this,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if(t!=null){
                Log.d("throw","throw:"+t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(InfoActivity.this,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId)
        {
            case R.id.info_rg:
                for (int i = 0; i < rg.getChildCount(); i++) {
                    RadioButton rb = (RadioButton) rg.getChildAt(i);
                    if(rb.getId()==checkedId)
                    {
                        rb.setTextColor(Color.parseColor("#FC6B87"));
                        if(i==0)
                        {
                            //详情
                            initMylist();
                        }
                        if(i==1)
                        {
                            lv.setAdapter(new CommonAdapter<BeanInfo.DataBean.GoodsBean.AttributesBean>(InfoActivity.this,attributeslist,R.layout.info_list_text,attributeslist.size()) {
                                @Override
                                public void convert(ViewHolder helper, BeanInfo.DataBean.GoodsBean.AttributesBean item) {
                                    helper.setText(R.id.info_list1_text1,item.getAttr_name());
                                    helper.setText(R.id.info_list1_text2,item.getAttr_value());
                                }
                            });
                        }
                        if(i==2)
                        {
                            lv.setAdapter(new CommonAdapter<BeanInfo.DataBean.CommentsBean>(InfoActivity.this,beanInfo.getData().getComments(),R.layout.info_item_comment,beanInfo.getData().getComments().size()) {
                                @Override
                                public void convert(ViewHolder holder, BeanInfo.DataBean.CommentsBean item) {
                                    holder.setText(R.id.item_comment_user,item.getUser().getNick_name());
                                    holder.setText(R.id.item_comment_content,item.getContent());
                                    holder.setText(R.id.item_comment_time,item.getCreatetime());
                                    holder.setImageByUrl(R.id.item_comment_icon,item.getUser().getIcon());

                                    if(item.getPicture()!=null&&item.getPicture().size()==1)
                                    {
                                        holder.setImageByUrl(R.id.item_comment_image1,item.getPicture().get(0).getThumb_url());
                                    }
                                    else  if(item.getPicture()!=null&&item.getPicture().size()==2)
                                    {
                                        holder.setImageByUrl(R.id.item_comment_image1,item.getPicture().get(0).getThumb_url());
                                        holder.setImageByUrl(R.id.item_comment_image2,item.getPicture().get(1).getThumb_url());
                                    }
                                }
                            });
                        }
                    }
                    else
                    {
                        rb.setTextColor(Color.BLACK);
                    }
                }
                break;
        }
    }
}
