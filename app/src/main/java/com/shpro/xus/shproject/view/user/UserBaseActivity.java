package com.shpro.xus.shproject.view.user;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.shpro.xus.shproject.view.BaseActivity;
import com.wilddog.client.SyncReference;
import com.wilddog.wilddogauth.WilddogAuth;

/**
 * Created by xus on 2016/11/8.
 */

public abstract class UserBaseActivity extends BaseActivity{
    public abstract void getData();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContentView());
        initView();
        getData();
    }
}
