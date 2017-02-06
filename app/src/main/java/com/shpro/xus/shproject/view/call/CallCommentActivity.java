package com.shpro.xus.shproject.view.call;

import android.os.Bundle;
import android.util.Log;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.util.NetUtils;
import com.shpro.xus.shproject.interfaces.call.SHCallBack;
import com.shpro.xus.shproject.view.CommentActivity;

import java.util.List;

/**
 * Created by xus on 2016/12/2.
 */

public abstract class CallCommentActivity extends CommentActivity implements EMConnectionListener, SHCallBack {
    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            //收到消息
            Log.e("wangxu", messages.size() + "");
            CallCommentActivity.this.onMessageReceived();

        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
        }

        @Override
        public void onMessageRead(List<EMMessage> list) {

        }

        @Override
        public void onMessageDelivered(List<EMMessage> list) {

        }


        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
            Log.e("wangxu", "onMessageChanged:" + change.toString());
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EMClient.getInstance().addConnectionListener(this);
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().removeConnectionListener(this);
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
    }

    @Override
    public void onConnected() {

    }

    @Override
    public void onDisconnected(final int i) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (i == EMError.USER_REMOVED) {
                    // 显示帐号已经被移除
                } else if (i == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                    // 显示帐号在其他设备登录
                } else {
                    if (NetUtils.hasNetwork(CallCommentActivity.this)) {

                        //连接不到聊天服务器
                    } else {
                        //当前网络不可用，请检查网络设置
                    }
                }
            }
        });
    }

}
