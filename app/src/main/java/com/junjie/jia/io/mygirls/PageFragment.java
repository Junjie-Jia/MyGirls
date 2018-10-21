package com.junjie.jia.io.mygirls;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.junjie.jia.io.mygirls.bean.CategoryBean;
import com.junjie.jia.io.mygirls.bean.DataBean;
import com.junjie.jia.io.mygirls.net.GankServiceSingleton;

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
                swipeRefreshLayout.setRefreshing(false);
            }

        });

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        girlPhotoAdapter = new GirlPhotoAdapter();
        recyclerView.setAdapter(girlPhotoAdapter);

        getData();
        return rootView;
    }

    private void getData(){
        GankServiceSingleton
                .getGankService()
                .searchCategoryData("福利",20,1)
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
                        girlPhotoAdapter.setList(dataBeans);
                        girlPhotoAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }
}
