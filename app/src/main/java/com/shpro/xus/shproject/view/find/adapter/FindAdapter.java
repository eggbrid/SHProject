package com.shpro.xus.shproject.view.find.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.gone.ViewNum;
import com.shpro.xus.shproject.view.main.adapter.SHBaseAdapter;
import com.shpro.xus.shproject.view.main.adapter.SHBaseViewHolder;

import java.util.List;

/**
 * Created by xus on 2017/2/7.
 */

public class FindAdapter extends SHBaseAdapter<ViewNum, FindAdapter.ViewHolder> {

    public FindAdapter(Context context, List<ViewNum> list) {
        super(context, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup, ViewHolder viewHolder) {
        ViewNum vn = list.get(i);
        viewHolder.name.setText(vn.getName());
        return view;
    }

    @Override
    public FindAdapter.ViewHolder setViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public View getLayoutView(int i, LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.item_find, null);
    }

    public class ViewHolder extends SHBaseViewHolder {
        public TextView name;

        public ViewHolder(View root) {
            super(root);
        }

        @Override
        public void initView(View root) {
            name = (TextView) root.findViewById(R.id.text);
        }
    }
}
