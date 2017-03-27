package com.shpro.xus.shproject.view.find;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.gone.ViewNum;
import com.shpro.xus.shproject.view.CommentActivity;
import com.shpro.xus.shproject.view.find.adapter.FindAdapter;
import com.shpro.xus.shproject.view.gone.adapter.ViewNumAdapter;
import com.tencent.map.geolocation.TencentLocationManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xus on 2017/2/8.
 */

public class FindActivity extends CommentActivity implements View.OnClickListener ,AdapterView.OnItemClickListener{
    protected ListView list;
    private List<ViewNum> lists;
    private FindAdapter adapter;
    private TencentLocationManager locationManager;

    @Override
    public int setContentView() {
        return R.layout.activity_find;
    }

    @Override
    public void initView() throws Exception {
        setCommentTitleView("寻找");
        list = (ListView) findViewById(R.id.list);
        initData();
        adapter=new FindAdapter(this,lists);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);

    }
    public void initData() {
        lists = new ArrayList<>();
        lists.add(new ViewNum("寻找山海师", FindPeopleActivity.class));
        lists.add(new ViewNum("寻找物品", FindBagActivity.class));

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        lists.get(i).toActivity(this);

    }
}
