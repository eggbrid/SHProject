package com.shpro.xus.shproject.view.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.shpro.xus.shproject.APP;
import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.Bag;
import com.shpro.xus.shproject.bean.BagMap;
import com.shpro.xus.shproject.bean.BagTemplate;
import com.shpro.xus.shproject.bean.response.BagMapListResponse;
import com.shpro.xus.shproject.shprojectHttp.Url.UrlUtil;
import com.shpro.xus.shproject.shprojectHttp.okhttp.OkHttpUtil;
import com.shpro.xus.shproject.shprojectHttp.okhttp.interfaces.CallBack;
import com.shpro.xus.shproject.util.CommentUtil;
import com.shpro.xus.shproject.util.ImageLoaderUtil;
import com.shpro.xus.shproject.util.ToastUtil;
import com.shpro.xus.shproject.util.ViewUtil;
import com.shpro.xus.shproject.view.CommentActivity;
import com.shpro.xus.shproject.view.main.adapter.MapBagsAdapter;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdate;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends CommentActivity implements TencentLocationListener, TencentMap.OnCameraChangeListener {
    protected Button myLocation;
    protected Button delete;
    protected ListView bags;
    private MapView mMapView;
    private TencentMap tencentMap;
    private Marker markerLocation;
    private LatLng locationLatLng;
    private TencentLocationManager locationManager;
    private Marker centerMarker;
    private MapBagsAdapter adapter;
    private List<Marker> markers = new ArrayList<>();

    @Override
    public int setContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() throws Exception {
        setCommentTitleView("地图");
        isHasPoremess();
        mMapView = (MapView) findViewById(R.id.map);
        bags = (ListView) findViewById(R.id.bags);
        myLocation = (Button) findViewById(R.id.my_location);
        delete = (Button) findViewById(R.id.delete);
        myLocation.setOnClickListener(MainActivity.this);
        delete.setOnClickListener(MainActivity.this);
        bags.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        tencentMap = mMapView.getMap();
        tencentMap.setOnCameraChangeListener(this);
        tencentMap.setMapType(TencentMap.MAP_TYPE_NORMAL);
        adapter = new MapBagsAdapter(this, APP.getInstance().getBags());
        bags.setAdapter(adapter);
        doGetLocation();
    }

    public void getMapData(String lat, String lng) {
        showPross("正在获取背包");
        Map<String, String> map = new HashMap<>();
        map.put("lat", lat);
        map.put("lng", lng);
        OkHttpUtil.doGet(this, UrlUtil.BAGMAPLIST_URL, map, new CallBack<BagMapListResponse>() {
            @Override
            public void onSuccess(BagMapListResponse bagListResponse) {
                dissPross();
                if (bagListResponse != null) {
                    List<BagMap> list = bagListResponse.getList();
                    setMapMaker(list);
                } else {
                    ToastUtil.makeTextShort(MainActivity.this, "位置信号定位失踪！无法连接！重新再试");
                }

            }

            @Override
            public void onError(String s) {
                dissPross();
                ToastUtil.makeTextShort(MainActivity.this, s);

            }
        }, BagMapListResponse.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        mMapView.onStop();
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        mMapView.onRestart();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int error, String reason) {
        if (tencentMap == null) {
            return;
        }
        locationManager.removeUpdates(this);
        if (TencentLocation.ERROR_OK == error) {
            drawLocation(new LatLng(tencentLocation.getLatitude(), tencentLocation.getLongitude()));
            moveCamera(locationLatLng);
        } else {
            // 定位失败
        }
    }

    public void setMapMaker(List<BagMap> list) {

        LatLng centerLatLng = null;
        LatLng locationLatLng = null;
        if (centerMarker != null) {
            centerLatLng = new LatLng(centerMarker.getPosition().latitude, centerMarker.getPosition().longitude);
        }
        if (markerLocation != null) {
            locationLatLng = new LatLng(markerLocation.getPosition().latitude, markerLocation.getPosition().longitude);
        }
        tencentMap.clear();
        if (centerMarker != null) {
            drawCenter(centerLatLng);
        }
        if (markerLocation != null) {
            drawLocation(locationLatLng);
        }
        for (BagMap maps : list) {
            BagTemplate bt = maps.getBagTemplate();
            String icons = bt.getIcon();
            View mapItemView = View.inflate(this, R.layout.item_location_marker, null);
            ImageView icon = (ImageView) mapItemView.findViewById(R.id.icon);
            if (TextUtils.isEmpty(icons)) {
                icon.setImageResource(R.drawable.shpg_unno);
            } else if (icons.startsWith("http:")) {
                ImageLoaderUtil.getInstance().loadNomalImage(icons, icon);
            } else if (icons.startsWith("shpg")) {
                icon.setImageResource(CommentUtil.getIcon(icons, this));
            } else {
                icon.setImageResource(R.drawable.shpg_unno);
            }
            MarkerOptions option = new MarkerOptions();
            option.icon(BitmapDescriptorFactory.fromBitmap(ViewUtil.getViewBitmap(mapItemView)));
            option.position(new LatLng(Double.parseDouble(maps.getLat()), Double.parseDouble(maps.getLng())));
            Marker marker = tencentMap.addMarker(option);
            markers.add(marker);
        }
    }

    public void moveCamera(LatLng latLng) {
        if (latLng == null) {
            return;
        }
        CameraUpdate cameraSigma = CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng,//新的中心点坐标
                19,//新的缩放级别
                0f, //俯仰角 0~45° (垂直地图时为0)
                0f)); //偏航角 0~360° (正北方为0)
        tencentMap.moveCamera(cameraSigma);
    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //可在此继续其他操作。
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.my_location) {
            doGetLocation();
        } else if (view.getId() == R.id.delete) {
            //选择物品页面
            showBags();
        }
    }

    @Override
    public void onCameraChange(final CameraPosition cameraPosition) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                drawCenter(cameraPosition.target);

            }
        });
    }

    @Override
    public void onCameraChangeFinished(final CameraPosition cameraPosition) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getMapData(cameraPosition.target.latitude + "", cameraPosition.target.longitude + "");
            }
        });
//        getMapData();
    }

    public void isHasPoremess() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] permissions = {
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
            if (checkSelfPermission(permissions[0]) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissions, 0);
            }
        }
    }

    public void doGetLocation() {
        TencentLocationRequest request = TencentLocationRequest.create();
        request.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_GEO);
        request.setInterval(1000);
        locationManager = TencentLocationManager.getInstance(this);
        locationManager.requestLocationUpdates(request, this);
    }

    public void showBags() {
        delete.setText(bags.getVisibility() == View.VISIBLE ? "丢弃物品" : "关闭背包");
        bags.setVisibility(bags.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);

    }

    public void drawLocation(LatLng locationLatLng) {
        // 定位成功
        this.locationLatLng = locationLatLng;
        if (markerLocation != null) {
            markerLocation.remove();
        }
        markerLocation = tencentMap.addMarker(new MarkerOptions(locationLatLng).
                snippet("markerLocation"));
        markerLocation.setClickable(false);
        markerLocation.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.loc));
    }

    public void drawCenter(LatLng latLng) {
        if (centerMarker != null) {
            centerMarker.remove();
        }
        final MarkerOptions option = new MarkerOptions();
        option.icon(BitmapDescriptorFactory.fromResource(R.drawable.hognsedingwei));
        option.position(latLng);
        centerMarker = tencentMap.addMarker(option);


    }
}
