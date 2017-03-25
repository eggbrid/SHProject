package com.shpro.xus.shproject.bean.user;

import com.shpro.xus.shproject.bean.Basebean;

/**
 * Created by xus on 2016/11/9.
 */
public class User extends Basebean {
    private String number;
    private String token;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
