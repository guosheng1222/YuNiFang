package com.bwie.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.bwie.base.BaseFragment;
import com.bwie.utils.CommonUtils;
import com.bwie.view.ShowingPage;
import com.bwie.yunifang.LoginActivity;
import com.bwie.yunifang.R;

/**
 * Created by PC on 2016/11/28.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {
    @Override
    protected View createSuccessView() {
        View view = CommonUtils.inflate(R.layout.minefragment_main);
        Button login = (Button) view.findViewById(R.id.bt_login);
        login.setOnClickListener(this);
        return view;
    }

    @Override
    protected void onload() {
        new Thread()
        {
            @Override
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                MineFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
                super.run();

            }
        }.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.bt_login:
                Intent in=new Intent(getActivity(), LoginActivity.class);
                startActivity(in);
                getActivity().overridePendingTransition(R.anim.login_in,0);
                break;
        }
    }
}
