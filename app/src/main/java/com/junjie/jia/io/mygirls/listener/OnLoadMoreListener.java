package com.junjie.jia.io.mygirls.listener;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.util.Log;

/**
 * Create by Victor Jia on 2018.10.21
 * <p>
 * <p>
 * RecyclerView load more solution
 */
public abstract class OnLoadMoreListener extends RecyclerView.OnScrollListener {

    private final int PRE_LOAD_POSITION = 10;

    private int[] lastPositions;
    private int lastItemCount;
    private int itemCount;
    private int lastPosition;

    public abstract void onLoadMore();

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            itemCount = gridLayoutManager.getItemCount();
            lastPosition = gridLayoutManager.findLastCompletelyVisibleItemPosition();

            handle(lastPosition);
        } else if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            itemCount = linearLayoutManager.getItemCount();
            lastPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();

            handle(lastPosition);
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            itemCount = staggeredGridLayoutManager.getItemCount();
            lastPositions = staggeredGridLayoutManager.findLastVisibleItemPositions(new int[staggeredGridLayoutManager.getSpanCount()]);

            handle(findMax());
        }
    }

    private void handle(int lastPosition) {
        if (lastItemCount != itemCount && (itemCount - 1 - lastPosition <= PRE_LOAD_POSITION)) {
            lastItemCount = itemCount;
            this.onLoadMore();
            Log.i("OnLoadMoreListener", "onLoadMore() invoked! itemCount - 1 - lastPosition=" + (itemCount - 1 - lastPosition));
        }
    }

    private int findMax() {
        int max = lastPositions[0];
        for (int item : lastPositions) {
            max = Math.max(max, item);
        }
        return max;
    }
}
