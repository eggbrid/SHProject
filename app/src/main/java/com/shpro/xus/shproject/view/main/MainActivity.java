package com.shpro.xus.shproject.view.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.view.CommentActivity;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdate;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.LocationSource;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.UiSettings;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;

public class MainActivity extends CommentActivity implements TencentLocationListener {
    protected Button myLocation;
    protected Button delete;
    MapView mMapView;
    TencentMap tencentMap;
    CameraPosition cameraPosition;
    private Marker markerLocation;
    private LatLng locationLatLng;
    private       TencentLocationManager locationManager;

    @Override
    public int setContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() throws Exception {
        setCommentTitleView("地图");
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
        mMapView = (MapView) findViewById(R.id.map);
        tencentMap = mMapView.getMap();
        tencentMap.setMapType(TencentMap.MAP_TYPE_NORMAL);
        cameraPosition = tencentMap.getCameraPosition();
        UiSettings settings = tencentMap.getUiSettings();
        TencentLocationRequest request = TencentLocationRequest.create();
        request.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_GEO);
        request.setInterval(1000);
         locationManager = TencentLocationManager.getInstance(this);
        int error = locationManager.requestLocationUpdates(request, this);
        myLocation = (Button) findViewById(R.id.my_location);
        myLocation.setOnClickListener(MainActivity.this);
        delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(MainActivity.this);
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
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int error, String reason) {
        if (TencentLocation.ERROR_OK == error) {
            // 定位成功
            boolean b=locationLatLng==null;
         locationLatLng = new LatLng(tencentLocation.getLatitude(), tencentLocation.getLongitude());
            if (markerLocation != null) {
                tencentMap.clear();
            }
            if (markerLocation!=null){
                markerLocation.remove();
            }
            markerLocation = tencentMap.addMarker(new MarkerOptions(locationLatLng).
                    snippet("markerLocation"));
            markerLocation.setClickable(false);
            markerLocation.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.loc));
            if (b){
                moveCamera(locationLatLng);
            }
        } else {
            // 定位失败
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
            moveCamera(locationLatLng);
        } else if (view.getId() == R.id.delete) {
            //选择物品页面
        }
    }

}
