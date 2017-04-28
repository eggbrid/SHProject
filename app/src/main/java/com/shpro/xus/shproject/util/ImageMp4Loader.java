package com.shpro.xus.shproject.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.assist.ViewScaleType;
import com.nostra13.universalimageloader.core.decode.ImageDecodingInfo;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.utils.ImageSizeUtils;
import com.nostra13.universalimageloader.utils.L;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;
import com.shpro.xus.shproject.R;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by xus on 2017/4/28.
 */

public class ImageMp4Loader {
    private static ImageSize targetSize;

    public static void loadImage(final String path, final ImageView image, final Activity context) {
        ImageAware imageAware = (ImageAware) (new ImageViewAware(image));
        if (targetSize == null) {
            targetSize = ImageSizeUtils.defineTargetSizeForView(imageAware, new ImageSize(600, 800));
        }

        final String memoryCacheKey = MemoryCacheUtils.generateKey(path, targetSize);
        final DisplayImageOptions deOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(context.getResources().getDrawable(R.drawable.defult_icon))
                .build();
        File file = ImageLoader.getInstance().getDiskCache().get(memoryCacheKey);
        if (file == null || !file.exists() || file.length() <= 0L) {
            //执行网络获取
            Log.e("wangxu", "網絡獲取");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final Bitmap bitmap = createVideoThumbnail(path);
                        ImageLoader.getInstance().getDiskCache().save(memoryCacheKey, bitmap);
                        ImageLoader.getInstance().getMemoryCache().put(memoryCacheKey, bitmap);
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ImageLoader.getInstance().displayImage(path, image, deOptions);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            Log.e("wangxu", "本地獲取");
            ImageLoader.getInstance().displayImage(path, image, deOptions);
        }

    }

    public static Bitmap createVideoThumbnail(String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int kind = MediaStore.Video.Thumbnails.MINI_KIND;
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(filePath, new HashMap<String, String>());
            } else {
                retriever.setDataSource(filePath);
            }
            bitmap = retriever.getFrameAtTime();
            retriever.release();

        } catch (Exception ex) {
            Log.e("wangxu", ex.toString());
        }
        if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, 799, 599,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }

}
