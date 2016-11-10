package com.shpro.xus.shproject.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.shpro.xus.shproject.bean.skill.SkillB;
import com.shpro.xus.shproject.bean.user.Account;
import com.shpro.xus.shproject.bean.user.User;

/**
 * Created by xus on 2016/11/9.
 */

public class DBConfig extends OrmLiteSqliteOpenHelper {
    private static final String TABLE_NAME = "shproject.db";

    public DBConfig(Context context) {
        super(context, TABLE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, SkillB.class);
            TableUtils.createTable(connectionSource, Account.class);

        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
