package com.shpro.xus.shproject.view.find;

import android.util.Log;
import android.view.View;

import com.shpro.xus.shproject.APP;
import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.Bag;
import com.shpro.xus.shproject.bean.BagTemplate;
import com.shpro.xus.shproject.bean.response.BagListResponse;
import com.shpro.xus.shproject.bean.response.FindBagResponse;
import com.shpro.xus.shproject.shprojectHttp.Url.UrlUtil;
import com.shpro.xus.shproject.shprojectHttp.okhttp.OkHttpUtil;
import com.shpro.xus.shproject.shprojectHttp.okhttp.interfaces.CallBack;
import com.shpro.xus.shproject.util.ToastUtil;
import com.shpro.xus.shproject.view.CommentActivity;
import com.shpro.xus.shproject.view.main.SHMainActivity;
import com.shpro.xus.shproject.view.views.BagShowDialog;
import com.shpro.xus.shproject.view.views.TaiJiView;
import com.skyfishjy.library.RippleBackground;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            findBag();
        }
    }

    public void findBag() {
        content.startRippleAnimation();
        centerImage.start();
        Map<String, String> map = new HashMap<>();
        map.put("userId", APP.getInstance().getUser().getId());
        OkHttpUtil.doGet(this, UrlUtil.BAGGET_URL, map, new CallBack<FindBagResponse>() {
            @Override
            public void onSuccess(FindBagResponse findBagResponse) {
                if (findBagResponse != null) {
                    List<Bag> bags = findBagResponse.getList();
                    if (bags == null) {
                        bags = new ArrayList<Bag>();
                    }
                    APP.getInstance().setBags(bags);
                    stop(findBagResponse.getBagTemplate());
                } else {
                    ToastUtil.makeTextShort(FindBagActivity.this, "空间戒没有空间波动！");
                }

            }

            @Override
            public void onError(String s) {
                error(s);
            }
        }, FindBagResponse.class);
    }

    public void stop(final BagTemplate btl) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        content.stopRippleAnimation();
                        centerImage.stop();
                        Bag bag = new Bag();
                        bag.setBagTemplate(btl);
                        BagShowDialog bagShowDialog = new BagShowDialog(FindBagActivity.this, bag);
                        bagShowDialog.show();
                    }
                });

            }
        }).start();
    }

    public void error(final String s) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        content.stopRippleAnimation();
                        centerImage.stop();
                        ToastUtil.makeTextShort(FindBagActivity.this, s);

                    }
                });

            }
        }).start();
    }
}
