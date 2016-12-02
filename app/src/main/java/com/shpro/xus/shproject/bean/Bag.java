package com.shpro.xus.shproject.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by xus on 2016/11/18.
 */

public class Bag extends SHBean {
    private String name;
    private String icon;
    private String info;
    private String action;//1打开activity 2打开dialog 3无打开操作
    private String actionInfo;
    private String other;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActionInfo() {
        return actionInfo;
    }

    public void setActionInfo(String actionInfo) {
        this.actionInfo = actionInfo;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
