package com.example.user.ownread.bean;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by user on 2016/7/28.
 */
public class ContentItemBean  {
    public String status;
    public ArrayList<Datas> dataList;

    public class Datas implements Parcelable{
        public String id;
        public String uid;
        /**
         * 标题
         **/
        public String title;
        public String name;
        /**
         * 内容
         **/
        public String excerpt;
        public String lead;
        /**
         * 什么类型的数据
         **/
        public String modle;
        /**
         * 屏幕上的图片
         **/
        public String thumbnail;
        public String createTime;
        public String updateTime;
        /**
         * 视频地址
         */
        public String video;
        /**
         * 音乐地址
         **/
        public String fm;
        public String view;
        public String comment;
        public String good;
        /**
         * 作者
         **/
        public String author;
        /**
         * 查看
         **/
        public String category;
        public Bitmap thumbNailBitmap;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {

        }
    }
}
