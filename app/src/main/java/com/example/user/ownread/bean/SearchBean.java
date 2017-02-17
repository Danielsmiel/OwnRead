package com.example.user.ownread.bean;

import java.util.ArrayList;

/**
 * Created by user on 2016/8/4.
 */
public class SearchBean {
    public String status;
    public Datas datas;
    public String msg;
    public int code;

    public class Datas {
       public ArrayList<Lists> lists;
    }

    public class Lists {
        public String id;
        public String uid;
        public String title;
        public String excerpt;
        public String lead;
        public String thumbnail;
        public String modle;
    }
}
