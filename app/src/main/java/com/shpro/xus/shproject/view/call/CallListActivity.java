package com.shpro.xus.shproject.view.call;

import android.text.TextUtils;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.call.CallPeople;
import com.shpro.xus.shproject.bean.user.Account;
import com.shpro.xus.shproject.view.call.adapter.CallAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobUser;

/**
 * Created by xus on 2016/12/2.
 */

public class CallListActivity extends CallCommentActivity {
    protected GridView mainGrid;
    private List<CallPeople> list = new ArrayList<>();
    private CallAdapter adapter;

    @Override
    public int setContentView() {
        return R.layout.activity_call_list;
    }

    @Override
    public void initView() throws Exception {
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        Iterator it = conversations.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, EMConversation> entry = (Map.Entry<String, EMConversation>) it.next();
            CallPeople callPeople = new CallPeople();
            String key = entry.getKey().toString();
            if (entry.getValue().getLastMessage().getFrom().equals(TextUtils.isEmpty(BmobUser.getCurrentUser(Account.class).getUserid().toLowerCase()))) {
                callPeople.setName(entry.getValue().getLastMessage().getStringAttribute("toName", ""));
                callPeople.setAvatar(entry.getValue().getLastMessage().getStringAttribute("toAvatar", ""));
            } else {
                callPeople.setName(entry.getValue().getLastMessage().getStringAttribute("fromName", ""));
                callPeople.setAvatar(entry.getValue().getLastMessage().getStringAttribute("fromAvatar", ""));
            }
            callPeople.setUnRead(entry.getValue().getUnreadMsgCount());
            callPeople.setId(key);
            list.add(callPeople);
        }
        adapter = new CallAdapter(this, list);
        mainGrid = (GridView) findViewById(R.id.main_grid);
        mainGrid.setAdapter(adapter);
        setCommentTitleView("通讯器");
    }
}
