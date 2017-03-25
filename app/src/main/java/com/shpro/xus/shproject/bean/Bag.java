package com.shpro.xus.shproject.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by xus on 2016/11/18.
 */

public class Bag extends Basebean {
    private String userId;
    private BagTemplate bagTemplate;
    private String bagTemplateId;

    public String getBagTemplateId() {
        return bagTemplateId;
    }

    public void setBagTemplateId(String bagTemplateId) {
        this.bagTemplateId = bagTemplateId;
    }

    public BagTemplate getBagTemplate() {
        return bagTemplate;
    }

    public void setBagTemplate(BagTemplate bagTemplate) {
        this.bagTemplate = bagTemplate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
