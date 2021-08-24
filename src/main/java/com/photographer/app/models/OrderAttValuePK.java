package com.photographer.app.models;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class OrderAttValuePK implements Serializable {
    private int orderId;
    private int attId;

    @Column(name = "order_id")
    @Id
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Column(name = "att_id")
    @Id
    public int getAttId() {
        return attId;
    }

    public void setAttId(int attId) {
        this.attId = attId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderAttValuePK that = (OrderAttValuePK) o;

        if (orderId != that.orderId) return false;
        if (attId != that.attId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderId;
        result = 31 * result + attId;
        return result;
    }
}
