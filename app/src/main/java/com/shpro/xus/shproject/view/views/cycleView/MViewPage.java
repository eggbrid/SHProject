package com.shpro.xus.shproject.view.views.cycleView;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.lang.reflect.Field;

/**
 * Created by yanghaitao on 2016/8/12.
 */
public class MViewPage extends ViewPager {
    private int duration=1000;
    public void setDuration(int duration) {
        this.duration = duration;
    }

    //final public static int time=1500;
    public MViewPage(Context context) {
        this(context,null);
    }

    public MViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
//        changeViewPageScroller(context);
    }

    private void changeViewPageScroller(Context context) {
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            FixedSpeedScroller scroller;
            scroller = new FixedSpeedScroller(context,new AccelerateDecelerateInterpolator());
            scroller.setmDuration(duration);
            mField.set(this, scroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


