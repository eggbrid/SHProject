package com.shpro.xus.shproject.view.main;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.Bag;
import com.shpro.xus.shproject.bean.user.User;
import com.shpro.xus.shproject.db.cache.ACacheUtil;
import com.shpro.xus.shproject.util.AndroidIDUtil;
import com.shpro.xus.shproject.util.BagUtil;
import com.shpro.xus.shproject.view.CommentActivity;

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
        text.setText(ACacheUtil.getInstance().getObject(AndroidIDUtil.getID(this), User.class).getName() + ",您好+\n也许您第一次使用" +
                "这个app");
        getCall = (Button) findViewById(R.id.get_call);
        getCall.setOnClickListener(HelpActivity.this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.get_call) {
            Bag bag =new Bag();
            bag.setAction("1");
            bag.setName("通讯器");
            bag.setIcon("shpg_call");
            bag.setActionInfo("com.shpro.xus.shproject.view.call.CallListActivity");
            bag.setInfo("可以聊天的通讯器");
            BagUtil.getInstance().addBag(this, bag, true, 1, new BagUtil.OnSuccess() {
                @Override
                public void onSuccess(int flg) {
                    HelpActivity.this.finish();
                }
            });
        }
    }
}
