package com.shpro.xus.shproject.bean.call;

import com.shpro.xus.shproject.bean.SHBean;

import java.io.Serializable;

/**
 * Created by xus on 2016/12/2.
 */

public class CallPeople extends SHBean  {
    private String id;
    private String avatar;
    private String name;
    private int unRead;

    public int getUnRead() {
        return unRead;
    }

    public void setUnRead(int unRead) {
        this.unRead = unRead;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
