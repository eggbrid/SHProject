package com.shpro.xus.shproject.util;

import android.content.Context;
import android.util.Log;

import com.shpro.xus.shproject.bean.MyBmobInstallation;
import com.shpro.xus.shproject.bean.user.Account;

import java.util.List;

import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by xus on 2016/12/3.
 */

public class PushUtil {
    public static PushUtil pushUtil;

    PushUtil() {

    }

    public synchronized static PushUtil getInstance() {
        if (pushUtil == null) {
            pushUtil = new PushUtil();
        }
        return pushUtil;
    }

    public void initPush(final Context c) {
        BmobQuery<MyBmobInstallation> query = new BmobQuery<MyBmobInstallation>();
        query.addWhereEqualTo("installationId", MyBmobInstallation.getInstallationId(c));
        query.findObjects(new FindListener<MyBmobInstallation>() {
            @Override
            public void done(List<MyBmobInstallation> list, BmobException e) {
                if (list.size() > 0) {
                    MyBmobInstallation mbi = list.get(0);
                    mbi.setUid(Account.getCurrentUser(Account.class).getObjectId());
                    mbi.update(mbi.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {

                        }

                    });
                }
            }
        });
    }
}
