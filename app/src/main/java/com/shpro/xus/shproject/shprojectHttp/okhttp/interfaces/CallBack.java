package com.shpro.xus.shproject.shprojectHttp.okhttp.interfaces;

/**
 * Created by xus on 2017/3/24.
 */

public interface CallBack<P> {
    void onSuccess(P p);
    void onError(String s);

}
