package com.shpro.xus.shproject;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.L;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.shpro.xus.shproject.shprojectHttp.HttpUtil;

import java.io.File;

import cn.bmob.v3.Bmob;

/**
 * Created by xus on 2016/11/8.
 */

public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HttpUtil.getInstance().init(this);
        Bmob.initialize(this, "71c81ce12d70f8e9415d6c86d62d5a65");
        initImageLoader();
    }
    public void initImageLoader() {
        String filePath = "Android/data/com.shpro.xus.shproject/files/cache/img/";//Environment.getExternalStorageDirectory() + "/Android/data/" + instance.getPackageName() + "/cache/";
        File cacheDir = StorageUtils.getOwnCacheDirectory(this, filePath);// 获取到缓存的目录地址
        //L.d("cacheDir:"+cacheDir.getPath());
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(this);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.threadPoolSize(3);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.memoryCache(new WeakMemoryCache());

        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        //config.diskCacheSize(1 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.diskCache(new UnlimitedDiskCache(cacheDir));
//        config.writeDebugLogs(); // Remove for release app
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
        L.writeLogs(false);
    }
}
