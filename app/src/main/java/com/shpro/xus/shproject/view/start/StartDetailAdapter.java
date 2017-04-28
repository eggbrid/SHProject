package com.shpro.xus.shproject.view.start;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by xus on 2017/4/26.
 */

public class StartDetailAdapter extends PagerAdapter {
    public StartDetailAdapter(List<View> list) {
        this.list = list;
    }

    private List<View> list;

    public List<View> getList() {
        return list;
    }

    public void setList(List<View> list) {
        this.list = list;
    }

    // 实例化一个页卡
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));
        return list.get(position);
    }

    // 销毁一个页卡
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
