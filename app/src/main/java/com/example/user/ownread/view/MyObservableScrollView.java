package com.example.user.ownread.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by user on 2016/8/4.
 * 监听滑动距离的ScrollView
 */
public class MyObservableScrollView extends ScrollView implements ObservableScrollable {

    private boolean mDisableEdgeEffects = true;
    //接口
    private OnScrollChangedCallback mOnScrollChangedListener;

    public MyObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //主要是这个方法
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangedListener != null) {
            mOnScrollChangedListener.onScroll(l, t);
        }
    }

    @Override
    protected float getTopFadingEdgeStrength() {
        if (mDisableEdgeEffects
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return 0.0f;
        }
        return super.getTopFadingEdgeStrength();
    }

    @Override
    protected float getBottomFadingEdgeStrength() {
        if (mDisableEdgeEffects
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return 0.0f;
        }
        return super.getBottomFadingEdgeStrength();
    }

    //回调
    @Override
    public void setOnScrollChangedCallback(OnScrollChangedCallback callback) {
        mOnScrollChangedListener = callback;
    }
}

/**
 * 接口
 */
interface ObservableScrollable {
    void setOnScrollChangedCallback(OnScrollChangedCallback callback);
}

