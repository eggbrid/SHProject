package com.shpro.xus.shproject.view;

import android.os.Bundle;

import java.sql.SQLException;


/**
 * Created by xus on 2016/11/15.
 */

public abstract class CommentActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContentView());
        try {
            initView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
