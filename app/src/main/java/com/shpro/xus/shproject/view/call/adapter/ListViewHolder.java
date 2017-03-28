package com.shpro.xus.shproject.view.call.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shpro.xus.shproject.R;

/**
 * Created by xus on 2017/3/22.
 */

public class ListViewHolder extends BaseViewHolder {
    public TextView content;
    public ImageView avatar;


    public ListViewHolder(View itemView) {
        super(itemView);
    }


    @Override
    public void initView(View itemView) {
        content = (TextView) itemView.findViewById(R.id.content);
        avatar = (ImageView) itemView.findViewById(R.id.avatar);
    }
}
