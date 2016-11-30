package com.bwie.base;

import android.text.TextUtils;

import com.bwie.utils.CommonUtils;
import com.bwie.utils.MD5Encoder;
import com.bwie.view.ShowingPage;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by PC on 2016/11/29.
 */
public abstract class BaseData {
    public static final int NOTIME=0;
    public static final int NORMALTIME=3*24*60*60*1000;
    private final File fileDir;

    public BaseData() {
        File cacheDir= CommonUtils.getContext().getCacheDir();
        fileDir = new File(cacheDir,"yunifang");
        if(!fileDir.exists())
        {
            fileDir.mkdir();
        }
    }

    /**
     *
     * @param path  路径
     * @param args  参数
     * @param index 索引
     * @param validTime  有效时间
     */
    public void getData(String path,String args,int index, int validTime)
    {
        //如果有效时间为0
        if(validTime==0)
        {
            //从网络请求数据
            getDataFromNet(path,args,index,validTime);
        }
        else
        {
            //从本地获取数据
            String data=getDataFromLocal(path,args,index,validTime);
            //如果本地没有数据
            if(TextUtils.isEmpty(data))
            {
                //从网络获取数据
                getDataFromNet(path,args,index,validTime);
            }
            else
            {
                //返回数据
                setResultData(data);
            }
        }
    }

    public abstract void setResultData(String data);
    protected abstract void setResultError(ShowingPage.StateType stateLoadError);


    private String getDataFromLocal(String path, String args, int index, int validTime) {
        try {
            File file=new File(fileDir,MD5Encoder.encode(path)+index);
            BufferedReader  bufferedReader=new BufferedReader(new FileReader(file));
            String s = bufferedReader.readLine();
            long l = Long.parseLong(s);
            if(System.currentTimeMillis()<l)
            {
                StringBuilder builder=new StringBuilder();
                String lin=null;
                while((lin=bufferedReader.readLine())!=null)
                {
                    builder.append(lin);
                }
                bufferedReader.close();
                return builder.toString();
            }
            else
            {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    private void getDataFromNet(final String path, final String args, final int index, final int validTime) {
        RequestParams params=new RequestParams(path+"?"+args);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                setResultData(s);
                writeDataToLocal(path,args,index,validTime,s);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                setResultError(ShowingPage.StateType.STATE_LOAD_ERROR);
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
    private void writeDataToLocal(String path, String args, int index, int validTime,String data) {
        try {
            File file=new File(fileDir, MD5Encoder.encode(path)+index);
            BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(System.currentTimeMillis()+validTime+"\r\n");
            bufferedWriter.write(data);
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
