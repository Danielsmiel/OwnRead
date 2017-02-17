package com.example.user.ownread.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.ownread.R;
import com.example.user.ownread.bean.SearchBean;
import com.example.user.ownread.utils.FormatUtils;
import com.example.user.ownread.utils.ImageLoaderUtils;
import com.example.user.ownread.utils.LogUtils;
import com.example.user.ownread.utils.NetPathValues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends Activity {

    private static final String TAG = "SearchActivity";
    private TextView mTopTv;
    private TextView mResultTv;
    private TextView mHotTv;
    private EditText mSearchEt;
    private Button mClearBtn;
    private ListView mHotLv;
    private ListView mContentLv;
    private MyContentAdapter mContentAdapter;
    private MyHotAdapter mHotAdapter;
    private RequestQueue mRequestQueue;
    private Typeface mTypeface;
    private String mContentStr;
    private ArrayList<String> mHotList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mRequestQueue = Volley.newRequestQueue(this);
        mRequestQueue.add(getRequest(NetPathValues.HOT_SEARCH_PATH + FormatUtils.getUnixCurrentTime()));
        initView();
    }

    private void initView() {
        mTypeface = Typeface.createFromAsset(getAssets(), "fonts/PMingLiU.ttf");
        mTopTv = (TextView) findViewById(R.id.search_top_tv);
        mHotTv = (TextView) findViewById(R.id.search_hot_tv);
        mResultTv = (TextView) findViewById(R.id.search_result_tv);
        mSearchEt = (EditText) findViewById(R.id.search_et);
        mClearBtn = (Button) findViewById(R.id.search_clear_btn);
        mHotLv = (ListView) findViewById(R.id.search_hot_lv);
        mContentLv = (ListView) findViewById(R.id.search_content_lv);
        mTopTv.setTypeface(mTypeface);
        mSearchEt.setTypeface(mTypeface);
        mHotTv.setTypeface(mTypeface);
        mSearchEt.addTextChangedListener(mWatcher);
        mSearchEt.setOnKeyListener(mOnKeyListener);
        mClearBtn.setOnClickListener(mClearListener);
    }

    public void back(View view) {
        finish();
    }

    /**
     * 设置热搜的ListView
     *
     * @param mHotList
     */
    private void setHotLvData(ArrayList<String> mHotList) {
        mHotAdapter = new MyHotAdapter(mHotList, this);
        mHotLv.setAdapter(mHotAdapter);
    }

    private void setData(SearchBean searchBean) {
        if (searchBean != null) {
            if (searchBean.status.equals("ok")) {
                if (TextUtils.isEmpty(searchBean.msg)) {
                    mResultTv.setVisibility(View.GONE);
                    mContentLv.setVisibility(View.VISIBLE);
                    LogUtils.logV(TAG, searchBean.datas.lists.get(0).id);
                    mContentAdapter = new MyContentAdapter(searchBean.datas.lists);
                    mContentLv.setAdapter(mContentAdapter);
                } else {
                    mResultTv.setVisibility(View.VISIBLE);
                    mResultTv.setText("没有搜索到结果");
                }
            } else {
                mResultTv.setVisibility(View.VISIBLE);
                mResultTv.setText("请求错误");
            }
        } else {
            mResultTv.setVisibility(View.VISIBLE);
            mResultTv.setText("未知错误");
        }

    }

    View.OnClickListener mClearListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mClearBtn.setVisibility(View.GONE);
            mContentLv.setVisibility(View.GONE);
            mResultTv.setVisibility(View.GONE);
            mSearchEt.setText("");
        }
    };

    /**
     * 键盘改变点击事件
     */
    View.OnKeyListener mOnKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            if (i == KeyEvent.KEYCODE_ENTER) {
                //隐藏键盘
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                mRequestQueue.add(postRequest(NetPathValues.SEARCH_CONTENT_PATH, mSearchEt.getText().toString()));
                return true;
            }
            return false;
        }
    };

    /**
     * EditView监听事件
     */
    TextWatcher mWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (mSearchEt.getText().equals(""))
                mClearBtn.setVisibility(View.GONE);
            else
                mClearBtn.setVisibility(View.VISIBLE);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    /**
     * get网络请求
     *
     * @param path
     * @return
     */
    private StringRequest getRequest(String path) {
        StringRequest stringRequest = new StringRequest(path, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (!TextUtils.isEmpty(s)) {
                    mHotList = parseHotSearshJson(s);
                    setHotLvData(mHotList);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        return stringRequest;
    }

    /**
     * post网络请求
     *
     * @param path
     * @return
     */
    private StringRequest postRequest(String path, final String value) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, path, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                SearchBean searchBean = parseSearchJson(s);
                setData(searchBean);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("key", value);
                return map;
            }
        };
        return stringRequest;
    }

    /**
     * 解析热搜的JSON
     *
     * @param s
     * @return
     */
    private ArrayList<String> parseHotSearshJson(String s) {
        ArrayList<String> mHotList = new ArrayList<>();
        try {

            JSONObject jsonObject = new JSONObject(s);
            String status = jsonObject.getString("status");

            if (status.equals("ok")) {
                JSONArray dataArray = jsonObject.getJSONArray("datas");
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject dataObject = dataArray.getJSONObject(i);
                    String title = dataObject.getString("name");
                    mHotList.add(title);
                }
                return mHotList;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private SearchBean parseSearchJson(String s) {
        SearchBean searchBean = new SearchBean();
        try {
            JSONObject jsonObject = new JSONObject(s);
            searchBean.status = jsonObject.getString("status");
            if (searchBean.status.equals("ok")) {
                searchBean.msg = jsonObject.getString("msg");
                LogUtils.logV(TAG, searchBean.msg + "msg is null!");
                if (TextUtils.isEmpty(searchBean.msg)) {
                    searchBean.datas = searchBean.new Datas();
                    searchBean.datas.lists = new ArrayList<>();
                    JSONObject datasObject = jsonObject.getJSONObject("datas");
                    JSONArray listsArray = datasObject.getJSONArray("lists");
                    for (int i = 0; i < listsArray.length(); i++) {
                        SearchBean.Lists list = searchBean.new Lists();
                        JSONObject listObject = listsArray.getJSONObject(i);
                        list.id = listObject.getString("id");
                        list.uid = listObject.getString("uid");
                        list.modle = listObject.getString("model");
                        list.title = listObject.getString("title");
                        list.thumbnail = listObject.getString("thumbnail");
                        list.lead = listObject.getString("lead");
                        list.excerpt = listObject.getString("excerpt");
                        searchBean.datas.lists.add(list);
                    }
                    return searchBean;
                } else {
                    return searchBean;
                }
            } else {
                return searchBean;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 热搜LV适配器
     */
    class MyHotAdapter extends BaseAdapter {
        private ArrayList<String> mHotList;
        private Context context;


        public MyHotAdapter(ArrayList<String> mHotList, Context context) {

            this.mHotList = mHotList;
            this.context = context;
        }

        @Override
        public int getCount() {
            return mHotList.size();
        }

        @Override
        public Object getItem(int i) {
            return mHotList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_hot_lv, null);
            TextView hotTv = (TextView) convertView.findViewById(R.id.item_hot_tv);
            hotTv.setText(mHotList.get(position));
            hotTv.setTypeface(mTypeface);
            hotTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSearchEt.setText(mHotList.get(position));
                    mRequestQueue.add(postRequest(NetPathValues.SEARCH_CONTENT_PATH, mHotList.get(position)));
                }
            });
            return convertView;
        }
    }

    /**
     * 搜索结果适配器
     */
    class MyContentAdapter extends BaseAdapter {
        private ArrayList<SearchBean.Lists> lists;

        public MyContentAdapter(ArrayList<SearchBean.Lists> lists) {
            this.lists = lists;
        }

        @Override
        public int getCount() {
            return lists.size();
        }


        @Override
        public Object getItem(int i) {
            return lists.get(i);
        }


        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(SearchActivity.this).inflate(R.layout.item_search_lv, null);
            ViewHolder holder = null;
            if (holder == null) {
                holder = new ViewHolder();
                holder.thumbIv = (ImageView) view.findViewById(R.id.item_search_thumbnail);
                holder.titleTv = (TextView) view.findViewById(R.id.item_search_title);
                holder.titleTv.setTypeface(mTypeface);
                holder.authorTv = (TextView) view.findViewById(R.id.item_search_author);
                holder.authorTv.setTypeface(mTypeface);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            ImageLoaderUtils.getImageByLoader(lists.get(i).thumbnail, holder.thumbIv);
            holder.titleTv.setText(lists.get(i).title);
            switch (Integer.parseInt(lists.get(i).modle)) {
                case 1:
                    holder.authorTv.setText("文 字");
                    break;
                case 3:
                    holder.authorTv.setText("声 音");
                    break;
                case 2:
                    holder.authorTv.setText("影 音");
                    break;
            }


            return view;
        }

        private class ViewHolder {
            ImageView thumbIv;
            TextView titleTv;
            TextView authorTv;
        }
    }


}
