package com.shpro.xus.shproject.reciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.shpro.xus.shproject.bean.call.CallPeople;
import com.shpro.xus.shproject.bean.user.Account;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cn.bmob.push.PushConstants;
import cn.bmob.v3.BmobUser;

/**
 * Created by xus on 2016/12/3.
 */

public class MyPushMessageReceiver extends BroadcastReceiver {
    public static Map<String, OnMessageGetListener> listenerMap = new HashMap<>();

    public static Map<String, OnMessageGetListener> getListenerMap() {
        return listenerMap;
    }

    public static void setListenerMap(Map<String, OnMessageGetListener> listenerMap) {
        MyPushMessageReceiver.listenerMap = listenerMap;
    }

    public static void addPushListener(String tag, OnMessageGetListener onMessageGetListener) {
        listenerMap.put("tag", onMessageGetListener);
    }

    public static void removePushListener(String tag) {
        listenerMap.remove(tag);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {
            Iterator it = listenerMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, OnMessageGetListener> entry = (Map.Entry<String, OnMessageGetListener>) it.next();
                entry.getValue().onMessageGet(intent.getStringExtra("msg"));
            }
        }
    }

    public interface OnMessageGetListener {
        void onMessageGet(String msg);
    }
}