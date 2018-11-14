package com.junjie.jia.io.mygirls.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.junjie.jia.io.mygirls.GirlPhotoAdapter;
import com.junjie.jia.io.mygirls.R;
import com.junjie.jia.io.mygirls.bean.DataBean;
import com.junjie.jia.io.mygirls.listener.OnLoadMoreListener;
import com.junjie.jia.io.mygirls.mvp.GankPresenter;
import com.junjie.jia.io.mygirls.mvp.IView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class PageFragment extends Fragment implements IView {
    private static final String TAG = PageFragment.class.getSimpleName();
    private String title;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private GirlPhotoAdapter girlPhotoAdapter;

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
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page, container, false);

        if (!title.equals("妹子")) {
            return rootView;
        } else {
            findView(rootView);
            initSwipeRefreshLayout();
            initRecyclerView();

            gankPresenter = new GankPresenter(this);
            girlPhotoAdapter.setList(gankPresenter.getList());
            gankPresenter.loadData();
            return rootView;
        }
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        girlPhotoAdapter = new GirlPhotoAdapter();
        recyclerView.setAdapter(girlPhotoAdapter);
        recyclerView.setItemAnimator(null);
        recyclerView.setHasFixedSize(true);

        recyclerView.addOnScrollListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                gankPresenter.loadMoreData();
            }
        });
    }

    private void findView(View rootView) {
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        recyclerView = rootView.findViewById(R.id.recyclerView);
    }

    private void initSwipeRefreshLayout() {
        swipeRefreshLayout.setColorSchemeResources(R.color.holo_blue_bright,
                R.color.holo_green_light, R.color.holo_purple, R.color.holo_red_light);
        swipeRefreshLayout.setProgressViewOffset(true, 50, 100);
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
        girlPhotoAdapter.notifyItemRangeChanged(0, girlPhotoAdapter.getList().size());
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
        if (title.equals(getResources().getString(R.string.tab1))) {
            return getResources().getString(R.string.tab1_category);
        } else {
            return title;
        }
    }
}
