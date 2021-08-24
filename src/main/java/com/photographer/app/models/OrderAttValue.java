package com.photographer.app.models;

import javax.persistence.*;

@Entity
@Table(name = "order_att_value", schema = "public", catalog = "site_test")
@IdClass(OrderAttValuePK.class)
public class OrderAttValue {
    private int orderId;
    private int attId;
    private String attValue;

    @Id
    @Column(name = "order_id")
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Id
    @Column(name = "att_id")
    public int getAttId() {
        return attId;
    }

    public void setAttId(int attId) {
        this.attId = attId;
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

        OrderAttValue that = (OrderAttValue) o;

        if (orderId != that.orderId) return false;
        if (attId != that.attId) return false;
        if (attValue != null ? !attValue.equals(that.attValue) : that.attValue != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderId;
        result = 31 * result + attId;
        result = 31 * result + (attValue != null ? attValue.hashCode() : 0);
        return result;
    }
}
