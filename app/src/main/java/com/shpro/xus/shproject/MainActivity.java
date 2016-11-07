package com.shpro.xus.shproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.TencentMap;

public class MainActivity extends AppCompatActivity {
    MapView mMapView;
    TencentMap tencentMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMapView = (MapView)findViewById(R.id.map);
        tencentMap = mMapView.getMap();

    }
}
