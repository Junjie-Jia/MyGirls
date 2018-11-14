package com.junjie.jia.io.mygirls.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.junjie.jia.io.mygirls.App;
import com.junjie.jia.io.mygirls.GirlPhotoAdapter;
import com.junjie.jia.io.mygirls.R;
import com.junjie.jia.io.mygirls.bean.CategoryBean;
import com.junjie.jia.io.mygirls.bean.DataBean;
import com.junjie.jia.io.mygirls.listener.OnLoadMoreListener;
import com.junjie.jia.io.mygirls.net.GankServiceSingleton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class PageFragment extends Fragment {
    private static final String TAG = PageFragment.class.getSimpleName();
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

        if (!title.equals("妹子")) {
            return rootView;
        } else {

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
                    Log.e(TAG, "pageIndex == " + pageIndex);
                    getDataFromCache();
                }
            });

            getDataFromCache();
            return rootView;
        }
    }

    private void getData() {
        GankServiceSingleton
            .getGankService()
            .searchCategoryData("福利", pageSize, pageIndex)
            .map(new Function<CategoryBean, List<DataBean>>() {
                @Override
                public List<DataBean> apply(CategoryBean categoryBean) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
                    for (DataBean dataBean : categoryBean.getResults()) {
                        try {
                            dataBean.setCreateMilliseconds(simpleDateFormat.parse(dataBean.getCreatedAt()).getTime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    return categoryBean.getResults();
                }
            })
            .doOnNext(new Consumer<List<DataBean>>() {
                @Override
                public void accept(List<DataBean> dataBeans) {
                    saveData(dataBeans);
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
//                        preLoadImage(dataBeans);
                    list.addAll(dataBeans);
                    girlPhotoAdapter.notifyItemRangeChanged(0, list.size());

                }

                @Override
                public void onError(Throwable e) {
                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    Toast.makeText(getContext(), getResources().getString(R.string.net_error), Toast.LENGTH_LONG).show();
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

    private void saveData(final List<DataBean> dataBeans) {
        ((App) getActivity().getApplication()).getDataBase().gankDataDao().insert(dataBeans);
    }


    private void getDataFromCache() {
        ((App) getActivity().getApplication())
            .getDataBase()
            .gankDataDao()
            .findOnePageData(list.size(), pageSize, "福利")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<List<DataBean>>() {
                @Override
                public void accept(List<DataBean> dataBeans) {
                    if (dataBeans == null || dataBeans.size() == 0) {
                        getData();
                    } else {
                        list.addAll(dataBeans);
                        girlPhotoAdapter.notifyItemRangeChanged(0, list.size());
                    }
                }
            });
    }

}
