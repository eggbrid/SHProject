package com.shpro.xus.shproject.view.views;

import android.view.View;
import android.widget.TextView;

import com.shpro.xus.shproject.R;
import com.victor.loading.rotate.RotateLoading;


//import fr.castorflex.android.circularprogressbar.CircularProgressBar;

public class RefreshLayoutFooter {

    private View view_footer;
    private TextView textMore;
    private RotateLoading progressBar;

    public void setView_footer(View view_footer) {
        this.view_footer = view_footer;
        view_footer.setVisibility(View.GONE);
        textMore = (TextView) view_footer.findViewById(R.id.text_more);
        progressBar = (RotateLoading) view_footer.findViewById(R.id.progressbar_circular);
    }

    public void setViewVis() {
        progressBar.start();
        progressBar.setVisibility(View.VISIBLE);
        view_footer.setVisibility(View.VISIBLE);
    }

    public void setViewGone() {
        progressBar.stop();
        progressBar.setVisibility(View.GONE);
        view_footer.setVisibility(View.GONE);
    }

    public void settextMore(boolean isnodata) {
        if (isnodata) {
            textMore.setText("数据加载完毕");
        } else {
            textMore.setText("点击加载更多");
        }
    }

    public void openfooter() {
        if (textMore != null) {
            progressBar.setVisibility(View.GONE);
            textMore.setVisibility(View.VISIBLE);
            progressBar.stop();

        }
    }

    public void closefooter() {
        if (textMore != null) {
            progressBar.setVisibility(View.VISIBLE);
            textMore.setVisibility(View.GONE);
            progressBar.start();
        }
    }
}
