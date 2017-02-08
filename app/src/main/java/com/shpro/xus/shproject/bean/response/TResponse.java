package com.shpro.xus.shproject.bean.response;

/**
 * Created by xus on 2017/2/8.
 */

public class TResponse<T extends BaseResponse> extends Response {
    private T msg;

    public void setMsg(T msg) {
        this.msg = msg;
    }

    public T getMsg() {
        return msg;
    }
}
