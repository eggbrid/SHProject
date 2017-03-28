package com.shpro.xus.shproject.view.call.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xus on 2017/3/22.
 */

public abstract class BaseRecycleListAdapter<V extends BaseViewHolder,M> extends RecyclerView.Adapter<V> {
    protected Context context;
   public BaseRecycleListAdapter(Context context){
        this.context=context;
    }

    protected List<M> list;

    public List<M> getList() {
        return list;
    }

    public void setList(List<M> list) {
        this.list = list;
    }

    public BaseRecycleListAdapter(Context context, List<M> list) {
        if (list == null) {
            this.list = new ArrayList<>();
        } else {
            this.list = list;

        }
        this.context = context;
    }

    public abstract int setItemLayoutId(int viewType);
    public abstract V setViewHolder(View v);

    @Override
    public V onCreateViewHolder(ViewGroup parent, int viewType) {
        return setViewHolder(LayoutInflater.from(context).inflate(setItemLayoutId(viewType),null));
    }



}
