package com.photographer.app.model;

public class OrderItem {
    private long id;
    private long order_id;
    private long pr_id;
    private long count;

    public OrderItem() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public long getPr_id() {
        return pr_id;
    }

    public void setPr_id(long pr_id) {
        this.pr_id = pr_id;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }


    @Override
    public String toString() {
        return "\nOrderItem{" +
                "id=" + id +
                ", order_id=" + order_id +
                ", pr_id=" + pr_id +
                ", count=" + count +
                '}';
    }
}