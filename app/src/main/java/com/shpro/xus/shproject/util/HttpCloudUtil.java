package com.shpro.xus.shproject.util;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.shpro.xus.shproject.bean.response.Response;
import com.shpro.xus.shproject.bean.response.TResponse;
import com.shpro.xus.shproject.bean.response.find.FindBagItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CloudCodeListener;

/**
 * Created by xus on 2017/2/8.
 */

public class HttpCloudUtil {
    private static Gson gson = new Gson();

    public static <T extends TResponse,N> void post(Map<String, String> params, String cloudCodeName,final Class<T> c, final OnMessageGet<N> onMessageGet) {
        JSONObject mparams = new JSONObject();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            try {
                mparams.put(entry.getKey(),entry.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
        ace.callEndpoint(cloudCodeName, mparams, new CloudCodeListener() {
            @Override
            public void done(Object object, BmobException e) {
                if (e == null) {
                    String result = object.toString();
                    Log.e("wangxu",result);
                    Response response = gson.fromJson(result, Response.class);
                    if (response == null) {
                        onMessageGet.onError("response is null");
                        return;
                    }
                    if (!TextUtils.isEmpty(response.getError()) ){
                        onMessageGet.onError(response.getError());
                        return;
                    }
                    try{
                        T t= gson.fromJson(result,c);
                        onMessageGet.onSuccess( t.getResults());
                    }catch (Exception ex){
                            onMessageGet.onError(ex.toString());
                    }

                } else {
                    onMessageGet.onError(e.toString());
                }
            }
        });
    }


    public interface  OnMessageGet<N> {
        void onSuccess(List<N> s);

        void onError(String s);
    }
}
