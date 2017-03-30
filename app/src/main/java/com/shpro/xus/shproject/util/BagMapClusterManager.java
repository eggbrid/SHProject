package com.shpro.xus.shproject.util;

import android.content.Context;

import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
import com.tencent.tencentmap.mapsdk.vector.utils.clustering.ClusterManager;

/**
 * Created by xus on 2017/3/30.
 */

public class BagMapClusterManager extends ClusterManager<TencentMapItem> {
    private TencentMap.OnCameraChangeListener onCameraChangeListener ;
    public BagMapClusterManager(Context context, TencentMap tencentMap, TencentMap.OnCameraChangeListener onCameraChangeListener) {
        super(context, tencentMap);
        this.onCameraChangeListener=onCameraChangeListener;
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        super.onCameraChange(cameraPosition);
        onCameraChangeListener.onCameraChange(cameraPosition);
    }

    @Override
    public void onCameraChangeFinished(CameraPosition cameraPosition) {
        super.onCameraChangeFinished(cameraPosition);
        onCameraChangeListener.onCameraChangeFinished(cameraPosition);

    }
}
