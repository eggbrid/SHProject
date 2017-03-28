package com.shpro.xus.shproject.view.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.shpro.xus.shproject.R;

/**
 * Created by xus on 2017/3/28.
 */

public class RefreshListView extends LinearLayout {

    private static final int FRICTION = 2;
    private TaiJiView header;
    private ListView listView;
    private OnRefreshListener refreshListener;
    private SmoothScrollRunnable mSmoothScrollRunnable;
    private onRLClickListener clickListener;
    private boolean hasMoreData = true;

    public RefreshListView(Context context) {
        super(context);
        init(context);

    }

    public RefreshListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public RefreshListView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public void init(Context context) {
        this.setOrientation(LinearLayout.VERTICAL);
//        header = new TaiJiView(context);

        View v = LayoutInflater.from(context).inflate(R.layout.refresh_list_layout, null);
        listView = (ListView) v.findViewById(R.id.message_list);
        header = (TaiJiView) v.findViewById(R.id.taiji);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        this.addView(v, 0, lp);
//        listView = new ListView(context);
//        listView.setFadingEdgeLength(0);
//        android:cacheColorHint="#00000000"
//        android:fadingEdge="none"
//        lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//        this.addView(listView, -1, lp);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //判断listview触顶刷新
                if (AbsListView.OnScrollListener.SCROLL_STATE_IDLE == scrollState) {        //当状态从滚动到停止滚动时触发。
//					final Adapter adapter = listView.getAdapter();
                    final View firstVisibleChild = listView.getChildAt(0);
                    final int firstVisibleItem = listView.getFirstVisiblePosition();
                    if (firstVisibleChild != null) {
                        if (firstVisibleItem == 0 && firstVisibleChild.getTop() >= view.getTop()) {
                            if (header.getVisibility() == View.VISIBLE && hasMoreData) {
                                RefreshListView.this.smoothScrollTo(-header.getHeight(), scrollFinishedListener);
                                mState = State.REFRESHING;
                            }
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        listView.setDividerHeight(0);
        listView.setDivider(null);
    }

    public void setRLClickListener(onRLClickListener listener) {
        clickListener = listener;
    }

    public interface onRLClickListener {
        public void click();
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        listView.setOnItemClickListener(listener);
    }

    public void setAdapter(ListAdapter adapter) {
        this.listView.setAdapter(adapter);
    }

    /**
     * @Title: scrollToBottom
     * @Description: 滚动到底部
     */
    public void scrollToBottom() {
        ListAdapter adapter = listView.getAdapter();
        if (adapter != null && adapter.getCount() > 1) {
            this.listView.setSelection(adapter.getCount() - 1);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (h < oldh) {
            this.postDelayed(new Runnable() {
                @Override
                public void run() {
                    scrollToBottom();
                }
            }, 100);
        }
        int pTop = getPaddingTop();
//		int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
//		int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
//		header.measure(w, h);
        //计算header的高度，并把它隐藏起来。
        pTop = -header.getMeasuredHeight();
        setPadding(getPaddingLeft(), pTop, getPaddingRight(), getPaddingBottom());
    }

    private int mLastMotionY = 0;                //上一次触摸点的Y坐标
    private int mTouchDownY = 0;                //触摸点的Y坐标
    private boolean isBeingDragged = false;        //是否被拖拽中
    private State mState = State.RESET;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        if (clickListener != null) {
            clickListener.click();
        }

        if (isRefreshing()) {
            //如果正在刷新就把触摸事件拦截，不让拖拽。
            return true;
        }

        final int action = event.getAction();
        final int posY = (int) event.getY();

        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            if (isBeingDragged) {
                isBeingDragged = false;
                setState(mState);
                return false;
            }
        }

        switch (action) {
            case MotionEvent.ACTION_MOVE:
                //得到两次触摸点的Y坐标差
                int diff = posY - mLastMotionY;
                if (isReadyForPull() && !isBeingDragged) {
//				LogPrint.debug("mLastMotionY: " + mLastMotionY + "; posY: " + posY + "; diff: " + diff + "; TOUCH_SLOP: " + TOUCH_SLOP);
                    //当达到可以被拖拽并且当前不是被拖拽的条件时，获取最初的触摸点Y坐标值
                    if (diff > 1) {
                        isBeingDragged = true;
                        mTouchDownY = mLastMotionY = posY;
                    }
                }

                if (isBeingDragged) {
                    mLastMotionY = posY;
                    //如果正在拖拽中，那么每次移动都刷新拖拽距离
                    pullEvent();
                    return true;
                }

                mLastMotionY = posY;
                break;
            case MotionEvent.ACTION_DOWN:
                if (isReadyForPull()) {
                    mTouchDownY = mLastMotionY = posY;
                    isBeingDragged = false;
                }
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * @Title: pullEvent
     * @Description: 刷新拖拽的距离，并且通过距离判断是否到达“松手刷新”的状态
     */
    private void pullEvent() {
        final int lastMotionY = mLastMotionY;
        final int touchDownY = mTouchDownY;
        final int DISTANCE = Math.round(Math.min(touchDownY - lastMotionY, 0)) / FRICTION;
        if (DISTANCE == 0) {
            mTouchDownY = mLastMotionY;
        }
        scrollTo(0, DISTANCE);
        if (DISTANCE < 0) {
            mState = State.RELEASE_TO_REFRESH;
        } else {
            mState = State.PULL_TO_REFRESH;
        }
//		LogPrint.debug("State: " + mState);
    }

    /**
     * @return boolean
     * @Title: isRefreshing
     * @Description: 判断当前是否正在刷新
     */
    private boolean isRefreshing() {
        return mState == State.REFRESHING;
    }

    /**
     * @return boolean
     * @Title: isReadyForPull
     * @Description: 判断是否可以开始被拖拽
     */
    private boolean isReadyForPull() {
        final Adapter adapter = listView.getAdapter();

        if (null == adapter || adapter.isEmpty()) {
            return true;
        } else {
            /**
             * This check should really just be:
             * mRefreshableView.getFirstVisiblePosition() == 0, but PtRListView
             * internally use a HeaderView which messes the positions up. For
             * now we'll just add one to account for it and rely on the inner
             * condition which checks getTop().
             */
            if (listView.getFirstVisiblePosition() <= 1) {
                final View firstVisibleChild = listView.getChildAt(0);
                if (firstVisibleChild != null) {
                    return firstVisibleChild.getTop() >= listView.getTop();
                }
            }
        }
        return false;
    }

    /**
     * @param state void
     * @Title: setState
     * @Description: 通过状态执行相应的动作
     */
    public void setState(State state) {
        switch (state) {
            case RESET:
            case PULL_TO_REFRESH:
                smoothScrollTo(0);
                break;
            case RELEASE_TO_REFRESH:
                int height = header.getHeight();
                mState = State.REFRESHING;
                if (hasMoreData) {
                    RefreshListView.this.smoothScrollTo(-height, 800, scrollFinishedListener);
                } else {
                    RefreshListView.this.smoothScrollTo(0, 800, scrollFinishedListener);
                }
                break;
            case REFRESHING:
                break;
        }
    }

    public static enum State {
        RESET,
        PULL_TO_REFRESH,        //继续下拉可以刷新
        RELEASE_TO_REFRESH,        //松手刷新
        REFRESHING,                //正在刷新
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        this.refreshListener = listener;
    }

    private void smoothScrollTo(int toY) {
        smoothScrollTo(toY, null);
    }

    private void smoothScrollTo(int toY, OnSmoothScrollFinishedListener listener) {
        smoothScrollTo(toY, 0, listener);
    }

    private void smoothScrollTo(int toY, int duration, OnSmoothScrollFinishedListener listener) {
        final int fromY = getScrollY();
        if (fromY != toY) {
            mSmoothScrollRunnable = new SmoothScrollRunnable(fromY, toY, duration, listener);
            post(mSmoothScrollRunnable);
        }
    }

    final class SmoothScrollRunnable implements Runnable {
        private final int mScrollFromY;
        private final int mScrollToY;
        private final int mScrollDuration;
        private OnSmoothScrollFinishedListener scrollFinishedListener;

        private float mCurrentY = 0;
        private float deltaY;
        private final int PERIOD = 10;

        public SmoothScrollRunnable(int fromY, int toY, int duration, OnSmoothScrollFinishedListener listener) {
            mCurrentY = mScrollFromY = fromY;
            mScrollToY = toY;
            mScrollDuration = duration == 0 ? 400 : duration;
            scrollFinishedListener = listener;
            final float distance = (float) (mScrollToY - mScrollFromY);
            final float timeEverage = (float) mScrollDuration / (float) PERIOD;
            final float scrollStep = distance / timeEverage;
            deltaY = scrollStep == 0 ? 4 : scrollStep;
            deltaY = deltaY >= 0 ? deltaY + 10 : deltaY - 10;
//			LogPrint.debug("mCurrentY: " + mCurrentY + "DeltaY: " + deltaY + "; distance: " + (mScrollToY - mScrollFromY));
        }

        @Override
        public void run() {
//			LogPrint.prinstTime();
            mCurrentY += deltaY;
//			LogPrint.debug("mCurrentY: " + mCurrentY + "; mScrollToY: " + mScrollToY);
            if ((deltaY > 0 && mCurrentY >= mScrollToY) || (deltaY < 0 && mCurrentY <= mScrollToY)) {
                RefreshListView.this.scrollTo(0, mScrollToY);
                if (scrollFinishedListener != null) {
                    scrollFinishedListener.OnSmoothScrollFinished();
                }
                return;
            }
//			LogPrint.mark();
            RefreshListView.this.scrollTo(0, (int) mCurrentY);
            RefreshListView.this.postDelayed(this, PERIOD);
        }
    }

//	private OnScrollToTopListener listener = new OnScrollToTopListener() {
//		@Override
//		public void onScrollToTop() {
//			Toast.makeText(getContext(), "ScrollToTop", Toast.LENGTH_SHORT).show();
//			RefreshListView.this.smoothScrollTo(-header.getHeight(), scrollFinishedListener);
//			mState = State.REFRESHING;
//		}
//	};

    private OnSmoothScrollFinishedListener scrollFinishedListener = new OnSmoothScrollFinishedListener() {
        @Override
        public void OnSmoothScrollFinished() {
            int targetY = 0;
            int position = 0;
            if (refreshListener != null && hasMoreData) {
                position = refreshListener.refreshData();
                targetY = header.getMeasuredHeight();
                RefreshListView.this.scrollTo(0, 0);
                listView.setSelectionFromTop(position, targetY);
            }
            mState = State.RESET;
        }
    };

    interface OnScrollToTopListener {
        void onScrollToTop();
    }

    public interface OnSmoothScrollFinishedListener {
        void OnSmoothScrollFinished();
    }

    public interface OnRefreshListener {
        int refreshData();
    }

    public void setMoreData(boolean moreData) {
        hasMoreData = moreData;
        if (moreData) {
            this.header.setVisibility(View.VISIBLE);
            this.header.start();
        } else {
            this.header.setVisibility(View.INVISIBLE);
            this.header.stop();

        }
    }

}
