package com.shpro.xus.shproject.shprojectHttp.okhttp;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shpro.xus.shproject.shprojectHttp.Url.UrlUtil;
import com.shpro.xus.shproject.shprojectHttp.okhttp.interfaces.CallBack;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by xus on 2017/3/24.
 */

public class OkHttpUtil {
    private static OkHttpClient client = new OkHttpClient();
    private static Gson gson = new Gson();
    private static JsonParser parser = new JsonParser();

    public static void initHttpUtil() {
        UrlUtil.getUrl();
        client.newBuilder().connectTimeout(10, TimeUnit.SECONDS);
        client.newBuilder().readTimeout(10, TimeUnit.SECONDS);
        client.newBuilder().writeTimeout(10, TimeUnit.SECONDS);
    }

    public static <P> void doGet(final Activity context, String url, Map<String, String> map, final CallBack callBack, final Class<P> pClass) {
        try {

            String addUrl = "?";
            for (Map.Entry<String, String> entry : map.entrySet()) {
                addUrl = addUrl+entry.getKey() + "=" + entry.getValue() + "&";
            }
            addUrl = addUrl.substring(0, addUrl.length() - 1);
            url = url + addUrl;
            Request request = new Request.Builder().url(url).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    runUIError(context, callBack, e.toString());
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    setSuccess(context, response, callBack, pClass);

                }


            });
        } catch (Exception e) {
            runUIError(context, callBack, e.toString());
        }
    }

    public static <P> void doPost(final Activity context, String url, Map<String, String> map, final CallBack callBack, final Class<P> pClass) {
        try {
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
                            runUIError(context, callBack, e.toString());
                        }
                    });
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    setSuccess(context, response, callBack, pClass);
                }


            });
        } catch (Exception e) {
            e.printStackTrace();
            runUIError(context, callBack, e.toString());
        }
    }

    public static <P, V> void doPost(final Activity context, String url, V v, final CallBack callBack, final Class<P> pClass) {
        try {
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), toJson(v));
//            FormBody formBody = formBodyBuilder.build();
            Request request = new Request.Builder().url(url).post(requestBody).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    runUIError(context, callBack, e.toString());
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    setSuccess(context, response, callBack, pClass);
                }


            });
        } catch (Exception e) {
            e.printStackTrace();
            runUIError(context, callBack, e.toString());

        }
    }

    public static <P> void setSuccess(final Activity context, final Response response, final CallBack callBack, final Class<P> pClass) {
        try {
            String json = response.body().string();
            ResponseCodeBean responseCodeBean = gson.fromJson(json, ResponseCodeBean.class);
            if (responseCodeBean.getCode().equals("1")) {
                JsonObject jsonObject = parser.parse(json).getAsJsonObject();
                JsonObject results = jsonObject.getAsJsonObject("Results");
                P p = gson.fromJson(results.toString(), pClass);
                runUISuccess(context, callBack, p);
            } else {
                runUIError(context, callBack, responseCodeBean.getError());
            }
        } catch (Exception e) {
            runUIError(context, callBack, e.toString());
        }
    }

    public static <P> void runUISuccess(final Activity context, final CallBack callBack, final P p) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                callBack.onSuccess(p);

            }
        });

    }

    public static <P> void runUIError(final Activity context, final CallBack callBack, final String s) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                callBack.onError(s);

            }
        });
    }

    public static <P> String toJson(P p) {
        return gson.toJson(p);
    }
}
