package com.shpro.xus.shproject.db;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.shpro.xus.shproject.bean.people.Self;
import com.shpro.xus.shproject.bean.user.Account;
import com.shpro.xus.shproject.bean.user.User;
import com.shpro.xus.shproject.db.bean.QILing;
import com.shpro.xus.shproject.util.SntpTime;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by xus on 2016/11/17.
 */

public class DBHelper {
    public DBHelper(Context context) {
        dBConfig = OpenHelperManager.getHelper(context.getApplicationContext(), DBConfig.class);
        try {
            qILingDao = dBConfig.getDao(QILing.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static DBHelper dBHelper;
    public static DBConfig dBConfig;
    private static Dao<QILing, Integer> qILingDao;

    public static DBHelper getInstance(Context context) {
        if (dBHelper == null) {
            dBHelper = new DBHelper(context);
        }
        return dBHelper;
    }

    //
    public int getQILingTimes(final String userId) throws SQLException {
        List<QILing> qiLings = qILingDao.queryBuilder().where().eq("userId", userId).query();
        if (qiLings == null || qiLings.size() <= 0) {
            return 0;

        } else {
            QILing qiLing = qiLings.get(0);
            return qiLing.getTimes();
        }
    }

    public QILing setQILingTime(final String userId, long date) throws SQLException {
        List<QILing> qiLings = null;
        qiLings = qILingDao.queryBuilder().where().eq("userId", userId).query();
        if (qiLings == null || qiLings.size() <= 0) {
            QILing qiLing = new QILing();
            qiLing.setDate(date);
            qiLing.setUserId(userId);
            qiLing.setTimes(0);
            qILingDao.create(qiLing);

        } else {
            QILing qiLing = qiLings.get(0);
            int lastTime = qiLing.getTimes();
            long h = (date - qiLing.getDate()) / (1000 * 60 * 60);
            if (lastTime >= 20) {
                //器灵破碎
                qILingDao.delete(qiLing);
                return qiLing;
            }
            if (lastTime >= 8) {
                if (h < 12) {
                    //时间没有超过12小时 需要增加次数
                    qiLing.setTimes(qiLing.getTimes() + 1);
                    qILingDao.update(qiLing);
                } else {
                    //时间超过12小时 重置时间和次数
                    qiLing.setTimes(0);
                    qiLing.setDate(date);
                    qILingDao.update(qiLing);
                }
            } else {
                qiLing.setTimes(qiLing.getTimes() + 1);
                qiLing.setDate(date);
                qILingDao.update(qiLing);
            }
        }
        qiLings = qILingDao.queryBuilder().where().eq("userId", userId).query();
        return qiLings.get(0);
    }

}
