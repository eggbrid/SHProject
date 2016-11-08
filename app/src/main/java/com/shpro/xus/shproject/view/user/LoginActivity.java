package com.shpro.xus.shproject.view.user;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shpro.xus.shproject.R;
import com.wilddog.wilddogauth.WilddogAuth;
import com.wilddog.wilddogauth.core.Task;
import com.wilddog.wilddogauth.core.listener.OnCompleteListener;
import com.wilddog.wilddogauth.core.result.AuthResult;

/**
 * Created by xus on 2016/11/8.
 */

public class LoginActivity extends UserBaseActivity implements View.OnClickListener {
    private EditText email;
    private EditText password;
    private Button login;
    private Button reg;
    private WilddogAuth res;

    @Override
    public void getData(WilddogAuth res) {
        this.res = res;
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
                this.startActivity(new Intent(this,RegActivity.class));
                break;
        }
    }

    public void login() {
        String emails = email.getText().toString();
        String passwords = password.getText().toString();
        res.signInWithEmailAndPassword(emails, passwords).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> var1) {
                if (var1.isSuccessful()) {
                    Log.d("success", "Login success!");  // 登录成功
                    Log.d("Anonymous", String.valueOf(var1.getResult().toString()));
                } else {
                    Log.d("failure", "reason:" + var1.getException().toString()); // 登录失败及错误信息
                }
            }
        });
    }
}
