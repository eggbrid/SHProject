package com.shpro.xus.shproject.bean.response;

import java.io.Serializable;

/**
 * Created by xus on 2017/2/8.
 */

public class Response implements Serializable {
    private String  error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
