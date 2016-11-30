package com.bwie.fragment;

import android.view.View;
import android.widget.TextView;

import com.bwie.base.BaseData;
import com.bwie.base.BaseFragment;
import com.bwie.utils.CommonUtils;
import com.bwie.utils.URLUtils;
import com.bwie.view.ShowingPage;
import com.bwie.yunifang.R;

/**
 * Created by PC on 2016/11/28.
 */
public class HomeFragment extends BaseFragment {
    private String s;
    @Override
    protected View createSuccessView() {
        View view = CommonUtils.inflate(R.layout.homefragment_main);
        TextView text = (TextView) view.findViewById(R.id.data);
        text.setText(s);
        return view;

    }
    @Override
    protected void onload() {
       new BaseData() {
           @Override
           public void setResultData(String data) {
               HomeFragment.this.s=data;
               HomeFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
           }
           @Override
           protected void setResultError(ShowingPage.StateType stateLoadError) {
               HomeFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_ERROR);
           }
       }.getData(URLUtils.homeUrl,URLUtils.homeArgs,0,BaseData.NORMALTIME);
    }
}
