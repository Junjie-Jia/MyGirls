package com.junjie.jia.io.mygirls.bean;

import java.util.List;

public class DataBean {


    /**
     * _id : 5bba1b899d212261127b79d1
     * createdAt : 2018-10-07T14:43:21.406Z
     * desc : Android自动屏幕适配插件，大大减轻你和UI设计师的工作量
     * images : ["https://ww1.sinaimg.cn/large/0073sXn7ly1fw0vipvym5j30ny09o758","https://ww1.sinaimg.cn/large/0073sXn7ly1fw0vipycxjj30gy09gt9j"]
     * publishedAt : 2018-10-08T00:00:00.0Z
     * source : web
     * type : Android
     * url : http://tangpj.com/2018/09/29/calces-screen/
     * used : true
     * who : PJ Tang
     */

    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;
    private List<String> images;

    public String get_id() {
        return _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getSource() {
        return source;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public boolean isUsed() {
        return used;
    }

    public String getWho() {
        return who;
    }

    public List<String> getImages() {
        return images;
    }
}
