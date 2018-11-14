package com.junjie.jia.io.mygirls.mvp;

import com.junjie.jia.io.mygirls.bean.DataBean;
import com.junjie.jia.io.mygirls.ui.PageFragment;

import java.util.List;

public class GankPresenter implements IPresenter {
    private GankModel gankModel;
    private PageFragment pageFragment;

    public GankPresenter(IView iView) {
        this.pageFragment = (PageFragment) iView;
        gankModel = new GankModel(pageFragment.getContext(), pageFragment.getCategory());
        gankModel.setIModelListener(listener);
    }

    @Override
    public List<DataBean> getList() {
        return gankModel.getList();
    }

    @Override
    public void loadFirstPage() {
        gankModel.loadFirstPage();
    }

    @Override
    public void loadMoreData() {
        gankModel.loadMoreData();
    }

    @Override
    public void loadData() {
        gankModel.loadCache();
    }

    private GankModel.IModelListener listener = new GankModel.IModelListener() {
        @Override
        public void loadDataSuccess() {
            pageFragment.showData();
        }

        @Override
        public void loadDataError() {
            pageFragment.showError();
        }
    };


}
