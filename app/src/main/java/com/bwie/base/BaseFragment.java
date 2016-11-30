package com.bwie.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.view.ShowingPage;

/**
 * Created by PC on 2016/11/28.
 */
public abstract class BaseFragment extends Fragment {

    private ShowingPage showingPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        showingPage = new ShowingPage(getActivity()) {
            @Override
            protected void onload() {

            }
            @Override
            public View createSuccessView() {
                return BaseFragment.this.createSuccessView();
            }
        };
        BaseFragment.this.onload();
        return showingPage;
    }

    protected abstract View createSuccessView();

    protected abstract void onload();

    public void showCurrentPage(ShowingPage.StateType statetype)
    {
        if(showingPage!=null)
        {
            showingPage.showCurrentPage(statetype);
        }
    }
}
