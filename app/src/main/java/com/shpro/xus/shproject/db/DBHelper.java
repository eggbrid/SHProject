package com.shpro.xus.shproject.db;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.shpro.xus.shproject.bean.user.Account;
import com.shpro.xus.shproject.bean.user.User;

import java.sql.SQLException;

/**
 * Created by xus on 2016/11/17.
 */

public class DBHelper {
    public DBHelper(Context context) {
        dBConfig = OpenHelperManager.getHelper(context, DBConfig.class);
        try {
            user = dBConfig.getDao(User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static DBHelper dBHelper;
    public static DBConfig dBConfig;
    private static Dao<User, Integer> user;

    public static DBHelper getInstance(Context context) {
        if (dBHelper == null) {
            dBHelper = new DBHelper(context);
        }
        return dBHelper;
    }

    public void createUser(User accounts) throws SQLException {
        user.create(accounts);
    }

}
