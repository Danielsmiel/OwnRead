package com.example.user.ownread.Service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.example.user.ownread.utils.BroadCastValues;
import com.example.user.ownread.utils.LogUtils;

import java.io.IOException;

/**
 * Created by user on 2016/8/3.
 * 主要是用来播放音频的，可能大材小用了
 */
public class MusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    private static final String TAG = "MusicService";
    private MediaPlayer mediaPlayer;
    private String path;
    private MediaBroadCastReceiver mediaBroadCastReceiver;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x888) {
                if (mediaPlayer != null) {
                    BroadCastValues.MEDIA_PROGRASS = mediaPlayer.getCurrentPosition();
                    handler.sendEmptyMessageDelayed(0x888, 500);
                }
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadCastValues.OWNREAD_MUSIC_BROADCAST);
        mediaBroadCastReceiver = new MediaBroadCastReceiver();
        registerReceiver(mediaBroadCastReceiver, filter);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void playVoice(String path, Boolean isPause) {

        try {
            if (isPause) {
                mediaPlayer.start();
                BroadCastValues.IS_PLAYING = mediaPlayer.isPlaying();
            } else {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(path);
                mediaPlayer.prepareAsync();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            BroadCastValues.IS_PLAYING = mediaPlayer.isPlaying();
            Intent intent = new Intent();
            intent.putExtra("serviceType", BroadCastValues.SERVICEMEDIA_PAUSE);
            intent.setAction(BroadCastValues.OWNREAD_MUSIC_SEND_BROADCAST);
            sendBroadcast(intent);
        }
    }

    private void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            handler.removeMessages(0x888);
            BroadCastValues.IS_PLAYING = mediaPlayer.isPlaying();
            mediaPlayer.release();
            mediaPlayer = null;
            Intent intent = new Intent();
            intent.putExtra("serviceType", BroadCastValues.SERVICE_MEDIA_STOP);
            intent.setAction(BroadCastValues.OWNREAD_MUSIC_SEND_BROADCAST);
            sendBroadcast(intent);
            BroadCastValues.ITEM_ID = "";
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        stop();
    }


    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
        BroadCastValues.IS_PLAYING = mediaPlayer.isPlaying();
        BroadCastValues.MEDIA_MAX = mediaPlayer.getDuration();
        Intent intent = new Intent();
        intent.putExtra("serviceType", BroadCastValues.SERVICE_MEDIA_PLAY);
        intent.setAction(BroadCastValues.OWNREAD_MUSIC_SEND_BROADCAST);
        sendBroadcast(intent);
        handler.removeMessages(0x888);
        handler.sendEmptyMessage(0x888);
    }

    /**
     * 广播
     */
    public class MediaBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getIntExtra("type", -1)) {
                case BroadCastValues.MEDIA_PLAY:
                    path = intent.getStringExtra("voicePath");
                    boolean isFromPause = intent.getBooleanExtra("isFromPause", false);
                    playVoice(path, isFromPause);
                    LogUtils.logV(TAG, path);
                    break;
                case BroadCastValues.MEDIA_PAUSE:
                    pause();
                    break;
                case BroadCastValues.MEDIA_SEEK:
                    mediaPlayer.seekTo(BroadCastValues.MEDIA_PROGRASS);
                    break;
                case BroadCastValues.MEDIA_BACK:
                    BroadCastValues.MEDIA_PROGRASS = BroadCastValues.MEDIA_PROGRASS - 10000;
                    if (BroadCastValues.MEDIA_PROGRASS <= 0) {
                        mediaPlayer.seekTo(0);
                    } else {
                        mediaPlayer.seekTo(BroadCastValues.MEDIA_PROGRASS);
                    }
                    break;
                case BroadCastValues.MEDIA_SPEED:
                    BroadCastValues.MEDIA_PROGRASS = BroadCastValues.MEDIA_PROGRASS + 10000;
                    if (BroadCastValues.MEDIA_PROGRASS >= BroadCastValues.MEDIA_MAX) {
                        pause();
                    } else {
                        mediaPlayer.seekTo(BroadCastValues.MEDIA_PROGRASS);
                    }
                    break;
                case BroadCastValues.MEDIA_STOP:
                    stop();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mediaBroadCastReceiver);
    }
}
