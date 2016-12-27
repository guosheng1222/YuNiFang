package com.bwie.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.bwie.bean.BeanAddress;
import com.bwie.bean.BeanCart;
import com.bwie.bean.BeanOrder;

import java.util.ArrayList;

/**
 * Created by PC on 2016/12/14.
 */
public class SqliteDao {

    private final MySqliteOpenHelper mysqlite;
    Context context;
    public SqliteDao(Context context) {
        mysqlite = new MySqliteOpenHelper(context);
        this.context=context;
    }
    //添加收货地址
    public void addAddress(BeanAddress address)
    {
        SQLiteDatabase db = mysqlite.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from address where name=? and phone = ? and city=? and detail=?", new String[]{address.getName(),address.getPhone(),address.getCity(),address.getDetail()});
        while (cursor.moveToNext())
        {
            Toast.makeText(context,"存在相同的收货地址",Toast.LENGTH_SHORT).show();
            return;
        }
        db.execSQL("insert into address(name,phone,city,detail) values(?,?,?,?)",new Object[]{address.getName(),address.getPhone(),address.getCity(),address.getDetail()});
        Toast.makeText(context,"成功添加新收货地址",Toast.LENGTH_SHORT).show();
        db.close();
    }
    //添加购物车方法
    public void add(String id,String name,String image,String price,int number,String juan,String di,String collection,int limit_num)
    {
        SQLiteDatabase db = mysqlite.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from cart where name=?", new String[]{name});
        while (cursor.moveToNext())
        {
            Toast.makeText(context,"购物车中已存在此商品",Toast.LENGTH_SHORT).show();
            return;
        }
        db.execSQL("insert into cart(id,name,image,price,number,juan,di,collection,limit_num) values(?,?,?,?,?,?,?,?,?)",new Object[]{id,name,image,price,number,juan,di,collection,limit_num});
        Toast.makeText(context,"成功添加至购物车",Toast.LENGTH_SHORT).show();
        db.close();
    }
    //添加进订单
    public void addOrder(String orderid,String name,String image,String price,int number,String condition,String address)
    {
        SQLiteDatabase db = mysqlite.getWritableDatabase();
        db.execSQL("insert into myorder(orderid,name,image,price,number,condition,address) values(?,?,?,?,?,?,?)",new Object[]{orderid,name,image,price,number,condition,address});
        db.close();
    }

    public void updateNum(int num,String name)
    {
        SQLiteDatabase db = mysqlite.getWritableDatabase();
        db.execSQL("update cart set number=? where name=?",new Object[]{num,name});
        db.close();
    }
    //更新订单状态
    public void updateCondition(String condition,String orderid)
    {
        SQLiteDatabase db = mysqlite.getWritableDatabase();
        db.execSQL("update myorder set condition = ? where orderid=?",new Object[]{condition,orderid});
        db.close();
    }
    //删除方法
    public void deleteCart(String name)
    {
        SQLiteDatabase db = mysqlite.getWritableDatabase();
        db.execSQL("delete from cart where name=?",new Object[]{name});
        db.close();
    }
    public void deleteAddress(BeanAddress address)
    {
        SQLiteDatabase db = mysqlite.getWritableDatabase();
        db.execSQL("delete from address where name=? and phone = ? and city=? and detail=?",new Object[]{address.getName(),address.getPhone(),address.getCity(),address.getDetail()});
        db.close();
    }
    //查询方法
    ArrayList<BeanCart> list=new ArrayList<>();
    public ArrayList<BeanCart> query()
    {
        SQLiteDatabase db = mysqlite.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from cart", null);
        while(cursor.moveToNext())
        {
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String image = cursor.getString(cursor.getColumnIndex("image"));
            String price = cursor.getString(cursor.getColumnIndex("price"));
            int number = cursor.getInt(cursor.getColumnIndex("number"));
            String juan = cursor.getString(cursor.getColumnIndex("juan"));
            String di = cursor.getString(cursor.getColumnIndex("di"));
            String collection = cursor.getString(cursor.getColumnIndex("collection"));
            int limit_num = cursor.getInt(cursor.getColumnIndex("limit_num"));
            BeanCart beanCart=new BeanCart(id,name,image,price,number,juan,di,collection,limit_num);
            list.add(beanCart);
        }
        return list;
    }
    //查询方法
    ArrayList<BeanAddress> listAddress=new ArrayList<>();
    public ArrayList<BeanAddress> queryAddress()
    {
        SQLiteDatabase db = mysqlite.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from address", null);
        while(cursor.moveToNext())
        {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            String city = cursor.getString(cursor.getColumnIndex("city"));
            String detail = cursor.getString(cursor.getColumnIndex("detail"));
            BeanAddress beanAddress=new BeanAddress(name,phone,city,detail);
            listAddress.add(beanAddress);
        }
        return listAddress;
    }
    ArrayList<String> listOrderId=new ArrayList<>();
    public ArrayList<String> queryOrderId(String condition)
    {
        SQLiteDatabase db = mysqlite.getReadableDatabase();
        Cursor cursor=null;
        if(condition==null)
        {
            cursor = db.rawQuery("select distinct orderid from myorder",null);
        }
        else
        {
            cursor = db.rawQuery("select distinct orderid from myorder where condition= ?", new String[]{condition});
        }
        while(cursor.moveToNext())
        {
            String orderid = cursor.getString(cursor.getColumnIndex("orderid"));
            listOrderId.add(orderid);
        }
        return listOrderId;
    }

    //查询方法
    ArrayList<BeanOrder> listorder=new ArrayList<>();
    public ArrayList<BeanOrder> queryOrder(String id)
    {
        SQLiteDatabase db = mysqlite.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from myorder where orderid=?", new String[]{id});
        while(cursor.moveToNext())
        {
            String orderid = cursor.getString(cursor.getColumnIndex("orderid"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String image = cursor.getString(cursor.getColumnIndex("image"));
            String price = cursor.getString(cursor.getColumnIndex("price"));
            int number = cursor.getInt(cursor.getColumnIndex("number"));
            String condition = cursor.getString(cursor.getColumnIndex("condition"));
            String address = cursor.getString(cursor.getColumnIndex("address"));
            BeanOrder beanOrder=new BeanOrder(orderid,name,image,price,number,condition,address);
            listorder.add(beanOrder);
        }
        return listorder;
    }

}
