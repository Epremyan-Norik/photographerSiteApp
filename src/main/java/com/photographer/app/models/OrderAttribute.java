package com.photographer.app.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Entity
public class OrderAttribute implements Serializable {

    @Id
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @Transient
    @OneToMany(mappedBy = "attribute")
    private Set<OrderAttValue> ordersWithAtt;

    public OrderAttribute() {

    }

    @Override
    public String toString() {
        return "\nOrderAttribute{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", ordersWithAtt=" + ordersWithAtt +
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

    public Set<OrderAttValue> getOrdersWithAtt() {
        return ordersWithAtt;
    }

    public void setOrdersWithAtt(Set<OrderAttValue> ordersWithAtt) {
        this.ordersWithAtt = ordersWithAtt;
    }
}
