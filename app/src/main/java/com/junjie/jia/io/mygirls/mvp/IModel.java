package com.junjie.jia.io.mygirls.mvp;

import com.junjie.jia.io.mygirls.bean.DataBean;

import java.util.List;

public interface IModel {
    void loadCache();
    void loadNetworkData();
    void saveData(final List<DataBean> dataBeans);
    void loadFirstPage();
    void loadMoreData();
}
