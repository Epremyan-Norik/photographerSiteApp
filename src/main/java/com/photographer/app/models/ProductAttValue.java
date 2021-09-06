package com.photographer.app.models;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class ProductAttValue implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "pr_id")
    private Product product;

    @Id
    @ManyToOne
    @JoinColumn(name = "att_id")
    private ProductAttribute attribute;

    @NotNull
    private String value;

    public ProductAttValue() {
    }

    @Override
    public String toString() {
        return "\nProductAttValue{" +
                "product=" + product +
                ", attribute=" + attribute +
                ", value='" + value + '\'' +
                '}';
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductAttribute getAttribute() {
        return attribute;
    }

    public void setAttribute(ProductAttribute attribute) {
        this.attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
