package com.bwie.yunifang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bwie.utils.CommonUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.HashMap;
import java.util.Map;

public class QrcodeActivity extends AutoLayoutActivity implements View.OnClickListener {

    private ImageView back;
    private ImageView share;
    private PopupWindow pop;
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
    private ImageView iv;
    private SharedPreferences sp;
    private String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_qrcode);
        back = (ImageView) findViewById(R.id.qr_back);
        share = (ImageView) findViewById(R.id.qr_share);
        iv = (ImageView) findViewById(R.id.qrcode_image);

        //常见bitmap对象
        Bitmap qrBitmap = generateBitmap("http://www.yunifang.com/index.html",400, 400);
        iv.setImageBitmap(qrBitmap);
        sp = getSharedPreferences("login",MODE_PRIVATE);
        user_name = sp.getString("user_name", null);
        back.setOnClickListener(this);
        share.setOnClickListener(this);
    }
    private Bitmap generateBitmap(String content,int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (encode.get(j, i)) {
                        pixels[i * width + j] = 0x00000000;
                    } else {
                        pixels[i * width + j] = 0xffffffff;
                    }
                }
            }
            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.qr_back:
                finish();
                overridePendingTransition(0, R.anim.login_out);
                break;
            case R.id.qr_share:
                View share_view= CommonUtils.inflate(R.layout.share_pop);
                showPopup(share_view,850);
                Button share_qq = (Button) share_view.findViewById(R.id.share_qq);
                Button cancle = (Button) share_view.findViewById(R.id.but_cancel);
                cancle.setOnClickListener(this);
                share_qq.setOnClickListener(this);
                break;
            case R.id.share_qq:
                if(user_name==null)
                {
                    Intent in=new Intent(QrcodeActivity.this,LoginActivity.class);
                    startActivity(in);
                    overridePendingTransition(R.anim.login_in,0);
                }
                else
                {
                    new ShareAction(QrcodeActivity.this).setPlatform(SHARE_MEDIA.QQ)
                            .withTargetUrl("http://www.yunifang.com/index.html")
                            .setCallback(umShareListener)
                            .share();
                }
                break;
            case R.id.but_cancel:
                pop.dismiss();
                break;
        }
    }
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat","platform"+platform);

            Toast.makeText(QrcodeActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(QrcodeActivity.this,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if(t!=null){
                Log.d("throw","throw:"+t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(QrcodeActivity.this,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };
    private void showPopup(View view,int height) {
        /**
         * 创建popupwindow
         */
        pop = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,height);
        pop.setOutsideTouchable(true);
        pop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop.setAnimationStyle(R.style.Popupwindow);
        pop.showAtLocation(iv, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, -10);
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
}
