package com.shpro.xus.shproject.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.shprojectHttp.HttpUtil;
import com.wilddog.client.SyncReference;

/**
 * Created by xus on 2016/11/8.
 */

public abstract class BaseActivity extends Activity implements View.OnClickListener {

    protected ImageButton left;
    protected ImageButton right;
    protected LinearLayout title;
    protected TextView titleText;

    public abstract int setContentView();
    public abstract void initView();
    public void setCommentTitleView(String titles){
        initTitleView();
        left.setImageResource(R.drawable.back);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.this.finish();
            }
        });
        left.setVisibility(View.VISIBLE);
        titleText.setText(titles);
    }
    private void initTitleView(){
        titleText=(TextView) findViewById(R.id.title_text);
        left=(ImageButton) findViewById(R.id.left);
        right=(ImageButton) findViewById(R.id.right);
        title=(LinearLayout) findViewById(R.id.title);
        title.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.left) {

        } else if (view.getId() == R.id.right) {

        } else if (view.getId() == R.id.title) {

        }
    }
}
