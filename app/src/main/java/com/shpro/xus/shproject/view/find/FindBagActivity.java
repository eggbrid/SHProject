package com.shpro.xus.shproject.view.find;

import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.view.CommentActivity;
import com.shpro.xus.shproject.view.views.TaiJiView;
import com.skyfishjy.library.RippleBackground;

/**
 * Created by xus on 2017/2/8.
 */

public class FindBagActivity extends CommentActivity implements View.OnClickListener {
    protected TaiJiView centerImage;
    protected RippleBackground content;

    @Override
    public int setContentView() {
        return R.layout.activity_find_bag;
    }

    @Override
    public void initView() throws Exception {
        setCommentTitleView("寻找物品");
        centerImage = (TaiJiView) findViewById(R.id.centerImage);
        centerImage.setOnClickListener(FindBagActivity.this);
        content = (RippleBackground) findViewById(R.id.content);
        content.setOnClickListener(FindBagActivity.this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.centerImage) {
            content.startRippleAnimation();
            centerImage.start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            content.stopRippleAnimation();
                            centerImage.stop();
                        }
                    });

                }
            }).start();
        }
    }
}
