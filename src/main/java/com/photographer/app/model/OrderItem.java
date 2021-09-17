package com.photographer.app.model;

public class OrderItem {
    private long id;
    private long order_id;
    private String name;
    private long count;
    private double price;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "\nOrderItem{" +
                "id=" + id +
                ", order_id=" + order_id +
                ", name=" + name +
                ", count=" + count +
                ", price=" + price +
                '}';
    }
}