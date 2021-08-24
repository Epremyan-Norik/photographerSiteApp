package com.photographer.app.models;

import javax.persistence.*;

@Entity
@Table(name = "user_attribute", schema = "public", catalog = "site_test")
public class UserAttribute {
    private int id;
    private String name;
    private String discription;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name ")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "discription")
    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAttribute that = (UserAttribute) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (discription != null ? !discription.equals(that.discription) : that.discription != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (discription != null ? discription.hashCode() : 0);
        return result;
    }
}
