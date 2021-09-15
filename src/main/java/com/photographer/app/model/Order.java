package com.photographer.app.model;

public class Order {
    private long id;
    private long user_id;
    private String order_dt;
    private String status;
    private String total;

    public Order() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getOrder_dt() {
        return order_dt;
    }

    public void setOrder_dt(String order_dt) {
        this.order_dt = order_dt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "\nOrder{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", order_dt='" + order_dt + '\'' +
                ", status='" + status + '\'' +
                ", total='" + total + '\'' +
                '}';
    }
}
