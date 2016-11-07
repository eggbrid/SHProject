package com.shpro.xus.shproject.util;

/**
 * Created by xus on 2016/11/7.
 */

public class getSelfPlayer {
    public static getSelfPlayer selfPlayer;
    public synchronized static getSelfPlayer getInstance() {
        if (selfPlayer==null){

        }
        return selfPlayer;
    }
}
