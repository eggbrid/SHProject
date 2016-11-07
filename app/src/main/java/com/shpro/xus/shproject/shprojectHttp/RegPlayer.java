package com.shpro.xus.shproject.shprojectHttp;

import com.google.gson.Gson;
import com.shpro.xus.shproject.bean.Player;
import com.shpro.xus.shproject.people.Self;

/**
 * Created by xus on 2016/11/8.
 */

public class RegPlayer {
    private void regPlayer(Self player){
        HttpUtil.getInstance().getRef().child("palyer").setValue(player);
    }
}
