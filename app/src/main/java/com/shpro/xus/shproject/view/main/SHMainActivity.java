package com.shpro.xus.shproject.view.main;

import android.content.Intent;
import android.os.SystemClock;
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
import com.shpro.xus.shproject.view.find.FindActivity;
import com.shpro.xus.shproject.view.find.FindPeopleActivity;
import com.shpro.xus.shproject.view.gone.NumViewActivity;
import com.shpro.xus.shproject.view.main.adapter.MainAdapter;
import com.shpro.xus.shproject.view.user.LoginActivity;
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
   long[] mHints = new long[5];
    public void onDisplaySettingButton(View view) {
        System.arraycopy(mHints, 1, mHints, 0, mHints.length - 1);//把从第二位至最后一位之间的数字复制到第一位至倒数第一位
        mHints[mHints.length - 1] = SystemClock.uptimeMillis();//从开机到现在的时间毫秒数
        if (SystemClock.uptimeMillis() - mHints[0] <= 1000) {//连续点击之间间隔小于一秒，有效
            startActivity(new Intent(this, NumViewActivity.class));
        }
    }
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
        right.setOnClickListener(this);
        left.setOnClickListener(this);
        titleText.setOnClickListener(this);
        getBgaList();

    }

    @Override
    protected void onResume() {
        super.onResume();
        reBag();
    }

    public void reBag() {
        UserBag userBags = ACacheUtil.getInstance().getObject(AndroidIDUtil.getID(this) + "bag", UserBag.class);
        if(userBags==null){
            bags=new ArrayList<>();
        }else{
            bags=userBags.getBags();
        }
        adapter.setBags(bags);
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
                    reBag();
                } else {
                    ToastUtil.makeTextShort(SHMainActivity.this, "哎呀！背包进入了异次元，重新打开app试试吧");
                }
            }
        });
        getUser();

    }
    public void getUser() {
        BmobQuery<User> query = new BmobQuery<User>();
        query.getObject(BmobUser.getCurrentUser(Account.class).getUserid(), new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                    ACacheUtil.getInstance().cacheObject(AndroidIDUtil.getID(SHMainActivity.this), user);
            }
        });
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (i>=bags.size()){
            return;
        }
        BagDialog bagDialog = new BagDialog(this, bags.get(i), new BagDialog.OnDialogChange() {
            @Override
            public void onChagne() {
                reBag();
            }
        });
        bagDialog.show();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.left:
                Intent intent=new Intent(this, FindActivity.class);
                startActivity(intent);
                break;
            case R.id.right:
                break;
            case R.id.title_text:
                onDisplaySettingButton(view);
                break;
        }
    }
}
