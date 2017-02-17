package com.example.user.ownread.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.user.ownread.R;
import com.example.user.ownread.activity.MainActivity;
import com.example.user.ownread.adapter.MyFragPagerAdapter;
import com.example.user.ownread.bean.ContentItemBean;
import com.example.user.ownread.utils.BroadCastValues;
import com.example.user.ownread.utils.FormatUtils;
import com.example.user.ownread.utils.LogUtils;
import com.example.user.ownread.utils.NetPathValues;
import com.example.user.ownread.utils.ParseJson;
import com.example.user.ownread.view.VerticalViewPager;

import java.util.ArrayList;

/**
 * Created by user on 2016/7/26.
 */
public class ContentFragment extends BaseFragment {

    private static final String TAG = "ContentFragment";
    private int page = 1;
    private int model = 0;
    private String createTime = "0";
    private MyFragPagerAdapter adapter;
    private String path = NetPathValues.CONTENT_PATH_ARRAY[0] + page
            + NetPathValues.CONTENT_PATH_ARRAY[1] + model
            + NetPathValues.CONTENT_PATH_ARRAY[2] + createTime
            + NetPathValues.CONTENT_PATH_ARRAY[3] + FormatUtils.getUnixCurrentTime();
    private ArrayList<ContentItemBean.Datas> dataList;
    private ArrayList<Fragment> itemFragList;
    private VerticalViewPager contentViewPager;
    private Button mCCBtn, mCUBtn;
    private Intent mIntent;

    @Override
    public View initView() {
        View contentFragView = LayoutInflater.from(getActivity()).inflate(R.layout.frag_content, null);


        contentViewPager = (VerticalViewPager) contentFragView.findViewById(R.id.content_vertical_viewpager);
        mCCBtn = (Button) contentFragView.findViewById(R.id.frag_content_content_btn);
        mCUBtn = (Button) contentFragView.findViewById(R.id.frag_content_user_btn);
        mCCBtn.setOnClickListener(mCCListener);
        mCUBtn.setOnClickListener(mCUListener);
        contentViewPager.setOnPageChangeListener(pageChangeListener);
        dataList = new ArrayList<>();
        getNetData();
        return contentFragView;
    }

    public void setData() {
        itemFragList = new ArrayList<>();
        contentViewPager.setOffscreenPageLimit(5);
        for (int i = 0; i < dataList.size(); i++) {

            ItemContentFragment frag = new ItemContentFragment(dataList.get(i));
            itemFragList.add(frag);

        }
        adapter = new MyFragPagerAdapter(getFragmentManager(), itemFragList);
        contentViewPager.setAdapter(adapter);
    }

    private void getNetData() {
        ((MainActivity) getActivity()).requestQueue.add(stringRequest);
    }

    /**
     * 请求字符串
     **/
    StringRequest stringRequest = new StringRequest(path, new Response.Listener<String>() {
        @Override
        public void onResponse(String s) {
            ContentItemBean ceIbean = ParseJson.parseConentJson(s);
            dataList = ceIbean.dataList;
            LogUtils.logV(TAG, path);
            setData();
            LogUtils.logV(TAG, ceIbean.status);
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            LogUtils.logV(TAG, volleyError.getMessage());
        }
    });

    View.OnClickListener mCCListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ((MainActivity) getActivity()).controlView.setCurrentItem(0);
        }
    };

    View.OnClickListener mCUListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ((MainActivity) getActivity()).controlView.setCurrentItem(2);
        }
    };

    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onPageSelected(int position) {
            ItemContentFragment fragment = (ItemContentFragment) itemFragList.get(position);
            if (dataList.get(position).id.equals(BroadCastValues.ITEM_ID) && BroadCastValues.IS_PLAYING) {
                fragment.mSurfaceIv.setVisibility(View.GONE);
                fragment.mMusicRl.setVisibility(View.VISIBLE);
                fragment.mPlayMusicBtn.setBackgroundResource(R.drawable.music_pause);
            } else if (dataList.get(position).id.equals(BroadCastValues.ITEM_ID) && !BroadCastValues.IS_PLAYING) {
                fragment.mSurfaceIv.setVisibility(View.GONE);
                fragment.mMusicRl.setVisibility(View.VISIBLE);
                fragment.mPlayMusicBtn.setBackgroundResource(R.drawable.music_play);
            } else if (dataList.get(position).modle.equals("3")) {
                fragment.mSurfaceIv.setVisibility(View.VISIBLE);
                fragment.mSurfaceIv.setImageDrawable(getActivity().getDrawable(R.drawable.library_voice_play_symbol));
                fragment.mMusicRl.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


}
