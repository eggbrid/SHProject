package com.shpro.xus.shproject.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.shprojectHttp.RegPlayer;
import com.shpro.xus.shproject.view.user.LoginActivity;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.TencentMap;

public class MainActivity extends AppCompatActivity {
    MapView mMapView;
    TencentMap tencentMap;
    Button list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMapView = (MapView) findViewById(R.id.map);
        list = (Button) findViewById(R.id.list);
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        tencentMap = mMapView.getMap();
        RegPlayer regPlayer = new RegPlayer();
    }
}
