package com.shpro.xus.shproject.util;

import android.text.TextUtils;

import com.shpro.xus.shproject.bean.Bag;
import com.shpro.xus.shproject.bean.user.Account;
import com.shpro.xus.shproject.bean.user.UserBag;
import com.shpro.xus.shproject.db.cache.ACacheUtil;
import com.shpro.xus.shproject.view.BaseActivity;

import java.util.ArrayList;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by xus on 2016/12/2.
 */

public class BagUtil {
    public static BagUtil bagUtil;

    public BagUtil() {

    }

    public synchronized static BagUtil getInstance() {
        if (bagUtil == null) {
            bagUtil = new BagUtil();
        }
        return bagUtil;
    }

    public void creatBag(){
//        Bag bag = new Bag();
//        bag.setAction("1");
//        bag.setName("新手指南");
//        bag.setIcon("shpg_help");
//        bag.setActionInfo("com.shpro.xus.shproject.view.main.HelpActivity");
//        bag.setInfo("一个看起来很新的羊皮卷，里面貌似写着字");
    }
    public void addBag(final BaseActivity c, Bag b, final boolean isShowP,final int flg,final OnSuccess os) {
        if (isShowP){
            c.showPross("正在把物品放入包包里");
        }
        UserBag userBag = ACacheUtil.getInstance().getObject(AndroidIDUtil.getID(c) + "bag", UserBag.class);
        if (userBag == null) {
            userBag = new UserBag();
            userBag.setBags(new ArrayList<Bag>());
        }
        userBag.getBags().add(b);
        userBag.setUserid(BmobUser.getCurrentUser(Account.class).getObjectId());
        final UserBag f = userBag;
        if (!TextUtils.isEmpty(userBag.getObjectId())) {
            userBag.update(userBag.getObjectId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (isShowP){
                        c.dissPross();
                        os.onSuccess(flg);
                    }
                    if (e == null) {
                        ACacheUtil.getInstance().cacheObject(AndroidIDUtil.getID(c) + "bag", f);
                    } else {
                    }
                }
            });
        } else {
            userBag.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (isShowP){
                        c.dissPross();
                        os.onSuccess(flg);

                    }
                    if (e == null) {
                        f.setObjectId(s);
                        ACacheUtil.getInstance().cacheObject(AndroidIDUtil.getID(c) + "bag", f);
                    } else {
                    }
                }
            });
        }

    }
    public interface OnSuccess{
        void onSuccess(int flg);
    }
}
