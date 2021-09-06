package com.photographer.app.models;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Set;

@Entity
public class Orders {

    @Id
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    private Timestamp datetime;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "status_id")
    private  OrderStatus status;

    @NotNull
    private double amount;

    @Transient
    @OneToMany(mappedBy = "order")
    private Set<OrderAttValue> attributesOfOrder;

    @Transient
    @OneToMany(mappedBy = "order")
    private Set<OrderItems> itemsOfOrder;

    public Set<OrderItems> getItemsOfOrder() {
        return itemsOfOrder;
    }

    public void setItemsOfOrder(Set<OrderItems> itemsOfOrder) {
        this.itemsOfOrder = itemsOfOrder;
    }

    public Set<OrderAttValue> getAttributesOfOrder() {
        return attributesOfOrder;
    }

    public void setAttributesOfOrder(Set<OrderAttValue> attributesOfOrder) {
        this.attributesOfOrder = attributesOfOrder;
    }

    @Override
    public String toString() {
        return "\nOrders{" +
                "id=" + id +
                ", user=" + user +
                ", datetime=" + datetime +
                ", status=" + status +
                ", amount=" + amount +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Orders() {
    }
}
