package com.shpro.xus.shproject.view;

import android.os.Bundle;


/**
 * Created by xus on 2016/11/15.
 */

public abstract class CommentActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContentView());
        initView();
    }
}
