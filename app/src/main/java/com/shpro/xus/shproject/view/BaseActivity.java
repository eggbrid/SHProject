package com.shpro.xus.shproject.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.shpro.xus.shproject.shprojectHttp.HttpUtil;
import com.wilddog.client.SyncReference;

/**
 * Created by xus on 2016/11/8.
 */

public abstract class BaseActivity extends Activity {

    public abstract int setContentView();
    public abstract void initView();

}
