package com.shpro.xus.shproject.util;

import android.app.Activity;
import android.util.Log;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.shpro.xus.shproject.bean.call.CallPeople;
import com.shpro.xus.shproject.bean.user.UserDetail;
import com.shpro.xus.shproject.view.call.adapter.BaseRecycleListAdapter;
import com.shpro.xus.shproject.view.call.adapter.CallDetailChatAdapter;

/**
 * Created by xus on 2017/3/28.
 */

public class ChatUtil {
    /**
     * 发送文本消息
     *
     * @param callPeople 对方资料
     * @param userDetail 我方资料
     * @param s
     */
    public static void sendStringMessage(final Activity activity,CallPeople callPeople, UserDetail userDetail, String s, final CallDetailChatAdapter adapter) {
        EMMessage message = EMMessage.createTxtSendMessage(s, callPeople.getId());
        message.setChatType(EMMessage.ChatType.Chat);
        message.setAttribute("fromName", userDetail.getName());
        message.setAttribute("fromAvatar", userDetail.getAvatar());
        message.setAttribute("toName", callPeople.getName());
        message.setAttribute("toAvatar", callPeople.getAvatar());
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.noti();

                    }
                });
            }

            @Override
            public void onError(int i, String s) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.noti();

                    }
                });            }

            @Override
            public void onProgress(int i, String s) {
//                adapter.notifyDataSetChanged();

            }
        });
        try {
            EMChatManager manager = EMClient.getInstance().chatManager();
            manager.sendMessage(message);
        } catch (Exception e) {
            Log.e("########", e.toString());
        }
    }

    public static void resendStringMessage(final Activity activity,EMMessage message, final CallDetailChatAdapter adapter) {
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.noti();

                    }
                });
            }

            @Override
            public void onError(int i, String s) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.noti();

                    }
                });            }

            @Override
            public void onProgress(int i, String s) {
//                adapter.notifyDataSetChanged();

            }
        });
        try {
            EMChatManager manager = EMClient.getInstance().chatManager();
            manager.sendMessage(message);
        } catch (Exception e) {
            Log.e("########", e.toString());
        }
    }
    }
