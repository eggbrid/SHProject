package com.shpro.xus.shproject.bean.response;

import com.shpro.xus.shproject.bean.baisi.ShowBaiSiBean;
import com.shpro.xus.shproject.bean.people.NearPeople;

import java.util.List;

/**
 * Created by xus on 2017/3/27.
 */

public class BaisiResponse extends Response {
    private List<ShowBaiSiBean> list;

    public List<ShowBaiSiBean> getList() {
        return list;
    }

    public void setList(List<ShowBaiSiBean> list) {
        this.list = list;
    }
}
