package com.shpro.xus.shproject.view.main.adapter;

import android.view.View;

/**
 * Created by xus on 2016/12/2.
 */

public abstract class SHBaseViewHolder {
    public SHBaseViewHolder(View root) {
        initView(root);
    }

    public abstract void initView(View root);
}
