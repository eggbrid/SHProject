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

import com.shpro.xus.shproject.APP;
import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.Bag;
import com.shpro.xus.shproject.bean.response.FindBagResponse;
import com.shpro.xus.shproject.bean.user.User;
import com.shpro.xus.shproject.bean.user.UserBag;
import com.shpro.xus.shproject.db.cache.ACacheUtil;
import com.shpro.xus.shproject.shprojectHttp.Url.UrlUtil;
import com.shpro.xus.shproject.shprojectHttp.okhttp.OkHttpUtil;
import com.shpro.xus.shproject.shprojectHttp.okhttp.interfaces.CallBack;
import com.shpro.xus.shproject.util.AndroidIDUtil;
import com.shpro.xus.shproject.util.CommentUtil;
import com.shpro.xus.shproject.util.ImageLoaderUtil;
import com.shpro.xus.shproject.util.ToastUtil;
import com.shpro.xus.shproject.view.BaseActivity;
import com.shpro.xus.shproject.view.find.FindBagActivity;
import com.shpro.xus.shproject.view.main.SHMainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    protected BaseActivity context;
    protected OnDialogChange onDialogChange;

    public BagDialog(BaseActivity context, Bag bag, OnDialogChange onDialogChange) {
        super(context, R.style.Dialog_Fullscreen);
        this.bag = bag;
        this.context = context;
        this.onDialogChange = onDialogChange;
    }

    public BagDialog(BaseActivity context, int themeResId) {
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
            if (bag.getBagTemplate().getAction().equals("1")) {
                try {
                    context.startActivity(new Intent(context, Class.forName(bag.getBagTemplate().getActionInfo())));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                ToastUtil.makeTextShort(context, "暂时不支持使用该物品");
            }

        } else if (view.getId() == R.id.d) {
            context.showPross("正在丢弃");
            Map<String, String> map = new HashMap<>();
            map.put("userId", APP.getInstance().getUser().getId());
            map.put("bagId", bag.getId());

            OkHttpUtil.doPost(context, UrlUtil.DELETEBAG_URL, map, new CallBack<FindBagResponse>() {
                @Override
                public void onSuccess(FindBagResponse findBagResponse) {
                    context. dissPross();
                    BagDialog.this.dismiss();
                    if (findBagResponse != null) {
                        List<Bag> bags = findBagResponse.getList();
                        if (bags == null) {
                            bags = new ArrayList<Bag>();
                        }
                        APP.getInstance().setBags(bags);
                        onDialogChange.onChagne();
                    } else {
                        ToastUtil.makeTextShort(context, "空间戒没有空间波动！");
                    }

                }

                @Override
                public void onError(String s) {
                    context. dissPross();
                    ToastUtil.makeTextShort(context, s);

                }
            }, FindBagResponse.class);

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
        info.setText(bag.getBagTemplate().getInfo());
        if (bag.getBagTemplate().getAction().equals("1") || bag.getBagTemplate().getAction().equals("3")) {
            use.setVisibility(View.VISIBLE);
        } else {
            use.setVisibility(View.GONE);
        }
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

    public interface OnDialogChange {
        void onChagne();
    }
}
