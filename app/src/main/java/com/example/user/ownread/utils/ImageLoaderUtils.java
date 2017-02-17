package com.example.user.ownread.utils;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.example.user.ownread.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

/**
 * ����ͼƬ���ع��� �����Ƿ�ʹ�û���
 *
 * @author user
 */
public class ImageLoaderUtils {
    public static void getImageByLoader(final String url, ImageView iv) {
        DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.welcome_default2)
                .showImageForEmptyUri(R.drawable.welcome_default2)
                // ��������ʾ
                .showImageOnFail(R.drawable.welcome_default2)
                // �������ص�ͼƬ�Ƿ���ʾ���ڴ���
                .cacheInMemory(true)
                // �Ƿ���ʾ��SD��
                .cacheOnDisk(true)
                // ͼƬ����
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                // ͼƬ����
                .bitmapConfig(Bitmap.Config.RGB_565)
                // ����ʱ��
                // .displayer(new FadeInBitmapDisplayer(100))
                // Բ��ͼƬ
                // .displayer(new RoundedBitmapDisplayer(20))
                .build();

        ImageLoader.getInstance().displayImage(url, iv, options, new ImageLoadingListener() {

            @Override
            public void onLoadingStarted(String arg0, View arg1) {
            }

            @Override
            public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
            }

            @Override
            public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
            }

            @Override
            public void onLoadingCancelled(String arg0, View arg1) {

            }
        }, new ImageLoadingProgressListener() {

            @Override
            public void onProgressUpdate(String arg0, View arg1, int arg2, int arg3) {
            }
        });
    }
}
