package com.junjie.jia.io.mygirls.bean;

import java.util.List;

class Results {
    private List<DataBean> Android;
    private List<DataBean> App;
    private List<DataBean> iOS;
    private List<DataBean> 休息视频;
    private List<DataBean> 瞎推荐;
    private List<DataBean> 福利;

    public List<DataBean> getAndroid() {
        return Android;
    }

    public List<DataBean> getApp() {
        return App;
    }

    public List<DataBean> getiOS() {
        return iOS;
    }

    public List<DataBean> get休息视频() {
        return 休息视频;
    }

    public List<DataBean> get瞎推荐() {
        return 瞎推荐;
    }

    public List<DataBean> get福利() {
        return 福利;
    }
}
