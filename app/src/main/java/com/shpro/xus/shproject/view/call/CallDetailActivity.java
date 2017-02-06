package com.shpro.xus.shproject.view.call;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.shpro.xus.shproject.APP;
import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.call.CallPeople;
import com.shpro.xus.shproject.bean.user.User;
import com.shpro.xus.shproject.util.ToastUtil;
import com.shpro.xus.shproject.view.call.adapter.CallDetailAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xus on 2016/12/2.
 */

public class CallDetailActivity extends CallCommentActivity implements View.OnClickListener {
    protected ListView messageList;
    protected Button send;
    protected RelativeLayout buttom;
    protected EditText contentText;
    private CallDetailAdapter adapter;
    private CallPeople callPeople;
    private User user;

    @Override
    public int setContentView() {
        return R.layout.activity_call_detail;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(callPeople.getId());
//指定会话消息未读数清零
        conversation.markAllMessagesAsRead();    }

    @Override
    public void initView() throws Exception {
        user = APP.getUser();
        callPeople = (CallPeople) getIntent().getSerializableExtra("people");
        setCommentTitleView(callPeople.getName());
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(callPeople.getId());
        List<EMMessage> list;
        try {
            list = conversation.getAllMessages();

        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<>();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        adapter = new CallDetailAdapter(this, list);
        adapter.setUsername(callPeople.getId());
        messageList = (ListView) findViewById(R.id.message_list);
        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(CallDetailActivity.this);
        buttom = (RelativeLayout) findViewById(R.id.buttom);
        buttom.setOnClickListener(CallDetailActivity.this);
        messageList.setAdapter(adapter);
        contentText = (EditText) findViewById(R.id.content_text);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.send) {
            if (TextUtils.isEmpty(contentText.getText().toString())) {
                ToastUtil.makeTextShort(this, "通讯器发出呲呲呲的声音");
            } else {
//发送消息
                EMMessage message = EMMessage.createTxtSendMessage(contentText.getText().toString(), callPeople.getId());
//              EMMessage message = EMMessage.createTxtSendMessage(contentText.getText().toString(),"923f9f90a4");


                message.setChatType(EMMessage.ChatType.Chat);
                message.setAttribute("fromName",user.getName());
                message.setAttribute("fromAvatar",user.getAvatar());
                message.setAttribute("toName",callPeople.getName());
                message.setAttribute("toAvatar",callPeople.getAvatar());
                try {
                    EMChatManager manager = EMClient.getInstance().chatManager();
                    manager.sendMessage(message);
                } catch (Exception e) {
                    Log.e("########", e.toString());
                }
                contentText.setText("");
                adapter.noti();
            }

        }
    }


    @Override
    public void onMessageReceived() {
        adapter.noti();
    }
}
