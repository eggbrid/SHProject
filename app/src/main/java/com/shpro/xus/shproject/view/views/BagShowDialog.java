package com.shpro.xus.shproject.view.views;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.Bag;
import com.shpro.xus.shproject.util.CommentUtil;
import com.shpro.xus.shproject.util.ImageLoaderUtil;
import com.shpro.xus.shproject.view.BaseActivity;

/**
 * Created by xus on 2016/11/18.
 */

public class BagShowDialog extends Dialog implements View.OnClickListener {
    protected ImageView icon;
    protected TextView info;
//    protected Button use;
    protected Button d;
    protected Bag bag;
    protected BaseActivity context;

    public BagShowDialog(BaseActivity context, Bag bag) {
        super(context, R.style.Dialog_FullscreenB);
        this.bag = bag;
        this.context = context;
    }

    public BagShowDialog(BaseActivity context, int themeResId) {
        super(context, R.style.Dialog_FullscreenB);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_show_bag);
        initView();
    }


    @Override
    public void onClick(View view) {
     /*   if (view.getId() == R.id.use) {
            BagUtil.getInstance().addBag(context, bag, true, 1, new BagUtil.OnSuccess() {
                @Override
                public void onSuccess(int flg) {
                    BagShowDialog.this.dismiss();
                }
            });
        } else*/ if (view.getId() == R.id.d) {
            this.dismiss();
        }
    }

    private void initView() {
        icon = (ImageView) findViewById(R.id.icon);
        info = (TextView) findViewById(R.id.info);
//        use = (Button) findViewById(R.id.use);
//        use.setOnClickListener(BagShowDialog.this);
        d = (Button) findViewById(R.id.d);
        d.setOnClickListener(BagShowDialog.this);
        info.setText(bag.getBagTemplate().getInfo());
//        if (bag.getBagTemplate().getAction().equals("1") || bag.getBagTemplate().getAction().equals("3")) {
//            use.setVisibility(View.VISIBLE);
//        } else {
//            use.setVisibility(View.GONE);
//        }
        if (TextUtils.isEmpty(bag.getBagTemplate().getIcon())) {
            icon.setImageResource(R.drawable.shpg_unno);
        } else if (bag.getBagTemplate().getIcon().startsWith("http:")) {
            ImageLoaderUtil.getInstance().loadNomalImage(bag.getBagTemplate().getIcon(), icon);
        } else if (bag.getBagTemplate().getIcon().startsWith("shpg")) {
            icon.setImageResource(CommentUtil.getIcon(bag.getBagTemplate().getIcon(), context));
        } else {
            icon.setImageResource(R.drawable.shpg_unno);
        }

    }


}
