package com.shpro.xus.shproject.db;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.shpro.xus.shproject.bean.people.Self;
import com.shpro.xus.shproject.bean.user.Account;
import com.shpro.xus.shproject.bean.user.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by xus on 2016/11/17.
 */

public class DBHelper {
    public DBHelper(Context context) {
        dBConfig = OpenHelperManager.getHelper(context.getApplicationContext(), DBConfig.class);
        try {
            user = dBConfig.getDao(User.class);
            self = dBConfig.getDao(Self.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static DBHelper dBHelper;
    public static DBConfig dBConfig;
    private static Dao<User, Integer> user;
    private static Dao<Self, Integer> self;

    public static DBHelper getInstance(Context context) {
        if (dBHelper == null) {
            dBHelper = new DBHelper(context);
        }
        return dBHelper;
    }
//
//    public void createUser(User accounts) throws SQLException {
//        self.delete(self.queryForAll());
//        self.create(accounts.getSelf());
//        user.delete(user.queryForAll());
//        user.create(accounts);
//    }
    public void delectUser(User accounts) throws SQLException {
        user.delete(user.queryForAll());
    }


    public User getUser() throws SQLException {
        List<User> users= user.queryForAll();
        if (users!=null&&users.size()>0){
            return users.get(0);
        }
        return null;
    }
}
