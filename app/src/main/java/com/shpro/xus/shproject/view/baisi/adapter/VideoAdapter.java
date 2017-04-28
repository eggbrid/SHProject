package com.shpro.xus.shproject.view.baisi.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.baisi.ShowBaiSiBean;
import com.shpro.xus.shproject.util.ImageLoaderUtil;
import com.shpro.xus.shproject.view.find.adapter.FindAdapter;
import com.shpro.xus.shproject.view.main.adapter.SHBaseAdapter;
import com.shpro.xus.shproject.view.main.adapter.SHBaseViewHolder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class VideoAdapter extends SHBaseAdapter<ShowBaiSiBean, VideoAdapter.ViewHolder> {


    public VideoAdapter(Context context, List<ShowBaiSiBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup, ViewHolder viewHolder) {
        viewHolder.videoplayer.setUp(list.get(i).getVideo_uri()
                , JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, "");
        ImageLoaderUtil.getInstance().loadNomalImage(list.get(i).getProfile_image().replace("_mini", ""), viewHolder.videoplayer.thumbImageView);
//        ImageLoaderUtil.getInstance().loadMp4Image(list.get(i).getVideo_uri(),viewHolder.videoplayer.thumbImageView,(Activity) context);
//        viewHolder.videoplayer.thumbImageView.setImageBitmap(createVideoThumbnail(list.get(i).getVideo_uri()));
        viewHolder.title.setText(list.get(i).getText());
        return view;
    }

    @Override
    public VideoAdapter.ViewHolder setViewHolder(View view) {
        return new VideoAdapter.ViewHolder(view);
    }

    @Override
    public View getLayoutView(int i, LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.videoitem, viewGroup, false);
    }


    public class ViewHolder extends SHBaseViewHolder {
        JCVideoPlayerStandard videoplayer;
        TextView title;

        public ViewHolder(View root) {
            super(root);
        }

        @Override
        public void initView(View root) {
            videoplayer = (JCVideoPlayerStandard) root.findViewById(R.id.videoplayer);
            title = (TextView) root.findViewById(R.id.title);
        }
    }


}
