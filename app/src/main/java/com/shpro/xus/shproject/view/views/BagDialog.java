package com.shpro.xus.shproject.view.views;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.Bag;
import com.shpro.xus.shproject.bean.user.User;
import com.shpro.xus.shproject.bean.user.UserBag;
import com.shpro.xus.shproject.db.cache.ACacheUtil;
import com.shpro.xus.shproject.util.AndroidIDUtil;
import com.shpro.xus.shproject.util.CommentUtil;
import com.shpro.xus.shproject.util.ImageLoaderUtil;
import com.shpro.xus.shproject.util.ToastUtil;
import com.shpro.xus.shproject.view.main.SHMainActivity;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by xus on 2016/11/18.
 */

public class BagDialog extends Dialog implements View.OnClickListener {
    protected ImageView icon;
    protected TextView info;
    protected Button use;
    protected Button d;
    protected Button back;
    protected Bag bag;
    protected Context context;
    protected OnDialogChange onDialogChange;

    public BagDialog(Context context, Bag bag, OnDialogChange onDialogChange) {
        super(context, R.style.Dialog_Fullscreen);
        this.bag = bag;
        this.context = context;
        this.onDialogChange = onDialogChange;
    }

    public BagDialog(Context context, int themeResId) {
        super(context, R.style.Dialog_Fullscreen);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bag);
        initView();
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.use) {
            this.dismiss();
            if (bag.getAction().equals("1")) {
                try {
                    context.startActivity(new Intent(context, Class.forName(bag.getActionInfo())));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                ToastUtil.makeTextShort(context, "暂时不支持使用该物品");
            }

        } else if (view.getId() == R.id.d) {
            final UserBag userBags = ACacheUtil.getInstance().getObject(AndroidIDUtil.getID(context) + "bag", UserBag.class);
            for (Bag b : userBags.getBags()) {
                if (b.getActionInfo().equals(bag.getActionInfo()) && b.getName().equals(bag.getName()) && b.getIcon().equals(bag.getIcon())) {
                    userBags.getBags().remove(b);
                }
            }
            UserBag userBag = new UserBag();
            userBags.update(userBags.getObjectId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    BagDialog.this.dismiss();
                    if (e == null) {
                        ACacheUtil.getInstance().cacheObject(AndroidIDUtil.getID(context) + "bag", userBags);
                        onDialogChange.onChagne();
                    } else {
                        ToastUtil.makeTextShort(context, "丢弃失败了");
                    }

                }
            });

        } else if (view.getId() == R.id.back) {
            this.dismiss();

        }
    }

    private void initView() {
        icon = (ImageView) findViewById(R.id.icon);
        info = (TextView) findViewById(R.id.info);
        use = (Button) findViewById(R.id.use);
        use.setOnClickListener(BagDialog.this);
        d = (Button) findViewById(R.id.d);
        d.setOnClickListener(BagDialog.this);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(BagDialog.this);
        info.setText(bag.getInfo());
        if (bag.getAction().equals("1") || bag.getAction().equals("3")) {
            use.setVisibility(View.VISIBLE);
        } else {
            use.setVisibility(View.GONE);
        }
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

    public interface OnDialogChange {
        void onChagne();
    }
}
