package com.shpro.xus.shproject.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * 普通的后台Service进程
 *
 * @author clock
 * @since 2016-04-12
 */
public class BackgroundService extends Service {

    private final static String TAG = BackgroundService.class.getSimpleName();

    @Override
    public void onCreate() {
        Log.e("wangxu", "BackgroundService 又活了");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Log.e("wangxu", "我不停的执行哈哈哈哈哈哈");

                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
    }
}
