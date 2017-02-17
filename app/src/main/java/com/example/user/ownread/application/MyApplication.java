package com.example.user.ownread.application;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by user on 2016/7/28.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoder(getApplicationContext());
    }

    private void initImageLoder(Context context) {
        // 设置缓存图片的路径
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, "imageloader/Catch");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .diskCacheSize(50 * 1024 * 1024).diskCache(new UnlimitedDiscCache(cacheDir))
                .memoryCache(new WeakMemoryCache()).tasksProcessingOrder(QueueProcessingType.LIFO).build();
        // 单例模式就是不能New这个对象，构造方法私有，找不到这个对象，见到getInstance就是单例模式
        ImageLoader.getInstance().init(config);
    }
}
