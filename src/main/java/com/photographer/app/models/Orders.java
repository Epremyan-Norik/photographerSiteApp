package com.photographer.app.models;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
public class Orders {
    private int id;
    private int uId;
    private Timestamp datetime;
    private int statusId;
    private BigInteger amout;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "u_id")
    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    @Basic
    @Column(name = "datetime")
    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    @Basic
    @Column(name = "status_id")
    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    @Basic
    @Column(name = "amout")
    public BigInteger getAmout() {
        return amout;
    }

    public void setAmout(BigInteger amout) {
        this.amout = amout;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Orders orders = (Orders) o;

        if (id != orders.id) return false;
        if (uId != orders.uId) return false;
        if (statusId != orders.statusId) return false;
        if (datetime != null ? !datetime.equals(orders.datetime) : orders.datetime != null) return false;
        if (amout != null ? !amout.equals(orders.amout) : orders.amout != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + uId;
        result = 31 * result + (datetime != null ? datetime.hashCode() : 0);
        result = 31 * result + statusId;
        result = 31 * result + (amout != null ? amout.hashCode() : 0);
        return result;
    }
}
