package com.junjie.jia.io.mygirls.bean;

public class OneSentenceBean {

    private int id;
    private String hitokoto;
    private String type;
    private String from;
    private String creator;
    private String created_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHitokoto() {
        return hitokoto;
    }

    public void setHitokoto(String hitokoto) {
        this.hitokoto = hitokoto;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "OneSentenceBean{" +
            "id=" + id +
            ", hitokoto='" + hitokoto + '\'' +
            ", type='" + type + '\'' +
            ", from='" + from + '\'' +
            ", creator='" + creator + '\'' +
            ", created_at='" + created_at + '\'' +
            '}';
    }

}
