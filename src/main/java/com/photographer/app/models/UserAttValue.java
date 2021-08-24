package com.photographer.app.models;

import javax.persistence.*;

@Entity
@Table(name = "user_att_value", schema = "public", catalog = "site_test")
@IdClass(UserAttValuePK.class)
public class UserAttValue {
    private int uId;
    private int aId;
    private String value;

    @Id
    @Column(name = "u_id")
    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
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
    @Column(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAttValue that = (UserAttValue) o;

        if (uId != that.uId) return false;
        if (aId != that.aId) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uId;
        result = 31 * result + aId;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
