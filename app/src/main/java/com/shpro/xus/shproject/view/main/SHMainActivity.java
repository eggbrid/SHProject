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

public class SHMainActivity extends CommentActivity implements AdapterView.OnItemClickListener{
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

    public void reBag(){
        UserBag userBags = ACacheUtil.getInstance().getObject(AndroidIDUtil.getID(this) + "bag", UserBag.class);
        adapter.setUserBag(userBags);
        mainGrid.setAdapter(adapter);
        adapter.notifyDataSetChanged();
//        BagDialog
    }
    public void getBgaList() {
        UserBag userBags = ACacheUtil.getInstance().getObject(AndroidIDUtil.getID(this) + "bag", UserBag.class);
        if (userBags == null) {
           final UserBag userBag = new UserBag();
            userBag.setBags(new ArrayList<Bag>());
            Bag bag = new Bag();
            bag.setAction("1");
            bag.setName("新手指南");
            bag.setIcon("shpg_help");
            bag.setActionInfo("com.shpro.xus.shproject.view.main.HelpActivity");
            bag.setInfo("一个看起来很新的羊皮卷，里面貌似写着字");
            userBag.getBags().add(bag);
            userBag.setUserid(BmobUser.getCurrentUser(Account.class).getUserid());
            userBag.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        bags=userBag.getBags();
                        ACacheUtil.getInstance().cacheObject(AndroidIDUtil.getID(SHMainActivity.this) + "bag", userBag);
                        reBag();
                    } else {
                        ToastUtil.makeTextShort(SHMainActivity.this, "哎呀！刚刚好像看到了什么物品，重新打开app试试吧");
                    }
                }
            });
        } else {
            BmobQuery<UserBag> query = new BmobQuery<UserBag>();
            query.getObject(BmobUser.getCurrentUser(Account.class).getUserid(), new QueryListener<UserBag>() {
                @Override
                public void done(UserBag userBag, BmobException e) {
                    if(e==null){
                        ACacheUtil.getInstance().cacheObject(AndroidIDUtil.getID(SHMainActivity.this) + "bag",userBag);
                        bags=userBag.getBags();
                        reBag();
                    }else{
                        if(e.getErrorCode()==101){

                        }
                        ToastUtil.makeTextShort(SHMainActivity.this, "哎呀！背包进入了异次元，重新打开app试试吧");
                    }
                }
            });
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        BagDialog bagDialog=new BagDialog(this, bags.get(i), new BagDialog.OnDialogChange() {
            @Override
            public void onChagne() {
           reBag();
            }
        });
        bagDialog.show();
    }
}
