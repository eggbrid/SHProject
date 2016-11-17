package com.shpro.xus.shproject.view;

import android.view.View;

import com.shpro.xus.shproject.R;

/**
 * Created by xus on 2016/11/15.
 */

public class SHMainActivity extends CommentActivity {
    @Override
    public int setContentView() {
        return R.layout.sh_main_activity;
    }

    @Override
    public void initView() {
        initTitleView();
        titleText.setText("背包");
        left.setVisibility(View.VISIBLE);
        right.setVisibility(View.VISIBLE);

    }
}
