package com.photographer.app.models;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Product implements Serializable {

    @Id
    private Long id;

    @NotNull
    private String name;

    @Transient
    @OneToMany(mappedBy = "product")
    private Set<ProductAttValue> allAttOfProduct;

    @Transient
    @OneToMany(mappedBy = "product")
    private Set<OrderItems> orderWithProduct;

    @Override
    public String toString() {
        return "\nProduct{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", allAttOfProduct=" + allAttOfProduct +
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

    public Set<ProductAttValue> getAllAttOfProduct() {
        return allAttOfProduct;
    }

    public void setAllAttOfProduct(Set<ProductAttValue> allAttOfProduct) {
        this.allAttOfProduct = allAttOfProduct;
    }

    public Product() {
    }
}
