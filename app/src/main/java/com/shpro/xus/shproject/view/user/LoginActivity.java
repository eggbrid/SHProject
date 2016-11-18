package com.shpro.xus.shproject.view.user;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.user.Account;
import com.shpro.xus.shproject.bean.user.User;
import com.shpro.xus.shproject.db.cache.ACacheUtil;
import com.shpro.xus.shproject.util.AndroidIDUtil;
import com.shpro.xus.shproject.util.ToastUtil;
import com.shpro.xus.shproject.view.main.SHActivity;
import com.shpro.xus.shproject.view.main.SHMainActivity;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

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
        Account bu2 = new Account();
        bu2.setUsername(emails);
        bu2.setPassword(passwords);
        bu2.login(new SaveListener<Account>() {

            @Override
            public void done(Account bmobUser, BmobException e) {
                dissPross();
                if (e == null) {
                    if (TextUtils.isEmpty(BmobUser.getCurrentUser(Account.class).getUserid())) {
                        BmobQuery<User> query = new BmobQuery<User>();
                        query.getObject(BmobUser.getCurrentUser(Account.class).getUserid(), new QueryListener<User>() {

                            @Override
                            public void done(User object, BmobException e) {
                                if (e == null) {
                                    ACacheUtil.getInstance().cacheObject(AndroidIDUtil.getID(LoginActivity.this), object);
                                    LoginActivity.this.startActivity(new Intent(LoginActivity.this, UpdateUserAvtivity.class));
                                } else {
                                    ToastUtil.makeTextShort(LoginActivity.this, "加载失败了！");
                                }
                            }

                        });
                    } else {
                        Intent intent = new Intent(LoginActivity.this, SHMainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        LoginActivity.this.startActivity(intent);
                    }
                    //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
                    //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户信息
                } else {
                    ToastUtil.makeTextShort(LoginActivity.this, e.toString());
                }
            }
        });
    }
}
