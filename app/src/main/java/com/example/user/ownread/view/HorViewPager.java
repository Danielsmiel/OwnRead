package com.example.user.ownread.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by user on 2016/8/1.
 */
public class HorViewPager extends ViewPager {

    private static final String TAG = "HorViewPager";


    public HorViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        return false;
    }
}
