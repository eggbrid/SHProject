package com.shpro.xus.shproject.filter;

/**
 * Created by xus on 2016/12/3.
 */

public class PushFilter {
    private String[] fs;

    public PushFilter(String... s) {
        fs = s;
    }

    public boolean canNext(String s) {
        for (String arg : fs) {
            if (s.equals(arg)) {
                return true;
            }
        }
        return false;
    }

}
