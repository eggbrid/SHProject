package com.shpro.xus.shproject.view.taobao;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.tb.TBOneDay;
import com.shpro.xus.shproject.shprojectHttp.Url.UrlUtil;
import com.shpro.xus.shproject.shprojectHttp.okhttp.OkHttpUtil;
import com.shpro.xus.shproject.shprojectHttp.okhttp.interfaces.CallBack;
import com.shpro.xus.shproject.util.ImageLoaderUtil;
import com.shpro.xus.shproject.util.ToastUtil;
import com.shpro.xus.shproject.view.BaseActivity;
import com.shpro.xus.shproject.view.views.cycleView.ImageCycleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xus on 2017/4/11.
 */

public class TBOneDayActivity extends BaseActivity implements View.OnClickListener {
    protected ImageCycleView pager;
    protected TextView salary;
    protected TextView content;
    protected Button btnGoTb;
    protected RelativeLayout has_data;
private  TBOneDay tbOneDay;
    private  TextView contentTitle;

    @Override
    public int setContentView() {
        return R.layout.activity_one_day_tb;
    }

    @Override
    public void initView() throws Exception {
        pager = (ImageCycleView) findViewById(R.id.pager);
        salary = (TextView) findViewById(R.id.salary);
        content = (TextView) findViewById(R.id.content);
        btnGoTb = (Button) findViewById(R.id.btn_go_tb);
        contentTitle=(TextView)findViewById(R.id.title);
        btnGoTb.setOnClickListener(TBOneDayActivity.this);
        has_data = (RelativeLayout) findViewById(R.id.has_data);
        findBag();
    }

    public void findBag() {
        showPross("正在获取");
        Map<String, String> map = new HashMap<>();
        OkHttpUtil.doGet(this, UrlUtil.GETTODAY_URL, map, new CallBack<TBOneDay>() {
            @Override
            public void onSuccess(TBOneDay tBOneDay) {
                dissPross();
                if (tBOneDay != null) {
                    tbOneDay=tBOneDay;
                    has_data.setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(tBOneDay.getImages())) {
                        ArrayList<String> cycleImages = new ArrayList<String>();
                        cycleImages.addAll(Arrays.asList(tBOneDay.getImages().split("@@shproject@@")));
                        setupAutoScrollView(cycleImages);
                    }
                    contentTitle.setText(tBOneDay.getTitle());
                    salary.setText(tBOneDay.getSalary());
                    content.setText(tBOneDay.getContent());
                } else {
                    TBOneDayActivity.this.finish();
                    ToastUtil.makeTextShort(TBOneDayActivity.this, "暂无推荐");

                }
            }

            @Override
            public void onError(String s) {
                dissPross();
                TBOneDayActivity.this.finish();
                ToastUtil.makeTextShort(TBOneDayActivity.this, s);
            }
        }, TBOneDay.class);
    }

    private void setupAutoScrollView(final ArrayList<String> cycleImages) {
        pager.setImageResources(new ArrayList<String>(), cycleImages, new ImageCycleView.ImageCycleViewListener() {
            @Override
            public void displayImage(String imageURL, ImageView imageView) {
                ImageLoaderUtil.getInstance().loadNomalImage(imageURL, imageView);
            }

            @Override
            public void onImageClick(int position, View imageView) {

            }
        });
        pager.startImageCycle();
    }

    public void openUrl(String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_go_tb) {
            openUrl(tbOneDay.getUrl());
        }
    }
}
