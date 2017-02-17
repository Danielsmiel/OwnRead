package com.example.user.ownread.utils;

import android.util.Log;

/**
 * Created by user on 2016/7/26.
 */
public class LogUtils {
    private static boolean isLoged = true;

    public static void logV(String TAG, String info) {
        if (isLoged) {
            Log.v(TAG, info);
        }
    }
}
