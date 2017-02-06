package com.shpro.xus.shproject.view.call.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.call.CallPeople;
import com.shpro.xus.shproject.bean.user.Account;
import com.shpro.xus.shproject.util.ImageLoaderUtil;
import com.shpro.xus.shproject.view.main.adapter.SHBaseAdapter;
import com.shpro.xus.shproject.view.main.adapter.SHBaseViewHolder;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobUser;

/**
 * Created by xus on 2016/12/2.
 */

public class CallAdapter extends SHBaseAdapter<CallPeople,CallAdapter.ViewHolder> {

    public CallAdapter(Context context, List list) {
        super(context, list);
    }

public void noity(){
    list.clear();
    Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
    Iterator it = conversations.entrySet().iterator();
    while (it.hasNext()) {
        Map.Entry<String, EMConversation> entry = (Map.Entry<String, EMConversation>) it.next();
        CallPeople callPeople = new CallPeople();
        String key = entry.getKey().toString();
        if (entry.getValue().getLastMessage().getFrom().equals(BmobUser.getCurrentUser(Account.class).getUserid().toLowerCase())) {
            callPeople.setName(entry.getValue().getLastMessage().getStringAttribute("toName", ""));
            callPeople.setAvatar(entry.getValue().getLastMessage().getStringAttribute("toAvatar", ""));
        } else {
            callPeople.setName(entry.getValue().getLastMessage().getStringAttribute("fromName", ""));
            callPeople.setAvatar(entry.getValue().getLastMessage().getStringAttribute("fromAvatar", ""));
        }
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(key);
      ;
        callPeople.setUnRead(  conversation.getUnreadMsgCount());
        callPeople.setId(key);
        list.add(callPeople);
    }
}
    @Override
    public View getView(int i, View view, ViewGroup viewGroup, ViewHolder viewHolder) {
        ImageLoaderUtil.getInstance().loadCircleImage(list.get(i).getAvatar(),viewHolder.avatar);
        viewHolder.name.setText(list.get(i).getName()+"");
        if (list.get(i).getUnRead()>0){
            viewHolder.unRead.setText(list.get(i).getUnRead()+"");
            viewHolder.unRead.setVisibility(View.VISIBLE);

        }else {
            viewHolder.unRead.setVisibility(View.INVISIBLE);
        }
        return view;
    }

    @Override
    public View getLayoutView(int i, LayoutInflater inflater, ViewGroup viewGroup) {
        return   inflater.inflate(R.layout.item_call, null);
    }



    @Override
    public SHBaseViewHolder setViewHolder(View root) {
        return new ViewHolder(root);
    }


    public class ViewHolder extends SHBaseViewHolder {
        protected ImageView avatar;
        protected TextView name;
        protected TextView unRead;

        public ViewHolder(View root) {
            super(root);
        }

        @Override
        public void initView(View root) {
            avatar = (ImageView) root.findViewById(R.id.avatar);
            name = (TextView) root.findViewById(R.id.name);
            unRead= (TextView) root.findViewById(R.id.un_read);
        }
    }
}
