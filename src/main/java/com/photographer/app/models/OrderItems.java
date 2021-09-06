package com.photographer.app.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class OrderItems implements Serializable {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;

    @NotNull
    private int pr_count;

    @Override
    public String toString() {
        return "\nOrderItems{" +
                "id=" + id +
                ", product=" + product +
                ", order=" + order +
                ", pr_count=" + pr_count +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public int getPr_count() {
        return pr_count;
    }

    public void setPr_count(int pr_count) {
        this.pr_count = pr_count;
    }

    public OrderItems() {
    }
}
