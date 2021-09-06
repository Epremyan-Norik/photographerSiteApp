package com.photographer.app.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Entity
public class OrderStatus implements Serializable {

    @Id
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    public OrderStatus() {
    }

    @Transient
    @OneToMany(mappedBy = "status")
    private Set<Orders> ordersWithStatus;

    @Override
    public String toString() {
        return "\nOrderStatus{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", ordersWithStatus=" + ordersWithStatus +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Orders> getOrdersWithStatus() {
        return ordersWithStatus;
    }

    public void setOrdersWithStatus(Set<Orders> ordersWithStatus) {
        this.ordersWithStatus = ordersWithStatus;
    }
}
