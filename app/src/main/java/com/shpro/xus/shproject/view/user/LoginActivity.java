package com.shpro.xus.shproject.view.user;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shpro.xus.shproject.APP;
import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.response.LoginResponse;
import com.shpro.xus.shproject.db.cache.ACacheUtil;
import com.shpro.xus.shproject.shprojectHttp.Url.UrlUtil;
import com.shpro.xus.shproject.shprojectHttp.okhttp.OkHttpUtil;
import com.shpro.xus.shproject.shprojectHttp.okhttp.interfaces.CallBack;
import com.shpro.xus.shproject.util.ConstantUtil;
import com.shpro.xus.shproject.util.MD5Util;
import com.shpro.xus.shproject.util.ToastUtil;
import com.shpro.xus.shproject.view.main.SHMainActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xus on 2016/11/8.
 */

public class LoginActivity extends UserBaseActivity implements View.OnClickListener {
    private EditText email;
    private EditText password;
    private Button login;
    private Button reg;

    @Override
    public void getData() {

    }

    @Override
    public int setContentView() {
        return R.layout.login_activity;
    }

    @Override
    public void initView() {
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        reg = (Button) findViewById(R.id.reg);
        login.setOnClickListener(this);
        reg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                login();
                break;
            case R.id.reg:
                this.startActivity(new Intent(this, RegActivity.class));
                break;
        }
    }

    public void login() {
        String emails = email.getText().toString();
        String passwords = password.getText().toString();
        if (TextUtils.isEmpty(emails)) {
            ToastUtil.makeTextShort(this, "请输入邮箱");
            return;
        }
        if (TextUtils.isEmpty(passwords)) {
            ToastUtil.makeTextShort(this, "请输入密码");
            return;
        }
        showPross("正在登陆");
        Map<String, String> map = new HashMap<>();
        map.put("number", emails);
        map.put("pwd", MD5Util.md5(passwords));

        OkHttpUtil.doPost(this, UrlUtil.LOGIN_URL, map, new CallBack<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse loginResponse) {
                dissPross();
                if (loginResponse != null) {
                    APP.getInstance().setLoginResponse(loginResponse);
                    if (loginResponse.getUserDetail() == null) {
                        LoginActivity.this.startActivity(new Intent(LoginActivity.this, UpdateUserAvtivity.class));
                    } else {
                        Intent intent = new Intent(LoginActivity.this, SHMainActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        LoginActivity.this.startActivity(intent);
                    }
                } else {
                    ToastUtil.makeTextShort(LoginActivity.this, "哎？连接失败？退出app看看？");
                }
            }

            @Override
            public void onError(String s) {
                dissPross();
                ToastUtil.makeTextShort(LoginActivity.this, s);

            }
        }, LoginResponse.class);
    }


}
