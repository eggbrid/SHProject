package com.shpro.xus.shproject.db.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by xus on 2017/5/5.
 */
@DatabaseTable(tableName = "tb_qiling")

public class QILing {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField()
    private int times;//每1个小时减一
    @DatabaseField()
    private long date;//时间字符串
    @DatabaseField()
    private String userId;//用户UserId

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
