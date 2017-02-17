package com.example.user.ownread.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.ownread.R;
import com.example.user.ownread.bean.ContentItemBean;
import com.example.user.ownread.bean.SplashImgBean;
import com.example.user.ownread.utils.FormatUtils;
import com.example.user.ownread.utils.ImageLoaderUtils;
import com.example.user.ownread.utils.LogUtils;
import com.example.user.ownread.utils.NetPathValues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SplashActivity extends Activity {

    private String TAG = getClass().getSimpleName();
    RequestQueue mQueue;
    private ImageView mIv;
    private int page = 1;
    private int model = 0;
    private String createTime = "0";
    private String mArticlPath = NetPathValues.CONTENT_PATH_ARRAY[0] + page
            + NetPathValues.CONTENT_PATH_ARRAY[1] + model
            + NetPathValues.CONTENT_PATH_ARRAY[2] + createTime
            + NetPathValues.CONTENT_PATH_ARRAY[3] + FormatUtils.getUnixCurrentTime();
    private ArrayList<ContentItemBean.Datas> dataList;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what == 0x001) {
                dataList = (ArrayList<ContentItemBean.Datas>) message.obj;
                Bundle mBundle = new Bundle();
                mBundle.putParcelableArrayList("articList", dataList);
                Intent mIntent = new Intent();
                mIntent.putExtras(mBundle);
                mIntent.setClass(SplashActivity.this, MainActivity.class);
                startActivity(mIntent);
                finish();
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mQueue = Volley.newRequestQueue(getApplicationContext());
        LogUtils.logV(TAG, "01");
        init();
        getNetData();
    }

    private void init() {
        mIv = (ImageView) findViewById(R.id.splash_iv);
    }

    public void getNetData() {
        mQueue.add(getRequest(NetPathValues.SPLASH_IMG_LISTPATH + FormatUtils.getUnixCurrentTime(), 0));
        mQueue.add(getRequest(mArticlPath, 1));
    }

    private StringRequest getRequest(String path, final int type) {
        StringRequest stringRequest = new StringRequest(path
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                switch (type) {
                    case 0:
                        setSplash(s);
                        break;
                    case 1:
                        setArtic(s);
                        break;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtils.logV(TAG, volleyError.getMessage());
            }
        });
        return stringRequest;
    }

    private void setSplash(String s) {
        SplashImgBean sIbean = parseSplashJson(s);
        LogUtils.logV(TAG, sIbean.imageList.get(0));
        //String splashBitmpPath = sIbean.imageList.get(getRandom());
        //ImageLoaderUtils.getImageByLoader(splashBitmpPath, mIv);
    }

    private void setArtic(final String s) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ContentItemBean ceIbean = parseConentJson(s);
                dataList = ceIbean.dataList;
                Message message = mHandler.obtainMessage();
                message.obj = dataList;
                message.what = 0x001;
                mHandler.sendMessageDelayed(message, 1000);
            }
        }).start();
    }

    private int getRandom() {
        int randomNum = (int) ((Math.random()) * 7 + 1);
        return randomNum;
    }

    private SplashImgBean parseSplashJson(String splashJsonStr) {
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

    private ContentItemBean parseConentJson(String contentJsonStr) {
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
