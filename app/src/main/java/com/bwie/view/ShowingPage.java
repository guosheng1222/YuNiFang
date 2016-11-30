package com.bwie.view;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.bwie.utils.CommonUtils;
import com.bwie.yunifang.R;


/**
 * Created by zhiyuan on 16/9/28.
 */
public abstract class ShowingPage extends FrameLayout implements View.OnClickListener {
    public static final int STATE_UNLOAD = 1;
    public static final int STATE_LOAD = 2;
    public static final int STATE_LOAD_ERROR = 3;
    public static final int STATE_LOAD_EMPTY = 4;
    public static final int STATE_LOAD_SUCCESS = 5;

    //定义一个初始状态--当前是未加载状态
    public int currentState = STATE_UNLOAD;
    private View showingpage_unload;
    private View showingpage_load_error;
    private View showingpage_load_empty;
    private View showingpage_loading;
    private final LayoutParams layoutParams;

    private View showingPage_success;
    /**
     * 重新加载的按钮
     */
    private Button bt_reload;

    //从代码中调用的构造方法
    public ShowingPage(Context context) {
        super(context);

        layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        //初始化
        if (showingpage_load_empty == null) {
            showingpage_load_empty = CommonUtils.inflate(R.layout.showingpage_load_empty);
            this.addView(showingpage_load_empty, layoutParams);

        }
        //初始化正在加载-并添加到布局中
        if (showingpage_loading == null) {
            showingpage_loading = CommonUtils.inflate(R.layout.showingpage_loading);
            this.addView(showingpage_loading, layoutParams);
        }
        //初始化未加载状态，并添加到布局中
        if (showingpage_unload == null) {
            showingpage_unload = CommonUtils.inflate(R.layout.showingpage_unload);
            this.addView(showingpage_unload, layoutParams);
        }
        //初始化加载出错，并添加到布局中
        if (showingpage_load_error == null) {
            showingpage_load_error = CommonUtils.inflate(R.layout.showingpage_load_error);
            bt_reload = (Button) showingpage_load_error.findViewById(R.id.bt_reload);
            bt_reload.setOnClickListener(this);
            this.addView(showingpage_load_error, layoutParams);
        }
        //添加展示
        showPage();
        //数据的加载
        onload();

    }


    //提供一个请求结束之后，设置当前状态，展示界面的方法  6
    public void showCurrentPage(StateType stateType) {
        //获取枚举中的数字,并赋值给当前类型
        this.currentState = stateType.getCurrentState();
        //展示一下
        showPage();
    }


    protected abstract void onload();
    //展示界面
    public void showPage() {
        CommonUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                showUIPage();
            }
        });
    }
    /**
     * 在主线程中展示界面
     */
    private void showUIPage() {
        //根据当前状态进行展示
        //未加载
        if (showingpage_unload != null) {
            showingpage_unload.setVisibility(currentState == STATE_UNLOAD ? View.VISIBLE : View.GONE);
        }
        if (showingpage_loading != null) {
            showingpage_loading.setVisibility(currentState == STATE_LOAD ? View.VISIBLE : View.GONE);
        }
        if (showingpage_load_empty != null) {
            showingpage_load_empty.setVisibility(currentState == STATE_LOAD_EMPTY ? View.VISIBLE : View.GONE);
        }
        if (showingpage_load_error != null) {
            showingpage_load_error.setVisibility(currentState == STATE_LOAD_ERROR ? View.VISIBLE : View.GONE);
        }
        //成功的状态--没有成功界面
        if (showingPage_success == null && currentState == STATE_LOAD_SUCCESS) {
            //加载成功的界面--添加到当前的showingPage
            showingPage_success = createSuccessView();
            //添加到showingPage
            this.addView(showingPage_success, layoutParams);
        }
        //判断是否是成功状态
        if (showingPage_success != null) {
            showingPage_success.setVisibility(currentState == STATE_LOAD_SUCCESS ? View.VISIBLE : View.GONE);
        }
    }
    /**
     * 创建请求成功的界面
     *
     * @return
     */
    public abstract View createSuccessView();

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_reload:
                resetView();
                break;
        }
    }
    /**
     * 重置
     */
    private void resetView() {
        //重新加载
        //1--重置状态
        if (currentState != STATE_UNLOAD) {
            currentState = STATE_UNLOAD;
        }
        //2展示界面效果
        showPage();
        //3重新加载
        onload();
    }

    /**
     * 枚举类
     */
    public enum StateType{
        //请求类型
        STATE_LOAD_ERROR(3),STATE_LOAD_EMPTY(4),STATE_LOAD_SUCCESS(5);
        private final int currentState;

        StateType(int currentState){
            this.currentState=currentState;
        }
        public int getCurrentState(){
            return currentState;
        }
    }
}
