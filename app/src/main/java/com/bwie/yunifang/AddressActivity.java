package com.bwie.yunifang;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bwie.adapter.CommonAdapter;
import com.bwie.adapter.ViewHolder;
import com.bwie.alipay.domain.PayResult;
import com.bwie.alipay.util.SignUtils;
import com.bwie.bean.BeanAddress;
import com.bwie.bean.BeanCart;
import com.bwie.database.SqliteDao;
import com.bwie.view.MyListView;
import com.zhy.autolayout.AutoLayoutActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

public class AddressActivity extends AutoLayoutActivity implements View.OnClickListener {

    private MyListView myListView;
    private Button bt_pay;
    private TextView pay_tv;
    private double sum_price;
    private TextView args;
    private int goods_num;
    private ArrayList<BeanCart> list;
    private TextView tv_address;
    private SqliteDao dao;
    private ImageView back;
    private BeanAddress beanAddress;
    private SharedPreferences sp;
    public static final String PARTNER = "2088901305856832";

    // 商户收款账号
    public static final String SELLER = "8@qdbaiu.com";

    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAM/KCxg/OIj6er2GEig0DOkHqBSzEPHGigMbTXP1k2nrxEHeE59xOOuyovQH/A1LgbuVKyOac3uAN4GXIBEoozRVzDhu5dobeNm48BPcpYSAfvN3K/5GLacvJeENqsiBx8KufM/9inlHaDRQV7WhX1Oe2ckat1EkdHwxxQgc36NhAgMBAAECgYEAwn3sWpq6cUR65LD8h9MIjopTImTlpFjgz72bhsHDZK6A+eJDXcddrwh7DI34t/0IBqu+QEoOc/f0fIEXS9hMwTvFY59XG7M8M6SdeaAbemrGmZ1IdD6YDmpbQFHn2ishaYF0YDZIkBS3WLDFrtk/efaarBCpGAVXeEiVQE4LewECQQD5W1rpkq+dHDRzzdtdi1bJ479wun5CfmVDVb2CJH7Iz0t8zyp/iEVV2QELnxZMphmoSzKaLXutTTj2OImpzCtRAkEA1VMxG6nQq9NkU51H1+I3mlUXRZ0XeFA1GFJ7xWpNRAVhEWlDz2zy9v/gX+RFpNC3f5uznycas70Xp78ew43TEQJAZFFqi9mlqTF1sLk67bFnIyXrGPEOZrXvC13tNfR0xVkQZ4/46wHp0xXQo9pG4GNaoyhNnVV7EkelCPnJ+HPZYQJAQh6T9QgQZoGR8hyovPAf3dUL7oa/VIo/urcuJ8VIB5JHQNdIrk0NjaNHj1E4iNosVgATj3vWWel9IIArb99QkQJAKvfm78lwnImtg5IM604hdn/Wu1XF8tpxsKLWcnfchMr0bM9rCmKmhAY+wdmqSyPZRiNb1QaaaDTqJxLy6AnQ+Q==";

