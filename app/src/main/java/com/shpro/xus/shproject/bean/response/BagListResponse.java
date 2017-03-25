package com.shpro.xus.shproject.bean.response;

import com.shpro.xus.shproject.bean.Bag;

import java.util.List;

/**
 * Created by xus on 2017/3/25.
 */

public class BagListResponse extends Response {
    private List<Bag> list;

    public List<Bag> getList() {
        return list;
    }

    public void setList(List<Bag> list) {
        this.list = list;
    }
}
