package com.shpro.xus.shproject.bean.user;

import com.shpro.xus.shproject.bean.Bag;
import com.shpro.xus.shproject.bean.SHBean;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by xus on 2016/11/18.
 */

public class UserBag extends SHBean {
    private String userid;//用户属性关联建
    private List<Bag> bags;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public List<Bag> getBags() {
        return bags;
    }

    public void setBags(List<Bag> bags) {
        this.bags = bags;
    }
}
