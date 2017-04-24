package com.shpro.xus.shproject.view.views;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.BagTemplate;
import com.shpro.xus.shproject.util.CommentUtil;
import com.shpro.xus.shproject.util.ImageLoaderUtil;
import com.shpro.xus.shproject.view.BaseActivity;

/**
 * Created by xus on 2016/11/18.
 */

public class CommentBagDialog extends Dialog implements View.OnClickListener {
    protected ImageView icon;
    protected TextView info;
    protected Button use;
    protected Button d;
    protected Button back;
    protected BagTemplate bag;
    protected Activity context;

    public CommentBagDialog setButton1(String button1) {
        use.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(button1)) {
            use.setText(button1);
        }
        return this;
    }



    public CommentBagDialog setButton2(String button2) {
        d.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(button2)) {
            d.setText(button2);
        }
        return this;

    }


    public CommentBagDialog setButton3(String button3) {
        back.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(button3)) {
            back.setText(button3);
        }
        return this;
    }


    public CommentBagDialog setButtonListener1(View.OnClickListener buttonListener1) {
        use.setVisibility(View.VISIBLE);
        if (buttonListener1 != null) {
            use.setOnClickListener(buttonListener1);
        }
        return this;

    }


    public CommentBagDialog setButtonListener2(View.OnClickListener buttonListener2) {
        d.setVisibility(View.VISIBLE);
        if (buttonListener2 != null) {
            d.setOnClickListener(buttonListener2);
        }
        return this;

    }

    public CommentBagDialog setButtonListener3(View.OnClickListener buttonListener3) {
        back.setVisibility(View.VISIBLE);
        if (buttonListener3 != null) {
            back.setOnClickListener(buttonListener3);
        }        return this;
    }

    public CommentBagDialog(BaseActivity context, BagTemplate bag) {
        super(context, R.style.Dialog_Fullscreen);
        this.bag = bag;
        this.context = context;
    }
    public CommentBagDialog(BaseActivity context, int themeResId) {
        super(context, R.style.Dialog_Fullscreen);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bag);
        initView();
    }


    private void initView() {
        icon = (ImageView) findViewById(R.id.icon);
        info = (TextView) findViewById(R.id.info);
        use = (Button) findViewById(R.id.use);
        use.setVisibility(View.GONE);
        d = (Button) findViewById(R.id.d);
        d.setVisibility(View.GONE);
        back = (Button) findViewById(R.id.back);
        back.setVisibility(View.GONE);
        info.setText(bag.getInfo());
        if (TextUtils.isEmpty(bag.getIcon())) {
            icon.setImageResource(R.drawable.shpg_unno);
        } else if (bag.getIcon().startsWith("http:")) {
            ImageLoaderUtil.getInstance().loadNomalImage(bag.getIcon(), icon);
        } else if (bag.getIcon().startsWith("shpg")) {
            icon.setImageResource(CommentUtil.getIcon(bag.getIcon(), context));
        } else {
            icon.setImageResource(R.drawable.shpg_unno);
        }

    }

    @Override
    public void onClick(View view) {

    }
}
