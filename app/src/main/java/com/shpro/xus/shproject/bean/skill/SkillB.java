package com.shpro.xus.shproject.bean.skill;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by xus on 2016/11/9.
 */
@DatabaseTable(tableName = "tb_skill")
public class SkillB {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(columnName = "info")
    private String info;
    @DatabaseField(columnName = "useMp")
    private int useMp;
    @DatabaseField(columnName = "hitHp")
    private int hitHp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getUseMp() {
        return useMp;
    }

    public void setUseMp(int useMp) {
        this.useMp = useMp;
    }

    public int getHitHp() {
        return hitHp;
    }

    public void setHitHp(int hitHp) {
        this.hitHp = hitHp;
    }
}
