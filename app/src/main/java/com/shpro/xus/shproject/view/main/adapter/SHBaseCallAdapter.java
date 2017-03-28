package com.shpro.xus.shproject.view.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xus on 2016/12/2.
 */

public abstract class SHBaseCallAdapter<M, ViewHodler extends SHBaseViewHolder> extends BaseAdapter {
    protected Context context;
    protected List<M> list;

    public SHBaseCallAdapter(Context context, List<M> list) {
        if (list == null) {
            this.list = new ArrayList<>();
        } else {
            this.list = list;

        }
        this.context = context;
    }

    public List<M> getList() {
        return list;
    }

    public void setList(List<M> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public M getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHodler holder;
        view = getLayoutView(i, (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE), viewGroup);
        holder = setViewHolder(view);
        view.setTag(holder);
        return getView(i, view, viewGroup, holder);
    }

    public abstract View getView(int i, View view, ViewGroup viewGroup, ViewHodler viewHodler);

    public abstract <ViewHodler extends SHBaseViewHolder> ViewHodler setViewHolder(View view);

    public abstract View getLayoutView(int i, LayoutInflater inflater, ViewGroup viewGroup);
}
