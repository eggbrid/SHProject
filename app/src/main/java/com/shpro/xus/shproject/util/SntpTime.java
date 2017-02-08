package com.shpro.xus.shproject.util;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.shpro.xus.shproject.APP;
import com.shpro.xus.shproject.db.cache.ACacheUtil;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by xus on 2017/2/7.
 */

public class SntpTime {
    private String TAG = "SntpTime";
    private int mTimeOut;
    private Handler mOtherHandler;
    private int mHandlerWhatNumber;

    public SntpTime() {

    }

    // 时间获取ip地址 // socket超时时间   //  消息句柄// 消息标号自定义What数字
    public SntpTime(int timeOut, Handler handler, int handlerWhatNumber) {
        this.mTimeOut = timeOut;
        this.mOtherHandler = handler;
        this.mHandlerWhatNumber = handlerWhatNumber;
    }

    public void getNetTime() {
        new Thread() {
            public void run() {
                //使用隐形类
                try {
                    long startTime = SystemClock.elapsedRealtime();
                    URL url = new URL("http://www.baidu.com");//取得资源对象
                    URLConnection uc = url.openConnection();//生成连接对象
                    uc.connect(); //发出连接
                    long endTime = SystemClock.elapsedRealtime();
                    long ld = uc.getDate() + (endTime - startTime) / 2; //取得网站日期时间
                    ACacheUtil.getInstance().cacheString("SntpTimeStartTime", endTime + "");
                    ACacheUtil.getInstance().cacheString("SntpTimeNowTime", ld + "");
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }.start();
    }

    public void getTime() {
        String cacheTime = ACacheUtil.getInstance().getString(APP.getInstance(), "SntpTimeStartTime");
        String cacheNowTime = ACacheUtil.getInstance().getString(APP.getInstance(), "SntpTimeNowTime");
        if (TextUtils.isEmpty(cacheTime) || TextUtils.isEmpty(cacheNowTime)) {
            Log.e("wangxu", "获取网络时间");
            new Thread() {
                public void run() {
                    //使用隐形类

                    try {
                        long startTime = SystemClock.elapsedRealtime();
                        URL url = new URL("http://www.baidu.com");//取得资源对象
                        URLConnection uc = url.openConnection();//生成连接对象
                        uc.connect(); //发出连接
                        long endTime = SystemClock.elapsedRealtime();
                        long ld = uc.getDate() + (endTime - startTime) / 2; //取得网站日期时间
                        ACacheUtil.getInstance().cacheString("SntpTimeStartTime", endTime + "");
                        ACacheUtil.getInstance().cacheString("SntpTimeNowTime", ld + "");
                        Message toOtherHandler = new Message();
                        toOtherHandler.what = mHandlerWhatNumber;
                        toOtherHandler.obj = ld;
                        mOtherHandler.sendMessage(toOtherHandler);

                    } catch (MalformedURLException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                }
            }.start();
        } else {
            Log.e("wangxu", "获取本地时间");
            long startTime = Long.parseLong(cacheTime);
            long thatTime = Long.parseLong(cacheNowTime);
            long endTime = SystemClock.elapsedRealtime();
            long ld = thatTime + endTime - startTime;
            Message toOtherHandler = new Message();
            toOtherHandler.what = mHandlerWhatNumber;
            toOtherHandler.obj = ld;
            mOtherHandler.sendMessage(toOtherHandler);
        }
    }

}
