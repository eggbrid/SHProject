package com.shpro.xus.shproject.shprojectHttp.okhttp;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shpro.xus.shproject.shprojectHttp.okhttp.interfaces.CallBack;
import com.wilddog.wilddogauth.common.Context;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by xus on 2017/3/24.
 */

public class OkHttpUtil {
    private static OkHttpClient client = new OkHttpClient();
    private static Gson gson = new Gson();
    private static JsonParser parser = new JsonParser();

    public static void initHttpUtil() {
        client.newBuilder().connectTimeout(10, TimeUnit.SECONDS);
        client.newBuilder().readTimeout(10, TimeUnit.SECONDS);
        client.newBuilder().writeTimeout(10, TimeUnit.SECONDS);
    }

    public static <P> void doGet(final Activity context, String url, Map<String, String> map, final CallBack callBack, final Class<P> pClass) {
        String addUrl = "?";
        for (Map.Entry<String, String> entry : map.entrySet()) {
            addUrl = entry.getKey() + "=" + entry.getValue() + "&";
        }
        addUrl = addUrl.substring(0, addUrl.length() - 1);
        url = url + addUrl;
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call,final IOException e) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onError(e.toString());

                    }
                });
            }

            @Override
            public void onResponse(Call call,final  Response response) throws IOException {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            String json = response.body().string();
                            ResponseCodeBean responseCodeBean = gson.fromJson(json, ResponseCodeBean.class);
                            if (responseCodeBean.getCode().equals("1")) {
                                JsonObject jsonObject = parser.parse(json).getAsJsonObject();
                                JsonObject results = jsonObject.getAsJsonObject("Results");
                                P p = gson.fromJson(results.toString(), pClass);
                                callBack.onSuccess(p);
                            } else {
                                callBack.onError(responseCodeBean.getError());

                            }
                        } catch (Exception e) {
                            callBack.onError(e.toString());
                        }
                    }
                });

            }


        });
    }

    public static <P> void doPost(final Activity context, String url, Map<String, String> map, final CallBack callBack, final Class<P> pClass) {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            formBodyBuilder = formBodyBuilder.add(entry.getKey(), entry.getValue());
        }
        FormBody formBody = formBodyBuilder.build();
        Request request = new Request.Builder().url(url).post(formBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                callBack.onError(e.toString());
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                try {
                    String json = response.body().string();
                    ResponseCodeBean responseCodeBean = gson.fromJson(json, ResponseCodeBean.class);
                    if (responseCodeBean.getCode().equals("1")) {
                        JsonObject jsonObject = parser.parse(json).getAsJsonObject();
                        JsonObject results = jsonObject.getAsJsonObject("Results");
                        P p = gson.fromJson(results.toString(), pClass);
                        callBack.onSuccess(p);
                    } else {
                        callBack.onError(responseCodeBean.getError());

                    }
                } catch (Exception e) {
                    callBack.onError(e.toString());
                }
                    }
                });
            }


        });
    }
}
