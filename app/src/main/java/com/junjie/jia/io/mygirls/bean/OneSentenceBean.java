package com.junjie.jia.io.mygirls.bean;

import com.google.gson.Gson;

public class OneSentenceBean {

    /**
     * text : 没有谁的生活会一直完美，但无论什么时候，都要看着前方，满怀希望就会所向披靡。
     * author : 巫哲
     * source : 《撒野》
     */

    private String text;
    private String author;
    private String source;

    public static OneSentenceBean objectFromData(String str) {

        return new Gson().fromJson(str, OneSentenceBean.class);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "OneSentenceBean{" +
                "text='" + text + '\'' +
                ", author='" + author + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
