package com.shpro.xus.shproject.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by xus on 2016/11/10.
 */

public class ToastUtil {
    public static void makeTextShort(Context context,String text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    };
}
