package com.shpro.xus.shproject.bean.response;

import java.util.List;

/**
 * Created by xus on 2017/2/8.
 */

public class TResponse<T> extends Response {
    private List<T> results;

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
