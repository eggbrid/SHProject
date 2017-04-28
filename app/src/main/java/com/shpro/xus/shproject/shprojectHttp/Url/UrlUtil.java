package com.shpro.xus.shproject.shprojectHttp.Url;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.shpro.xus.shproject.APP;

/**
 * Created by xus on 2017/3/25.
 */

public class UrlUtil {
    private static String baseUrl="";
    public static String getUrl(){
        if (TextUtils.isEmpty(baseUrl)) {
            ApplicationInfo info = null;
            try {
                info = APP.getInstance().getPackageManager().getApplicationInfo(APP.getInstance().getPackageName(), PackageManager.GET_META_DATA);
                baseUrl = info.metaData.getString("hostIp");
            } catch (PackageManager.NameNotFoundException e) {

            }
            if (TextUtils.isEmpty(baseUrl)) {
                baseUrl = "http://60.205.227.63:8080/";
            }
        }
        return baseUrl;
    }
    public static String LOGIN_URL=getUrl()+"shproject/user/login";
    public static String REGISTER_URL=getUrl()+"shproject/user/register";
    public static String INSERTUSERDETAIL_URL=getUrl()+"shproject/user/insertUserDetail";
    public static String BAGLIST_URL=getUrl()+"shproject/bag/bagList";
    public static String NEARPEOPLE_LIST_URL=getUrl()+"shproject/nearPeople/list";
    public static String ADDBAG_URL=getUrl()+"shproject/bag/addBag";
    public static String BAGGET_URL=getUrl()+"shproject/bag/bagGet";
    public static String DELETEBAG_URL=getUrl()+"shproject/bag/deleteBag";
    public static String BAGMAPLIST_URL=getUrl()+"shproject/bag/bagMapList";
    public static String PUTBAGTOHEAR_URL=getUrl()+"shproject/bag/putBagToHear";
    public static String GETBAGBYHERE_URL=getUrl()+"shproject/bag/getBagByHere";
    public static String GETTODAY_URL=getUrl()+"shproject/tbOneDay/getToday";
    public static String BAISI_LIST_URL=getUrl()+"shproject/baisi/list";
    public static String START_DETAIL_URL=getUrl()+"shproject/start/detail";


}
