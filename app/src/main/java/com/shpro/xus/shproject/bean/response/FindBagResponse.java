package com.shpro.xus.shproject.bean.response;

import com.shpro.xus.shproject.bean.Bag;
import com.shpro.xus.shproject.bean.BagTemplate;

import java.util.List;

/**
 * Created by xus on 2017/3/27.
 */

public class FindBagResponse extends Response {
    private List<Bag> list;
    private BagTemplate bagTemplate;

    public List<Bag> getList() {
        return list;
    }

    public void setList(List<Bag> list) {
        this.list = list;
    }

    public BagTemplate getBagTemplate() {
        return bagTemplate;
    }

    public void setBagTemplate(BagTemplate bagTemplate) {
        this.bagTemplate = bagTemplate;
    }
}
