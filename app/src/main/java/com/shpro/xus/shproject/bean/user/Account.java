package com.shpro.xus.shproject.bean.user;

import cn.bmob.v3.BmobUser;

/**
 * Created by xus on 2016/11/9.
 */
public class Account extends BmobUser {
    private String shid;//android唯一设备号
    public String getShid() {
        return shid;
    }

    public void setShid(String shid) {
        this.shid = shid;
    }
}
