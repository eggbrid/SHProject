package com.shpro.xus.shproject.db.cache;

import android.app.Fragment;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.shpro.xus.shproject.APP;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xus on 2016/7/22.
 */
public class ACacheUtil {
    public static ACacheUtil aCacheUtil;
    private Gson gson;

    ACacheUtil() {
        gson = new Gson();
    }

    public synchronized static ACacheUtil getInstance() {
        if (aCacheUtil == null) {

            aCacheUtil = new ACacheUtil();
        }
        return aCacheUtil;
    }
    public <T> void cacheString(String key, String t, Fragment fragment) {
        ACache.get(APP.getInstance()).remove(key);
        ACache.get(APP.getInstance()).put(key, t);
    }
    public <T> void cacheString(String key, String t, int savetime, Fragment fragment) {
        ACache.get(APP.getInstance()).remove(key);
        ACache.get(APP.getInstance()).put(key, t,savetime);
    }

    public <T> void cacheString( String key, String t) {
        ACache.get(APP.getInstance()).remove(key);
        ACache.get(APP.getInstance()).put(key, t);
    }
    public <T> void cacheString(String key, String t, int savetime) {
        ACache.get(APP.getInstance()).remove(key);
        ACache.get(APP.getInstance()).put(key, t,savetime);
    }
    public <T> void cacheObject( String key, T t) {
        String obj = gson.toJson(t);
        ACache.get(APP.getInstance()).remove(key);
        ACache.get(APP.getInstance()).put(key, obj);
    }
    public  void removeCache( String key) {
        ACache.get(APP.getInstance()).remove(key);
    }

    public <T> void cacheObject( String key, T t, int savetime) {
        String obj = gson.toJson(t);
        ACache.get(APP.getInstance()).remove(key);
        ACache.get(APP.getInstance()).put(key, obj, savetime);
    }

    public <T> T getObject(String key, Class<T> classs) {
        String json = ACache.get(APP.getInstance()).getAsString(key);
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        T t;
        try {
            t = gson.fromJson(json, classs);
        } catch (Exception e) {
            Log.e("ACacheUtil:", "get object error :" + e.toString());
            return null;
        }
        return t;
    }

    public <T> void cacheList( String key, List<T> t) {
        String list = gson.toJson(t);
        ACache.get(APP.getInstance()).remove(key);
        ACache.get(APP.getInstance()).put(key, list);
    }

    public <T> void cacheList( String key, List<T> t, int savetime) {
        String list = gson.toJson(t);
        ACache.get(APP.getInstance()).remove(key);
        ACache.get(APP.getInstance()).put(key, list, savetime);
    }

    public <T> List<T> getList( String key, Class<T> clazz) {
        List<T> t = new ArrayList<T>();
        String json = ACache.get(APP.getInstance()).getAsString(key);
        try {
            Type objectType = type(List.class, clazz);
            t = gson.fromJson(json, objectType);
            if (t == null) {
                t = new ArrayList<T>();
            }
        } catch (Exception e) {
            Log.e("ACacheUtil:", "get object error :" + e.toString());
        }
        return t;
    }

    static ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };


    }


    public String getString(Context c, String key) {
        String obj=null;
        try {
            obj= ACache.get(c).getAsString(key);
        }catch (Exception e){
            obj=null;
        }
        return obj;
    }

    public void put(Context c, String key,String value) {
        try {
            ACache.get(c).put(key,value);
        }catch (Exception e){
        }
    }

    public void put(Context c, String key,String value,int saveTime) {
        try {
            ACache.get(c).put(key,value,saveTime);
        }catch (Exception e){
        }
    }
}
