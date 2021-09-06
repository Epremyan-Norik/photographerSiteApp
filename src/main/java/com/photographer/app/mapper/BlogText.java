package com.photographer.app.mapper;

public class BlogText {

    private long id;

    private String anons;

    private String full_text;

    public BlogText() {
    }

    @Override
    public String toString() {
        return "\nBlogText{" +
                "id=" + id +
                ", anons='" + anons + '\'' +
                ", full_text='" + full_text + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAnons() {
        return anons;
    }

    public void setAnons(String anons) {
        this.anons = anons;
    }

    public String getFull_text() {
        return full_text;
    }

    public void setFull_text(String full_text) {
        this.full_text = full_text;
    }
}
