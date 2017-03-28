package com.shpro.xus.shproject.util;

import android.util.Log;

import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.shpro.xus.shproject.bean.call.CallPeople;
import com.shpro.xus.shproject.bean.user.UserDetail;

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
    public static void sendStringMessage(CallPeople callPeople, UserDetail userDetail, String s) {
        EMMessage message = EMMessage.createTxtSendMessage(s, callPeople.getId());
        message.setChatType(EMMessage.ChatType.Chat);
        message.setAttribute("fromName", userDetail.getName());
        message.setAttribute("fromAvatar", userDetail.getAvatar());
        message.setAttribute("toName", callPeople.getName());
        message.setAttribute("toAvatar", callPeople.getAvatar());
        try {
            EMChatManager manager = EMClient.getInstance().chatManager();
            manager.sendMessage(message);
        } catch (Exception e) {
            Log.e("########", e.toString());
        }
    }
}
