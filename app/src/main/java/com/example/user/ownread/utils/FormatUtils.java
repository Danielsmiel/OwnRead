package com.example.user.ownread.utils;

/**
 * Created by user on 2016/7/26.
 */
public class FormatUtils {
    /**
     * 获取当前系统时间
     *
     * @return Unix时间戳
     */
    public static String getUnixCurrentTime() {
        Long timeMillis = System.currentTimeMillis();
        return timeMillis.toString();
    }

    public static String getFormatCurrentTime() {
        return null;
    }

    /**
     * 格式化数据
     *
     * @param time
     * @return
     */
    public static String formatTime(int time) {
        if (time / 1000 % 60 < 10) {
            return time / 1000 / 60 + ":0" + time / 1000 % 60;
        } else {
            return time / 1000 / 60 + ":" + time / 1000 % 60;
        }
    }
}
