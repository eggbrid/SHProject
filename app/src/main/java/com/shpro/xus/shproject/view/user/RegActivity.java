package com.shpro.xus.shproject.view.user;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shpro.xus.shproject.APP;
import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.response.LoginResponse;
import com.shpro.xus.shproject.bean.user.Account;
import com.shpro.xus.shproject.bean.user.User;
import com.shpro.xus.shproject.db.cache.ACacheUtil;
import com.shpro.xus.shproject.shprojectHttp.Url.UrlUtil;
import com.shpro.xus.shproject.shprojectHttp.okhttp.OkHttpUtil;
import com.shpro.xus.shproject.shprojectHttp.okhttp.interfaces.CallBack;
import com.shpro.xus.shproject.util.AndroidIDUtil;
import com.shpro.xus.shproject.util.MD5Util;
import com.shpro.xus.shproject.util.SHCallUtil;
import com.shpro.xus.shproject.util.ToastUtil;
import com.shpro.xus.shproject.view.main.SHMainActivity;

import java.util.HashMap;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by xus on 2016/11/8.
 */

public class RegActivity extends UserBaseActivity implements View.OnClickListener {
    private EditText email;

    private EditText password;

    private EditText repassword;

    private Button reg;

    @Override
    public void getData() {

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
        String emails = email.getText().toString();
        String passwords = password.getText().toString();
        String repasswords = repassword.getText().toString();

        if (TextUtils.isEmpty(email.getText().toString())) {
            ToastUtil.makeTextShort(this, "请输入邮箱");
        }
        if (TextUtils.isEmpty(password.getText().toString())) {
            ToastUtil.makeTextShort(this, "请输入密码");
        }
        if (TextUtils.isEmpty(repasswords) || !repasswords.equals(passwords)) {
            ToastUtil.makeTextShort(this, "两次密码不一致");
            return;
        }
        showPross("正在注册");
        Map<String, String> map = new HashMap<>();
        map.put("number", emails);
        map.put("pwd", MD5Util.md5(passwords));

        OkHttpUtil.doPost(this, UrlUtil.REGISTER_URL, map, new CallBack<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse loginResponse) {
                dissPross();
                if (loginResponse != null) {
                    APP.getInstance().setLoginResponse(loginResponse);
                    RegActivity.this.startActivity(new Intent(RegActivity.this, UpdateUserAvtivity.class));
                    RegActivity.this.finish();
                } else {
                    ToastUtil.makeTextShort(RegActivity.this, "哎？连接失败？退出app看看？");
                }
            }

            @Override
            public void onError(String s) {
                dissPross();
                ToastUtil.makeTextShort(RegActivity.this, s);

            }
        }, LoginResponse.class);
    }
}
