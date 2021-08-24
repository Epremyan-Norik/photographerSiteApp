package com.photographer.app.models;

import javax.persistence.*;

@Entity
@Table(name = "product_att_value", schema = "public", catalog = "site_test")
@IdClass(ProductAttValuePK.class)
public class ProductAttValue {
    private int pId;
    private int aId;
    private String attValue;

    @Id
    @Column(name = "p_id")
    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    @Id
    @Column(name = "a_id")
    public int getaId() {
        return aId;
    }

    public void setaId(int aId) {
        this.aId = aId;
    }

    @Basic
    @Column(name = "att_value")
    public String getAttValue() {
        return attValue;
    }

    public void setAttValue(String attValue) {
        this.attValue = attValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductAttValue that = (ProductAttValue) o;

        if (pId != that.pId) return false;
        if (aId != that.aId) return false;
        if (attValue != null ? !attValue.equals(that.attValue) : that.attValue != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = pId;
        result = 31 * result + aId;
        result = 31 * result + (attValue != null ? attValue.hashCode() : 0);
        return result;
    }
}
