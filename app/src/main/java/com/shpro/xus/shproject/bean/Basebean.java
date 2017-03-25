package com.shpro.xus.shproject.bean;

import java.io.Serializable;

/**
 * Created by xus on 2017/3/25.
 */

public class Basebean implements Serializable {
    private String id;
    private String creatTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }
}
