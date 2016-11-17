package com.shpro.xus.shproject.db;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.shpro.xus.shproject.bean.user.Account;

/**
 * Created by xus on 2016/11/17.
 */

public class DBHelper {
    public DBHelper(Context context) {
        dBConfig = OpenHelperManager.getHelper(context, DBConfig.class);
    }

    public static DBHelper dBHelper;
    public static DBConfig dBConfig;

    public static DBHelper getInstance(Context context) {
        if (dBHelper == null) {
            dBHelper = new DBHelper(context);
        }
        return dBHelper;
    }

    public Dao<Account, Integer> dao;
}
