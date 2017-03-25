package com.shpro.xus.shproject.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.shpro.xus.shproject.APP;
import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.response.LoginResponse;
import com.shpro.xus.shproject.db.cache.ACacheUtil;
import com.shpro.xus.shproject.util.ConstantUtil;
import com.shpro.xus.shproject.view.BaseActivity;
import com.shpro.xus.shproject.view.user.LoginActivity;
import com.shpro.xus.shproject.view.user.UpdateUserAvtivity;
import com.shpro.xus.shproject.view.views.TaiJiView;

/**
 * Created by xus on 2016/11/10.
 */

public class SHActivity extends BaseActivity {
    private TaiJiView taiji;
    private TextView message_info;
    private NumberProgressBar number_progress_bar;
    private final int INIT_APP = 1;
    private final int GET_USER_INFO = 2;
    private final int GET_MESSAGE_BY_NET = 3;
    private int progress = 0;
    private LoginResponse loginResponse;
    private int userStatus = 0;//0 无状态 1 有用户 2无用户
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case INIT_APP:
                    message_info.setText("正在初始化app");
                    if (progress >= 30) {
                        progress++;
                        Message message = new Message();
                        message.what = GET_USER_INFO;
                        message.arg1 = progress;
                        handler.sendMessageDelayed(message, 30);
                    } else {
                        progress++;
                        number_progress_bar.setProgress(progress);
                        Message message = new Message();
                        message.what = INIT_APP;
                        message.arg1 = progress;

                        handler.sendMessageDelayed(message, 30);
                    }

                    break;
                case GET_USER_INFO:
                    message_info.setText("正在获取用户信息");
                    if (userStatus == 0) {
                        if (loginResponse == null) {
                            loginResponse = APP.getInstance().getLoginResponse();
                        }
                        userStatus = (loginResponse == null) ? 2 : 1;
                    }
                    if (progress >= 70) {
                        progress++;
                        Message message = new Message();
                        message.what = GET_MESSAGE_BY_NET;
                        message.arg1 = progress;
                        handler.sendMessageDelayed(message, 30);
                    } else {
                        progress++;
                        number_progress_bar.setProgress(progress);
                        Message message = new Message();
                        message.what = GET_USER_INFO;
                        message.arg1 = progress;
                        handler.sendMessageDelayed(message, 30);
                    }
                    break;
                case GET_MESSAGE_BY_NET:
                    message_info.setText("正在监测版本");
                    if (progress >= 100) {
                        going();
                    } else {
                        progress++;
                        number_progress_bar.setProgress(progress);
                        Message message = new Message();
                        message.what = GET_MESSAGE_BY_NET;
                        message.arg1 = progress;
                        handler.sendMessageDelayed(message, 30);
                    }
                    break;
            }
        }
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
        taiji = (TaiJiView) findViewById(R.id.taiji);
        message_info = (TextView) findViewById(R.id.message_info);
        number_progress_bar = (NumberProgressBar) findViewById(R.id.number_progress_bar);
        taiji.start();
        number_progress_bar.setProgress(progress);
        Message message = new Message();
        message.what = INIT_APP;
        message.arg1 = progress;
        handler.sendMessageDelayed(message, 30);
    }

    private void going() {
        if (userStatus == 2) {
            SHActivity.this.startActivity(new Intent(SHActivity.this, LoginActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            if (loginResponse.getUserDetail() == null) {
                SHActivity.this.startActivity(new Intent(SHActivity.this, UpdateUserAvtivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            } else {
                SHActivity.this.startActivity(new Intent(SHActivity.this, SHMainActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }
        taiji.stop();
        this.finish();
    }
}
