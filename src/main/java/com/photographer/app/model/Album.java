package com.photographer.app.model;

public class Album {
    private long id;
    private String name;
    private long p_id;


    public Album() {
    }

    public long getP_id() {
        return p_id;
    }

    public void setP_id(long p_id) {
        this.p_id = p_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "\nAlbum{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
