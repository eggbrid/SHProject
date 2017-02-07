package com.shpro.xus.shproject.view.gone;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;

import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.util.SntpTime;
import com.shpro.xus.shproject.util.ToastUtil;
import com.shpro.xus.shproject.view.CommentActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by xus on 2017/2/7.
 */

public class TimeViewActivity extends CommentActivity implements Runnable {

    protected TextView time;
    protected TextView gpsTime;
    protected TextView netTime;
    private String times;
    private String gpsTimes;
    private String netTimes;
    private long netTimeslong=0;
    private long gpsTimelong=0;

    private boolean isContinue = true;

    @Override
    public int setContentView() {
        return R.layout.activity_gone_timeview;
    }

    @Override
    public void initView() throws Exception {
        initTitleView();
        setCommentTitleView("时间");
        time = (TextView) findViewById(R.id.time);
        gpsTime = (TextView) findViewById(R.id.gpsTime);
        netTime = (TextView) findViewById(R.id.netTime);
        getGPSTime();
        startSntpTime();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isContinue) {
                    runOnUiThread(TimeViewActivity.this);
                    try {
                        Thread.sleep(1000);
                        netTimeslong=netTimeslong+1000;
                        gpsTimelong=gpsTimelong+1000;
                        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        dff.setTimeZone(TimeZone.getTimeZone("GMT+08"));
                        gpsTimes ="GPS时间："+ dff.format(new Date(gpsTimelong))+"----时间戳："+gpsTimelong;
                        netTimes ="网络时间："+ dff.format(new Date(netTimeslong))+"----时间戳："+netTimeslong;
                        long time = System.currentTimeMillis();
                        times ="系统时间："+ dff.format(new Date(time))+"----时间戳："+time;


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    public void getGPSTime() {
        LocationManager locMan = (LocationManager) this.getSystemService(TimeViewActivity.LOCATION_SERVICE);
        //获取最近一次知道的时间
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ToastUtil.makeTextShort(this, "没有开启Gps");
            return;
        }
        //或者实时的获取时间：
        locMan.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                gpsTimelong = location.getElapsedRealtimeNanos();

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        }); //获取当前时间
    }

    @Override
    protected void onDestroy() {
        isContinue = false;
        super.onDestroy();
    }

    private void startSntpTime() {
        Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    /////////////////////////////////Handler消息分类
                    //Long这里一定要用 Long  代替long 否则linux源码mm编译的时候会报错
                    case 1:
                        netTimeslong = (long) msg.obj;
                        break;
                }
            }
        };
//参数1 表示Ip
//参数2 超时时间
//参数3 Handler
//参数4 传入的Handler.What值
        SntpTime currentTime = new SntpTime(30000, mHandler, 1);
        currentTime.getTime();
    }

    @Override
    public void run() {
        time.setText(times);
        gpsTime.setText(gpsTimes);
        netTime.setText(netTimes);
    }
}
