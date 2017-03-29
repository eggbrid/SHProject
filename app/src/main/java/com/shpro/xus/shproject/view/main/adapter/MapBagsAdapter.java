package com.shpro.xus.shproject.view.main.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.Bag;
import com.shpro.xus.shproject.util.CommentUtil;
import com.shpro.xus.shproject.util.ImageLoaderUtil;

import java.util.List;

/**
 * Created by xus on 2017/3/29.
 */

public class MapBagsAdapter extends SHBaseAdapter<Bag, MapBagsAdapter.ViewHodler> {

    public MapBagsAdapter(Context context, List<Bag> list) {
        super(context, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup, ViewHodler viewHodler) {
        Bag bag = list.get(i);
        viewHodler.text.setText(bag.getBagTemplate().getName());
        String icon=bag.getBagTemplate().getIcon();
        if (TextUtils.isEmpty(icon)){
            viewHodler.avatar.setImageResource(R.drawable.shpg_unno);
        }else if(icon.startsWith("http:")){
            ImageLoaderUtil.getInstance().loadNomalImage(icon,viewHodler.avatar);
        }else if(icon.startsWith("shpg")){
            viewHodler.avatar.setImageResource(  CommentUtil.getIcon(icon,context));
        }else{
            viewHodler.avatar.setImageResource(R.drawable.shpg_unno);
        }
        return view;
    }

    @Override
    public MapBagsAdapter.ViewHodler setViewHolder(View view) {
        return new ViewHodler(view);
    }

    @Override
    public View getLayoutView(int i, LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.item_bagmap, null);
    }

    public class ViewHodler extends SHBaseViewHolder {
        public TextView text;
        public ImageView avatar;

        public ViewHodler(View root) {
            super(root);
        }

        @Override
        public void initView(View root) {
            text = (TextView) root.findViewById(R.id.text);
            avatar = (ImageView) root.findViewById(R.id.avatar);
        }
    }
}
