package com.shpro.xus.shproject.view.start;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.view.CommentActivity;

/**
 * Created by xus on 2017/4/24.
 */

public class StartActivity extends CommentActivity implements View.OnClickListener {

    protected LinearLayout baiyang;
    protected LinearLayout jinniu;
    protected LinearLayout shuangzi;
    protected LinearLayout sheshou;
    protected LinearLayout shizi;
    protected LinearLayout juxie;
    protected LinearLayout tianxie;
    protected LinearLayout shuiping;
    protected LinearLayout shuangyu;
    protected LinearLayout tiancheng;
    protected LinearLayout mojie;
    protected LinearLayout chunv;

    @Override
    public int setContentView() {
        return R.layout.activity_start;
    }

    @Override
    public void initView() throws Exception {
        setCommentTitleView("星座");
        baiyang = (LinearLayout) findViewById(R.id.baiyang);
        baiyang.setOnClickListener(StartActivity.this);
        jinniu = (LinearLayout) findViewById(R.id.jinniu);
        jinniu.setOnClickListener(StartActivity.this);
        shuangzi = (LinearLayout) findViewById(R.id.shuangzi);
        shuangzi.setOnClickListener(StartActivity.this);
        sheshou = (LinearLayout) findViewById(R.id.sheshou);
        sheshou.setOnClickListener(StartActivity.this);
        shizi = (LinearLayout) findViewById(R.id.shizi);
        shizi.setOnClickListener(StartActivity.this);
        juxie = (LinearLayout) findViewById(R.id.juxie);
        juxie.setOnClickListener(StartActivity.this);
        tianxie = (LinearLayout) findViewById(R.id.tianxie);
        tianxie.setOnClickListener(StartActivity.this);
        shuiping = (LinearLayout) findViewById(R.id.shuiping);
        shuiping.setOnClickListener(StartActivity.this);
        shuangyu = (LinearLayout) findViewById(R.id.shuangyu);
        shuangyu.setOnClickListener(StartActivity.this);
        tiancheng = (LinearLayout) findViewById(R.id.tiancheng);
        tiancheng.setOnClickListener(StartActivity.this);
        mojie = (LinearLayout) findViewById(R.id.mojie);
        mojie.setOnClickListener(StartActivity.this);
        chunv = (LinearLayout) findViewById(R.id.chunv);
        chunv.setOnClickListener(StartActivity.this);


    }

    @Override
    public void onClick(View view) {
        String id = "baiyang";
        if (view.getId() == R.id.baiyang) {
            id = "baiyang";
        } else if (view.getId() == R.id.jinniu) {
            id = "jinniu";
        } else if (view.getId() == R.id.shuangzi) {
            id = "shuangzi";
        } else if (view.getId() == R.id.sheshou) {
            id = "sheshou";
        } else if (view.getId() == R.id.shizi) {
            id = "shizi";
        } else if (view.getId() == R.id.juxie) {
            id = "juxie";
        } else if (view.getId() == R.id.tianxie) {
            id = "tianxie";
        } else if (view.getId() == R.id.shuiping) {
            id = "shuiping";
        } else if (view.getId() == R.id.shuangyu) {
            id = "shuangyu";
        } else if (view.getId() == R.id.tiancheng) {
            id = "tiancheng";
        } else if (view.getId() == R.id.mojie) {
            id = "mojie";
        } else if (view.getId() == R.id.chunv) {
            id = "chunv";

        }
        Intent intent = new Intent(this, StartDetailActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}
