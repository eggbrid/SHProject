package com.shpro.xus.shproject.bean.response;

import java.io.Serializable;

/**
 * Created by xus on 2017/2/8.
 */

public class ErrorResponse implements Serializable {
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
