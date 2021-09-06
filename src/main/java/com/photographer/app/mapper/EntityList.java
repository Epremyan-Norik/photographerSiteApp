package com.photographer.app.mapper;

public class EntityList {
    long id;

    String name;

    public EntityList() {
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
        return "\nEntityList{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
