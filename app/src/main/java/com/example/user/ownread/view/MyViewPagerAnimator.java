package com.example.user.ownread.view;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by user on 2016/8/2.
 */
public class MyViewPagerAnimator implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.75f;

    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0.0f);

        } else if (position <= 0) { // [-1,0]
            // Use the default slide transition when moving to the left page
            view.setAlpha(1);
            view.setTranslationX(0);
            view.setScaleX(1);
            view.setScaleY(1);

        } else if (position <= 1) {

            view.setAlpha(1 - position);

            view.setTranslationX(pageWidth * -position);


            float scaleFactor = MIN_SCALE
                    + (1 - MIN_SCALE) * (1 - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

        } else {
            view.setAlpha(0.0f);
        }
    }
}
