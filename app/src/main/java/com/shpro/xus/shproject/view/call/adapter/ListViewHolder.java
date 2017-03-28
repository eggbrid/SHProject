package com.shpro.xus.shproject.view.call.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shpro.xus.shproject.R;

/**
 * Created by xus on 2017/3/22.
 */

public class ListViewHolder extends BaseViewHolder {
    public TextView content;
    public ImageView avatar;
    public ProgressBar pro;
    public Button send_error;

    public ListViewHolder(View itemView) {
        super(itemView);
    }


    @Override
    public void initView(View itemView) {
        content = (TextView) itemView.findViewById(R.id.content);
        avatar = (ImageView) itemView.findViewById(R.id.avatar);
        pro = (ProgressBar) itemView.findViewById(R.id.pro);
        send_error = (Button) itemView.findViewById(R.id.send_error);
    }
}
