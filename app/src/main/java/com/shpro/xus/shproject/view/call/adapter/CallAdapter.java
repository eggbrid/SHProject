package com.shpro.xus.shproject.view.call.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.call.CallPeople;
import com.shpro.xus.shproject.util.ImageLoaderUtil;
import com.shpro.xus.shproject.view.main.adapter.SHBaseAdapter;
import com.shpro.xus.shproject.view.main.adapter.SHBaseViewHolder;

import java.util.List;

/**
 * Created by xus on 2016/12/2.
 */

public class CallAdapter extends SHBaseAdapter<CallPeople,CallAdapter.ViewHolder> {

    public CallAdapter(Context context, List list) {
        super(context, list);
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup, ViewHolder viewHolder) {
        ImageLoaderUtil.getInstance().loadCircleImage(list.get(i).getAvatar(),viewHolder.avatar);
        viewHolder.name.setText(list.get(i).getName()+"");
        viewHolder.unRead.setText(list.get(i).getUnRead()+"");
        return view;
    }

    @Override
    public View getLayoutView(int i, LayoutInflater inflater, ViewGroup viewGroup) {
        return   inflater.inflate(R.layout.item_call, null);
    }



    @Override
    public SHBaseViewHolder setViewHolder(View root) {
        return new ViewHolder(root);
    }


    public class ViewHolder extends SHBaseViewHolder {
        protected ImageView avatar;
        protected TextView name;
        protected TextView unRead;

        public ViewHolder(View root) {
            super(root);
        }

        @Override
        public void initView(View root) {
            avatar = (ImageView) root.findViewById(R.id.avatar);
            name = (TextView) root.findViewById(R.id.name);
            unRead= (TextView) root.findViewById(R.id.un_read);
        }
    }
}
