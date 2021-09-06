package com.photographer.app.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class ProductAttribute {
    @Id
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @Transient
    @OneToMany(mappedBy = "attribute")
    private Set<ProductAttValue> allProductWithAtt;

    @Override
    public String toString() {
        return "\nProductAttribute{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", allProductWithAtt=" + allProductWithAtt +
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

    public Set<ProductAttValue> getAllProductWithAtt() {
        return allProductWithAtt;
    }

    public void setAllProductWithAtt(Set<ProductAttValue> allProductWithAtt) {
        this.allProductWithAtt = allProductWithAtt;
    }

    public ProductAttribute() {
    }
}
