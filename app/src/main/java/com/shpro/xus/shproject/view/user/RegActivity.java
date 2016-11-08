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

import static android.R.attr.id;

/**
 * Created by xus on 2016/11/8.
 */

public class RegActivity extends UserBaseActivity implements View.OnClickListener{
    private EditText email;

    private EditText password;

    private EditText repassword;

    private Button login;
    private WilddogAuth res;

    @Override
    public void getData(WilddogAuth res) {
        this.res = res;
    }

    @Override
    public int setContentView() {
        return R.layout.reg_activity;
    }

    @Override
    public void initView() {
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        repassword=(EditText)findViewById(R.id.repassword);
        login=(Button)findViewById(R.id.login);
        login.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                reg();
                break;

        }
    }
    private void reg(){
        String emails = email.getText().toString();
        String passwords = password.getText().toString();
        res.createUserWithEmailAndPassword(emails,passwords).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> var1) {
                if (var1.isSuccessful()) {
                    Log.d("result", "Create user success");
                } else {
                    Log.d("result", "reason:" + var1.getException().toString());
                }
            }
        });
    }
}
