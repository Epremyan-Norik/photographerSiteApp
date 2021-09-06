package com.photographer.app.mapper;

public class Entity {

    private long id;

    private long id_type;

    public Entity() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getType_id() {
        return id_type;
    }

    public void setType_id(long id_type) {
        this.id_type = id_type;
    }

    @Override
    public String toString() {
        return "\nEntity{" +
                "id=" + id +
                ", idType=" + id_type +
                '}';
    }
}
