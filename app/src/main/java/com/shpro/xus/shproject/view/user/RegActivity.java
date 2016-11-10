package com.shpro.xus.shproject.view.user;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.user.Account;
import com.shpro.xus.shproject.util.AndroidIDUtil;
import com.shpro.xus.shproject.util.ToastUtil;
import com.wilddog.wilddogauth.WilddogAuth;
import com.wilddog.wilddogauth.core.Task;
import com.wilddog.wilddogauth.core.listener.OnCompleteListener;
import com.wilddog.wilddogauth.core.result.AuthResult;

import cn.bmob.sms.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static android.R.attr.id;

/**
 * Created by xus on 2016/11/8.
 */

public class RegActivity extends UserBaseActivity implements View.OnClickListener {
    private EditText email;

    private EditText password;

    private EditText repassword;

    private Button reg;
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
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        repassword = (EditText) findViewById(R.id.repassword);
        reg = (Button) findViewById(R.id.reg);
        reg.setOnClickListener(this);
        setCommentTitleView("注册");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reg:
                reg();
                break;

        }
    }

    private void reg() {
        if (TextUtils.isEmpty(email.getText().toString())) {
            ToastUtil.makeTextShort(this, "请输入邮箱");
        }
        if (TextUtils.isEmpty(password.getText().toString())) {
            ToastUtil.makeTextShort(this, "请输入密码");
        }

        if (TextUtils.isEmpty(repassword.getText().toString()) || !repassword.getText().toString().equals(password.getText().toString())) {
            ToastUtil.makeTextShort(this, "两次密码不一致");
        }
        Account bu = new Account();
        bu.setPassword(repassword.getText().toString());
        bu.setUsername(email.getText().toString());
        bu.setEmail(email.getText().toString());
        bu.setShid(AndroidIDUtil.getID(this));
        //注意：不能用save方法进行注册
        bu.signUp(new SaveListener<Account>() {
            @Override
            public void done(Account account, cn.bmob.v3.exception.BmobException e) {
                if (e == null) {
                    ToastUtil.makeTextShort(RegActivity.this, "注册成功");
                    RegActivity.this.finish();
                } else {
                    ToastUtil.makeTextShort(RegActivity.this, e.toString());
                }
            }

        });
    }
}
