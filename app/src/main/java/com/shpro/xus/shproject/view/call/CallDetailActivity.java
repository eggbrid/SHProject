package com.shpro.xus.shproject.view.call;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.shpro.xus.shproject.APP;
import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.call.CallPeople;
import com.shpro.xus.shproject.bean.user.UserDetail;
import com.shpro.xus.shproject.interfaces.views.RefreshListener;
import com.shpro.xus.shproject.util.ChatUtil;
import com.shpro.xus.shproject.util.ToastUtil;
import com.shpro.xus.shproject.view.call.adapter.CallDetailChatAdapter;
import com.shpro.xus.shproject.view.views.RecyclerListView;

import java.util.ArrayList;
import java.util.List;

import static android.R.transition.move;

/**
 * Created by xus on 2016/12/2.
 */

public class CallDetailActivity extends CallCommentActivity implements RefreshListener {
    protected RecyclerView messageList;
    protected Button send;
    protected RelativeLayout buttom;
    protected EditText contentText;
    private CallDetailChatAdapter adapter;
    private CallPeople callPeople;
    private UserDetail userDetail;
    private SwipeRefreshLayout swipeContainer;
    private EMConversation conversation;
    private LinearLayoutManager linearLayoutManager;
    private int pos;
    private boolean move;

    @Override
    public int setContentView() {
        return R.layout.activity_call_detail;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearUnReadMessage();
    }

    @Override
    public void initView() throws Exception {
        userDetail = APP.getInstance().getUserDetail();
        callPeople = (CallPeople) getIntent().getSerializableExtra("people");
        setCommentTitleView(callPeople.getName());
        List<EMMessage> list = new ArrayList<>();
        adapter = new CallDetailChatAdapter(this, list);
        adapter.setUsername(callPeople.getId());
        messageList = (RecyclerView) findViewById(R.id.message_list);
        linearLayoutManager = new LinearLayoutManager(this);
        messageList.setLayoutManager(linearLayoutManager);
        messageList.setOnScrollListener(new RecyclerViewListener());
        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(CallDetailActivity.this);
        buttom = (RelativeLayout) findViewById(R.id.buttom);
        buttom.setOnClickListener(CallDetailActivity.this);
        messageList.setAdapter(adapter);
        contentText = (EditText) findViewById(R.id.content_text);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        adapter.noti();
        conversation = EMClient.getInstance().chatManager().getConversation(callPeople.getId());
        //指定会话消息未读数清零
        conversation.markAllMessagesAsRead();
        setRefresh(swipeContainer, messageList, false, this);
        MoveToPosition(adapter.getList().size() - 1);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.send) {
            if (TextUtils.isEmpty(contentText.getText().toString())) {
                ToastUtil.makeTextShort(this, "通讯器发出呲呲呲的声音");
            } else {
                ChatUtil.sendStringMessage(this,callPeople, userDetail, contentText.getText().toString(),adapter);
                contentText.setText("");
                adapter.noti();
                MoveToPosition(adapter.getList().size() - 1);

            }

        }
    }


    @Override
    public void onMessageReceived() {
        clearUnReadMessage();
        adapter.noti();
        MoveToPosition(adapter.getList().size() - 1);
    }

    @Override
    public void onLoadMore() {
    }

    @Override
    public void onRefresh() {
        startGetList();
    }


    public void startGetList() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<EMMessage> list = new ArrayList<>();
                if (adapter.getList() != null && adapter.getList().size() > 0) {
                    list = conversation.loadMoreMsgFromDB(adapter.getList().get(0).getMsgId(), 20);
                    adapter.getList().addAll(0, list);
                }
                final int sp = list.size();
                CallDetailActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        MoveToPosition(sp);
                        stopRefresh(swipeContainer);
                    }
                });

            }
        });
        thread.start();
    }

    public void MoveToPosition(int n) {
        pos = n;
        int firstItem = linearLayoutManager.findFirstVisibleItemPosition();
        int lastItem = linearLayoutManager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            messageList.scrollToPosition(n);
        } else if (n <= lastItem) {
            int top = messageList.getChildAt(n - firstItem).getTop();
            messageList.scrollBy(0, top);
        } else {
            messageList.scrollToPosition(n);
            move=true;
        }
    }

    private void clearUnReadMessage() {

        if (conversation != null)
            conversation.markAllMessagesAsRead();
    }


    class RecyclerViewListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            //在这里进行第二次滚动（最后的100米！）
            if (move) {
                move = false;
                //获取要置顶的项在当前屏幕的位置，mIndex是记录的要置顶项在RecyclerView中的位置
                int n = pos - linearLayoutManager.findFirstVisibleItemPosition();
                if (0 <= n && n < messageList.getChildCount()) {
                    //获取要置顶的项顶部离RecyclerView顶部的距离
                    int top = messageList.getChildAt(n).getTop();
                    //最后的移动
                    messageList.scrollBy(0, top);
                }
            }
        }
    }
}
