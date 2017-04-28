package com.shpro.xus.shproject.view.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by xus on 2017/4/26.
 */

public class VerticalViewPagerScrollView extends ScrollView {
    public VerticalViewPagerScrollView(Context context) {
        super(context);
    }

    public VerticalViewPagerScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VerticalViewPagerScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent p_event) {
        return true;
    }

    //    @Override
//    public boolean onTouchEvent(MotionEvent p_event)
//    {
//        if (p_event.getAction() == MotionEvent.ACTION_MOVE && getParent() != null)
//        {
//            getParent().requestDisallowInterceptTouchEvent(true);
//        }
//
//        return super.onTouchEvent(p_event);
//    }
    private int mLastY;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        int y = (int) ev.getY();
        View childView = getChildAt(0);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);

                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mLastY - y > 0 && childView != null && childView.getMeasuredHeight() <= getScrollY() + getHeight()) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                    return false;
                }

                if (mLastY - y < 0 && getScrollY() <= 5) {
                    getParent().requestDisallowInterceptTouchEvent(false);

                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mLastY - y > 0 && childView != null && childView.getMeasuredHeight() <= getScrollY() + getHeight()) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                    return false;
                }
                if (mLastY - y < 0 && getScrollY() <= 5) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                    return false;
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
//    private float oldy = 0;

//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        if(ev.getAction() == MotionEvent.ACTION_DOWN){
//            oldy=ev.getY();
//        }
//        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
//            float newy = ev.getY();
//            if (newy - oldy < 0) {//下拉
//                if (getScrollY() == 0) {
//                    getParent().requestDisallowInterceptTouchEvent(false);
//                    oldy = newy;
//
//                    return false;
//                } else {
//                    getParent().requestDisallowInterceptTouchEvent(true);
//                    oldy = newy;
//
//                    return super.onTouchEvent(ev);
//                }
//            } else {//上拉
//                if (getChildAt(0).getHeight() - getHeight() == getScrollY()) {
//                    getParent().requestDisallowInterceptTouchEvent(false);
//                    oldy = newy;
//
//                    return false;
//                } else {
//                    getParent().requestDisallowInterceptTouchEvent(true);
//                    oldy = newy;
//
//                    return super.onTouchEvent(ev);
//                }
//            }
//        } else {
//            getParent().requestDisallowInterceptTouchEvent(true);
//            return super.onTouchEvent(ev);
//        }
//
//
//    }
}