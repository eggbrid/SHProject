package com.shpro.xus.shproject;

import android.app.ActivityManager;
import android.app.Application;
import android.content.pm.PackageManager;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.L;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.shpro.xus.shproject.bean.Bag;
import com.shpro.xus.shproject.bean.response.LoginResponse;
import com.shpro.xus.shproject.bean.user.User;
import com.shpro.xus.shproject.bean.user.UserDetail;
import com.shpro.xus.shproject.db.cache.ACacheUtil;
import com.shpro.xus.shproject.shprojectHttp.HttpUtil;
import com.shpro.xus.shproject.shprojectHttp.okhttp.OkHttpUtil;
import com.shpro.xus.shproject.util.AndroidIDUtil;
import com.shpro.xus.shproject.util.ConstantUtil;
import com.shpro.xus.shproject.util.SntpTime;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import cn.bmob.v3.Bmob;

/**
 * Created by xus on 2016/11/8.
 */

public class APP extends Application {
    public static APP app;

    public static APP getInstance() {
        return app;
    }

    private User user;
    private UserDetail userDetail;
    private LoginResponse loginResponse;
    private List<Bag> bags;

    public boolean hasBag(String bagTemplateId) {
        bags = getBags();
        for (Bag bag : bags) {
            if (bag.getBagTemplateId().equals(bagTemplateId)) {
                return true;
            }
        }
        return false;
    }

    public void setBags(List<Bag> bags) {
        this.bags = bags;
        ACacheUtil.getInstance().cacheList(ConstantUtil.BAG,bags);
    }

    public List<Bag> getBags() {
        if (bags==null){
            bags = ACacheUtil.getInstance().getList(ConstantUtil.BAG, Bag.class);
        }
        return bags;
    }

    public User getUser() {
        if (!isLogin()) {
            return null;
        }
        if (user == null) {
            this.user = loginResponse.getUser();
        }
        return user;
    }

    public LoginResponse getLoginResponse() {
        loginResponse = ACacheUtil.getInstance().getObject(ConstantUtil.USER, LoginResponse.class);
        return loginResponse;
    }

    public UserDetail getUserDetail() {
        if (!isLogin()) {
            return null;
        }
        if (userDetail == null) {
            this.userDetail = loginResponse.getUserDetail();
        }
        return userDetail;
    }

    public boolean isLogin() {
        if (loginResponse!=null){
            return true;
        }
        loginResponse = ACacheUtil.getInstance().getObject(ConstantUtil.USER, LoginResponse.class);
        return loginResponse != null;
    }

    public void setLoginResponse(LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
        ACacheUtil.getInstance().cacheObject(ConstantUtil.USER, loginResponse);
    }

    public void setUser(User user) {
        this.user = user;
        this.loginResponse.setUser(user);
        ACacheUtil.getInstance().cacheObject(ConstantUtil.USER, loginResponse);
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
        this.loginResponse.setUserDetail(userDetail);
        ACacheUtil.getInstance().cacheObject(ConstantUtil.USER, loginResponse);

    }


    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        OkHttpUtil.initHttpUtil();
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        if (processAppName == null || !processAppName.equalsIgnoreCase(app.getPackageName())) {
            return;
        }
        HttpUtil.getInstance().init(this);
        Bmob.initialize(this, "71c81ce12d70f8e9415d6c86d62d5a65");
        // 初始化BmobSDK
        // 使用推送服务时的初始化操作
//        BmobInstallation.getCurrentInstallation().save();
        // 启动推送服务
//        BmobPush.startWork(this);
        initImageLoader();
        initHX();
        new SntpTime().getNetTime();
    }

    public void initImageLoader() {
        String filePath = "Android/data/com.shpro.xus.shproject/files/cache/img/";//Environment.getExternalStorageDirectory() + "/Android/data/" + instance.getPackageName() + "/cache/";
        File cacheDir = StorageUtils.getOwnCacheDirectory(this, filePath);// 获取到缓存的目录地址
        //L.d("cacheDir:"+cacheDir.getPath());
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(this);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.threadPoolSize(3);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.memoryCache(new WeakMemoryCache());

        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        //config.diskCacheSize(1 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.diskCache(new UnlimitedDiskCache(cacheDir));
//        config.writeDebugLogs(); // Remove for release app
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
        L.writeLogs(false);
    }

    public void initHX() {
        EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
//        options.setAcceptInvitationAlways(false);
//初始化
        options.setAutoLogin(false);

        EMClient.getInstance().init(this, options);
//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

}
