package com.junjie.jia.io.mygirls.mvp;

import com.junjie.jia.io.mygirls.bean.DataBean;

import java.util.List;

public interface IPresenter {
    void loadData();
    void loadMoreData();
    void loadFirstPage();
    List<DataBean> getList();

}
