package com.example.user.ownread.utils;

import com.example.user.ownread.bean.ContentItemBean;
import com.example.user.ownread.bean.SplashImgBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by user on 2016/7/26.
 */
public class ParseJson {
    public static SplashImgBean parseSplashJson(String splashJsonStr) {
        try {

            SplashImgBean sIbean = new SplashImgBean();
            JSONObject JSONObject = new JSONObject(splashJsonStr);
            sIbean.status = JSONObject.getString("status");
            if (sIbean.status.equals("ok")) {
                sIbean.count = JSONObject.getInt("count");
                sIbean.imageList = new ArrayList<>();
                JSONArray imagesArray = JSONObject.getJSONArray("images");
                for (int i = 0; i < imagesArray.length(); i++) {
                    String imagePath = imagesArray.getString(i);
                    sIbean.imageList.add(imagePath);
                }
                return sIbean;
            }
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ContentItemBean parseConentJson(String contentJsonStr) {
        ContentItemBean ceIbean = new ContentItemBean();
        try {
            JSONObject jsonObject = new JSONObject(contentJsonStr);
            ceIbean.status = jsonObject.getString("status");
            if (ceIbean.status.equals("ok")) {
                ceIbean.dataList = new ArrayList<>();
                JSONArray datasArray = jsonObject.getJSONArray("datas");
                for (int i = 0; i < datasArray.length(); i++) {
                    ContentItemBean.Datas datas = ceIbean.new Datas();
                    JSONObject dataObject = datasArray.getJSONObject(i);
                    datas.id = dataObject.getString("id");
                    datas.uid = dataObject.getString("uid");
                    datas.title = dataObject.getString("title");
                    datas.excerpt = dataObject.getString("excerpt");
                    datas.category = dataObject.getString("category");
                    datas.lead = dataObject.getString("lead");
                    datas.modle = dataObject.getString("model");
                    datas.thumbnail = dataObject.getString("thumbnail");
                    datas.createTime = dataObject.getString("create_time");
                    datas.updateTime = dataObject.getString("update_time");
                    datas.video = dataObject.getString("video");
                    datas.fm = dataObject.getString("fm");
                    datas.view = dataObject.getString("view");
                    datas.comment = dataObject.getString("comment");
                    datas.good = dataObject.getString("good");
                    datas.author = dataObject.getString("author");
                    ceIbean.dataList.add(datas);
                }
                return ceIbean;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
