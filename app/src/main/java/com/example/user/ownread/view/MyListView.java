package com.example.user.ownread.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.ant.liao.GifView;
import com.example.user.ownread.R;

/**
 * Created by user on 2016/8/2.
 */
public class MyListView extends ListView implements AbsListView.OnScrollListener {
    private final static int RELEASE_TO_REFRESH = 0;
    private final static int PULL_TO_REFRESH = 1;
    //正在刷新
    private final static int REFRESHING = 2;
    //刷新完成
    private final static int DONE = 3;
    private final static int LOADING = 4;

    private final static int RADIO = 3;

    private LayoutInflater mInflater;
    private View mHeadView;
    private GifView mGifView;
    private RotateAnimation mAnimation;
    private RotateAnimation mReverseAnimation;
    private boolean mIsRecored;
    private int mHeadContentWidth;
    private int mHeadContentHeight;
    private int mStartY;
    private int mFirstItemIndex;
    private int mState;
    private boolean mISRefreshable;
    private OnRefreshListener mRefreshListener;

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mInflater = LayoutInflater.from(context);
        mHeadView = mInflater.inflate(R.layout.header_model_lv, null);

        mGifView = (GifView) mHeadView.findViewById(R.id.head_gif_view);
        mGifView.setGifImage(R.drawable.refresh_loading);
        measureView(mHeadView);
        mHeadContentHeight = mHeadView.getMeasuredHeight();
        System.out.println("mHeadContentHeight = " + mHeadContentHeight);
        mHeadContentWidth = mHeadView.getMeasuredWidth();
        System.out.println("mHeadContentWidth = " + mHeadContentWidth);
        mHeadView.setPadding(0, -1 * mHeadContentHeight, 0, 0);
        mHeadView.invalidate();
        addHeaderView(mHeadView, null, false);
        setOnScrollListener(this);
        mState = DONE;
        mISRefreshable = false;
    }

    private void measureView(View child) {
        android.view.ViewGroup.LayoutParams params = child.getLayoutParams();
        System.out.println("params = " + params);
        if (params == null) {
            params = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        }
        System.out.println("lpWidth = " + params.width);
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, params.width);
        System.out.println("childWidthSpec = " + childWidthSpec);
        int lpHeight = params.height;
        System.out.println("lpHeight = " + lpHeight);
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.UNSPECIFIED);
        }
        System.out.println("childHeightSpec = " + childHeightSpec);
        child.measure(childWidthSpec, childHeightSpec);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        mFirstItemIndex = firstVisibleItem;
    }

    public interface OnRefreshListener {
        public void onRefresh();
    }

    private void onRefresh() {
        if (mRefreshListener != null) {
            mRefreshListener.onRefresh();
        }
    }

    public void onRefreshComplete() {

        mState = DONE;

        changeHeaderViewByState();
    }

    public void setonRefreshListener(OnRefreshListener onRefreshListener) {
        this.mRefreshListener = onRefreshListener;
        mISRefreshable = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mISRefreshable) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (mFirstItemIndex == 0 && !mIsRecored) {
                        mIsRecored = true;
                        mStartY = (int) ev.getY();
                    }
                    break;

                case MotionEvent.ACTION_UP:
                    if (mState != REFRESHING && mState != LOADING) {
                        if (mState == DONE) {

                        }
                        if (mState == PULL_TO_REFRESH) {
                            mState = DONE;
                            changeHeaderViewByState();
                        }
                        if (mState == RELEASE_TO_REFRESH) {
                            mState = REFRESHING;
                            changeHeaderViewByState();
                            onRefresh();
                        }
                    }
                    mIsRecored = false;
                    break;

                case MotionEvent.ACTION_MOVE:
                    int tempY = (int) ev.getY();
                    if (!mIsRecored && mFirstItemIndex == 0) {
                        mIsRecored = true;
                        mStartY = tempY;
                    }
                    if (mState != REFRESHING && mIsRecored && mState != LOADING) {
                        if (mState == RELEASE_TO_REFRESH) {
                            setSelection(0);
                            if ((tempY - mStartY) / RADIO < mHeadContentHeight && (tempY - mStartY) > 0) {
                                mState = PULL_TO_REFRESH;
                                changeHeaderViewByState();
                            } else if (tempY - mStartY <= 0) {
                                mState = DONE;
                                changeHeaderViewByState();
                            }
                        }

                        if (mState == PULL_TO_REFRESH) {
                            setSelection(0);
                            if ((tempY - mStartY) / RADIO >= mHeadContentHeight) {
                                mState = RELEASE_TO_REFRESH;
                                changeHeaderViewByState();
                            }
                        } else if (tempY - mStartY <= 0) {
                            mState = DONE;
                            changeHeaderViewByState();
                        }

                        if (mState == DONE) {
                            if (tempY - mStartY > 0) {
                                mState = PULL_TO_REFRESH;
                                changeHeaderViewByState();
                            }
                        }

                        if (mState == PULL_TO_REFRESH) {
                            mHeadView.setPadding(0, -1 * mHeadContentHeight + (tempY - mStartY) / RADIO, 0, 0);
                        }

                        if (mState == RELEASE_TO_REFRESH) {
                            mHeadView.setPadding(0, (tempY - mStartY) / RADIO - mHeadContentHeight, 0, 0);
                        }
                    }
                    break;

                default:
                    break;
            }
        }
        return super.onTouchEvent(ev);
    }

    private void changeHeaderViewByState() {
        switch (mState) {
            case PULL_TO_REFRESH:
                mGifView.setVisibility(VISIBLE);
                break;

            case DONE:
                mHeadView.setPadding(0, -1 * mHeadContentHeight, 0, 0);
                mGifView.setVisibility(GONE);
                break;

            case REFRESHING:
                mHeadView.setPadding(0, 0, 0, 0);
                mGifView.setVisibility(VISIBLE);
                break;

            case RELEASE_TO_REFRESH:
                mGifView.setVisibility(VISIBLE);
                break;
            default:
                break;
        }
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
    }
}
