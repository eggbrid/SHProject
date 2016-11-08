package com.shpro.xus.shproject.shprojectHttp;

import android.content.Context;
import android.util.Log;

import com.shpro.xus.shproject.people.Self;
import com.wilddog.client.ChildEventListener;
import com.wilddog.client.DataSnapshot;
import com.wilddog.client.Query;
import com.wilddog.client.SyncError;
import com.wilddog.client.SyncReference;
import com.wilddog.client.WilddogSync;

/**
 * Created by xus on 2016/11/8.
 */

public class RegPlayer {
    public void regPlayer(Self player, Context context){
        HttpUtil.getInstance().getRef().child("palyer").child("1").setValue(player);
    }
    public void getPalyList(){

        SyncReference ref = WilddogSync.getInstance().getReference("palyer");
        Query queryRef = ref.orderByKey();
        queryRef.addChildEventListener(new ChildEventListener() {
            public void onChildAdded(DataSnapshot snapshot, String ref) {
                Log.e("wangxu",snapshot.getValue().toString());
            }
            public void onCancelled(SyncError arg0) {
            }
            public void onChildChanged(DataSnapshot arg0, String arg1) {
            }
            public void onChildMoved(DataSnapshot arg0, String arg1) {
            }
            public void onChildRemoved(DataSnapshot arg0) {
            }
        });

    }
}
