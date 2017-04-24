package com.shpro.xus.shproject.view.baisi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.shpro.xus.shproject.APP;
import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.baisi.ShowBaiSiBean;
import com.shpro.xus.shproject.bean.people.NearPeople;
import com.shpro.xus.shproject.bean.response.BaisiResponse;
import com.shpro.xus.shproject.bean.response.FindPeopleResponse;
import com.shpro.xus.shproject.interfaces.views.RefreshListener;
import com.shpro.xus.shproject.shprojectHttp.Url.UrlUtil;
import com.shpro.xus.shproject.shprojectHttp.okhttp.OkHttpUtil;
import com.shpro.xus.shproject.shprojectHttp.okhttp.interfaces.CallBack;
import com.shpro.xus.shproject.util.ToastUtil;
import com.shpro.xus.shproject.view.CommentActivity;
import com.shpro.xus.shproject.view.baisi.adapter.VideoAdapter;
import com.shpro.xus.shproject.view.find.FindPeopleActivity;
import com.shpro.xus.shproject.view.find.adapter.FindPeopleAdapter;
import com.shpro.xus.shproject.view.views.RefreshLayout;
import com.tencent.map.geolocation.TencentLocationListener;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class VideoActivity extends CommentActivity  implements RefreshListener {

    ListView videos;
    VideoAdapter adapter;
    private RefreshLayout swipe_container;
    private List<ShowBaiSiBean> list = new ArrayList<>();

    private int pageSize = 20;
    private int pageNum = 0;
    @Override
    public int setContentView() {
        return R.layout.activity_video;
    }

    @Override
    public void initView() throws Exception {
        setCommentTitleView("搞笑视频");
        videos = (ListView) findViewById(R.id.videos);
        swipe_container=(RefreshLayout) findViewById(R.id.swipe_container);
        adapter = new VideoAdapter(this, list);
        setRefresh(swipe_container, videos, true, true, this);
        videos.setAdapter(adapter);
    }


    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();

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
    public void getData() {
        Map<String, String> map = new HashMap<>();
        map.put("pageNum", pageNum + "");
        map.put("pageSize", pageSize + "");
        OkHttpUtil.doGet(this, UrlUtil.BAISI_LIST_URL, map, new CallBack<BaisiResponse>() {
            @Override
            public void onSuccess(BaisiResponse baisiResponse) {
                if (baisiResponse != null) {
                    List<ShowBaiSiBean> data = baisiResponse.getList();
                    if (pageNum == 0) {
                        list.clear();
                        list=new ArrayList<ShowBaiSiBean>();
                    }
                    if (data == null) {
                        data = new ArrayList<ShowBaiSiBean>();
                    }
                    stopRefresh(swipe_container, data.size() < pageSize);
                    list.addAll(data);
                    adapter.setList(list);
                    adapter.notifyDataSetChanged();
                } else {
                    ToastUtil.makeTextShort(VideoActivity.this, "空间戒没有空间波动！");
                }
            }
            @Override
            public void onError(String s) {
                stopRefresh(swipe_container, false);
                ToastUtil.makeTextShort(VideoActivity.this, s);
            }
        }, BaisiResponse.class);
    }
}
