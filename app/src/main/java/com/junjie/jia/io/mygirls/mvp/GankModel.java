package com.junjie.jia.io.mygirls.mvp;

import android.content.Context;
import android.util.Log;

import com.junjie.jia.io.mygirls.App;
import com.junjie.jia.io.mygirls.bean.CategoryBean;
import com.junjie.jia.io.mygirls.bean.DataBean;
import com.junjie.jia.io.mygirls.net.GankServiceSingleton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class GankModel implements IModel {

    private final String TAG = GankModel.class.getSimpleName();
    private final int PAGE_SIZE = 20;
    private int pageIndex = 1;

    private List<DataBean> list = new ArrayList<>();

    private Context context;
    private String category;

    public GankModel(Context context, String category) {
        this.context = context;
        this.category = category;
    }

    @Override
    public void loadCache() {
        ((App) context.getApplicationContext())
                .getDataBase()
                .gankDataDao()
                .findOnePageData(list.size(), PAGE_SIZE, category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<DataBean>>() {
                    @Override
                    public void accept(List<DataBean> dataBeans) {
                        if (dataBeans == null || dataBeans.size() == 0) {
                            loadNetworkData();
                        } else {
                            list.addAll(dataBeans);
                            if (iModelListener != null) {
                                iModelListener.loadDataSuccess();
                            }
                        }
                    }
                });
    }

    @Override
    public void loadNetworkData() {
        GankServiceSingleton
                .getGankService()
                .searchCategoryData(category, PAGE_SIZE, pageIndex)
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
                        list.addAll(dataBeans);
                        if (iModelListener != null) {
                            iModelListener.loadDataSuccess();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (iModelListener != null) {
                            iModelListener.loadDataError();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void saveData(final List<DataBean> dataBeans) {
        ((App) context.getApplicationContext()).getDataBase().gankDataDao().insert(dataBeans);
    }

    @Override
    public void loadFirstPage() {
        list.clear();
        pageIndex = 1;
        loadNetworkData();
    }

    @Override
    public void loadMoreData() {
        pageIndex++;
        Log.i(TAG, "pageIndex ===== " + pageIndex);
        loadCache();
    }

    public List<DataBean> getList() {
        return list;
    }

    private IModelListener iModelListener;

    public void setIModelListener(IModelListener iModelListener) {
        this.iModelListener = iModelListener;
    }

    interface IModelListener {
        void loadDataSuccess();

        void loadDataError();
    }
}
