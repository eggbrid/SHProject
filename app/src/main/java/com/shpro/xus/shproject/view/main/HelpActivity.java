package com.shpro.xus.shproject.view.main;

import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.user.User;
import com.shpro.xus.shproject.db.cache.ACacheUtil;
import com.shpro.xus.shproject.util.AndroidIDUtil;
import com.shpro.xus.shproject.view.CommentActivity;

import me.grantland.widget.AutofitTextView;

/**
 * Created by xus on 2016/11/18.
 */

public class HelpActivity extends CommentActivity {
    protected AutofitTextView text;

    @Override
    public int setContentView() {
        return R.layout.activity_help;
    }

    @Override
    public void initView() throws Exception {
        setCommentTitleView("新手帮助");
        text = (AutofitTextView) findViewById(R.id.text);
        text.setText(ACacheUtil.getInstance().getObject(AndroidIDUtil.getID(this), User.class).getName()+",您好+\n也许您第一次使用" +
                "这个app");
    }
}
