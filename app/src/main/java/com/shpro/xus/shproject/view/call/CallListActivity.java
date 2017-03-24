package com.shpro.xus.shproject.view.call;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.call.CallPeople;
import com.shpro.xus.shproject.bean.user.Account;
import com.shpro.xus.shproject.util.SHCallUtil;
import com.shpro.xus.shproject.util.ToastUtil;
import com.shpro.xus.shproject.view.call.adapter.CallAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import cn.bmob.v3.BmobUser;

/**
 * Created by xus on 2016/12/2.
 */

public class CallListActivity extends CallCommentActivity {
    protected GridView mainGrid;
    private List<CallPeople> list = new ArrayList<>();
    private CallAdapter adapter;

//    static {
//        System.loadLibrary("MyJni");//导入生成的链接库文件
//    }
//    public native String getStringFromNative();//本地方法
//    public native String getString_From_c();
    @Override
    public int setContentView() {

        return R.layout.activity_call_list;

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter!=null)
        adapter.noity();

    }

    @Override
    public void initView() throws Exception {
       new  SHCallUtil().toCall(BmobUser.getCurrentUser(Account.class).getUserid().toLowerCase(),new SHCallUtil.CallBack(){

           @Override
           public void onSuccess() {runOnUiThread(new Runnable() {
               @Override
               public void run() {
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
                       callPeople.setUnRead(  conversation.getUnreadMsgCount());
                       callPeople.setId(key);
                       list.add(callPeople);
                   }
                   adapter = new CallAdapter(CallListActivity.this, list);
                   mainGrid = (GridView) findViewById(R.id.main_grid);
                   mainGrid.setAdapter(adapter);
                   setCommentTitleView("通讯器");
                   mainGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                       @Override
                       public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                           Intent intent=new Intent(CallListActivity.this,CallDetailActivity.class);
                           intent.putExtra("people",list.get(i));
                            startActivity(intent);
                       }
                   });
               }
           });

           }

           @Override
           public void onError() {
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       ToastUtil.makeTextShort(CallListActivity.this,"通讯器发出呲呲呲的声音...");
                       CallListActivity.this.finish();
                   }
               });

           }
       });

    }


    @Override
    public void onMessageReceived() {
        if (adapter!=null)
            adapter.noity();

    }
}
