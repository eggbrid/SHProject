package com.shpro.xus.shproject.view.main.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.user.UserBag;
import com.shpro.xus.shproject.util.CommentUtil;
import com.shpro.xus.shproject.util.ImageLoaderUtil;

/**
 * Created by xus on 2016/11/18.
 */

public class MainAdapter extends BaseAdapter {
    public Context context;

    private UserBag userBag;

    public UserBag getUserBag() {
        return userBag;
    }

    public void setUserBag(UserBag userBag) {
        this.userBag = userBag;
    }

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
        if (userBag.getBags().size()> i) {
            viewHolder.name.setText(userBag.getBags().get(i).getName());
            String icon=userBag.getBags().get(i).getIcon();
            if (TextUtils.isEmpty(icon)){
                viewHolder.icon.setImageResource(R.drawable.shpg_unno);
            }else if(icon.startsWith("http:")){
                ImageLoaderUtil.getInstance().loadNomalImage(icon,viewHolder.icon);
            }else if(icon.startsWith("shpg")){
                viewHolder.icon.setImageResource(  CommentUtil.getIcon(icon,context));
            }else{
                viewHolder.icon.setImageResource(R.drawable.shpg_unno);
            }
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
