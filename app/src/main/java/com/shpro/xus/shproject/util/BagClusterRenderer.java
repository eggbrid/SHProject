//package com.shpro.xus.shproject.util;
//
//import android.content.Context;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.ImageView;
//
//import com.shpro.xus.shproject.R;
//import com.shpro.xus.shproject.bean.BagMap;
//import com.tencent.mapsdk.raster.model.BitmapDescriptorFactory;
//import com.tencent.mapsdk.raster.model.MarkerOptions;
//import com.tencent.tencentmap.mapsdk.map.TencentMap;
//import com.tencent.tencentmap.mapsdk.vector.utils.clustering.Cluster;
//import com.tencent.tencentmap.mapsdk.vector.utils.clustering.ClusterManager;
//import com.tencent.tencentmap.mapsdk.vector.utils.clustering.view.DefaultClusterRenderer;
//
//import static cn.bmob.v3.Bmob.getApplicationContext;
//
///**
// * Created by xus on 2017/3/30.
// */
//
//public class BagClusterRenderer extends DefaultClusterRenderer<TencentMapItem> {
//
//    private View mapItemView;
//    private ImageView icon;
//
//    public BagClusterRenderer(Context context, TencentMap tencentMap, ClusterManager<TencentMapItem> clusterManager) {
//        super(context, tencentMap, clusterManager);
//         mapItemView = LayoutInflater.from(context).inflate(R.layout.item_location_marker, null);
//         icon = (ImageView) mapItemView.findViewById(R.id.icon);
//
//        setMinClusterSize(1);
//    }
//
//    @Override
//    public void onBeforeClusterItemRendered(TencentMapItem tencentMapItem, MarkerOptions markerOptions) {
//        BagMap bagMap=tencentMapItem.getBagMap();
//        String icons=bagMap.getBagTemplate().getIcon();
//        ImageView icon = (ImageView) mapItemView.findViewById(R.id.icon);
//        if (TextUtils.isEmpty(icons)) {
//            icon.setImageResource(R.drawable.shpg_unno);
//        } else if (icons.startsWith("http:")) {
//            ImageLoaderUtil.getInstance().loadNomalImage(icons, icon);
//        } else if (icons.startsWith("shpg")) {
//            icon.setImageResource(CommentUtil.getIcon(icons, getApplicationContext()));
//        } else {
//            icon.setImageResource(R.drawable.shpg_unno);
//        }
//        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(ViewUtil.getViewBitmap(mapItemView)));
//    }
//
//    @Override
//    public void onBeforeClusterRendered(Cluster<TencentMapItem> cluster, MarkerOptions markerOptions) {
//        super.onBeforeClusterRendered(cluster,markerOptions);
//    }
////        BagMap[] resources = new BagMap[cluster.getItems().size()];
////        int i = 0;
////        for (TencentMapItem item : cluster.getItems()) {
////            resources[i++] = item.getBagMap();
////        }
////        PetalDrawable drawable = new PetalDrawable(getApplicationContext(), resources);
////        mClusterImageView.setImageDrawable(drawable);
////        Bitmap icon = mClusterIconGenerator.makeIcon();
////        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));    }
//}
