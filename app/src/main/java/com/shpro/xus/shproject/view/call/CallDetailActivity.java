package com.shpro.xus.shproject.view.call;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.call.CallPeople;
import com.shpro.xus.shproject.util.ToastUtil;
import com.shpro.xus.shproject.view.call.adapter.CallDetailAdapter;

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

    @Override
    public int setContentView() {
        return R.layout.activity_call_detail;
    }

    @Override
    public void initView() throws Exception {
        callPeople = (CallPeople) getIntent().getSerializableExtra("people");
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(callPeople.getId());
        adapter = new CallDetailAdapter(this, conversation.getAllMessages(), callPeople.getId());
        setCommentTitleView(callPeople.getName());
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
                //如果是群聊，设置chattype，默认是单聊
//           if (chatType == CHATTYPE_GROUP)
//            message.setChatType(EMMessage.ChatType.GroupChat);
//发送消息
                EMMessage message = EMMessage.createTxtSendMessage(contentText.getText().toString(), callPeople.getId());
                EMClient.getInstance().chatManager().sendMessage(message);
                contentText.setText("");
            }

        }
    }
}
