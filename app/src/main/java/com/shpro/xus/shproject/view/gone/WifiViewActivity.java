package com.shpro.xus.shproject.view.gone;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.view.CommentActivity;

/**
 * Created by xus on 2017/2/7.
 */

public class WifiViewActivity extends CommentActivity implements Runnable {
    protected TextView info;
    private  boolean isContinue=true;
    public long times=0;
    @Override
    public int setContentView() {

        return R.layout.activity_gone_wifiview;
    }
    @Override
    public void initView() throws Exception {
        initTitleView();
        setCommentTitleView("wifi信息");
        info = (TextView) findViewById(R.id.info);
        info.setText(getInfo());
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isContinue){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(WifiViewActivity.this);
                }
            }
        }).start();
    }

    private String getInfo() {
        times++;

        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String maxText = info.getMacAddress();
        String ipText = intToIp(info.getIpAddress());
        String status = "";
        if (wifi.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {
            status = "WIFI_STATE_ENABLED";
        }
        String ssid = info.getSSID();
        int networkID = info.getNetworkId();
        int speed = info.getLinkSpeed();
        String bssid=info.getBSSID();
        return "mac：" + maxText + "\n\r"
                + "ip：" + ipText + "\n\r"
                + "wifi status :" + status + "\n\r"
                + "ssid :" + ssid + "\n\r"
                + "net work id :" + networkID + "\n\r"
                + "connection speed:" + speed + "\n\r"
                + "bssid:" + bssid + "\n\r"
                + "第:" + times + "次刷新\n\r"

                ;
    }

    private String intToIp(int ip) {
        return (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "."
                + ((ip >> 24) & 0xFF);
    }

    @Override
    protected void onDestroy() {
        isContinue=false;
        super.onDestroy();
    }

    @Override
    public void run() {
        info.setText(getInfo());

    }
}
