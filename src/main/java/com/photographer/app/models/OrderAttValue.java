package com.photographer.app.models;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class OrderAttValue implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;

    @Id
    @ManyToOne
    @JoinColumn
    private OrderAttribute attribute;

    @NotNull
    private String value;

    @Override
    public String toString() {
        return "\nOrderAttValue{" +
                "order=" + order +
                ", attribute=" + attribute +
                ", value='" + value + '\'' +
                '}';
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public OrderAttribute getAttribute() {
        return attribute;
    }

    public void setAttribute(OrderAttribute attribute) {
        this.attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public OrderAttValue() {
    }
}
