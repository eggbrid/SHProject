package com.shpro.xus.shproject.util;

import android.net.Uri;
import android.os.Environment;

import java.io.File;

/**
 * Created by xus on 2016/11/16.
 */

public class FileUtil {
    public static Uri getFileUri(){
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
      return Uri.fromFile(file);
    }

}
