package com.shpro.xus.shproject.bean.user;

import com.shpro.xus.shproject.bean.SHBean;
import com.shpro.xus.shproject.bean.people.Self;

import cn.bmob.v3.BmobObject;

/**
 * Created by xus on 2016/11/9.
 */
public class User extends BmobObject {
    private String type;
    private int sex;
    private Self self;
    private String name;
    private String avatar;
    private String accountId;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Self getSelf() {
        return self;
    }

    public void setSelf(Self self) {
        this.self = self;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
