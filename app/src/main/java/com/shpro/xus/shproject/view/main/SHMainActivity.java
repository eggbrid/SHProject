package com.shpro.xus.shproject.view.main;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.Bag;
import com.shpro.xus.shproject.bean.user.Account;
import com.shpro.xus.shproject.bean.user.User;
import com.shpro.xus.shproject.bean.user.UserBag;
import com.shpro.xus.shproject.db.cache.ACacheUtil;
import com.shpro.xus.shproject.util.AndroidIDUtil;
import com.shpro.xus.shproject.util.ToastUtil;
import com.shpro.xus.shproject.view.CommentActivity;
import com.shpro.xus.shproject.view.main.adapter.MainAdapter;
import com.shpro.xus.shproject.view.views.BagDialog;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by xus on 2016/11/15.
 */

public class SHMainActivity extends CommentActivity implements AdapterView.OnItemClickListener {
    protected GridView mainGrid;
    private MainAdapter adapter;
    private List<Bag> bags;

    @Override
    public int setContentView() {
        return R.layout.sh_main_activity;
    }

    @Override
    public void initView() {
        initTitleView();
        titleText.setText("背包");
        left.setVisibility(View.VISIBLE);
        right.setVisibility(View.VISIBLE);
        right.setImageResource(R.drawable.map);
        left.setImageResource(R.drawable.help);
        adapter = new MainAdapter(this);
        left = (ImageButton) findViewById(R.id.left);
        titleText = (TextView) findViewById(R.id.title_text);
        right = (ImageButton) findViewById(R.id.right);
        title = (LinearLayout) findViewById(R.id.title);
        mainGrid = (GridView) findViewById(R.id.main_grid);
        mainGrid.setOnItemClickListener(this);
        getBgaList();

    }

    public void reBag() {
        UserBag userBags = ACacheUtil.getInstance().getObject(AndroidIDUtil.getID(this) + "bag", UserBag.class);
        adapter.setUserBag(userBags);
        mainGrid.setAdapter(adapter);
        adapter.notifyDataSetChanged();
//        BagDialog
    }

    public void getBgaList() {

        BmobQuery<UserBag> query = new BmobQuery<UserBag>();
        query.getObject(BmobUser.getCurrentUser(Account.class).getPgid(), new QueryListener<UserBag>() {
            @Override
            public void done(UserBag userBag, BmobException e) {
                if (e == null) {
                    ACacheUtil.getInstance().cacheObject(AndroidIDUtil.getID(SHMainActivity.this) + "bag", userBag);
                    bags = userBag.getBags();
                    reBag();
                } else {
                    ToastUtil.makeTextShort(SHMainActivity.this, "哎呀！背包进入了异次元，重新打开app试试吧");
                }
            }
        });


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        BagDialog bagDialog = new BagDialog(this, bags.get(i), new BagDialog.OnDialogChange() {
            @Override
            public void onChagne() {
                reBag();
            }
        });
        bagDialog.show();
    }
}
