package com.shpro.xus.shproject.view.find;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.Bag;
import com.shpro.xus.shproject.bean.call.CallPeople;
import com.shpro.xus.shproject.bean.user.Account;
import com.shpro.xus.shproject.bean.user.User;
import com.shpro.xus.shproject.bean.user.UserBag;
import com.shpro.xus.shproject.db.cache.ACacheUtil;
import com.shpro.xus.shproject.util.AndroidIDUtil;
import com.shpro.xus.shproject.util.SHCallUtil;
import com.shpro.xus.shproject.util.ToastUtil;
import com.shpro.xus.shproject.view.CommentActivity;
import com.shpro.xus.shproject.view.call.CallDetailActivity;
import com.shpro.xus.shproject.view.call.CallListActivity;
import com.shpro.xus.shproject.view.call.adapter.CallAdapter;
import com.shpro.xus.shproject.view.find.adapter.FindPeopleAdapter;
import com.shpro.xus.shproject.view.main.SHMainActivity;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by xus on 2016/12/3.
 */

public class FindPeopleActivity extends CommentActivity {
    public FindPeopleAdapter adapter;
    protected ListView peoples;
    private List<User> list;

    @Override
    public int setContentView() {
        return R.layout.activity_find_people;
    }

    @Override
    public void initView() throws Exception {
        peoples = (ListView) findViewById(R.id.peoples);

//        peoples.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                User user = list.get(i);
//                if (user.getAccountId().equals(BmobUser.getCurrentUser(Account.class).getObjectId())) {
//                    //自己详情
//                    ToastUtil.makeTextShort(FindPeopleActivity.this, "不要点击自己！");
//                } else {
//                    if (isHasChat()){
//                        gotoChat(user);
//                    }else{
//                        ToastUtil.makeTextShort(FindPeopleActivity.this, "您现在没有通讯器呢，若没有扔掉新手指南，可以在里面领取哦！");
//
//                    }
//
//                }
//            }
//        });
        setCommentTitleView("山海师");
        getData();
    }

    public void getData() {
        BmobQuery<User> query = new BmobQuery<User>();
//返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(20);
//执行查询方法
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if (e == null) {
                    list = object;
                    adapter = new FindPeopleAdapter(FindPeopleActivity.this, list);
                    peoples.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    public boolean isHasChat() {
        UserBag userBag = ACacheUtil.getInstance().getObject(AndroidIDUtil.getID(FindPeopleActivity.this) + "bag", UserBag.class);
        for (Bag b : userBag.getBags()) {
            if ((!TextUtils.isEmpty(b.getBagTemplate().getActionInfo())) && b.getBagTemplate().getActionInfo().equals("com.shpro.xus.shproject.view.call.CallListActivity")) {
                return true;
            }
        }
        return false;
    }

    public void gotoChat(final  User user) {
        new SHCallUtil().toCall(BmobUser.getCurrentUser(Account.class).getUserid().toLowerCase(), new SHCallUtil.CallBack() {

            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        CallPeople callPeople = new CallPeople();
//                        callPeople.setName(user.getName());
//                        callPeople.setAvatar(user.getAvatar());
//                        callPeople.setUnRead(0);
//                        callPeople.setId(user.getObjectId().toLowerCase());
                        Intent intent = new Intent(FindPeopleActivity.this, CallDetailActivity.class);
                        intent.putExtra("people", callPeople);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onError() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.makeTextShort(FindPeopleActivity.this, "通讯器发出呲呲呲的声音...");
                        FindPeopleActivity.this.finish();
                    }
                });

            }
        });
    }
}
