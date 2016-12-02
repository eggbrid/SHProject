package com.shpro.xus.shproject.util;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

/**
 * Created by xus on 2016/12/2.
 */

public class SHCallUtil  {

    public void toCall(final String userName){
        EMClient.getInstance().login(userName,"123456",new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                if (code==204){
                    while (toCalls(userName));
                }
            }
        });
    }
    public  boolean toCalls(String userName){
        try {
            EMClient.getInstance().createAccount(userName, "123456");//同步方法
            return true;
        } catch (HyphenateException e) {
            return false;
        }
    }

    private void backFromCalls(){
        EMClient.getInstance().logout(true);
    }
}
