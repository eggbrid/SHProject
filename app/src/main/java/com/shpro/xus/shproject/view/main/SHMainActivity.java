package com.shpro.xus.shproject.view.main;

import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.view.CommentActivity;
import com.shpro.xus.shproject.view.main.adapter.MainAdapter;

/**
 * Created by xus on 2016/11/15.
 */

public class SHMainActivity extends CommentActivity {
    protected GridView mainGrid;
    private MainAdapter adapter;

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
        right.setImageResource(R.drawable.map);
        left.setImageResource(R.drawable.help);
        adapter = new MainAdapter(this);
        left = (ImageButton) findViewById(R.id.left);
        titleText = (TextView) findViewById(R.id.title_text);
        right = (ImageButton) findViewById(R.id.right);
        title = (LinearLayout) findViewById(R.id.title);
        mainGrid = (GridView) findViewById(R.id.main_grid);
        mainGrid.setAdapter(adapter);


    }
}
