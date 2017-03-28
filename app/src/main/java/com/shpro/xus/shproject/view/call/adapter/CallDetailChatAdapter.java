package com.shpro.xus.shproject.view.call.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.shpro.xus.shproject.APP;
import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.util.ChatUtil;
import com.shpro.xus.shproject.util.ImageLoaderUtil;

import java.util.List;

/**
 * Created by xus on 2017/3/22.
 */

public class CallDetailChatAdapter extends BaseRecycleListAdapter<ListViewHolder, EMMessage> {

    public CallDetailChatAdapter(Context context) {
        super(context);
    }

    public CallDetailChatAdapter(Context context, List<EMMessage> list) {
        super(context, list);
    }

    public synchronized void noti() {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(username);
        this.list = conversation.getAllMessages();
//获取此会话的所有消息
////SDK初始化加载的聊天记录为20条，到顶时需要去DB里获取更多
////获取startMsgId之前的pagesize条消息，此方法获取的messages SDK会自动存入到此会话中，APP中无需再次把获取到的messages添加到会话中
        if (list != null && list.size() > 0) {
            List<EMMessage> mlist = conversation.loadMoreMsgFromDB(list.get(list.size() - 1).getMsgId(), 19);
            mlist.add(list.get(list.size() - 1));
            this.list = mlist;
        }
        notifyDataSetChanged();

    }

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getFrom().equals(APP.getInstance().getUser().getId())) {
            return 1;

        } else {
            return 2;
        }
    }


    @Override
    public int setItemLayoutId(int viewType) {
        switch (viewType) {
            case 1:
                return R.layout.item_chat_to;
            default:
                return R.layout.item_chat_from;
        }

    }

    @Override
    public ListViewHolder setViewHolder(View v) {
        return new ListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ListViewHolder holder, final int position) {

        setStatus(list.get(position), holder);
        holder.send_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatUtil.resendStringMessage((Activity) context,list.get(position),CallDetailChatAdapter.this);
            }
        });
        if (list.get(position).getFrom().equals(APP.getInstance().getUser().getId())) {

            if (list.get(position).getBody() instanceof EMTextMessageBody) {
                holder.content.setText(((EMTextMessageBody) list.get(position).getBody()).getMessage());
            } else {
                holder.content.setText("????未知信息????");

            }

        } else {
            if (list.get(position).getFrom().equals("admin")) {
                if (list.get(position).getBody() instanceof EMTextMessageBody) {
                    holder.content.setText("管理员 : " + ((EMTextMessageBody) list.get(position).getBody()).getMessage());
                } else {
                    holder.content.setText("????未知信息????");

                }
            } else {
                if (list.get(position).getBody() instanceof EMTextMessageBody) {
                    holder.content.setText(((EMTextMessageBody) list.get(position).getBody()).getMessage());
                } else {
                    holder.content.setText("????未知信息????");
                }
            }


        }
        if (list.get(position).getFrom().equals("admin")) {
            holder.avatar.setImageResource(R.drawable.icon);
        } else {
            ImageLoaderUtil.getInstance().loadCircleImage(list.get(position).getStringAttribute("fromAvatar", ""), holder.avatar);

        }
    }

    public void setStatus(EMMessage message, ListViewHolder holder) {
        switch (message.status()) {
            case SUCCESS:
                holder.pro.setVisibility(View.INVISIBLE);
                holder.send_error.setVisibility(View.INVISIBLE);

                break;
            case FAIL:
                holder.pro.setVisibility(View.INVISIBLE);
                holder.send_error.setVisibility(View.VISIBLE);
                break;
            case INPROGRESS:
            case CREATE:
                holder.pro.setVisibility(View.VISIBLE);
                holder.send_error.setVisibility(View.INVISIBLE);
                break;
            default:
                holder.pro.setVisibility(View.INVISIBLE);
                holder.send_error.setVisibility(View.INVISIBLE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        Log.e("wangxu", "getItemCount");
        return list.size();
    }
}
