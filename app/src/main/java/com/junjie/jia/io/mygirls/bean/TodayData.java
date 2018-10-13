package com.junjie.jia.io.mygirls.bean;

import java.util.List;

public class TodayData {
    private List<String> category;
    private boolean error;
    private Results results;

    public List<String> getCategory() {
        return category;
    }

    public boolean isError() {
        return error;
    }


    public Results getResults() {
        return results;
    }
}
