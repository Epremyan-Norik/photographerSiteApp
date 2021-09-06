package com.photographer.app.models;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Entity
public class UserAttribute implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @NotNull
    private String name;

    @NotNull
    private String discription;

    @Transient
    @OneToMany(mappedBy = "attribute")
    private Set<UserAttValue> allAttValues;

    public UserAttribute() {
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

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    @Override
    public String toString() {
        return "\nUserAttribute{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", discription='" + discription + '\'' +
                '}';
    }
}
