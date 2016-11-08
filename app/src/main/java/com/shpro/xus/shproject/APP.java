package com.shpro.xus.shproject;

import android.app.Application;

import com.shpro.xus.shproject.shprojectHttp.HttpUtil;

/**
 * Created by xus on 2016/11/8.
 */

public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HttpUtil.getInstance().init(this);
    }
}
