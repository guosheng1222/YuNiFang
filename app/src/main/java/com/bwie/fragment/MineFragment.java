package com.bwie.fragment;

import android.view.View;

import com.bwie.base.BaseFragment;
import com.bwie.utils.CommonUtils;
import com.bwie.view.ShowingPage;
import com.bwie.yunifang.R;

/**
 * Created by PC on 2016/11/28.
 */
public class MineFragment extends BaseFragment{
    @Override
    protected View createSuccessView() {
        View view = CommonUtils.inflate(R.layout.minefragment_main);
        return view;
    }

    @Override
    protected void onload() {
        new Thread()
        {
            @Override
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                MineFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
                super.run();

            }
        }.start();
    }
}
