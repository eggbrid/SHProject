package com.shpro.xus.shproject;

import android.app.Application;

import com.shpro.xus.shproject.shprojectHttp.HttpUtil;

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
    }
}
