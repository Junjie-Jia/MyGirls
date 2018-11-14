package com.junjie.jia.io.mygirls.adapter;

import com.junjie.jia.io.mygirls.bean.DataBean;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public abstract class GankAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected List<DataBean> list;

    public List<DataBean> getList() {
        return list;
    }

    public void setList(List<DataBean> list) {
        this.list = list;
    }
}
