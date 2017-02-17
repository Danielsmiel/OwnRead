package com.example.user.ownread.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ant.liao.GifView;
import com.example.user.ownread.R;
import com.example.user.ownread.bean.ContentItemBean;
import com.example.user.ownread.utils.FormatUtils;
import com.example.user.ownread.utils.ImageLoaderUtils;
import com.example.user.ownread.utils.LogUtils;
import com.example.user.ownread.utils.NetPathValues;
import com.example.user.ownread.utils.ParseJson;
import com.example.user.ownread.view.MyListView;

import java.util.ArrayList;

public class ModelActivity extends Activity {
    private static final String TAG = "ModelActivity";
    Intent intent;
    private int type;
    private TextView topTv;
    private MyListView mDataLv;
    private GifView gifView;
    private int page = 1;
    private String createTime = "0";
    private RequestQueue mRequestQueue;
    private ArrayList<ContentItemBean.Datas> dataList;

    ;
    private ModelAdapter adapter;
    Typeface typeface;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            adapter.setList(dataList);
            mDataLv.onRefreshComplete();
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model);
        intent = getIntent();
        type = intent.getIntExtra("type", 0);
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        LogUtils.logV(TAG, type + "");
        getPath();
        initView();
    }

    public String getPath() {
        String path = NetPathValues.CONTENT_PATH_ARRAY[0] + page
                + NetPathValues.CONTENT_PATH_ARRAY[1] + type
                + NetPathValues.CONTENT_PATH_ARRAY[2] + createTime
                + NetPathValues.CONTENT_PATH_ARRAY[3] + FormatUtils.getUnixCurrentTime();
        return path;
    }

    private void initView() {
        topTv = (TextView) findViewById(R.id.model_top_tv);
        typeface = Typeface.createFromAsset(this.getAssets(), "fonts/PMingLiU.ttf");
        topTv.setTypeface(typeface);
        switch (type) {
            case 1:
                topTv.setText("文   字");
                break;
            case 2:
                topTv.setText("影   像");
                break;
            case 3:
                topTv.setText("声   音");
        }
        mDataLv = (MyListView) findViewById(R.id.model_lv);
        mDataLv.setonRefreshListener(refreshListener);
        refreshListener.onRefresh();
        mRequestQueue.add(getNetData(getPath()));
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        adapter = new ModelAdapter(dataList);
        mDataLv.setAdapter(adapter);
    }

    /**
     * 数据请求
     */
    private StringRequest getNetData(String path) {
        StringRequest stringRequest = new StringRequest(getPath(), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtils.logV(TAG, s);
                ContentItemBean cIBean = ParseJson.parseConentJson(s);
                dataList = cIBean.dataList;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessageDelayed(0x999, 1000);
                    }
                }).start();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtils.logV(TAG, volleyError.getMessage());
            }
        });
        return stringRequest;
    }


    /**
     * 下拉刷新
     */
    MyListView.OnRefreshListener refreshListener = new MyListView.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mRequestQueue.add(getNetData(getPath()));
        }
    };

    public void back(View view) {
        finish();
    }

    /**
     * 适配器
     */
    class ModelAdapter extends BaseAdapter {

        private ArrayList<ContentItemBean.Datas> dataList;

        public ModelAdapter(ArrayList<ContentItemBean.Datas> dataList) {
            this.dataList = dataList;
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int i) {
            return dataList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(ModelActivity.this).inflate(R.layout.item_model_lv, null);
            ViewHoler holer = null;
            if (holer == null) {
                holer = new ViewHoler();
                holer.thumbIv = (ImageView) view.findViewById(R.id.item_model_thumbnail);
                holer.titleTv = (TextView) view.findViewById(R.id.item_model_title);
                holer.titleTv.setTypeface(typeface);
                holer.authorTv = (TextView) view.findViewById(R.id.item_model_author);
                holer.authorTv.setTypeface(typeface);
                view.setTag(holer);
            } else {
                holer = (ViewHoler) view.getTag();
            }
            ImageLoaderUtils.getImageByLoader(dataList.get(i).thumbnail, holer.thumbIv);
            holer.titleTv.setText(dataList.get(i).title);
            holer.authorTv.setText(dataList.get(i).author);
            return view;
        }

        public void setList(ArrayList<ContentItemBean.Datas> dataList) {
            this.dataList = dataList;
            notifyDataSetChanged();
        }

        class ViewHoler {
            private ImageView thumbIv;
            private TextView titleTv, authorTv;
        }
    }
}
