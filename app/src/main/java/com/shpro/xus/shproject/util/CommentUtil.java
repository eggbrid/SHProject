package com.shpro.xus.shproject.util;

import android.app.ActivityManager;
import android.content.Context;

import com.shpro.xus.shproject.R;

import java.util.List;

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
    public static boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(900);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }
}
