package com.junjie.jia.io.mygirls.bean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "gankDatas")
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
    @PrimaryKey
    @NonNull
    private String _id;
    @Ignore
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;
    @Ignore
    private List<String> images;

    private int width;
    private int height;

    private long createMilliseconds;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

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

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public long getCreateMilliseconds() {
        return createMilliseconds;
    }

    public void setCreateMilliseconds(long createMilliseconds) {
        this.createMilliseconds = createMilliseconds;
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "_id='" + _id + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", desc='" + desc + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", source='" + source + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", used=" + used +
                ", who='" + who + '\'' +
                ", images=" + images +
                ", width=" + width +
                ", height=" + height +
                ", createMilliseconds=" + createMilliseconds +
                '}';
    }
}
