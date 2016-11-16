package com.shpro.xus.shproject.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;

import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.user.Account;
import com.shpro.xus.shproject.view.user.LoginActivity;
import com.shpro.xus.shproject.view.views.TaiJiView;

import cn.bmob.v3.BmobUser;

/**
 * Created by xus on 2016/11/10.
 */

public class SHActivity extends BaseActivity {
    TaiJiView taiji;
    Handler handler=new Handler(){

    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContentView());
        initView();
    }

    @Override
    public int setContentView() {
        return R.layout.sh_activity;
    }

    @Override
    public void initView() {
        taiji=(TaiJiView)findViewById(R.id.taiji) ;
        final AnimationSet animationSet = new AnimationSet(true);
         ScaleAnimation animation_suofang =new ScaleAnimation(1.0f, 5.0f, 1.0f, 5.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        animation_suofang.setDuration(2700);                     //执行时间
        RotateAnimation rotateAnimation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(2500);                     //执行时间

        rotateAnimation.setRepeatCount(0);
        rotateAnimation.setFillAfter(false);

        animationSet.addAnimation(animation_suofang);
        animationSet.addAnimation(rotateAnimation);
        taiji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taiji.startAnimation(animationSet);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SHActivity.this.finish();
                        if (BmobUser.getCurrentUser(Account.class)!=null) {
                            if (TextUtils.isEmpty(BmobUser.getCurrentUser(Account.class).getUserid())){
                                SHActivity. this.startActivity(new Intent(SHActivity.this,MainActivity.class));
                                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

                            }else{

                            }

                        }else{
                            SHActivity.this.startActivity(new Intent(SHActivity.this,LoginActivity.class));
                            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                        }
                    }
                },2500);
            }
        });


    }
}
