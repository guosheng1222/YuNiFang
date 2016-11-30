package com.bwie.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

import com.bwie.utils.ImageLoaderUtils;

import org.xutils.x;

/**
 * Created by PC on 2016/11/28.
 */
public class MyApplication extends Application {

    private static Context context;
    private static Handler handler;
    private static int mainThreadId;

    @Override
    public void onCreate() {
        super.onCreate();
        //获取当前应用上下文
        context = getApplicationContext();
        handler = new Handler();
        //获取主线程的线程号
        mainThreadId = Process.myTid();
        //imageloader初始化
        ImageLoaderUtils.initConfiguration(this);
        //xutils3初始化
        x.Ext.init(this);
        x.Ext.setDebug(true);
    }
    public static Handler getHandler() {
        return handler;
    }

    public static int getMainThreadId() {
        return mainThreadId;
    }
    public static Thread getMainThread() {
        return Thread.currentThread();
    }

    public static Context getContext() {
        return context;
    }
}
