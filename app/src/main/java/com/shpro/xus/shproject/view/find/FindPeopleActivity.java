package com.shpro.xus.shproject.view.find;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.shpro.xus.shproject.APP;
import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.Bag;
import com.shpro.xus.shproject.bean.call.CallPeople;
import com.shpro.xus.shproject.bean.people.NearPeople;
import com.shpro.xus.shproject.bean.response.BagListResponse;
import com.shpro.xus.shproject.bean.response.FindPeopleResponse;
import com.shpro.xus.shproject.bean.user.Account;
import com.shpro.xus.shproject.bean.user.User;
import com.shpro.xus.shproject.bean.user.UserBag;
import com.shpro.xus.shproject.bean.user.UserDetail;
import com.shpro.xus.shproject.db.cache.ACacheUtil;
import com.shpro.xus.shproject.interfaces.views.RefreshListener;
import com.shpro.xus.shproject.shprojectHttp.Url.UrlUtil;
import com.shpro.xus.shproject.shprojectHttp.okhttp.OkHttpUtil;
import com.shpro.xus.shproject.shprojectHttp.okhttp.interfaces.CallBack;
import com.shpro.xus.shproject.util.AndroidIDUtil;
import com.shpro.xus.shproject.util.SHCallUtil;
import com.shpro.xus.shproject.util.ToastUtil;
import com.shpro.xus.shproject.view.CommentActivity;
import com.shpro.xus.shproject.view.call.CallDetailActivity;
import com.shpro.xus.shproject.view.call.CallListActivity;
import com.shpro.xus.shproject.view.call.adapter.CallAdapter;
import com.shpro.xus.shproject.view.find.adapter.FindPeopleAdapter;
import com.shpro.xus.shproject.view.main.SHMainActivity;
import com.shpro.xus.shproject.view.views.RefreshLayout;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by xus on 2016/12/3.
 */

public class FindPeopleActivity extends CommentActivity implements View.OnClickListener, RefreshListener, TencentLocationListener {
    public FindPeopleAdapter adapter;
    protected ListView peoples;
    private RefreshLayout swipe_container;
    private List<NearPeople> list = new ArrayList<>();
    private int pageSize = 20;
    private int pageNum = 0;
    private double lat=39.908683;
    private double lng=116.397518;
    private TencentLocationManager locationManager;

    @Override
    public int setContentView() {
        return R.layout.activity_find_people;
    }

    @Override
    public void initView() throws Exception {
        peoples = (ListView) findViewById(R.id.peoples);
        swipe_container=(RefreshLayout) findViewById(R.id.swipe_container);
        peoples.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                UserDetail user = list.get(i).getUserDetail();
                if (APP.getInstance().hasBag("1002")) {
                    gotoChat(user);
                } else {
                    ToastUtil.makeTextShort(FindPeopleActivity.this, "您现在没有通讯器呢，若没有扔掉新手指南，可以在里面领取哦！");

                }

            }
        });
        setCommentTitleView("山海师");
        locationManager = TencentLocationManager.getInstance(this);
        TencentLocationRequest request = TencentLocationRequest.create();
        request.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_GEO);
        request.setInterval(1000);
        int error = locationManager.requestLocationUpdates(request, this);
        swipe_container.setRefreshing(true);

    }

    public void getData() {
        Map<String, String> map = new HashMap<>();
        map.put("userId", APP.getInstance().getUser().getId());
        map.put("lat", lat+"");
        map.put("lng", lng+"");
        map.put("pageNum", pageNum + "");
        map.put("pageSize", pageSize + "");
        OkHttpUtil.doGet(this, UrlUtil.NEARPEOPLE_LIST_URL, map, new CallBack<FindPeopleResponse>() {
            @Override
            public void onSuccess(FindPeopleResponse findPeopleResponse) {
                if (findPeopleResponse != null) {
                    List<NearPeople> data = findPeopleResponse.getList();
                    if (pageNum == 0) {
                        list.clear();
                    }
                    if (data == null) {
                        data = new ArrayList<NearPeople>();
                    }
                    stopRefresh(swipe_container, data.size() < pageSize);
                    list.addAll(data);
                    adapter = new FindPeopleAdapter(FindPeopleActivity.this, list);
                    peoples.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    ToastUtil.makeTextShort(FindPeopleActivity.this, "空间戒没有空间波动！");
                }

            }

            @Override
            public void onError(String s) {
                stopRefresh(swipe_container, false);
                ToastUtil.makeTextShort(FindPeopleActivity.this, s);
            }
        }, FindPeopleResponse.class);
    }



    public void gotoChat(final UserDetail user) {
        new SHCallUtil().toCall(APP.getInstance().getUser().getId().toLowerCase(), new SHCallUtil.CallBack() {

            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        CallPeople callPeople = new CallPeople();
                        callPeople.setName(user.getName());
                        callPeople.setAvatar(user.getAvatar());
                        callPeople.setUnRead(0);
                        callPeople.setId(user.getUserid().toLowerCase());
                        Intent intent = new Intent(FindPeopleActivity.this, CallDetailActivity.class);
                        intent.putExtra("people", callPeople);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onError() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.makeTextShort(FindPeopleActivity.this, "通讯器发出呲呲呲的声音...");
                        FindPeopleActivity.this.finish();
                    }
                });

            }
        });
    }

    @Override
    public void onLoadMore() {
        pageNum++;
        getData();
    }

    @Override
    public void onRefresh() {
        pageNum = 0;
        getData();
    }

    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {
        locationManager.removeUpdates(this);

        if (TencentLocation.ERROR_OK == i) {
            // 定位成功
            boolean b = tencentLocation == null;
            lat=tencentLocation.getLatitude();
            lng=tencentLocation.getLongitude();
        } else {
        }
        setRefresh(swipe_container, peoples, true, true, this);
    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {
        if (s.equals("cell")) {
        }
        if (s.equals("wifi")) {
        }
        if (s.equals("GPS")) {
        }
    }
}
