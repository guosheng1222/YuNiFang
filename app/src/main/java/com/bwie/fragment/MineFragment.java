package com.bwie.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.base.BaseFragment;
import com.bwie.utils.CommonUtils;
import com.bwie.view.CircleImageView;
import com.bwie.view.ShowingPage;
import com.bwie.yunifang.LoginActivity;
import com.bwie.yunifang.MyOrderActivity;
import com.bwie.yunifang.R;
import com.bwie.yunifang.SettingActivity;

/**
 * Created by PC on 2016/11/28.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {

    private ImageView setting;
    private Button login;
    private CircleImageView icon;
    private Button bt_login;
    private TextView tv_user_name;
    protected SharedPreferences sp;
    private RadioGroup rg;
    private RelativeLayout myorder_lin;

    @Override
    protected View createSuccessView() {
        View view = initView();
        setting.setOnClickListener(this);
        login.setOnClickListener(this);
        return view;
    }
    //查找控件
    private View initView() {
        View view = CommonUtils.inflate(R.layout.minefragment_main);
        login = (Button) view.findViewById(R.id.bt_login);
        setting = (ImageView) view.findViewById(R.id.iv_setting);
        tv_user_name = (TextView) view.findViewById(R.id.user_name);
        bt_login = (Button) view.findViewById(R.id.bt_login);
        icon = (CircleImageView) view.findViewById(R.id.user_icon_no_set);
        rg = (RadioGroup) view.findViewById(R.id.mine_rg);
        myorder_lin = (RelativeLayout) view.findViewById(R.id.mine_myorder);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < rg.getChildCount(); i++) {
                    RadioButton rb = (RadioButton) rg.getChildAt(i);
                    if(rb.isChecked())
                    {
                        if(i!=rg.getChildCount()-1)
                        {
                            Intent in=new Intent(getActivity(), MyOrderActivity.class);
                            in.putExtra("position",i+1);
                            startActivity(in);
                        }
                    }
                }
            }
        });
        return view;
    }

    @Override
    protected void onload() {
        MineFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
    }

    @Override
    public void onResume() {
        super.onResume();
        sp = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        String user_name = sp.getString("user_name", null);
        String user_icon = sp.getString("user_icon", null);
        if (user_name != null) {
            //判断是否是登陆状态
            tv_user_name.setVisibility(View.VISIBLE);
            tv_user_name.setText(user_name);
            bt_login.setText("会员中心");
        } else {
            bt_login.setText("登录");
            tv_user_name.setVisibility(View.GONE);
        }
        if (user_icon != null) {
            icon.setImageUrl(user_icon);
        } else {
            icon.setImageResource(R.mipmap.user_icon_no_set);
        }
    }
    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                if (bt_login.getText().toString().equals("登录")) {
                    Intent in = new Intent(getActivity(), LoginActivity.class);
                    startActivity(in);
                    getActivity().overridePendingTransition(R.anim.login_in, 0);
                }
                if (bt_login.getText().toString().equals("会员中心")) {
                    Toast.makeText(getActivity(), "此功能仅限手机登录可用", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_setting:
                Intent in_setting = new Intent(getActivity(), SettingActivity.class);
                startActivity(in_setting);
                getActivity().overridePendingTransition(R.anim.login_in, 0);
                break;
        }
    }
}
