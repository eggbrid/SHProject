package com.shpro.xus.shproject.view.find.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.user.User;
import com.shpro.xus.shproject.util.ImageLoaderUtil;
import com.shpro.xus.shproject.view.main.adapter.SHBaseAdapter;
import com.shpro.xus.shproject.view.main.adapter.SHBaseViewHolder;

import java.util.List;

/**
 * Created by xus on 2016/12/3.
 */

public class FindPeopleAdapter extends SHBaseAdapter<User, FindPeopleAdapter.ViewHodler> {
    public FindPeopleAdapter(Context context, List<User> list) {
        super(context, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup, ViewHodler viewHodler) {
        User user = list.get(i);
        viewHodler.name.setText(user.getName());
        ImageLoaderUtil.getInstance().loadCircleImage(user.getAvatar(), viewHodler.avatar);
        viewHodler.sex.setImageResource(user.getSex() == 1 ? R.drawable.man : R.drawable.woman);
        return view;
    }

    @Override
    public View getLayoutView(int i, LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.item_peoples, null);
    }

    @Override
    public FindPeopleAdapter.ViewHodler setViewHolder(View root) {
        return new ViewHodler(root);
    }



    public class ViewHodler extends SHBaseViewHolder {
        public TextView name;
        public ImageView sex;
        public ImageView avatar;

        public ViewHodler(View root) {
            super(root);
        }

        @Override
        public void initView(View root) {
            name=(TextView)root.findViewById(R.id.name);
            sex=(ImageView)root.findViewById(R.id.sex);
            avatar=(ImageView)root.findViewById(R.id.avatar);
        }
    }
}
