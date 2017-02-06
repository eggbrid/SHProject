package com.shpro.xus.shproject.util;

import android.util.Log;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

/**
 * Created by xus on 2016/12/2.
 */

public class SHCallUtil  {

    public void toCall(final String userName,final CallBack callBack){
        if ( EMClient.getInstance().isConnected()){
            callBack.onSuccess();
            return;
        }
        EMClient.getInstance().login(userName.toLowerCase(),"123456",new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                callBack.onSuccess();
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.e("wangxu",message);
                if (code==204){
                    while (toCalls(userName));
                }
                callBack.onError();
            }
        });
    }
    public  boolean toCalls(String userName){
        Log.e("wangxu","toCalls");

        try {
            EMClient.getInstance().createAccount(userName.toLowerCase(), "123456");//同步方法
            return true;
        } catch (HyphenateException e) {
            return false;
        }
    }

    private void backFromCalls(){
        EMClient.getInstance().logout(true);
    }
    public interface CallBack{
        void onSuccess();
        void onError();
    }
}
