package com.inyongtisto.myhelper;

import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class PullRefresh {
    public PullRefresh(SwipeRefreshLayout view, @Nullable OnRefreshListener listener) {
        assert listener != null;
        view.setOnRefreshListener(listener::onRefresh);
        view.setColorSchemeResources(
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }
    public interface OnRefreshListener {
        void onRefresh();
    }
}
