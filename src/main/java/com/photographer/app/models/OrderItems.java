package com.photographer.app.models;

import javax.persistence.*;

@Entity
@Table(name = "order_items", schema = "public", catalog = "site_test")
public class OrderItems {
    private int id;
    private int productId;
    private int orderId;
    private int prCount;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "product_id")
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Basic
    @Column(name = "order_id")
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "pr_count")
    public int getPrCount() {
        return prCount;
    }

    public void setPrCount(int prCount) {
        this.prCount = prCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderItems that = (OrderItems) o;

        if (id != that.id) return false;
        if (productId != that.productId) return false;
        if (orderId != that.orderId) return false;
        if (prCount != that.prCount) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + productId;
        result = 31 * result + orderId;
        result = 31 * result + prCount;
        return result;
    }
}
