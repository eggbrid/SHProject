package com.shpro.xus.shproject.bean.user;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.shpro.xus.shproject.bean.people.Self;

import cn.bmob.v3.BmobObject;

/**
 * Created by xus on 2016/11/9.
 */
@DatabaseTable(tableName = "user")
public class User extends BmobObject {
    @DatabaseField(columnName = "type")
    private String type;
    @DatabaseField(columnName = "sex")
    private int sex;
    @DatabaseField(columnName = "self")
    private Self self;
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(columnName = "avatar")

    private String avatar;

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
