package com.shpro.xus.shproject.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
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
    private static MediaMetadataRetriever retriever = new MediaMetadataRetriever();

    private static Thread thread;

    public static void stopThread() {
        if (thread != null) {
            thread.interrupt();
            retriever.release();

            thread = null;
        }
    }

    public static void loadImage(final String path, final ImageView image, final Activity context) {
        if (thread != null) {
            thread.interrupt();
            retriever.release();
            thread = null;
        }
        File file = ImageLoader.getInstance().getDiskCache().get(path);
        if (file == null || !file.exists() || file.length() <= 0L) {
            //执行网络获取
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final Bitmap bitmap = createVideoThumbnail(path);
                        if (bitmap != null) {
                            ImageLoader.getInstance().getDiskCache().save(path, bitmap);
                            ImageLoader.getInstance().getMemoryCache().put(path, bitmap);
                            bitmap.recycle();
                            context.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    File file = ImageLoader.getInstance().getDiskCache().get(path);
                                    image.setImageURI(Uri.fromFile(file));
//                                ImageLoader.getInstance().displayImage(path, image, deOptions);
                                }
                            });
                        }

                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }
            });
            thread.start();
        } else {
            image.setImageURI(Uri.fromFile(file));

        }

    }

    public static Bitmap createVideoThumbnail(String filePath) {
        Bitmap bitmap = null;
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
        }
        if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, 799, 599,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }

}
