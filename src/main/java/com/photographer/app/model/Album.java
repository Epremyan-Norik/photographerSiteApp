package com.photographer.app.model;

public class Album {
    private long id;
    private String name;


    public Album() {
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
