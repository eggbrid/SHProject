package com.shpro.xus.shproject.view.call.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.chat.adapter.message.EMATextMessageBody;
import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.user.Account;
import com.shpro.xus.shproject.util.ImageLoaderUtil;
import com.shpro.xus.shproject.view.main.adapter.SHBaseAdapter;
import com.shpro.xus.shproject.view.main.adapter.SHBaseCallAdapter;
import com.shpro.xus.shproject.view.main.adapter.SHBaseViewHolder;

import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * Created by xus on 2016/12/3.
 */

public class CallDetailAdapter extends SHBaseCallAdapter<EMMessage, CallDetailAdapter.ViewHolder> {
    public String id = BmobUser.getCurrentUser(Account.class).getUserid().toLowerCase();
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public CallDetailAdapter(Context context, List<EMMessage> list) {
        super(context, list);
    }

    public void noti() {
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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup, ViewHolder viewHolder) {
        if (list.get(i).getFrom().equals(id)) {
            if (list.get(i).getBody() instanceof EMTextMessageBody) {
                viewHolder.content.setText(((EMTextMessageBody) list.get(i).getBody()).getMessage());
            } else {
                viewHolder.content.setText("????未知信息????");

            }

        } else {
            if (list.get(i).getFrom().equals("admin")) {
                if (list.get(i).getBody() instanceof EMTextMessageBody) {
                    viewHolder.content.setText("管理员 : " + ((EMTextMessageBody) list.get(i).getBody()).getMessage());
                } else {
                    viewHolder.content.setText("????未知信息????");

                }
            } else {
                if (list.get(i).getBody() instanceof EMTextMessageBody) {
                    viewHolder.content.setText(((EMTextMessageBody) list.get(i).getBody()).getMessage());
                } else {
                    viewHolder.content.setText("????未知信息????");
                }
            }


        }
        if (list.get(i).getFrom().equals("admin")) {
            viewHolder.avatar.setImageResource(R.drawable.icon);
        } else {
            ImageLoaderUtil.getInstance().loadCircleImage(list.get(i).getStringAttribute("fromAvatar", ""), viewHolder.avatar);

        }

        return view;
    }

    @Override
    public View getLayoutView(int i, LayoutInflater inflater, ViewGroup viewGroup) {
        if (list.get(i).getFrom().equals(id)) {
            return inflater.inflate(R.layout.item_chat_to, viewGroup, false);

        } else {
            return inflater.inflate(R.layout.item_chat_from, viewGroup, false);
        }
    }

    @Override
    public CallDetailAdapter.ViewHolder setViewHolder(View root) {
        return new ViewHolder(root);
    }


    public class ViewHolder extends SHBaseViewHolder {
        private TextView content;
        private ImageView avatar;

        public ViewHolder(View root) {
            super(root);
        }

        @Override
        public void initView(View root) {
            content = (TextView) root.findViewById(R.id.content);
            avatar = (ImageView) root.findViewById(R.id.avatar);

        }
    }
}