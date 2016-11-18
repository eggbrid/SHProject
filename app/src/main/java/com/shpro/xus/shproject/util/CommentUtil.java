package com.shpro.xus.shproject.util;

import android.content.Context;

import com.shpro.xus.shproject.R;

/**
 * Created by xus on 2016/11/18.
 */

public class CommentUtil {
    public static int getIcon(String id, Context context) {
        try {
            return context.getResources().getIdentifier(id, "drawable", context.getPackageName());
        } catch (Exception e) {
            return R.drawable.shpg_unno;
        }
    }
}
