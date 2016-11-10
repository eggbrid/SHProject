package com.shpro.xus.shproject.bean.user;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.shpro.xus.shproject.bean.people.Self;

/**
 * Created by xus on 2016/11/9.
 */
@DatabaseTable(tableName = "tb_user")
public class User {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "shid")
    private String shid;

    @DatabaseField(columnName = "self")
    private Self self;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShid() {
        return shid;
    }

    public void setShid(String shid) {
        this.shid = shid;
    }


    public Self getSelf() {
        return self;
    }

    public void setSelf(Self self) {
        this.self = self;
    }
}
