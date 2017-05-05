package com.shpro.xus.shproject.bean.hai;

import com.shpro.xus.shproject.bean.Basebean;

/**
 * Created by xus on 2017/5/4.
 */

public class HaiBean extends Basebean{
    private String rc;
    private String operation;
    private String service;
    private Answer answer;
    private String text;
    private WeatherList data;

    public WeatherList getData() {
        return data;
    }

    public void setData(WeatherList data) {
        this.data = data;
    }

    public String getRc() {
        return rc;
    }

    public void setRc(String rc) {
        this.rc = rc;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