    private static final int SDK_PAY_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(AddressActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(AddressActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(AddressActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_address);
        dao = new SqliteDao(this);
        back = (ImageView) findViewById(R.id.address_back);
        list = (ArrayList<BeanCart>) getIntent().getExtras().getSerializable("goods");
        tv_address = (TextView) findViewById(R.id.indent_address_tv);
        myListView = (MyListView) findViewById(R.id.indent_goods_lv);
        bt_pay = (Button) findViewById(R.id.bt_pay);
        pay_tv = (TextView) findViewById(R.id.indent_pay_tv);
        args = (TextView) findViewById(R.id.indent_goods_args_tv);
        sp = getSharedPreferences("address", MODE_PRIVATE);
        tv_address.setText(sp.getString("ads", "请填写收货地址"));

        //设置适配器
        myListView.setAdapter(new CommonAdapter<BeanCart>(this, list, R.layout.address_item, list.size()) {
            @Override
            public void convert(ViewHolder holder, final BeanCart item) {
                holder.setText(R.id.address_list_name, item.getName());
                holder.setImageByUrl(R.id.address_list_image, item.getImage());
                holder.setText(R.id.address_list_price, "¥ " + item.getPrice() + "");
                ImageView juan = holder.getView(R.id.address_list_juan);
                ImageView di = holder.getView(R.id.address_list_di);
                final TextView buy_num = holder.getView(R.id.address_buy_num);
                final ImageView add_image = holder.getView(R.id.address_add_image);
                //减号
                final ImageView reduce_image = holder.getView(R.id.address_reduce_image);
                buy_num.setText("" + item.getNumber());
                if (item.getJuan().equals("false")) {
                    juan.setVisibility(View.GONE);
                }
                if (item.getDi().equals("0")) {
                    di.setVisibility(View.GONE);
                }
                if (item.getNumber() == item.getLimit_num()) {
                    add_image.setImageResource(R.mipmap.add_unable);
                }
                if (item.getNumber() > 1) {
                    reduce_image.setImageResource(R.mipmap.reduce_able);
                }
                reduce_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.getNumber() > 1) {
                            item.setNumber(item.getNumber() - 1);
                            buy_num.setText("" + item.getNumber());
                            sum();
                            add_image.setImageResource(R.mipmap.add_able);
                            if (item.getNumber() == 1) {
                                reduce_image.setImageResource(R.mipmap.reduce_unable);
                            } else if (item.getNumber() == item.getLimit_num()) {
                                add_image.setImageResource(R.mipmap.add_unable);
                            }
                        }
                    }
                });
                //加号监听
                add_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.getNumber() < item.getLimit_num()) {
                            item.setNumber(item.getNumber() + 1);
                            buy_num.setText("" + item.getNumber());
                            sum();
                            reduce_image.setImageResource(R.mipmap.reduce_able);
                            if (item.getNumber() == item.getLimit_num()) {
                                add_image.setImageResource(R.mipmap.add_unable);
                                reduce_image.setImageResource(R.mipmap.reduce_able);
                            }
                        }
                    }
                });
            }
        });
        sum();
        if (dao.queryAddress().size() == 1) {
            BeanAddress address = dao.queryAddress().get(0);
            tv_address.setText("   收货人:" + address.getName() + "             " + address.getPhone() + "\r\n" + "   详细地址: " + address.getCity() + address.getDetail());
        }
        bt_pay.setOnClickListener(this);
        tv_address.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    private void sum() {
        sum_price = 0.0;
        goods_num = 0;
        for (int i = 0; i < list.size(); i++) {
            sum_price += list.get(i).getNumber() * Double.parseDouble(list.get(i).getPrice());
            goods_num += list.get(i).getNumber();
        }
        String s = String.format("%.2f", sum_price);
        pay_tv.setText("实付 :" + s);
        args.setText("共计" + goods_num + "件商品  总计: ¥" + s);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_pay:
                if (tv_address.getText().toString().trim().equals("请填写收货地址")) {
                    Toast.makeText(this, "请填写收货地址", Toast.LENGTH_SHORT).show();
                } else {
                    String s=getMyorderId();
                    for (int i = 0; i < list.size(); i++) {
                        dao.deleteCart(list.get(i).getName());
                        dao.addOrder(s+"",list.get(i).getName(),list.get(0).getImage(),String.format("%.2f", sum_price),goods_num,"待付款",tv_address.getText().toString());
                    }
                   if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
                        new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialoginterface, int i) {
                                        finish();
                                    }
                                }).show();
                        return;
                    }
                    String orderInfo = getOrderInfo(list.get(0).getName(), "该测试商品的详细描述", "0.01");

                    /**
                     * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
                     */
                    String sign = sign(orderInfo);
                    try {
                        /**
                         * 仅需对sign 做URL编码
                         */
                        sign = URLEncoder.encode(sign, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    /**
                     * 完整的符合支付宝参数规范的订单信息
                     */
                    final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

                    Runnable payRunnable = new Runnable() {

                        @Override
                        public void run() {
                            // 构造PayTask 对象
                            PayTask alipay = new PayTask(AddressActivity.this);
                            // 调用支付接口，获取支付结果
                            String result = alipay.pay(payInfo, true);

                            Message msg = new Message();
                            msg.what = SDK_PAY_FLAG;
                            msg.obj = result;
                            mHandler.sendMessage(msg);
                        }
                    };
                    // 必须异步调用
                    Thread payThread = new Thread(payRunnable);
                    payThread.start();
                }
                break;
            case R.id.indent_address_tv:
                //判断数据库是否有数据  如果没有 新建收货地址
                if (dao.queryAddress().size() == 0) {
                    Intent in = new Intent(AddressActivity.this, AddAddressActivity.class);
                    startActivityForResult(in, 101);
                    overridePendingTransition(R.anim.login_in, 0);
                }
                //选择收货地址
                else {
                    Intent in = new Intent(AddressActivity.this, CheckAddressActivity.class);
                    in.putExtra("goods", tv_address.getText().toString());
                    startActivityForResult(in, 101);
                    overridePendingTransition(R.anim.login_in, 0);
                }
                break;
            case R.id.address_back:
                finish();
                overridePendingTransition(0, R.anim.login_out);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data.getExtras().getSerializable("address") != null) {
            BeanAddress address = (BeanAddress) data.getExtras().getSerializable("address");
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("ads", "   收货人:" + address.getName() + "             " + address.getPhone() + "\r\n" + "   详细地址: " + address.getCity() + address.getDetail());
            edit.commit();
            tv_address.setText(sp.getString("ads", null));
        }
    }

    /**
     * create the order info. 创建订单信息
     */
    private String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }
    public static String getMyorderId()
    {
        int matchId=1;
        int hashcode= UUID.randomUUID().toString().hashCode();
        if(hashcode<0)
        {
            hashcode=-hashcode;
        }
        return matchId+String.format("%015d",hashcode);
    }
}
