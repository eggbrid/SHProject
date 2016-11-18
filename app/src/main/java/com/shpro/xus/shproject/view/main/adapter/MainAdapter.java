package com.shpro.xus.shproject.view.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shpro.xus.shproject.R;

/**
 * Created by xus on 2016/11/18.
 */

public class MainAdapter extends BaseAdapter {
    public Context context;
    String[] p = {"物品1", "物品2", "物品3", "物品4", "物品5", "物品6", "物品7"};

    public MainAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 30;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_packge, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (p.length > i) {
            viewHolder.name.setText(p[i]);
            viewHolder.icon.setImageResource(R.drawable.map);
        } else {
            viewHolder.name.setText("");
            viewHolder.icon.setImageResource(0);
        }
        return view;
    }

    public class ViewHolder {
        protected ImageView icon;
        protected TextView name;

        ViewHolder(View rootView) {
            initView(rootView);
        }

        private void initView(View rootView) {
            icon = (ImageView) rootView.findViewById(R.id.icon);
            name = (TextView) rootView.findViewById(R.id.name);
        }
    }
}
