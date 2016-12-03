package com.shpro.xus.shproject.view.find;

import android.util.Log;
import android.widget.ListView;

import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.user.User;
import com.shpro.xus.shproject.view.CommentActivity;
import com.shpro.xus.shproject.view.find.adapter.FindPeopleAdapter;

import java.util.List;

import cn.bmob.v3.BmobQuery;
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
}
