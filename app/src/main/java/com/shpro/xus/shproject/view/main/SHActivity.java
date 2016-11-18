package com.shpro.xus.shproject.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;

import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.user.Account;
import com.shpro.xus.shproject.bean.user.User;
import com.shpro.xus.shproject.db.cache.ACacheUtil;
import com.shpro.xus.shproject.util.AndroidIDUtil;
import com.shpro.xus.shproject.util.ToastUtil;
import com.shpro.xus.shproject.view.BaseActivity;
import com.shpro.xus.shproject.view.user.LoginActivity;
import com.shpro.xus.shproject.view.user.UpdateUserAvtivity;
import com.shpro.xus.shproject.view.views.TaiJiView;

import java.sql.SQLException;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by xus on 2016/11/10.
 */

public class SHActivity extends BaseActivity {
    TaiJiView taiji;
    Handler handler = new Handler();
    AnimationSet animationSet;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContentView());
        try {
            initView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int setContentView() {
        return R.layout.sh_activity;
    }

    @Override
    public void initView() throws SQLException {
        taiji = (TaiJiView) findViewById(R.id.taiji);
        animationSet = new AnimationSet(true);
        ScaleAnimation animation_suofang = new ScaleAnimation(1.0f, 5.0f, 1.0f, 5.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        animation_suofang.setDuration(2700);                     //执行时间
        RotateAnimation rotateAnimation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(2500);                     //执行时间

        rotateAnimation.setRepeatCount(0);
        rotateAnimation.setFillAfter(false);

        animationSet.addAnimation(animation_suofang);
        animationSet.addAnimation(rotateAnimation);
        if (BmobUser.getCurrentUser(Account.class) != null && (!TextUtils.isEmpty(BmobUser.getCurrentUser(Account.class).getUserid())) && ACacheUtil.getInstance().getObject(AndroidIDUtil.getID(this), User.class) != null) {
            BmobQuery<User> query = new BmobQuery<User>();
            query.getObject(BmobUser.getCurrentUser(Account.class).getUserid(), new QueryListener<User>() {

                @Override
                public void done(User object, BmobException e) {
                    if (e == null) {
                        ACacheUtil.getInstance().cacheObject(AndroidIDUtil.getID(SHActivity.this), object);
                        going();
                    } else {
                        ToastUtil.makeTextShort(SHActivity.this, "加载失败了！");
                    }
                }

            });

        } else {

            going();
        }
    }

    private void going() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                taiji.startAnimation(animationSet);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SHActivity.this.finish();
                        if (BmobUser.getCurrentUser(Account.class) != null) {
                            if (TextUtils.isEmpty(BmobUser.getCurrentUser(Account.class).getUserid())) {

                                SHActivity.this.startActivity(new Intent(SHActivity.this, UpdateUserAvtivity.class));
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            } else {
                                SHActivity.this.startActivity(new Intent(SHActivity.this, SHMainActivity.class));
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            }
                        } else {
                            SHActivity.this.startActivity(new Intent(SHActivity.this, LoginActivity.class));
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        }
                    }
                }, 2500);
            }
        }, 1000);
    }
}
