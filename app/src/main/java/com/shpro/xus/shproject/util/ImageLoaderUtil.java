package com.shpro.xus.shproject.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.utils.IoUtils;
import com.shpro.xus.shproject.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

/**
 * Created by xus on 2016/7/29.
 */
public class ImageLoaderUtil {
    private static ImageLoaderUtil util;

    public synchronized static ImageLoaderUtil getInstance() {
        if (util == null) {
            util = new ImageLoaderUtil();
        }
        return util;
    }


    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .displayer(new CircleBitmapDisplayer())
            .build();

    public void loadCircleImage(String url, ImageView imageView) {

        ImageLoader.getInstance().displayImage(url, imageView, options);
    }

    public void loadCircleImage(String url, ImageView imageView, int failId) {

        DisplayImageOptions deOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .displayer(new CircleBitmapDisplayer())
                .showImageOnFail(failId)
                .showImageForEmptyUri(failId)
                .showImageOnLoading(R.drawable.defult_icon)
                .build();
        ImageLoader.getInstance().displayImage(url, imageView, deOptions);
    }

    public void loadConnerImage(String url, ImageView imageView, int failId, int c) {

        DisplayImageOptions deOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .displayer(new RoundedBitmapDisplayer(c))
                .showImageOnFail(failId)
                .showImageForEmptyUri(failId)
                .showImageOnLoading(failId)
                .build();
        ImageLoader.getInstance().displayImage(url, imageView, deOptions);
    }

    public void loadNomalImage(String url, ImageView imageView, int failId) {

        DisplayImageOptions deOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnFail(failId)
                .showImageForEmptyUri(failId)
                .build();

        ImageLoader.getInstance().displayImage(url, imageView, deOptions);
    }

    public void loadNomalImageFriend(String url, ImageView imageView, int failId) {

        DisplayImageOptions deOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnFail(failId)
                .showImageForEmptyUri(failId)
                .showImageOnLoading(R.drawable.defult_icon)
                .build();

        ImageLoader.getInstance().displayImage(url, imageView, deOptions);
    }


    public void loadNomalImageFriendLOad(String url, ImageView imageView, int failId) {

        DisplayImageOptions deOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnFail(failId)
                .showImageForEmptyUri(failId)
                .showImageOnLoading(failId)
                .build();

        ImageLoader.getInstance().displayImage(url, imageView, deOptions);
    }

    public void loadNomalImage(String url, ImageView imageView) {
        loadNomalImage(url, imageView, R.drawable.defult_icon);
    }


    public void loadMp4Image(String url, ImageView imageView, Activity activity) {
        ImageMp4Loader.loadImage(url,imageView,activity);

    }

    public interface OnGetBitMap {
        void onGetBitMap(Bitmap bitmap);
    }
}
