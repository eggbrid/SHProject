package com.shpro.xus.shproject.bean.response;

import com.shpro.xus.shproject.bean.people.NearPeople;

import java.util.List;

/**
 * Created by xus on 2017/3/27.
 */

public class FindPeopleResponse extends Response {
    private List<NearPeople> list;

    public List<NearPeople> getList() {
        return list;
    }

    public void setList(List<NearPeople> list) {
        this.list = list;
    }
}
