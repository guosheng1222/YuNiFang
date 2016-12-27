package com.bwie.bean;

import java.io.Serializable;

/**
 * Created by PC on 2016/12/14.
 */
public class BeanCart implements Serializable{
    private String id;
    private String name;
    private String image;
    private String price;
    private int number;
    private String juan;
    private String di;
    private String collection;
    private int limit_num;
    private boolean flag;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public BeanCart() {
    }

    public BeanCart(String id, String name, String image, String price, int number, String juan, String di, String collection,int limit_num) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.number = number;
        this.juan = juan;
        this.di = di;
        this.collection = collection;
        this.limit_num=limit_num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getJuan() {
        return juan;
    }

    public void setJuan(String juan) {
        this.juan = juan;
    }

    public String getDi() {
        return di;
    }

    public void setDi(String di) {
        this.di = di;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public int getLimit_num() {
        return limit_num;
    }

    public void setLimit_num(int limit_num) {
        this.limit_num = limit_num;
    }
}
