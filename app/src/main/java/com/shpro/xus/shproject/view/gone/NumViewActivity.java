package com.shpro.xus.shproject.view.gone;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.utils.L;
import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.gone.ViewNum;
import com.shpro.xus.shproject.view.CommentActivity;
import com.shpro.xus.shproject.view.gone.adapter.ViewNumAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xus on 2017/2/7.
 */

public class NumViewActivity extends CommentActivity implements AdapterView.OnItemClickListener {
    protected ListView list;
    private List<ViewNum> lists;
private ViewNumAdapter adapter;
    @Override
    public int setContentView() {
        return R.layout.activity_gone_numview;
    }

    @Override
    public void initView() throws Exception {
        initTitleView();
        title = (LinearLayout) findViewById(R.id.title);
        list = (ListView) findViewById(R.id.list);
        setCommentTitleView("调试界面");
        initData();
        adapter=new ViewNumAdapter(this,lists);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
    }

    public void initData() {
        lists = new ArrayList<>();
        lists.add(new ViewNum("WIFI信息", WifiViewActivity.class));
        lists.add(new ViewNum("时间信息", TimeViewActivity.class));

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        lists.get(i).toActivity(this);
    }
}
