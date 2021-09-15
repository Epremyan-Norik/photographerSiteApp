package com.photographer.app.model;

public class Image {
    private long id;
    private String imagebyte64;
    private long en_id;

    public Image() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImagebyte64() {
        return imagebyte64;
    }

    public void setImagebyte64(String imagebyte64) {
        this.imagebyte64 = imagebyte64;
    }

    public long getEn_id() {
        return en_id;
    }

    public void setEn_id(long en_id) {
        this.en_id = en_id;
    }
}
