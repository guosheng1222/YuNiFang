package com.bwie.fragment;

import android.view.View;
import android.widget.TextView;

import com.bwie.base.BaseFragment;
import com.bwie.view.ShowingPage;

/**
 * Created by PC on 2016/11/28.
 */
public class CategoryFragment extends BaseFragment{
    @Override
    protected View createSuccessView() {
        TextView text=new TextView(getActivity());
        text.setText("哈哈哈哈");
        return text;
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
                CategoryFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
                super.run();

            }
        }.start();

    }
}
