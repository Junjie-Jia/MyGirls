package com.junjie.jia.io.mygirls.bean;

import java.util.List;

public class CategoryBean {
    private boolean error;
    private List<DataBean> results;

    public boolean isError() {
        return error;
    }

    public List<DataBean> getResults() {
        return results;
    }
}
