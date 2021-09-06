package com.photographer.app.mapper;

public class Attribute {
    long id;

    String att_name;

    String description;

    long en_type;

    public Attribute() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAttName() {
        return att_name;
    }

    public void setAttName(String att_name) {
        this.att_name = att_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String discription) {
        this.description = discription;
    }

    public long getEntityType() {
        return en_type;
    }

    public void setEntityType(long en_type) {
        this.en_type = en_type;
    }

    @Override
    public String toString() {
        return "\nAttribute{" +
                "id=" + id +
                ", attName='" + att_name + '\'' +
                ", discription='" + description + '\'' +
                ", entityType=" + en_type +
                '}';
    }
}
