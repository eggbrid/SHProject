package com.shpro.xus.shproject.view.main;

import android.view.View;
import android.widget.Button;

import com.shpro.xus.shproject.APP;
import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.Bag;
import com.shpro.xus.shproject.bean.response.BagListResponse;
import com.shpro.xus.shproject.shprojectHttp.Url.UrlUtil;
import com.shpro.xus.shproject.shprojectHttp.okhttp.OkHttpUtil;
import com.shpro.xus.shproject.shprojectHttp.okhttp.interfaces.CallBack;
import com.shpro.xus.shproject.util.ToastUtil;
import com.shpro.xus.shproject.view.CommentActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.grantland.widget.AutofitTextView;

/**
 * Created by xus on 2016/11/18.
 */

public class HelpActivity extends CommentActivity implements View.OnClickListener {
    protected AutofitTextView text;
    protected Button getCall;

    @Override
    public int setContentView() {
        return R.layout.activity_help;
    }

    @Override
    public void initView() throws Exception {
        setCommentTitleView("新手帮助");
        text = (AutofitTextView) findViewById(R.id.text);
//        text.setText(ACacheUtil.getInstance().getObject(AndroidIDUtil.getID(this), User.class).getName() + ",您好+\n也许您第一次使用" +
//                "这个app");
        getCall = (Button) findViewById(R.id.get_call);
        getCall.setOnClickListener(HelpActivity.this);
    }

    @Override
    public void onClick(View view) {
        showPross("正在将通讯器放入包裹");
        Map<String, String> map = new HashMap<>();
        map.put("userId", APP.getInstance().getUser().getId());
        map.put("templateId","1002");
        OkHttpUtil.doPost(this, UrlUtil.ADDBAG_URL, map, new CallBack<BagListResponse>() {
            @Override
            public void onSuccess(BagListResponse bagListResponse) {
                dissPross();
                if (bagListResponse != null) {
                    if (bagListResponse.getList() == null) {
                        bagListResponse.setList(new ArrayList<Bag>());
                    }
                    APP.getInstance().setBags(bagListResponse.getList());
                    HelpActivity.this.finish();
                } else {
                    ToastUtil.makeTextShort(HelpActivity.this, "空间戒没有空间波动！");
                }
            }

            @Override
            public void onError(String s) {
                dissPross();
                ToastUtil.makeTextShort(HelpActivity.this, s);

            }
        }, BagListResponse.class);
    }
}
