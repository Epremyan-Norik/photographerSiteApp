package com.photographer.app.models;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class ProductAttValuePK implements Serializable {
    private int pId;
    private int aId;

    @Column(name = "p_id")
    @Id
    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    @Column(name = "a_id")
    @Id
    public int getaId() {
        return aId;
    }

    public void setaId(int aId) {
        this.aId = aId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductAttValuePK that = (ProductAttValuePK) o;

        if (pId != that.pId) return false;
        if (aId != that.aId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = pId;
        result = 31 * result + aId;
        return result;
    }
}
