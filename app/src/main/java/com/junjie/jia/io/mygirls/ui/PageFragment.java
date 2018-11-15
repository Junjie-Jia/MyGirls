package com.junjie.jia.io.mygirls.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.junjie.jia.io.mygirls.R;
import com.junjie.jia.io.mygirls.adapter.GankAdapter;
import com.junjie.jia.io.mygirls.adapter.GankTextAdapter;
import com.junjie.jia.io.mygirls.adapter.GirlPhotoAdapter;
import com.junjie.jia.io.mygirls.listener.OnLoadMoreListener;
import com.junjie.jia.io.mygirls.mvp.GankPresenter;
import com.junjie.jia.io.mygirls.mvp.IView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class PageFragment extends Fragment implements IView {
    private static final String TAG = PageFragment.class.getSimpleName();
    private String title;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private GankAdapter gankAdapter;

    private GankPresenter gankPresenter;

    public static PageFragment newInstance(@NonNull String title) {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            title = args.getString("title");
            Log.i(TAG, "title : " + title);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page, container, false);

        findView(rootView);
        initSwipeRefreshLayout();
        initRecyclerView();

        gankPresenter = new GankPresenter(this);
        gankAdapter.setList(gankPresenter.getList());
        gankPresenter.loadData();
        return rootView;
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(createLayoutManager());
        gankAdapter = createAdapter();
        recyclerView.setAdapter(gankAdapter);
        recyclerView.setItemAnimator(null);
        recyclerView.setHasFixedSize(true);

        recyclerView.addOnScrollListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                gankPresenter.loadMoreData();
            }
        });
    }

    private RecyclerView.LayoutManager createLayoutManager() {
        if (isPhotoFragment()) {
            return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        }
        return new LinearLayoutManager(getContext());
    }

    private GankAdapter createAdapter() {
        if (isPhotoFragment()) {
            return new GirlPhotoAdapter();
        }
        return new GankTextAdapter();
    }

    private boolean isPhotoFragment() {
        return title.equals(getResources().getString(R.string.tab1));
    }

    private void findView(View rootView) {
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        recyclerView = rootView.findViewById(R.id.recyclerView);
    }

    private void initSwipeRefreshLayout() {
        swipeRefreshLayout.setColorSchemeResources(R.color.holo_blue_bright,
            R.color.holo_green_light, R.color.holo_purple, R.color.holo_red_light);
        swipeRefreshLayout.setProgressViewOffset(true, 50, 120);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                gankPresenter.loadFirstPage();
            }
        });
    }

    @Override
    public void showData() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        gankAdapter.notifyItemRangeChanged(0, gankAdapter.getList().size());
    }

    @Override
    public void showError() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        Toast.makeText(getContext(), getResources().getString(R.string.net_error), Toast.LENGTH_LONG).show();
    }

    @Override
    public String getCategory() {
        if (isPhotoFragment()) {
            return getResources().getString(R.string.tab1_category);
        } else {
            return title;
        }
    }
}
