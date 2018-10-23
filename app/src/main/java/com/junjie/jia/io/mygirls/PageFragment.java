package com.junjie.jia.io.mygirls;

import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.junjie.jia.io.mygirls.bean.CategoryBean;
import com.junjie.jia.io.mygirls.bean.DataBean;
import com.junjie.jia.io.mygirls.listener.OnLoadMoreListener;
import com.junjie.jia.io.mygirls.net.GankServiceSingleton;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class PageFragment extends Fragment {
    private String title;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private GirlPhotoAdapter girlPhotoAdapter;
    private final int pageSize = 15;
    private int pageIndex = 1;

    private List<DataBean> list = new ArrayList<>();

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
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        recyclerView = rootView.findViewById(R.id.recyclerView);

        swipeRefreshLayout.setColorSchemeResources(R.color.holo_blue_bright,
                R.color.holo_green_light, R.color.holo_purple, R.color.holo_red_light);
        swipeRefreshLayout.setProgressViewOffset(true, 50, 100);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                list.clear();
                getData();
            }
        });

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        girlPhotoAdapter = new GirlPhotoAdapter();
        girlPhotoAdapter.setList(list);
        recyclerView.setAdapter(girlPhotoAdapter);
        recyclerView.setItemAnimator(null);
        recyclerView.setHasFixedSize(true);

        recyclerView.addOnScrollListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageIndex++;
                getData();
            }
        });

        getData();
        return rootView;
    }

    private void getData() {
        GankServiceSingleton
                .getGankService()
                .searchCategoryData("福利", pageSize, pageIndex)
                .map(new Function<CategoryBean, List<DataBean>>() {
                    @Override
                    public List<DataBean> apply(CategoryBean categoryBean) throws Exception {
                        return categoryBean.getResults();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<DataBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<DataBean> dataBeans) {
                        if (swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        Log.e("OkHttp:", "pageIndex == " + pageIndex);
//                        preLoadImage(dataBeans);
                        list.addAll(dataBeans);
                        girlPhotoAdapter.notifyItemRangeChanged(0, list.size());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void preLoadImage(List<DataBean> dataBeans) {
        if (pageIndex > 1) {
            for (final DataBean dataBean : dataBeans) {
                Glide.with(getContext())
                        .asBitmap()
                        .load(dataBean.getUrl())
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                dataBean.setWidth(resource.getWidth());
                                dataBean.setHeight(resource.getHeight());
                            }
                        });
            }
        }
    }
}
