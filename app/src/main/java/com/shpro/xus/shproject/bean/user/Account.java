package com.shpro.xus.shproject.bean.user;

import cn.bmob.v3.BmobUser;

/**
 * Created by xus on 2016/11/9.
 */
public class Account extends BmobUser {
    private String shid;//android唯一设备号passwords
    private String userid;//用户属性关联建

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getShid() {
        return shid;
    }

    public void setShid(String shid) {
        this.shid = shid;
    }
}
