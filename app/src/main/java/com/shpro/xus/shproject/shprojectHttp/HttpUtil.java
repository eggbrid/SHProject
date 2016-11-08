package com.shpro.xus.shproject.shprojectHttp;

import android.content.Context;

import com.wilddog.client.SyncReference;
import com.wilddog.client.WilddogSync;
import com.wilddog.wilddogcore.WilddogApp;
import com.wilddog.wilddogcore.WilddogOptions;

/**
 * Created by xus on 2016/11/7.
 */

public class HttpUtil {
    private static HttpUtil httpUtil;

    public SyncReference getRef() {
        return WilddogSync.getInstance().getReference();
    }
    public SyncReference getRef(String c) {
        return WilddogSync.getInstance().getReference(c);
    }

    public static HttpUtil getInstance() {
        if (httpUtil==null){
            httpUtil=new HttpUtil();
        }
        return httpUtil;
    }
    public void init(Context c){
        WilddogOptions options = new WilddogOptions.Builder().setSyncUrl("https://shproject.wilddogio.com").build();
        WilddogApp.initializeApp(c, options);
    }
}
