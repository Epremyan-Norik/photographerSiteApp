package com.photographer.app.models;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = "main_paige_slider", schema = "public", catalog = "site_test")
public class MainPaigeSlider {
    private int id;
    private String name;
    private byte[] value;
    private String format;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "value")
    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    @Basic
    @Column(name = "format")
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MainPaigeSlider that = (MainPaigeSlider) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (!Arrays.equals(value, that.value)) return false;
        if (format != null ? !format.equals(that.format) : that.format != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(value);
        result = 31 * result + (format != null ? format.hashCode() : 0);
        return result;
    }
}
