package com.photographer.app.models;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class UserAttValuePK implements Serializable {
    private int uId;
    private int aId;

    @Column(name = "u_id")
    @Id
    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
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

        UserAttValuePK that = (UserAttValuePK) o;

        if (uId != that.uId) return false;
        if (aId != that.aId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uId;
        result = 31 * result + aId;
        return result;
    }
}
