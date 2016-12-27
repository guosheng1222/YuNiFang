package com.bwie.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by PC on 2016/11/29.
 */
public class BeanMain implements Serializable{
    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{

        private ActivityInfoBean activityInfo;
        private boolean creditRecived;

        private List<SubjectsBean> subjects;

        private List<BestSellersBean> bestSellers;
        /**
         * id : 693
         * image : http://image.hmeili.com/yunifang/images/goods/ad0/16101517113385857065462262.jpg
         * ad_type : 0
         * sort : 838
         * position : 0
         * enabled : 1
         * createtime : 2016.10.15 16:59:54
         * createuser : leiling
         * ad_type_dynamic : 1
         * ad_type_dynamic_data : http://m.yunifang.com/yunifang/web/client-h5/invite/invite.html?login_check=2
         * ad_type_dynamic_detail : http%3A%2F%2Fm.yunifang.com%2Fyunifang%2Fweb%2Fclient-h5%2Finvite%2Finvite.html%3Flogin_check%3D2
         * show_channel : 1
         * title : 邀请有礼安卓
         * channelType : 3
         */

        private List<Ad1Bean> ad1;
        /**
         * id : 359
         * image : http://image.hmeili.com/yunifang/images/goods/ad0/160823172997710201253418883.png
         * ad_type : 4
         * sort : 106
         * position : 5
         * enabled : 0
         * ad_type_dynamic : 1
         * ad_type_dynamic_data : http://m.yunifang.com/yunifang/web/client-h5/sign/sign.html?login_check=2
         * ad_type_dynamic_detail : http%3A%2F%2Fm.yunifang.com%2Fyunifang%2Fweb%2Fclient-h5%2Fsign%2Fsign.html%3Flogin_check%3D2
         * show_channel : 1,2
         * title : 每日签到
         */

        private List<Ad5Bean> ad5;
        /**
         * id : 121
         * goods_name : 第三代升级丨美白嫩肤蚕丝面膜7片
         * shop_price : 39.9
         * market_price : 99.0
         * goods_img : http://image.hmeili.com/yunifang/images/goods/121/goods_img/161031103891211632161121178.jpg
         * reservable : false
         * efficacy : 镇店之宝 美白爆款
         */

        private List<DefaultGoodsListBean> defaultGoodsList;

        public ActivityInfoBean getActivityInfo() {
            return activityInfo;
        }

        public void setActivityInfo(ActivityInfoBean activityInfo) {
            this.activityInfo = activityInfo;
        }

        public boolean isCreditRecived() {
            return creditRecived;
        }

        public void setCreditRecived(boolean creditRecived) {
            this.creditRecived = creditRecived;
        }

        public List<SubjectsBean> getSubjects() {
            return subjects;
        }

        public void setSubjects(List<SubjectsBean> subjects) {
            this.subjects = subjects;
        }

        public List<BestSellersBean> getBestSellers() {
            return bestSellers;
        }

        public void setBestSellers(List<BestSellersBean> bestSellers) {
            this.bestSellers = bestSellers;
        }

        public List<Ad1Bean> getAd1() {
            return ad1;
        }

        public void setAd1(List<Ad1Bean> ad1) {
            this.ad1 = ad1;
        }

        public List<Ad5Bean> getAd5() {
            return ad5;
        }

        public void setAd5(List<Ad5Bean> ad5) {
            this.ad5 = ad5;
        }

        public List<DefaultGoodsListBean> getDefaultGoodsList() {
            return defaultGoodsList;
        }

        public void setDefaultGoodsList(List<DefaultGoodsListBean> defaultGoodsList) {
            this.defaultGoodsList = defaultGoodsList;
        }

        public static class ActivityInfoBean implements Serializable{
            private String activityAreaDisplay;
            /**
             * id : 1
             * activityImg : http://image.hmeili.com/yunifang/images/goods/temp/161126204242819893797816568.jpg
             * activityType : 4
             * activityData : 163
             * activityDataDetail : 163
             * startSeconds : -297563
             * endSeconds : 199236
             * activityStatus : 2
             * activityAreaDisplay : 1
             * countDownEnable : 1
             * starttime : 2016.11.28 00:00:00
             * endtime : 2016.12.03 18:00:00
             * sort : 0
             */

            private List<ActivityInfoListBean> activityInfoList;

            public String getActivityAreaDisplay() {
                return activityAreaDisplay;
            }

            public void setActivityAreaDisplay(String activityAreaDisplay) {
                this.activityAreaDisplay = activityAreaDisplay;
            }

            public List<ActivityInfoListBean> getActivityInfoList() {
                return activityInfoList;
            }

            public void setActivityInfoList(List<ActivityInfoListBean> activityInfoList) {
                this.activityInfoList = activityInfoList;
            }

            public static class ActivityInfoListBean implements Serializable{
                private String id;
                private String activityImg;
                private String activityType;
                private String activityData;
                private String activityDataDetail;
                private String startSeconds;
                private String endSeconds;
                private String activityStatus;
                private String activityAreaDisplay;
                private String countDownEnable;
                private String starttime;
                private String endtime;
                private int sort;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getActivityImg() {
                    return activityImg;
                }

                public void setActivityImg(String activityImg) {
                    this.activityImg = activityImg;
                }

                public String getActivityType() {
                    return activityType;
                }

                public void setActivityType(String activityType) {
                    this.activityType = activityType;
                }

                public String getActivityData() {
                    return activityData;
                }

                public void setActivityData(String activityData) {
                    this.activityData = activityData;
                }

                public String getActivityDataDetail() {
                    return activityDataDetail;
                }

                public void setActivityDataDetail(String activityDataDetail) {
                    this.activityDataDetail = activityDataDetail;
                }

                public String getStartSeconds() {
                    return startSeconds;
                }

                public void setStartSeconds(String startSeconds) {
                    this.startSeconds = startSeconds;
                }

                public String getEndSeconds() {
                    return endSeconds;
                }

                public void setEndSeconds(String endSeconds) {
                    this.endSeconds = endSeconds;
                }

                public String getActivityStatus() {
                    return activityStatus;
                }

                public void setActivityStatus(String activityStatus) {
                    this.activityStatus = activityStatus;
                }

                public String getActivityAreaDisplay() {
                    return activityAreaDisplay;
                }

                public void setActivityAreaDisplay(String activityAreaDisplay) {
                    this.activityAreaDisplay = activityAreaDisplay;
                }

                public String getCountDownEnable() {
                    return countDownEnable;
                }

                public void setCountDownEnable(String countDownEnable) {
                    this.countDownEnable = countDownEnable;
                }

                public String getStarttime() {
                    return starttime;
                }

                public void setStarttime(String starttime) {
                    this.starttime = starttime;
                }

                public String getEndtime() {
                    return endtime;
                }

                public void setEndtime(String endtime) {
                    this.endtime = endtime;
                }

                public int getSort() {
                    return sort;
                }

                public void setSort(int sort) {
                    this.sort = sort;
                }
            }
        }

        public static class SubjectsBean implements Serializable{
            private String id;
            private String title;
            private String detail;
            private String image;
            private String start_time;
            private String end_time;
            private int show_number;
            private String state;
            private int sort;
            /**
             * id : 1254
             * goods_name : 新品推荐|黑珍珠盈润亮采黑面膜7片
             * shop_price : 49.9
             * market_price : 99.0
             * goods_img : http://image.hmeili.com/yunifang/images/goods/1254/goods_img/16112517549399376533709198.jpg
             * reservable : false
             * efficacy : 清洁补水 提亮肤色
             */

            private List<GoodsListBean> goodsList;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public int getShow_number() {
                return show_number;
            }

            public void setShow_number(int show_number) {
                this.show_number = show_number;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public List<GoodsListBean> getGoodsList() {
                return goodsList;
            }

            public void setGoodsList(List<GoodsListBean> goodsList) {
                this.goodsList = goodsList;
            }

            public static class GoodsListBean implements Serializable{
                private String id;
                private String goods_name;
                private double shop_price;
                private double market_price;
                private String goods_img;
                private boolean reservable;
                private String efficacy;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getGoods_name() {
                    return goods_name;
                }

                public void setGoods_name(String goods_name) {
                    this.goods_name = goods_name;
                }

                public double getShop_price() {
                    return shop_price;
                }

                public void setShop_price(double shop_price) {
                    this.shop_price = shop_price;
                }

                public double getMarket_price() {
                    return market_price;
                }

                public void setMarket_price(double market_price) {
                    this.market_price = market_price;
                }

                public String getGoods_img() {
                    return goods_img;
                }

                public void setGoods_img(String goods_img) {
                    this.goods_img = goods_img;
                }

                public boolean isReservable() {
                    return reservable;
                }

                public void setReservable(boolean reservable) {
                    this.reservable = reservable;
                }

                public String getEfficacy() {
                    return efficacy;
                }

                public void setEfficacy(String efficacy) {
                    this.efficacy = efficacy;
                }
            }
        }

        public static class BestSellersBean implements Serializable {
            private String id;
            private String name;
            private String descript;
            private String state;
            private int show_number;
            private String begin_date;
            private String end_date;
            /**
             * id : 684
             * goods_name : 花粹水润面膜套装10片
             * shop_price : 29.9
             * market_price : 139.0
             * goods_img : http://image.hmeili.com/yunifang/images/goods/684/goods_img/160819095063413908186337181.jpg
             * reservable : false
             * efficacy : 水润充盈 鲜嫩少女肌
             */

            private List<GoodsListBean> goodsList;

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

            public String getDescript() {
                return descript;
            }

            public void setDescript(String descript) {
                this.descript = descript;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public int getShow_number() {
                return show_number;
            }

            public void setShow_number(int show_number) {
                this.show_number = show_number;
            }

            public String getBegin_date() {
                return begin_date;
            }

            public void setBegin_date(String begin_date) {
                this.begin_date = begin_date;
            }

            public String getEnd_date() {
                return end_date;
            }

            public void setEnd_date(String end_date) {
                this.end_date = end_date;
            }

            public List<GoodsListBean> getGoodsList() {
                return goodsList;
            }

            public void setGoodsList(List<GoodsListBean> goodsList) {
                this.goodsList = goodsList;
            }

            public static class GoodsListBean implements Serializable{
                private String id;
                private String goods_name;
                private double shop_price;
                private double market_price;
                private String goods_img;
                private boolean reservable;
                private String efficacy;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getGoods_name() {
                    return goods_name;
                }

                public void setGoods_name(String goods_name) {
                    this.goods_name = goods_name;
                }

                public double getShop_price() {
                    return shop_price;
                }

                public void setShop_price(double shop_price) {
                    this.shop_price = shop_price;
                }

                public double getMarket_price() {
                    return market_price;
                }

                public void setMarket_price(double market_price) {
                    this.market_price = market_price;
                }

                public String getGoods_img() {
                    return goods_img;
                }

                public void setGoods_img(String goods_img) {
                    this.goods_img = goods_img;
                }

                public boolean isReservable() {
                    return reservable;
                }

                public void setReservable(boolean reservable) {
                    this.reservable = reservable;
                }

                public String getEfficacy() {
                    return efficacy;
                }

                public void setEfficacy(String efficacy) {
                    this.efficacy = efficacy;
                }
            }
        }

        public static class Ad1Bean implements Serializable{
            private String id;
            private String image;
            private int ad_type;
            private int sort;
            private int position;
            private int enabled;
            private String createtime;
            private String createuser;
            private String ad_type_dynamic;
            private String ad_type_dynamic_data;
            private String ad_type_dynamic_detail;
            private String show_channel;
            private String title;
            private String channelType;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public int getAd_type() {
                return ad_type;
            }

            public void setAd_type(int ad_type) {
                this.ad_type = ad_type;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public int getPosition() {
                return position;
            }

            public void setPosition(int position) {
                this.position = position;
            }

            public int getEnabled() {
                return enabled;
            }

            public void setEnabled(int enabled) {
                this.enabled = enabled;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public String getCreateuser() {
                return createuser;
            }

            public void setCreateuser(String createuser) {
                this.createuser = createuser;
            }

            public String getAd_type_dynamic() {
                return ad_type_dynamic;
            }

            public void setAd_type_dynamic(String ad_type_dynamic) {
                this.ad_type_dynamic = ad_type_dynamic;
            }

            public String getAd_type_dynamic_data() {
                return ad_type_dynamic_data;
            }

            public void setAd_type_dynamic_data(String ad_type_dynamic_data) {
                this.ad_type_dynamic_data = ad_type_dynamic_data;
            }

            public String getAd_type_dynamic_detail() {
                return ad_type_dynamic_detail;
            }

            public void setAd_type_dynamic_detail(String ad_type_dynamic_detail) {
                this.ad_type_dynamic_detail = ad_type_dynamic_detail;
            }

            public String getShow_channel() {
                return show_channel;
            }

            public void setShow_channel(String show_channel) {
                this.show_channel = show_channel;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getChannelType() {
                return channelType;
            }

            public void setChannelType(String channelType) {
                this.channelType = channelType;
            }
        }

        public static class Ad5Bean implements Serializable{
            private String id;
            private String image;
            private int ad_type;
            private int sort;
            private int position;
            private int enabled;
            private String ad_type_dynamic;
            private String ad_type_dynamic_data;
            private String ad_type_dynamic_detail;
            private String show_channel;
            private String title;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public int getAd_type() {
                return ad_type;
            }

            public void setAd_type(int ad_type) {
                this.ad_type = ad_type;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public int getPosition() {
                return position;
            }

            public void setPosition(int position) {
                this.position = position;
            }

            public int getEnabled() {
                return enabled;
            }

            public void setEnabled(int enabled) {
                this.enabled = enabled;
            }

            public String getAd_type_dynamic() {
                return ad_type_dynamic;
            }

            public void setAd_type_dynamic(String ad_type_dynamic) {
                this.ad_type_dynamic = ad_type_dynamic;
            }

            public String getAd_type_dynamic_data() {
                return ad_type_dynamic_data;
            }

            public void setAd_type_dynamic_data(String ad_type_dynamic_data) {
                this.ad_type_dynamic_data = ad_type_dynamic_data;
            }

            public String getAd_type_dynamic_detail() {
                return ad_type_dynamic_detail;
            }

            public void setAd_type_dynamic_detail(String ad_type_dynamic_detail) {
                this.ad_type_dynamic_detail = ad_type_dynamic_detail;
            }

            public String getShow_channel() {
                return show_channel;
            }

            public void setShow_channel(String show_channel) {
                this.show_channel = show_channel;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }

        public static class DefaultGoodsListBean implements Serializable{
            private String id;
            private String goods_name;
            private double shop_price;
            private double market_price;
            private String goods_img;
            private boolean reservable;
            private String efficacy;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public double getShop_price() {
                return shop_price;
            }

            public void setShop_price(double shop_price) {
                this.shop_price = shop_price;
            }

            public double getMarket_price() {
                return market_price;
            }

            public void setMarket_price(double market_price) {
                this.market_price = market_price;
            }

            public String getGoods_img() {
                return goods_img;
            }

            public void setGoods_img(String goods_img) {
                this.goods_img = goods_img;
            }

            public boolean isReservable() {
                return reservable;
            }

            public void setReservable(boolean reservable) {
                this.reservable = reservable;
            }

            public String getEfficacy() {
                return efficacy;
            }

            public void setEfficacy(String efficacy) {
                this.efficacy = efficacy;
            }
        }
    }
}
