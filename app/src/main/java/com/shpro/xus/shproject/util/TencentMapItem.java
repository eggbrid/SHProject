//package com.shpro.xus.shproject.util;
//
//import com.shpro.xus.shproject.bean.BagMap;
//import com.tencent.mapsdk.raster.model.LatLng;
//import com.tencent.tencentmap.mapsdk.vector.utils.clustering.ClusterItem;
//
///**
// * Created by xus on 2017/2/9.
// */
//public class TencentMapItem implements ClusterItem {
//    private final LatLng mLatLng;
//    private final BagMap bagMap;
//
//    public TencentMapItem(double latitude, double longitude ,BagMap bagMap) {
//        // TODO Auto-generated constructor stub
//        mLatLng = new LatLng(latitude, longitude);
//        this.bagMap=bagMap;
//    }
//
//    public BagMap getBagMap() {
//        return bagMap;
//    }
//
//    @Override
//    public LatLng getPosition() {
//        return mLatLng;
//    }
//}
