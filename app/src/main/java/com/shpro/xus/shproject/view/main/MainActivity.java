package com.shpro.xus.shproject.view.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.shpro.xus.shproject.APP;
import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.BagMap;
import com.shpro.xus.shproject.bean.BagTemplate;
import com.shpro.xus.shproject.bean.response.BagMapAndBagListResponse;
import com.shpro.xus.shproject.bean.response.BagMapListResponse;
import com.shpro.xus.shproject.shprojectHttp.Url.UrlUtil;
import com.shpro.xus.shproject.shprojectHttp.okhttp.OkHttpUtil;
import com.shpro.xus.shproject.shprojectHttp.okhttp.interfaces.CallBack;
import com.shpro.xus.shproject.util.CommentUtil;
import com.shpro.xus.shproject.util.ImageLoaderUtil;
import com.shpro.xus.shproject.util.MapUtils;
import com.shpro.xus.shproject.util.ToastUtil;
import com.shpro.xus.shproject.util.ViewUtil;
import com.shpro.xus.shproject.view.CommentActivity;
import com.shpro.xus.shproject.view.main.adapter.MapBagsAdapter;
import com.shpro.xus.shproject.view.views.CommentBagDialog;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.mapsdk.raster.model.BitmapDescriptorFactory;
import com.tencent.mapsdk.raster.model.CameraPosition;
import com.tencent.mapsdk.raster.model.Circle;
import com.tencent.mapsdk.raster.model.CircleOptions;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.map.CameraUpdate;
import com.tencent.tencentmap.mapsdk.map.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.TencentMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends CommentActivity implements TencentLocationListener, TencentMap.OnMapCameraChangeListener, TencentMap.OnMarkerClickListener {
    protected Button myLocation;
    protected Button delete;
    protected ListView bags;
    private MapView mMapView;
    private TencentMap tencentMap;
    private Marker markerLocation;
    private LatLng locationLatLng;
    private LatLng centerLatLng;
    private LatLng lastLatLng;
    private Circle circle;

    private TencentLocationManager locationManager;
    private MapBagsAdapter adapter;
    private List<Marker> markers = new ArrayList<>();
    private Map<Marker, BagMap> mapMap = new HashMap<>();

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
                LatLng latLng = centerLatLng;
                setBagToHere(latLng.getLatitude() + "", latLng.getLongitude() + "", adapter.getList().get(i).getId());
            }
        });
        tencentMap = mMapView.getMap();
        tencentMap.setOnMapCameraChangeListener(this);
//        tencentMap.setMapType(TencentMap.MAP_TYPE_NORMAL);
        tencentMap.setOnMarkerClickListener(this);
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

    public void getBagByHere(String lat, String lng, String bagMapId) {
        showPross("正在捡起包裹");
        Map<String, String> map = new HashMap<>();
        map.put("lat", lat);
        map.put("lng", lng);
        map.put("bagMapId", bagMapId);
        map.put("userId", APP.getInstance().getUser().getId());
        OkHttpUtil.doPost(this, UrlUtil.GETBAGBYHERE_URL, map, new CallBack<BagMapAndBagListResponse>() {
            @Override
            public void onSuccess(BagMapAndBagListResponse bagListResponse) {
                dissPross();
                if (bagListResponse != null) {
                    List<BagMap> list = bagListResponse.getList();
                    APP.getInstance().setBags(bagListResponse.getListBag());
                    adapter.setList(APP.getInstance().getBags());
                    adapter.notifyDataSetChanged();
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
        }, BagMapAndBagListResponse.class);
    }

    public void setBagToHere(String lat, String lng, String bid) {
        showPross("正在把包裹放在地图上");
        Map<String, String> map = new HashMap<>();
        map.put("lat", lat);
        map.put("lng", lng);
        map.put("bagId", bid);
        OkHttpUtil.doPost(this, UrlUtil.PUTBAGTOHEAR_URL, map, new CallBack<BagMapAndBagListResponse>() {
            @Override
            public void onSuccess(BagMapAndBagListResponse bagListResponse) {
                dissPross();
                if (bagListResponse != null) {
                    List<BagMap> list = bagListResponse.getList();
                    APP.getInstance().setBags(bagListResponse.getListBag());
                    adapter.setList(APP.getInstance().getBags());
                    adapter.notifyDataSetChanged();
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
        }, BagMapAndBagListResponse.class);
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
        LatLng locationLatLng = null;
        if (markerLocation != null) {
            locationLatLng = new LatLng(markerLocation.getPosition().getLatitude(), markerLocation.getPosition().getLongitude());
        }
        tencentMap.clearAllOverlays();
        markers.clear();
        mapMap.clear();
        if (markerLocation != null) {
            drawLocation(locationLatLng);
        }
        for (BagMap maps : list) {
            BagTemplate bt = maps.getBagTemplate();
            String icons = bt.getIcon();
            View mapItemView = LayoutInflater.from(this).inflate(R.layout.item_location_marker, null);
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
            Marker marker = tencentMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(ViewUtil.getViewBitmap(mapItemView))).
                    position(new LatLng(Double.parseDouble(maps.getLat()), Double.parseDouble(maps.getLng()))));
            markers.add(marker);
            mapMap.put(marker, maps);
        }
//        clusterManager.addItems(items);
    }

    public void moveCamera(LatLng latLng) {
        if (latLng == null) {
            return;
        }
        CameraUpdate cameraSigma = CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng,//新的中心点坐标
                19)); //偏航角 0~360° (正北方为0)
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
                centerLatLng = cameraPosition.getTarget();

            }
        });
    }



    @Override
    public void onCameraChangeFinish(final CameraPosition cameraPosition) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (lastLatLng != null) {
                    if (MapUtils.GetDistance(lastLatLng.getLatitude(), lastLatLng.getLongitude(), cameraPosition.getTarget().getLatitude(), cameraPosition.getTarget().getLongitude()) > 50) {
                        getMapData(cameraPosition.getTarget().getLatitude() + "", cameraPosition.getTarget().getLongitude() + "");
                        lastLatLng = cameraPosition.getTarget();
                    }
                } else {
                    getMapData(cameraPosition.getTarget().getLatitude() + "", cameraPosition.getTarget().getLongitude() + "");
                    lastLatLng = cameraPosition.getTarget();
                }
            }
        });
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
        if (circle != null) {
            circle.remove();
        }
        markerLocation = tencentMap.addMarker(new MarkerOptions().position(locationLatLng).
                snippet("markerLocation"));
//        markerLocation.setClickable(false);
        markerLocation.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.loc));
        circle = tencentMap.addCircle(new CircleOptions().
                center(locationLatLng).
                radius(20000d).
                fillColor(0x330000ff).
                strokeColor(0x33000000).
                strokeWidth(3));
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        marker. hideInfoWindow();
        if (MapUtils.GetDistance(locationLatLng.getLatitude(), locationLatLng.getLongitude(), marker.getPosition().getLatitude(), marker.getPosition().getLongitude()) > 20000d) {
            ToastUtil.makeTextShort(this,"你不能捡起圈圈外的东西哦！");
            return true;
        }
        if (marker == markerLocation) {

        } else {
           final  BagMap bagMap = mapMap.get(marker);
            final CommentBagDialog dialog = new CommentBagDialog(this, bagMap.getBagTemplate());
            dialog.show();
            dialog.setButton1("放入背包").setButton2("无视").setButtonListener1(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getBagByHere(bagMap.getLat(),bagMap.getLng(),bagMap.getId());
                    dialog.dismiss();
                }
            }).setButtonListener2(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();

                }
            });
        }
        return true;
    }


}
