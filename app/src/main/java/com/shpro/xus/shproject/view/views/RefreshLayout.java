package com.shpro.xus.shproject.view.views;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.ListView;

import com.shpro.xus.shproject.R;


public class RefreshLayout extends SwipeRefreshLayout {


    private final int mTouchSlop;
    private ListView mListView;
    private OnLoadListener mOnLoadListener;
    private Context context;

    private float firstTouchY;
    private float lastTouchY;

    private boolean isLoading = false;
    private RefreshLayoutFooter refreshLayoutFooter;
    private boolean isNoData = false;
    private View view_footer;
    private View view_footera;

    public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        isLoading = true;
    }

    public RefreshLayoutFooter getRefreshLayoutFooter() {
        return refreshLayoutFooter;
    }

    public void setNoData(boolean noData) {
        if (refreshLayoutFooter != null) {
            isNoData = noData;
            refreshLayoutFooter.settextMore(noData);

            if (noData) {
                if (view_footer != null) {
                    mListView.removeFooterView(view_footer);
                    view_footera = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.listview_sw_footer_nodata, null);
                    mListView.addFooterView(view_footera);
                }
            }
        }
    }

    public void setFooter() {
        if (view_footera != null) {
            mListView.removeFooterView(view_footera);
            view_footera = null;
            mListView.addFooterView(view_footer);
        }
    }


    public void addFooter(boolean isLoading) {

        if (mListView != null && context != null) {
            view_footer = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.listview_sw_footer, null);
            refreshLayoutFooter = new RefreshLayoutFooter();
            refreshLayoutFooter.setView_footer(view_footer);
            mListView.addFooterView(view_footer);


            view_footer.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isNoData) {
                        loadData();
                    }
                }
            });

            if (isLoading) {
                setOnScrollListener();
            }
        }
    }


    public void setOnScrollListener() {
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_FLING:
                        break;
                    case SCROLL_STATE_IDLE:
                        if (canLoadMore()) {
                            if (!isNoData) {
                                loadData();
                            }
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    //set the child view of RefreshLayout,ListView

    public void setChildView(final ListView mListView, boolean isLoading) {
        this.mListView = mListView;
        addFooter(isLoading);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        if (mListView == null) {
            return super.dispatchTouchEvent(event);
        }
        final int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                firstTouchY = event.getRawY();
                break;

            case MotionEvent.ACTION_UP:
                lastTouchY = event.getRawY();
                if (canLoadMore()) {
                    if (!isNoData) {
                        loadData();
                    }
                }
                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    private boolean canLoadMore() {
        return isBottom() && !isLoading && isPullingUp();
    }

    private boolean isBottom() {
        if (mListView.getCount() > 0) {
            if (mListView.getLastVisiblePosition() == mListView.getAdapter().getCount() - 1 &&
                    mListView.getChildAt(mListView.getChildCount() - 1).getBottom() <= mListView.getHeight()) {
                return true;
            }
        }
        return false;
    }

    private boolean isPullingUp() {
        return (firstTouchY - lastTouchY) >= mTouchSlop;
    }

    private void loadData() {
        if (mOnLoadListener != null) {
            setLoading(true);
        }
    }

    public void setLoading(boolean loading) {

        if (mListView == null) return;
        isLoading = loading;
        setFooter();
        if (loading) {
            if (isRefreshing()) {
                setRefreshing(false);
            }
            mListView.setSelection(mListView.getAdapter().getCount() - 1);
            mOnLoadListener.onLoad();
        } else {
            firstTouchY = 0;
            lastTouchY = 0;
        }
    }

    public void setOnLoadListener(OnLoadListener loadListener) {
        mOnLoadListener = loadListener;
    }

    public interface OnLoadListener {
        public void onLoad();
    }
}