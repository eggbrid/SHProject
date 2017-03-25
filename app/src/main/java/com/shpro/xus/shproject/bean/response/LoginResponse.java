package com.shpro.xus.shproject.bean.response;

import com.shpro.xus.shproject.bean.user.User;
import com.shpro.xus.shproject.bean.user.UserDetail;

/**
 * Created by xus on 2017/3/25.
 */

public class LoginResponse {
    private User user;
    private UserDetail userDetail;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }
}
