package com.shpro.xus.shproject.view.user;

import android.os.Bundle;

import com.shpro.xus.shproject.view.BaseActivity;

/**
 * Created by xus on 2016/11/8.
 */

public abstract class UserBaseActivity extends BaseActivity{
    public abstract void getData();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContentView());
        try {
            initView();
        } catch (Exception e) {
            e.printStackTrace();
        }
        getData();
    }
}
