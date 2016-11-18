package com.shpro.xus.shproject.view.user;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.people.Self;
import com.shpro.xus.shproject.bean.user.Account;
import com.shpro.xus.shproject.bean.user.User;
import com.shpro.xus.shproject.db.cache.ACacheUtil;
import com.shpro.xus.shproject.util.AndroidIDUtil;
import com.shpro.xus.shproject.util.ImageLoaderUtil;
import com.shpro.xus.shproject.util.ToastUtil;

import java.io.File;
import java.util.Calendar;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by xus on 2016/11/15.
 */

public class UpdateUserAvtivity extends UserBaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    protected ImageView avatar;
    protected EditText name;
    protected RadioGroup sex;
    protected Button ok;
    private int sexs = 1;//1男2女
    private int type;
    private Self self;
    private String avatars;
    private String nickName;

    @Override
    public void getData() {

    }

    @Override
    public int setContentView() {
        return R.layout.activity_user_update;
    }

    @Override
    public void initView() {
        setCommentTitleView("完善信息");
        avatar = (ImageView) findViewById(R.id.avatar);
        avatar.setOnClickListener(UpdateUserAvtivity.this);
        name = (EditText) findViewById(R.id.name);
        sex = (RadioGroup) findViewById(R.id.sex);
        ok = (Button) findViewById(R.id.ok);
        ok.setOnClickListener(UpdateUserAvtivity.this);
        sex.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.avatar) {
            showPhoto(view);
        } else if (view.getId() == R.id.ok) {
            if (TextUtils.isEmpty(avatars)) {
                ToastUtil.makeTextShort(this, "痛哉！君之面貌，吾竟未见");
                return;
            }
            if (TextUtils.isEmpty(name.getText().toString())) {
                ToastUtil.makeTextShort(this, "哀哉！君之大名，吾竟未知");
                return;
            }
            nickName = name.getText().toString();
            randomRace(nickName);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        sexs = i;
    }

    @Override
    public void onFileGet(File file) {
        showPross("正在处理图片");
        final BmobFile bmobFile = new BmobFile(file);
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                dissPross();
                if (e == null) {
                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                    ImageLoaderUtil.getInstance().loadCircleImage(bmobFile.getFileUrl(), avatar);
                    avatars = bmobFile.getFileUrl();
                } else {
                    ToastUtil.makeTextShort(UpdateUserAvtivity.this, "图片被神秘力量夺走，请重新来过");

                }

            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
                showPross("正在处理图片" + value + "%");
            }
        });
    }

    public void randomRace(String nickName) {
        showPross("正在根据您的信息生成您的种族分类");
        Calendar calendar = Calendar.getInstance();
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        type = (nickName.length() + sexs + h) % 3;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dissPross();
                getSelf();
            }
        }, 2000);
    }

    public void getSelf() {
        showPross("正在生成自身属性");
        self = Self.creatSelf(type);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dissPross();
                save();
            }
        }, 2000);
    }

    public void save() {
        showPross("正在向shProject保存数据");
      final  User user = new User();
        user.setAvatar(avatars);
        user.setName(nickName);
        user.setSex(sexs);
        user.setSelf(self);
        user.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    ACacheUtil.getInstance().cacheObject(AndroidIDUtil.getID(UpdateUserAvtivity.this),user);
                    updateAccount(s);
                } else {
                    save();
                }
            }
        });
    }

    public void updateAccount(final String id) {
        Account newUser = new Account();
        newUser.setUserid(id);
       final  Account bmobUser = Account.getCurrentUser(Account.class);
        newUser.update(bmobUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    dissPross();
                    bmobUser.logOut();
                    Intent intent = new Intent(UpdateUserAvtivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    UpdateUserAvtivity.this.startActivity(intent);
                } else {
                    updateAccount(id);
                }
            }
        });
    }


}
