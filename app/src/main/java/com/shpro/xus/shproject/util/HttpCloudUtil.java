package com.shpro.xus.shproject.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.shpro.xus.shproject.bean.response.BaseResponse;
import com.shpro.xus.shproject.bean.response.Response;
import com.shpro.xus.shproject.bean.response.TResponse;

import org.json.JSONObject;

import java.util.Map;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CloudCodeListener;

/**
 * Created by xus on 2017/2/8.
 */

public class HttpCloudUtil {
    private static Gson gson = new Gson();

    public static <T extends BaseResponse> void post(Map<String, String> params, String cloudCodeName, final OnMessageGet<T> onMessageGet) {
        JSONObject mparams = new JSONObject();
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
        ace.callEndpoint(cloudCodeName, mparams, new CloudCodeListener() {
            @Override
            public void done(Object object, BmobException e) {
                if (e == null) {
                    String result = object.toString();

                    Response response = gson.fromJson(result, Response.class);
                    if (response == null) {
                        onMessageGet.onError("response is null");
                        return;
                    }
                    if ( response.getCode() != 200 ){
                        onMessageGet.onError("Code is "+response.getCode());
                        return;
                    }
                    try{
                        TResponse t= gson.fromJson(result,new TResponse<T>().getClass());
                        onMessageGet.onSuccess((T) t.getMsg());
                    }catch (Exception ex){
                        onMessageGet.onError(ex.toString());
                    }

                } else {
                    onMessageGet.onError(e.toString());
                }
            }
        });
    }

    public interface  OnMessageGet<T extends BaseResponse> {
        void onSuccess(T s);

        void onError(String s);
    }
}
