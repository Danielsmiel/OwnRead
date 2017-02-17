package com.example.user.ownread.utils;

/**
 * Created by user on 2016/8/3.
 */
public class BroadCastValues {
    /**
     * id
     */
    public static String ITEM_ID = "";
    /**
     * 播放模式
     */
    public static final int MEDIA_PLAY = 0;
    /**
     * 暂停模式
     */
    public static final int MEDIA_PAUSE = 1;
    /**
     * SeekBar的当前长度
     */
    public static int MEDIA_PROGRASS = 0;
    /**
     * 歌曲总长度
     */
    public static int MEDIA_MAX = 0;
    /**
     * 是否在播放
     */
    public static boolean IS_PLAYING = false;
    /**
     * 拖动模式
     */
    public static final int MEDIA_SEEK = 2;
    /**
     * 快退
     */
    public static final int MEDIA_BACK = 3;
    /**
     * 快进
     */
    public static final int MEDIA_SPEED = 4;
    /**
     * 停止播放
     */
    public static final int MEDIA_STOP = 5;

    public static String OWNREAD_MUSIC_BROADCAST = "com.example.user.ownread.OWNREAD_SERVICE_BROADCAST";
    public static String OWNREAD_MUSIC_SEND_BROADCAST = "com.example.user.ownread.OWNREAD_SERVICE_SEND_BROADCAST";

    /**
     * Service停止播放
     */
    public static final int SERVICE_MEDIA_STOP = 2;
    /**
     * Service播放模式
     */
    public static final int SERVICE_MEDIA_PLAY = 0;
    /**
     * Service暂停模式
     */
    public static final int SERVICEMEDIA_PAUSE = 1;

}
