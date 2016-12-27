package com.bwie.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by PC on 2016/12/14.
 */
public class MySqliteOpenHelper extends SQLiteOpenHelper {
    public MySqliteOpenHelper(Context context) {
        super(context,"bwie", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表
        db.execSQL("create table cart (_id integer primary key autoincrement , id varchar(20),name varchar(30),image varchar(100),price varchar(20),number integer(20),juan varchar(20),di varchar(20),collection varchar(20),limit_num integer(20))");
        db.execSQL("create table address(_id integer primary key autoincrement ,name varchar(20), phone varchar(20),city varchar(20),detail varchar(40))" );
        db.execSQL("create table myorder(_id integer primary key autoincrement ,orderid varchar(20),name varchar(20), image varchar(100),price varchar(20),number integer(20),condition varchar(20),address varchar(20))" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
